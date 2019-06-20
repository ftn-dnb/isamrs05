<template>
    <div>
        <h1 class="heading text-md-center">Welcome {{user.firstName}} {{user.lastName}} !</h1>
        <br />

        <h1 class="heading text-md-center">{{hotel.name}}</h1>
        <h2 class="subheading text-md-center">{{hotel.address.name}} {{hotel.address.street_number}} {{hotel.address.locality}}, {{hotel.address.country}}</h2>

        <h3 class="subheading grey--text text-md-center">
            {{hotel.description}}
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

    </div>
</template>

<script>
import axios from 'axios';

export default{
    name:'AdminHotel',
    
    props: {},

    components: {},

    data(){
        return{
            hotel: {
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
            },
            user: {
                id: null,
                firstName: null,
                lastName: null,
            },
            center: {},
            markers: []
        };
    },

    methods: {
    },

    mounted(){
        const username = localStorage.getItem('username');
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        axios.post('http://localhost:8080/api/hotels/findHotelByAdmin/', username, header)
        .then(response => {
            this.hotel = response.data;
            this.center = {
                lat: this.hotel.address.latitude,
                lng: this.hotel.address.longitude
            },
            this.markers = [
                {
                    position: {
                        lat: this.hotel.address.latitude,
                        lng: this.hotel.address.longitude
                    }
                }
            ]
        })
        .catch(error => this.$toasted.error('Error while loading hotel', {duration:5000}));

        axios.get(`http://localhost:8080/api/users/info/${username}`, header)
        .then(response => this.user = response.data)
        .catch(error => this.$toasted.error('Error while getting data about user', {duration:5000}));
    }
}

</script>

<style>
</style>