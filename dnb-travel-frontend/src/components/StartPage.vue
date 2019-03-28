<template>
    <div>
        <h1>Hello from Vue.js</h1>

		<!-- Ove 2 komponente ovde samo stoje zbog testiranja, obrisati kad se zavrsi sa njihovim radom -->
        <user-settings></user-settings>
		<airline-settings></airline-settings>
    </div>
</template>

<script>
import UserSettings from './UserSettings.vue';
import AirlineSettings from './AirlineSettings.vue';

export default {
    name: 'StartPage',
    props: {},
    components: {
		UserSettings,
		AirlineSettings,
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
