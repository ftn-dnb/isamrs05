<template>
    <div>
        <v-container grid-list-md text-xs-center>
            <v-layout row wrap>
                <v-flex xs4>
                    <v-combobox label="Select start destination" 
                        v-model="filterSearch.startDestination" 
                        :items="destinations" 
                        item-text="airportName" 
                        return-object
                        prepend-icon="flight_takeoff"
                    ></v-combobox>

                    <v-menu>
                        <v-text-field label="Departure date" 
                            slot="activator" 
                            :value="formattedDate(filterSearch.startDate)"
                            prepend-icon="event"
                        ></v-text-field>
                        <v-date-picker v-model="filterSearch.startDate">
                        </v-date-picker>
                    </v-menu>

                    <v-slider
                        label="Travel time (h)"
                        v-model="filterSearch.travelTime"
                        thumb-label="always"
                        :thumb-size="24"
                        min="0"
                        max="50"
                    ></v-slider>

                    <v-checkbox
                        label="Direct flight"
                        v-model="filterSearch.isDirect"
                    ></v-checkbox>

                    <v-text-field type="number"
                        label="Min. price"
                        min="0"
                        v-model="filterSearch.minPrice"
                        prepend-icon="attach_money"
                    ></v-text-field>

                    <v-text-field type="number"
                        label="Number of people"
                        v-model="filterSearch.numOfPeople"
                        min="0"
                        prepend-icon="people"
                    ></v-text-field>

                </v-flex>
                <v-flex xs4>
                    <v-combobox label="Select end destination" 
                        v-model="filterSearch.endDestination" 
                        :items="destinations" 
                        item-text="airportName" 
                        return-object
                        prepend-icon="flight_land"
                    ></v-combobox>

                    <v-menu>
                        <v-text-field label="Return date" 
                            slot="activator" 
                            :value="formattedDate(filterSearch.endDate)"
                            prepend-icon="event"
                        ></v-text-field>
                        <v-date-picker v-model="filterSearch.endDate">
                        </v-date-picker>
                    </v-menu>

                    <v-slider
                        label="Travel length (km)"
                        v-model="filterSearch.travelLength"
                        thumb-label="always"
                        min="0"
                        max="20000"
                    ></v-slider>

                    <v-checkbox
                        label="One way"
                        v-model="filterSearch.isOneWay"
                    ></v-checkbox>

                    <v-text-field type="number"
                        label="Max. price"
                        min="0"
                        v-model="filterSearch.maxPrice"
                        prepend-icon="attach_money"
                    ></v-text-field>

                    <v-combobox label="Select airline" 
                        v-model="airline" 
                        :items="airlines" 
                        item-text="name" 
                        prepend-icon="flight"
                    ></v-combobox>

                    <v-btn small @click="searchFlights">
                        <v-icon left>search</v-icon>
                        <span>Search</span>
                    </v-btn>
                </v-flex>
            </v-layout>   
        </v-container>

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="flight in flights" :key="flight.id">
                <v-card flat class="text-xs-center ma-3">
                    <v-card-text>
                        <div class="subheading">
                            {{flight.startDestination.city}}, {{flight.startDestination.country}}
                        </div>
                        <div class="grey--text">
                            {{flight.startDestination.airportName}}
                        </div>
                        <div>
                            <v-icon>flight_takeoff</v-icon>
                            {{formattedDate(flight.startDateTime)}}
                        </div>

                        <v-icon>expand_more</v-icon>
                        
                        <div class="subheading">
                            {{flight.endDestination.city}}, {{flight.endDestination.country}}
                        </div>
                        <div class="grey--text">
                            {{flight.endDestination.airportName}}
                        </div>
                        
                        <div>
                            <v-icon>flight_land</v-icon>
                            {{formattedDate(flight.endDateTime)}}
                        </div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small router :to="{ name: 'FlightDetailedView', params: { flightId: flight.id} }">
                            <v-icon left>info</v-icon>
                            <span>See details</span>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>

import axios from 'axios';
import format from 'date-fns/format';

export default {
    name: 'FlightList',
    props: {
        airlineId: null
    },

    components: {},
    
    data() {
        return {
            minDate: null,
            airlines: [],
            flights: [],
            destinations: [],
            airline: null,
            filterSearch: {
                airlineId: null,
                startDestination: null,
                endDestination: null,
                startDate: null,
                endDate: null,
                travelTime: null,
                travelLength: null,
                minPrice: null,
                maxPrice: null,
                isDirect: null,
                isOneWay: null,
                numOfPeople: null,
            },
        };
    },

    methods: {
        searchFlights() {
            this.filterSearch.airlineId = this.airline.id;

            axios.post('http://localhost:8080/api/flights', this.filterSearch)
            .then(response => {this.flights = response.data; })
            .catch(error => this.$toasted('Error while loading flights', {duration:5000}));
        },

        formattedDate(date) {
            return date ? format(date, 'Do MMM YYYY') : '';
        },
    },

    mounted() {
        if (this.airlineId == null) {
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
        } else {
            axios.get(`http://localhost:8080/api/airlines/${this.airlineId}/flights`)
            .then(response => this.flights = response.data)
        }

        axios.get('http://localhost:8080/api/destinations')
        .then(response => this.destinations = response.data)
        .catch(error => this.$toasted.error('Error while loading destinations', {duration:5000}));

        let now = new Date();
        const today = now.toISOString().substring(0,10);
        this.searchStartDate = this.searchEndDate = today;

        this.filterSearch.airlineId = this.airlineId;
    }
}
</script>

<style>

</style>
