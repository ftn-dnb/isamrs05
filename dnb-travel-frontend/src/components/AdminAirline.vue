<template>
    <div>

        <ul>
            <!-- <li><router-link :to="{ name: 'FlightList' }">Flights list</router-link></li> -->
            <!-- <li><router-link :to="{ name: 'AddFlight' }">Add flight</router-link></li>-->
            <!-- <li><router-link :to="{ name: 'AirlineSettings' }">Airline settings</router-link></li>-->

            <li><a href="#" @click="showComponent('AirlineSettings')">Airline settings</a></li>
            <li><a href="#" @click="showComponent('FlightList')">Flight list</a></li>
            <li><a href="#" @click="showComponent('AddFlight')">Add flight</a></li>
            <li><a href="#" @click="showComponent('UserSettings')">Profile settings</a></li>
        </ul>

        <component :is="currentComponent"></component>

    </div>
</template>

<script>
import axios from 'axios';

import AirlineSettings from './AirlineSettings.vue';
import FlightList from './FlightList.vue';
import AddFlight from './AddFlight.vue';
import UserSettings from './UserSettings.vue';

export default{
    name:'AdminAirline',
    
    props: {},

    components: {
        AirlineSettings,
        FlightList,
        AddFlight,
        UserSettings,
    },

    data(){
        return{
            currentComponent: 'AirlineSettings',
        };
    },

    methods: { 
        showComponent(componentName) {
            this.currentComponent = componentName;
        },
    },

    mounted(){
        // @TODO: dobaviti informacije o user-u pa samim tim i ID kompanije za koju je admin
        // @TODO: za adminov prikaz svih letova omoguciti da mu se prikazuju samo letovi njegove kompanije
    }
}

</script>

<style>
</style>