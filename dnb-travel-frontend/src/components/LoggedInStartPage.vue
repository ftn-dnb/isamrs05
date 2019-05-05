<template>
    <div>
        <ul>
            <li><a href="#" @click="showComponent('FlightList')">Flight list</a></li>
            <li><a href="#" @click="showComponent('Friends')">Friends</a></li>
            <li><a href="#" @click="showComponent('UserSettings')">Profile settings</a></li>
        </ul>

        <component :is="currentComponent"></component>
    </div>
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
