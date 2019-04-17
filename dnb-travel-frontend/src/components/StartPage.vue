<template>
    <div>
        <login></login>

        <router-link :to="{ name: 'FlightList' }">Flights list</router-link>
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
