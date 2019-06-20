<template>

</template>

<script>

import axios from 'axios';

import FlightList from './FlightList.vue';
import UserSettings from './UserSettings.vue';
import Friends from './Friends.vue';

export default {
    name: 'LoggedInStartPage',
    props: {},
    components: {
        FlightList, 
        UserSettings,
        Friends,
    },

    data() {
        return {
            currentComponent: 'FlightList',
        };
    },

    methods: {
        showComponent(componentName) {
            this.currentComponent = componentName;
        },
    },

    mounted() {
    }
}
</script>

<style scoped>

</style>
