<template>
    <div>
        <add-flight></add-flight>

        <airline-settings></airline-settings>

        <flight-list></flight-list>
    </div>
</template>

<script>
import axios from 'axios';
import AirlineSettings from './AirlineSettings.vue';
import FlightList from './FlightList.vue';
import AddFlight from './AddFlight.vue';

export default{
    name:'AdminAirline',
    
    props: {},

    components: {
        AddFlight,
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