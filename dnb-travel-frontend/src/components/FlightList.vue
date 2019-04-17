<template>
    <div>
        <table border="1">
            <tr>
                <th>Start destination</th>
                <th>End destination</th>
                <th>Take-off date & time</th>
                <th>Landing date & time</th>
                <th>Length (km)</th>
                <th>Travel time (h)</th>
                <th>Num. of transits</td>
                <th>Airline</th>
                <th>Rating</th>
                <th>&nbsp;</th>
            </tr>
            <tr v-for="flight in flights">
                <td>{{flight.startDestination.city}}, {{flight.startDestination.country}}</td>
                <td>{{flight.endDestination.city}}, {{flight.endDestination.country}}</td>
                <td>{{flight.startDateTime}}</td>
                <td>{{flight.endDateTime}}</td>
                <td>{{flight.travelLength}}</td>
                <td>{{flight.travelTime}}</td>
                <td>{{flight.transits.length}}</td>
                <td>{{flight.airlineName}}</td>
                <td>{{flight.rating}}</td>
                <td>
                    <router-link :to="{ name: 'FlightDetailedView', params: { flightId: flight.id } }">See details</router-link>
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'FlightList',
    props: {},
    components: {},
    
    data() {
        return {
            airlines: [],
            flights: [],
        };
    },

    methods: {
    },

    mounted() {
        axios.get('http://localhost:8080/api/airlines').then(response => {
            this.airlines = response.data;

            // Copy flight info into new array
            for (let airline of this.airlines) {
                for (let flight of airline.flights) {
                    flight.airlineName = airline.name;
                    this.flights.push(flight);
                }
            }
        });
    }
}
</script>

<style>

</style>
