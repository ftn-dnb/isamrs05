<template>
    <div>
        <h1 class="heading text-md-center">{{airline.name}}</h1>
        <h2 class="subheading text-md-center">{{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}</h2>

        <h3 class="subheading grey--text text-md-center">
            {{airline.description}}
        </h3>

        <br /><br />
        <div>
            <h3 class="subheading grey--text text-md-center">Tickets for fast reservations</h3>

            <v-list v-if="fastReservations.length != 0">
                <v-list-tile class="my-1" v-for="res in fastReservations">
                    <v-list-tile-avatar>
                        {{res.price}} 
                        <v-icon>euro_symbol</v-icon>
                    </v-list-tile-avatar>

                    <v-list-tile-content>
                        <v-list-tile-title>{{res.startDestination.city}}, {{res.startDestination.country}}, {{res.startDateTime}}</v-list-tile-title>
                        <v-list-tile-sub-title>{{res.endDestination.city}}, {{res.endDestination.country}}, {{res.endDateTime}}</v-list-tile-sub-title>
                    </v-list-tile-content>

                    <v-list-tile-action>
                        <v-dialog width="500" v-model="dialog">
                            <template v-slot:activator="{ on }">
                                <v-btn v-on="on">
                                    <v-icon left>attach_money</v-icon>
                                    <span>Buy</span>
                                </v-btn>
                            </template>

                            <v-card>
                                <v-card-title class="headline grey lighten-2" primary-title>Input passport number</v-card-title>

                                <v-card-text>
                                    <v-text-field v-model="passportNumber" label="Passport number"></v-text-field>
                                </v-card-text>

                                <v-divider></v-divider>

                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn @click="buyFastTicket(res.flightId, res.reservationId)">
                                        <v-icon left>attach_money</v-icon>
                                        <span>Buy</span>
                                    </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-list-tile-action>
                </v-list-tile>
            </v-list>

            <div v-else>
                <p class="text-md-center">Currently we do not have any tickets for fast reservations</p>
            </div>
        </div>

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
            fastReservations: [],
            dialog: false,
            passportNumber: null,
        };
    },

    methods: {
        buyFastTicket(flightId, reservationId) {
            if (this.passportNumber == null) {
                this.$toasted.error('Please enter your passport number', {duration:5000});
                return;
            }

            const reservationInfo = {
                reservationId: reservationId,
                username: localStorage.getItem('username'),
                passport: this.passportNumber
            };

            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            axios.post(`http://localhost:8080/api/flights/${flightId}/reserveFast`, reservationInfo, header)
            .then(response => {
                this.$toasted.success('Ticket successfully reserved', {duration:5000});
                this.getInfo();
            })
            .catch(error => this.$toasted.error('There was an error while buying a ticket', {duration:5000}))

            this.passportNumber = null;
        },

        getFastReservations() {
            this.fastReservations = [];

            this.airline.flights.forEach(flight => {
                flight.reservations.forEach(reservation => {
                    if (reservation.fastReservation) {
                        this.fastReservations.push({
                            flightId: flight.id,
                            reservationId: reservation.id,
                            startDestination: flight.startDestination,
                            endDestination: flight.endDestination,
                            startDateTime: flight.startDateTime,
                            endDateTime: flight.endDateTime,
                            price: reservation.price,
                            row: reservation.seatRow,
                            column: reservation.seatColumn,
                        });
                    }
                });
            });
        },

        getInfo() {
            axios.get('http://localhost:8080/api/airlines/' + this.airlineId)
            .then(response => {
                this.airline = response.data;
                this.getFastReservations();
            })
            .catch(error => this.$toasted.error('Error while loading data.', {duration:5000}));
        },
    },

    mounted() {
         this.getInfo();

        //  axios.get(`http://localhost:8080/api/airlines/${this.airlineId}/fastReservations`)
        //  .then(response => this.fastReservations = response.data)
        //  .catch(error => this.$toasted.error('Error while loading data.', {duration:5000}));
        
    }
}
</script>

<style scoped>

</style>
