<template>
    <div>

        <airline-list></airline-list>

        <add-flight></add-flight>

    </div>
</template>

<script>

import AirlineList from './AirlineList.vue';

import AddFlight from './AddFlight.vue';

export default {
    props: {},
    components: {
        AirlineList,

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
