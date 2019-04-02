<template>
    <div>
        <h1>Hello from Vue.js</h1>

		<!-- Ove 2 komponente ovde samo stoje zbog testiranja, obrisati kad se zavrsi sa njihovim radom -->
        <user-settings></user-settings>
    </div>
</template>

<script>
import UserSettings from './UserSettings.vue';

export default {
    props: {},
    components: {
		UserSettings,
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
