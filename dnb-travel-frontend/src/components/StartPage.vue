<template>
    <div>
        <h1>Hello from Vue.js</h1>

		<!-- Ove 2 komponente ovde samo stoje zbog testiranja, obrisati kad se zavrsi sa njihovim radom -->
        <user-settings></user-settings>
        <hotel-settings></hotel-settings>
        <hotel-registration></hotel-registration>
        <rent-a-car-company-settings></rent-a-car-company-settings>
    </div>
</template>

<script>
import UserSettings from './UserSettings.vue';
import HotelSettings from './HotelSettings.vue';
import HotelRegistration from './HotelRegistration.vue';
import RentACarCompanySettings from './RentACarCompanySettings.vue';

export default {
    props: {},
    components: {
        UserSettings,
        HotelSettings,
        HotelRegistration,
        RentACarCompanySettings,
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
