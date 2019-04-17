<template>
    <div>
        <airline-list></airline-list>

        <airline-settings></airline-settings>

        <flight-list></flight-list>
    </div>
</template>

<script>
import axios from 'axios';
import AirlineList from './AirlineList.vue';
import AirlineSettings from './AirlineSettings.vue';
import FlightList from './FlightList.vue'

export default{
    name:'AdminAirline',
    
    props: {},

    components: {
        AirlineList,
        AirlineSettings,
        FlightList
    },

    data(){
        return{
        };
    },

    methods:{ 
    },

    mounted(){
        // @TODO: dobaviti informacije o user-u pa samim tim i ID kompanije za koju je admin
    }
}

</script>

<style>
</style>