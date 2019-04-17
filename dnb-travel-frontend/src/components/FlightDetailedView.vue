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
                <td></td>
            </tr>
        </table>     

        <br />
        <table border="1">
            <caption>Price list</caption>

            <tr v-for="price in flight.prices">
            </tr>
        </table>   
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
                startDestination: null,
                endDestination: null,            
                transits: null,
                airlineId: null,
                airplane: null,
                prices: null,            
            },
        };
    },

    methods: { 
        getHoursDifference() {
            let startDateTime = Date.parse(this.flight.startDateTime);
            let endDateTime = Date.parse(this.flight.endDateTime);
            return Math.abs(endDateTime - startDateTime) / (60 * 60 * 1000);
        },
    },

    mounted() {
        axios.get(`http://localhost:8080/api/flights/${this.flightId}`)
        .then(response => {this.flight = response.data; console.log(this.flight)});
    }
}

</script>

<style>
</style>