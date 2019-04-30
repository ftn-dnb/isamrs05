<template>
    <div>
        ADMIN RENT A CAR
    <rent-a-car-company-settings/>
    <user-settings/>
    </div>
</template>

<script>
import axios from 'axios';
import RentACarCompanySettings from './RentACarCompanySettings';
import UserSettings from './UserSettings'


export default{
    name:'AdminRentACar',
    props: {},
    components: {
        RentACarCompanySettings,
        UserSettings,
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
