<template>
    <div>
        
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'FlightList',
    props: {},
    components: {},
    
    data() {
        return {
            airlines: [],
        };
    },

    methods: {
    },

    mounted() {
        axios.get('http://localhost:8080/api/airlines').then(response => this.airlines = response.data);
    }
}
</script>

<style>

</style>
