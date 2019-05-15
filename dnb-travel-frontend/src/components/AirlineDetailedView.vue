<template>
    <div>
        <h1 class="heading text-md-center">{{airline.name}}</h1>
        <h2 class="subheading text-md-center">{{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}</h2>

        <h3 class="subheading grey--text text-md-center">
            {{airline.description}}
        </h3>

        <br /><br />
        <p class="text-md-center">
            Currently we work on {{airline.destinations.length}} destinations and we have {{airline.flights.length}} flights
        </p>

        <div class="text-md-center" v-if="airline.destinations.length != 0">
            <h3 class="subheading grey--text">Destinations where we work</h3>

            <v-layout row wrap>
                <v-flex xs12 sm6 md4 lg3 v-for="dest in airline.destinations" :key="dest.id">
                    <v-card flat class="text-xs-center ma-3">
                        <v-responsive class="pt-4">
                            <v-icon size="40">map</v-icon>
                        </v-responsive>
                        <v-card-text>
                            <div class="subheading">{{dest.city}}, {{dest.country}}</div>
                            <div>
                                {{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}
                            </div>
                            <div class="grey--text">
                                {{dest.airportName}}, {{dest.airportCode}}
                            </div>                        
                        </v-card-text>
                    </v-card>
                </v-flex>
            </v-layout>
        </div>
        <div v-else>
            <p class="text-md-center">Currently we do not work anywhere</p>
        </div>

        <div class="text-md-center" v-if="airline.flights.length != 0">
            <h3 class="subheading grey--text">Our flights</h3>
            <FlightList :airlineId=airline.id></FlightList>
        </div>
        <div v-else>
            <p class="text-md-center">Currently this airline doesn't have any flights</p>
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
         .catch(error => this.$toasted.error('Error while loading data.', {duration:5000}));
    }
}
</script>

<style scoped>

</style>
