<template>
    <div>
        <h3>List of all airlines</h3>

        <table border=1>
            <tr>
                <th>Company name</th>
                <th>HQ Address</th>
                <th>Description</th>
                <th>&nbsp;</th>
            </tr>
            <tr v-for="airline in airlines">
                <td>{{airline.name}}</td>
                <td>{{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}</td>
                <td>{{airline.description}}</td>
                <td>
                    <router-link :to="{ name: 'AirlineDetailedView', params: { airlineId: airline.id } }">See details</router-link>
                </td>
            </tr>
        </table>
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
    },

    mounted() {
        axios.get('http://localhost:8080/api/airlines').then(response => this.airlines = response.data);
    }
}
</script>

<style scoped>

</style>
