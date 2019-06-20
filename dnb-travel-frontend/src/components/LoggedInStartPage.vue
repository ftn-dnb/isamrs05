<template>

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="option in userDrawer" :key="option.route">
                <v-card flat class="text-xs-center ma-3">
                    <v-responsive class="pt-4">
                        <v-icon size="70">{{option.icon}}</v-icon>
                    </v-responsive>
                    <v-card-actions center>
                        <v-flex class="text-xs-center">
                            <v-btn flat @click="gotoRoute(option.route)">
                                <span>{{option.text}}</span>
                            </v-btn>
                        </v-flex>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>

</template>

<script>

import axios from 'axios';

import FlightList from './FlightList.vue';
import UserSettings from './UserSettings.vue';
import Friends from './Friends.vue';

export default {
    name: 'LoggedInStartPage',
    props: {},
    components: {
        FlightList, 
        UserSettings,
        Friends,
    },

    data() {
        return {
            currentComponent: 'FlightList',
            userDrawer: [
            { icon: 'flight_takeoff', text: 'Flights search', route: '/flights'},
            { icon: 'flight_takeoff', text: 'Airlines', route: '/airlines'},
            { icon: 'directions_car', text: 'Car search', route: '/search-rac'},
            { icon: 'hotel', text: 'Hotel Search', route: '/hotels'},
            { icon: 'people', text:'Friends', route:'/friends'},
            { icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
            { icon: 'delete', text: 'Cancel car reservation', route: '/cancel-car'},
            { icon: 'hotel', text: 'List hotel reservations', route: '/list-hotel-reservation'},
            { icon: 'flight_takeoff', text: 'List my flights', route: '/list-fligt-reservations'},
            ],
        };

        

    },

    methods: {
        showComponent(componentName) {
            this.currentComponent = componentName;
        },

        gotoRoute(path) {
            this.$router.push(path);
        },
    },

    mounted() {
    }
}
</script>

<style scoped>

</style>
