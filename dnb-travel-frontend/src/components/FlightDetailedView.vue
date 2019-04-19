<template>
    <div>
        
        <table border="1">
            <caption>Flight informations</caption>

            <tr>
                <td>Start destination:</td>
                <td>{{flight.startDestination.city}}, {{flight.startDestination.country}}</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>{{flight.startDestination.airportName}}, {{flight.startDestination.airportCode}}</td>
            </tr>
            <tr>
                <td>End destination:</td>
                <td>{{flight.endDestination.city}}, {{flight.endDestination.country}}</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>{{flight.endDestination.airportName}}, {{flight.endDestination.airportCode}}</td>
            </tr>
            <tr>
                <td>Take-off date & time:</td>
                <td>{{flight.startDateTime}}</td>
            </tr>
            <tr>
                <td>Landing date & time:</td>
                <td>{{flight.endDateTime}}</td>
            </tr>
            <tr>
                <td>Travel time:</td>
                <td>{{getHoursDifference()}} h</td>
            </tr>
            <tr>
                <td>Travel length:</td>
                <td>{{flight.travelLength}} km</td>
            </tr>
            <tr>
                <td>Number of transits:</td>
                <td>{{flight.transits.length}}</td>
            </tr>
            <tr>
                <td>Rating:</td>
                <td>{{'*'.repeat(flight.rating)}}</td>
            </tr>
        </table>

        <br />
        <table v-if="flight.transits.length != 0">
            <caption>Transit destinations</caption>
            
            <tr v-for="transit in flight.transits">
                <td>{{transit.city}}, {{transit.country}}</td>
            </tr>
        </table>

        <br />
        <table border="1">
            <caption>Price list</caption>

            <tr>
                <td>From</td>
                <td>To</td>
                <td>Price</td>
                <td>Discount</td>
            </tr>
            <tr v-for="price in flight.prices">
                <td>{{price.startDate}}</td>
                <td>{{price.endDate}}</td>
                <td>{{price.price}}</td>
                <td>{{price.activeDiscount}}%</td>
            </tr>
        </table>   

        <br /> 
        <table border="1">
            <caption>Airplane informations</caption>

            <tr>
                <td>Name:</td>
                <td>{{flight.airplane.name}}</td>
            </tr>
            <tr>
                <td>Number of seats:</td>
                <td>{{flight.airplane.numOfRows * flight.airplane.numOfColumns}}</td>
            </tr>
            <tr>
                <td>Available seats:</td>
                <td>{{flight.airplane.numOfRows * flight.airplane.numOfColumns - flight.reservations.length}}</td>
            </tr>
        </table>  

        <br />
        <div>
            <v-stage ref="stage" :config="configKonva">
                <v-layer ref="layer">
                    <v-rect v-for="item in seats" :config="item" :key="item.id"></v-rect>
                    <v-text v-for="item in seatsText" :config="item" :key="item.id"></v-text>
                </v-layer>
            </v-stage>
        </div>

    </div>
</template>

<script>
import axios from 'axios';

export default{
    name:'FlightDetailedView',
    
    props: {
        flightId: null,
    },

    components: { },

    data() {
        return{
            flight: {
                id: null,
                startDateTime: null,
                endDateTime: null,
                travelTime: null,
                travelLength: null,
                rating: null,
                startDestination: {
                    city: null,
                    country: null,
                    airportName: null,
                    airportCode: null,
                },
                endDestination: {
                    city: null,
                    country: null,
                    airportName: null,
                    airportCode: null,
                },    
                transits: [],
                airlineId: null,
                airplane: {
                    name: null
                },
                prices: [], 
                reservations: []      
            },

            seats: [],
            seatsText: [],

            configKonva: {
                width: 700,
                height: 700,
            },
        };
    },

    methods: { 
        getHoursDifference() {
            let startDateTime = Date.parse(this.flight.startDateTime);
            let endDateTime = Date.parse(this.flight.endDateTime);
            return Math.abs(endDateTime - startDateTime) / (60 * 60 * 1000);
        },

        configureKonva(rectWidth, marginSize) {
            this.configKonva.width = this.flight.airplane.numOfColumns * (rectWidth + marginSize);
            this.configKonva.height = this.flight.airplane.numOfRows * (rectWidth + marginSize);
        },

        createSeatsView() {
            const rectSize = 35;
            const margin = 5;

            this.configureKonva(rectSize, margin);

            for (let i = 0; i < this.flight.airplane.numOfColumns; ++i) {
                for (let j = 0; j < this.flight.airplane.numOfRows; ++j) {
                    this.seats.push({
                        x: i * (rectSize + margin),
                        y: j * (rectSize + margin),
                        width: rectSize,
                        height: rectSize,
                        fill: 'green',
                        stroke: 'black',
                    });

                    this.seatsText.push({
                        x: i * (rectSize + margin) + 3,
                        y: j * (rectSize + margin) + 12,
                        text: `${j + 1}/${i + 1}`,
                        fontSize: 12,
                        fill: 'white'
                    })
                }
            }

            for (let reservation of this.flight.reservations) {
                this.seats.push({
                    x: (reservation.seatColumn - 1) * (rectSize + margin),
                    y: (reservation.seatRow - 1) * (rectSize + margin),
                    width: rectSize,
                    height: rectSize,
                    fill: 'red',
                    stroke: 'black'
                });
            }
        },
    },

    mounted() {
        axios.get(`http://localhost:8080/api/flights/${this.flightId}`)
        .then(response => {
            this.flight = response.data;
            this.createSeatsView();
        }).catch(error => {
            alert('Error while loading data.');
        });
    }
}

</script>

<style>
</style>