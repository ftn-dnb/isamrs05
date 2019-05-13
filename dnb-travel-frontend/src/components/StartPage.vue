<template>
    <div>
        <login></login>
        <user-registration></user-registration>
        <ul>
            <li><router-link :to="{ name: 'FlightList' }">Flights list</router-link></li>
            <li><router-link :to="{ name: 'AirlineList' }">Airlines list</router-link></li>
        </ul>

    </div>
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
