<template>
    <div>

        <ul>
            <li><router-link :to="{ name: 'FlightList' }">Flights list</router-link></li>
            <li><router-link :to="{ name: 'AddFlight' }">Add flight</router-link></li>
            <li><router-link :to="{ name: 'AirlineSettings' }">Airline settings</router-link></li>
        </ul>

    </div>
</template>

<script>
import axios from 'axios';

export default{
    name:'AdminAirline',
    
    props: {},

    components: {
    },

    data(){
        return{
        };
    },

    methods:{ 
    },

    mounted(){
        // @TODO: dobaviti informacije o user-u pa samim tim i ID kompanije za koju je admin
        // @TODO: za adminov prikaz svih letova omoguciti da mu se prikazuju samo letovi njegove kompanije
    }
}

</script>

<style>
</style>