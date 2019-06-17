<template>
    <div>
        <h1 class="headline text-md-center">
            Hotel search
        </h1>
        <v-container grid-list-md text-xs-center>
            <v-layout row wrap>
                <v-flex xs4>
                    <v-combobox label="Select hotel address" 
                        v-model="searchFilter.address" 
                        :items="addresses" 
                        item-text="city" 
                        return-object
                        prepend-icon="location_on"
                    ></v-combobox>

                    <v-slider
                        label="Minimal rating"
                        v-model="searchFilter.rating"
                        thumb-label="always"
                        min="1"
                        max="5"
                    ></v-slider>

                    <v-btn small @click="searchHotels">
                        <v-icon left>search</v-icon>
                        <span>Search</span>
                    </v-btn>

                    <v-btn small @click="clearSearch">
                        <v-icon left>clear</v-icon>
                        <span>Clear</span>
                    </v-btn>
                </v-flex>
            </v-layout>   
        </v-container>
        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="hotel in this.hotels" :key="hotel.id">
                <v-card flat class="text-xs-center ma-3">
                    <v-card-text>
                        <div class="subheading">
                            {{hotel.name}}, {{hotel.address.city}}
                        </div>
                        <div class="grey--text">
                            {{hotel.description}}.<br/>
                            Rating:
                        </div>
                        <div>
                            <v-rating v-model="hotel.rating" readonly></v-rating>
                        </div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small router :to="{ name: 'HotelDetailedView', params: { hotelID: hotel.id, flight_res: flight, users: users} }">
                            <v-icon left>info</v-icon>
                            <span>View Hotel Profile</span>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'HotelSearch',
    props: {
        flight: null,
        users: null,
    },
    components: {},

    data() {
        return {
            userLoggedIn: null,
            hotels: [],
            addresses: [],
            searchFilter: {
                address: null,
                rating: null,
            }
        }
    },

    methods: {
        searchHotels() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            axios.post('http://localhost:8080/api/hotels/searchHotels', this.searchFilter, header)
            .then(response => this.hotels = response.data)
            .catch(error => this.$toasted.error('Error while searching hotels.', {duration: 5000}));
        },
        clearSearch() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            axios.get('http://localhost:8080/api/hotels/all', header)
            .then(response => this.hotels = response.data)
            .catch(error => this.$toasted.error('Error while searching hotels.', {duration: 5000}));

            this.searchFilter.address = "";
            this.searchFilter.rating = 0;
        }
    },

    mounted() {
        console.log(this.users);

        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

        axios.get('http://localhost:8080/api/hotels/all', header)
        .then(response => this.hotels = response.data)
        .catch(error => this.$toasted.error('Error while loading hotels.', {duration: 5000}));
    
        axios.get('http://localhost:8080/api/hotels/addresses', header)
        .then(response => this.addresses = response.data)
        .catch(error => this.$toasted.error('Error while loading addresses.', {duration: 5000}));

        this.userLoggedIn = localStorage.getItem("role") === "ROLE_USER";
    }

    
}
</script>
