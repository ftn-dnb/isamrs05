<template>
    <div>
        <v-text-field type="number"
            label = "Max price"
            v-model = "item.pricePerDay"
            prepend-icon="attach_money"
            min="0"
            max="1000"
        ></v-text-field>
    </div>
</template>

<script>
import axios from 'axios';

export default{
    name: 'AddCarReservation',
    props: {},
    components: {},

    data(){
        return {
            rentACarCompany:{
                id:null,
                name:null,
                cars:[],
            },
            item:{
                startDate: null,
                endDate: null,
                pricePerDay: null,
                car: null,
                activeDiscount: null,
            },

            user:{
                username: null,

            }

            reservations: [],

            priceListSet:{
                company_id: null,
                price_list_id: null,
            },

        };
    },

    methods:{

    },

    mounted(){
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.rentACarCompany = response.data;
            console.log(response.data)
        })
        .catch(error => console.log('mucak'));



    },



}

</script>

