<template>
    <div>
        <h3 class="subheading grey--text">List of all airlines</h3>

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="airline in airlines" :key="airline.id">
                <v-card flat class="text-xs-center ma-3">
                    <v-responsive class="pt-4">
                        <v-icon size="70">flight</v-icon>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{airline.name}}</div>
                        <div>
                            {{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}
                        </div>
                        <div class="grey--text">
                            {{airline.description}}
                        </div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small @click="seeDetails(airline.id)">
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
import AirlineDetailedView from './AirlineDetailedView.vue';

export default {
    name: 'AirlineList',
    props: {},
    components: {},

    data() {
        return {
            airlines: [],
        };
    },

    methods: {
        seeDetails(airlineId) {
            this.$router.push({ name: 'AirlineDetailedView', params: { airlineId: airlineId } });
        },
    },

    mounted() {
        axios.get('http://localhost:8080/api/airlines')
        .then(response => this.airlines = response.data)
        .catch(error => this.$toasted.error('There was an error while getting information about airlines', {duration: 5000}));
    }
}
</script>

<style scoped>

</style>
