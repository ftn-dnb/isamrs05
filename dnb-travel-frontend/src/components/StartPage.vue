<template>
    <div>
        <h1>Hello from Vue.js</h1>

        <add-flight></add-flight>

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

import AddFlight from './AddFlight.vue';

export default {
    props: {},
    components: {
        UserSettings,
        HotelSettings,
        HotelRegistration,
        RentACarCompanySettings,

        AddFlight
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
