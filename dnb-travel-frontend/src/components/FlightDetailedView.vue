<template>
    <div v-if="!flightExists">
        <h2 class="subheading">Sorry, this flight does not exist in our database.</h2>
    </div>
    <div v-else-if="flightExists">
        
        <h1 class="headline text-md-center">
            {{flight.startDestination.city}}, {{flight.startDestination.country}}
            <v-icon>arrow_forward</v-icon>
            {{flight.endDestination.city}}, {{flight.endDestination.country}}
        </h1>
        <h3 class="subheading grey--text text--darken-3 text-md-center">
            {{flight.startDestination.airportName}}, {{flight.startDestination.airportCode}}
            <v-icon>arrow_forward</v-icon>
            {{flight.endDestination.airportName}}, {{flight.endDestination.airportCode}}
        </h3>

        <br />

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>flight_takeoff</v-icon>
            <b>Take-off date & time:</b> {{flight.startDateTime}}
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>flight_land</v-icon>
            <b>Landing date & time:</b> {{flight.endDateTime}}
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>access_time</v-icon>
            <b>Travel time:</b> {{parseInt(getHoursDifference())}} h
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>directions_walk</v-icon>
            <b>Travel length:</b> {{flight.travelLength}} km
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>directions_walk</v-icon>
            <b>Travel length:</b> {{flight.travelLength}} km
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>flight</v-icon>
            <b>Number of transits:</b> {{flight.transits.length}}
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>star</v-icon>
            <b>Rating:</b> {{flight.rating}} / 5
        </h3>

        <br />
        <div v-if="flight.transits.length != 0">
            <h1 class="headline text-md-center">Transit destinations</h1>
            
            <v-list>
                <v-list-tile v-for="transit in flight.transits">
                    <v-list-tile-content>
                        <v-list-tile-title>{{transit.city}}, {{transit.country}}</v-list-tile-title>
                        <v-list-tile-sub-title>{{transit.airportName}}, {{transit.airportCode}}</v-list-tile-sub-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </div>

        <br />
        <h1 class="headline text-md-center">Price list</h1>

        <v-data-table :headers="priceListTableHeaders" :items="flight.prices">
            <template v-slot:items="props">
                <td>{{ props.item.startDate }}</td>
                <td>{{ props.item.endDate }}</td>
                <td>{{ props.item.price }}</td>
                <td>{{ props.item.activeDiscount }}</td>
            </template>
        </v-data-table>

        <v-btn v-if="userLoggedIn && canBuy(flight.startDateTime)" router :to="{ name: 'FlightReservation', params: { flightId: flight.id} }">
            <v-icon left>attach_money</v-icon>
            <span>Buy ticket</span>
        </v-btn>

        <br />
        <h1 class="headline text-md-center">Airplane information</h1>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>flight</v-icon>
            <b>Airplane model:</b> {{flight.airplane.name}}
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>airline_seat_recline_extra</v-icon>
            <b>Number of seats:</b> {{flight.airplane.numOfRows * flight.airplane.numOfColumns}}
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>airline_seat_recline_extra</v-icon>
            <b>Available seats:</b> {{flight.airplane.numOfRows * flight.airplane.numOfColumns - flight.reservations.length}}
        </h3>

        <br />
        <h3 class="subheading">Seats configuration</h3>

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
            userLoggedIn: false,
            priceListTableHeaders: [
                { text: 'From', value: 'startDate' },
                { text: 'To', value: 'endDate' },
                { text: 'Price $', value: 'price' },
                { text: 'Discount %', value: 'activeDiscount' }
            ],
            flightExists: true,
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
        canBuy(startDateTime) {
            let now = new Date();
            let flightDate = new Date(startDateTime);
            return flightDate.getTime() >= now.getTime();
        },

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
            this.flightExists = true;
            this.createSeatsView();
        }).catch(error => {
            this.flightExists = false;
        });

        this.userLoggedIn = localStorage.getItem("role") === "ROLE_USER";
    }
}

</script>

<style>
</style>