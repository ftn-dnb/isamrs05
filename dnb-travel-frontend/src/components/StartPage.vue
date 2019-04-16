<template>
    <div>
        <h1>Hello from Vue.js</h1>

		<!-- Ove 2 komponente ovde samo stoje zbog testiranja, obrisati kad se zavrsi sa njihovim radom -->
        <login></login>


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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
