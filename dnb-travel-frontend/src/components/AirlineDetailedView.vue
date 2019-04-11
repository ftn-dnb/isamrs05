<template>
    <div>
        <table>
            <tr>
                <td>{{airline.name}}</td>                
            </tr>
            <tr>
                <td>HQ address: {{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}</td>
            </tr>
            <tr>
                <td>Description: {{airline.description}}</td>
            </tr>
            <tr>
                <td>Number of destinations we work on: {{airline.destinations.length}}</td>
            </tr>
            <tr>
                <td>
                    <ul>
                        <li v-for="dest in airline.destinations">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>Number of flights: {{airline.flights.length}}</td>
            </tr>
        </table>        
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'AirlineDetailedView',
    
    props: [
        'airlineId',
    ],

    components: { },

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
        getAirlineData() {
            axios.get('http://localhost:8080/api/airlines/' + this.airlineId).then(response => this.airline = response.data);
        }
    },

    watch: {
        airlineId(newVal, oldVal) {
            this.getAirlineData();
        }
    },

    mounted() {
        this.getAirlineData();
    }
}
</script>

<style scoped>

</style>
