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
            filter:{
                "startDate": null,
                "endDate": null,
                "pricePerDay": null,
            },
            items: [],
        };
    },

    methods:{ 
    },

    mounted(){
       axios.post('http://localhost:8080/api/rentACarCompanies/carSearch', this.filter)
            .then(response => {
                this.items = response.data;
                cosole.log(this.items) });
    }
}

</script>

<style>
</style>
