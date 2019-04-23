<template>
    <div>
        <login></login>

        <ul>
            <li><router-link :to="{ name: 'FlightList' }">Flights list</router-link></li>
            <li><router-link :to="{ name: 'AirlineList' }">Airlines list</router-link></li>
        </ul>
    </div>
</template>

<script>

import Login from './Login.vue';

export default {
    props: {},
    components: {
        Login,
    },

    data() {
        return {

        };
    },

    methods: {

    },

    mounted() {
    }
}
</script>

<style scoped>

</style>
