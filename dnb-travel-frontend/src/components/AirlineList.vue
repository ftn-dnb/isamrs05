<template>
    <div>
        <h3>List of all airlines</h3>

        <table border=1>
            <tr>
                <th>Company name</th>
                <th>HQ Address</th>
                <th>Description</th>
            </tr>
            <tr v-for="airline in airlines" @click="showDetailedView(airline.id)">
                <td>{{airline.name}}</td>
                <td>{{airline.address.streetName}} {{airline.address.streetNumber}} {{airline.address.city}}, {{airline.address.country}}</td>
                <td>{{airline.description}}</td>
            </tr>
        </table>

        <component :is="currentComponent" v-bind:airlineId="airlineIdToShow"></component>
    </div>
</template>

<script>

import axios from 'axios';
import AirlineDetailedView from './AirlineDetailedView.vue';

export default {
    name: 'AirlineList',
    props: {},
    components: {
        AirlineDetailedView
    },

    data() {
        return {
            currentComponent: '',
            airlineIdToShow: null,
            airlines: [],
        };
    },

    methods: {
        showDetailedView(airlineId) {
            this.airlineIdToShow = airlineId;
            this.currentComponent = 'AirlineDetailedView';
        }
    },

    mounted() {
        axios.get('http://localhost:8080/api/airlines').then(response => this.airlines = response.data);
    }
}
</script>

<style scoped>

</style>
