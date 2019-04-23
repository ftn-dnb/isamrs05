<template>
    <div>
        ADMIN RENT A CAR
    <rent-a-car-company-settings/>
    </div>
</template>

<script>
import axios from 'axios';
import RentACarCompanySettings from './RentACarCompanySettings';


export default{
    name:'AdminRentACar',
    props: {},
    components: {
        RentACarCompanySettings,
    },

    data(){
        return{
        };
    },

    methods:{ 
    },

    mounted(){

    }
}

</script>

<style>
</style>
