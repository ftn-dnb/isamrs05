<template>
    <div>
        <h1 class="heading text-md-center">Welcome {{user.firstName}} {{user.lastName}} !</h1>
        <br />

        <h1 class="heading text-md-center">{{airline.name}}</h1>
        <h2 class="subheading text-md-center">{{airline.address.name}} {{airline.address.street_number}} {{airline.address.locality}}, {{airline.address.country}}</h2>

        <h3 class="subheading grey--text text-md-center">
            {{airline.description}}
        </h3>

        <br />
        <v-layout align-center justify-center row fill-height>
            <GmapMap
                :center="center"
                :zoom="7"
                map-type-id="terrain"
                style="width: 500px; height: 300px"
                >
                <GmapMarker
                    :key="index"
                    v-for="(m, index) in markers"
                    :position="m.position"
                    :clickable="true"
                    :draggable="true"
                     @click="center=m.position"
                />
            </GmapMap>
        </v-layout>
        

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="option in adminOptions" :key="option.route">
                <v-card flat class="text-xs-center ma-3">
                    <v-responsive class="pt-4">
                        <v-icon size="70">{{option.icon}}</v-icon>
                    </v-responsive>
                    <v-card-text>
                        {{option.description}}
                    </v-card-text>
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

    </div>
</template>

<script>
import axios from 'axios';

export default{
    name:'AdminAirline',
    
    props: {},

    components: {},

    data(){
        return{
            airline: {
                id: null,
                name: null,
                address: {
                    place_id: null,
                    country: null,
                    locality: null,
                    name: null,
                    postal_code: null,
                    latitude: null,
                    longitude: null,
                },
                description: null,
                destinations: [],
                flights: [],
            },
            user: {
                id: null,
                firstName: null,
                lastName: null,
            },
            adminOptions: [
				{ icon: 'flight_takeoff', text: 'All flights', description:'See all flights', route: '/flights' },
				{ icon: 'airplanemode_active', text: 'Add flight', description:'Add new flight', route: '/add-flight' },
				{ icon: 'map', text: 'Add destination', description:'Add new destination where company works', route: '/add-company-destination' },
				{ icon: 'fast_forward', text: 'Fast reservations', description:'Add new seats for fast reservations', route: '/fast-reservation-seats' },
				{ icon: 'assessment', text: 'Statistics', description:'See company statistics', route: '/airline-statistics' },
				{ icon: 'work_outline', text: 'Company settings', description:'Change company information', route: '/airline-settings' },
				{ icon: 'perm_identity', text: 'Profile settings', description:'Change your profile settings', route: '/user-settings' },
            ],
            center: {},
            markers: []
        };
    },

    methods: {
        gotoRoute(path) {
            this.$router.push(path);
        },
    },

    mounted(){
        const username = localStorage.getItem('username');
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        axios.post(`http://localhost:8080/api/airlines/company/${username}`, {}, header)
        .then(response => {
            this.airline = response.data;
            this.center = {
                lat: this.airline.address.latitude,
                lng: this.airline.address.longitude
            },
            this.markers = [
                {
                    position: {
                        lat: this.airline.address.latitude,
                        lng: this.airline.address.longitude
                    }
                }
            ]
        })
        .catch(error => this.$toasted.error('Error while loading airline company', {duration:5000}));

        axios.get(`http://localhost:8080/api/users/info/${username}`, header)
        .then(response => this.user = response.data)
        .catch(error => this.$toasted.error('Error while getting data about user', {duration:5000}));
    }
}

</script>

<style>
</style>