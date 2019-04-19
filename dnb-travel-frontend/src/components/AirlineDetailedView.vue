<template>
    <div>
        <table border="1">
            <caption>Informations about company</caption>
            <tr>
                <td colspan="2">{{airline.name}}</td>                
            </tr>
            <tr>
                <td>HQ address:</td>
                <td>{{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}</td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>{{airline.description}}</td>
            </tr>
            <tr>
                <td>Number of destinations we work on:</td>
                <td>{{airline.destinations.length}}</td>
            </tr>
            <tr>
                <td>Number of available flights:</td>
                <td>{{airline.flights.length}}</td>
            </tr>
        </table>        

        <br />
        <div v-if="airline.destinations.length != 0">
            <table border="1">
                <caption>Destinations where we work:</caption>
                <tr>
                    <th>City</th>
                    <th>Country</th>
                    <th>Airport name</th>
                    <th>Airport code</th>
                </tr>
                <tr v-for="dest in airline.destinations">
                    <td>{{dest.city}}</td>
                    <td>{{dest.country}}</td>
                    <td>{{dest.airportName}}</td>
                    <td>{{dest.airportCode}}</td>
                </tr>
            </table>
        </div>
        <div v-else>
            <p>Currently we do not work anywhere</p>
        </div>

        <div v-if="airline.flights.length != 0">
            <FlightList :airlineId=airline.id></FlightList>
        </div>
        <div v-else>
            <p>Currently this airline doesn't have any flights</p>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import FlightList from './FlightList.vue';

export default {
    name: 'AirlineDetailedView',
    
    props: [
        'airlineId',
    ],

    components: {
        FlightList
    },

    data() {
        return {
            airline: {
                id: null,
                name: null,
                address: {
                    id: null,
                    streetName: null,
                    streetNumber: null,
                    city: null,
                    country: null,
                    postalCode: null
                },
                description: null,
                destinations: [],
                flights: [],
            },
        };
    },

    methods: {

    },

    mounted() {
         axios.get('http://localhost:8080/api/airlines/' + this.airlineId)
         .then(response => this.airline = response.data)
         .catch(error => alert('Error while loading data.'));
    }
}
</script>

<style scoped>

</style>
