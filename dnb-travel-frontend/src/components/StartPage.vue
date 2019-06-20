<template>
            <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="option in defaultUser" :key="option.route">
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

import Login from './Login.vue';
import UserRegistration from './UserRegistration'

export default {
    props: {},
    components: {
        Login,
        UserRegistration,
    },

    data() {
        return {
        defaultUser: [
            {icon: 'flight_takeoff', text: 'Airlines', route: '/airlines'},
            {icon: 'flight_takeoff', text: 'Flights search', route:'/flights'},
            {icon: 'directions_car', text: 'Car search', route: '/search-rac'},
            {icon: 'hotel', text: 'Hotel Search', route: '/hotels'}
            ],
        };
    },

    methods: {
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
