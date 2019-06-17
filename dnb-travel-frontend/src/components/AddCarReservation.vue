<template>
    <div>
        <v-text-field type="number"
            label = "Price per day"
            v-model = "item.pricePerDay"
            prepend-icon="attach_money"
            min="0"
            max="1000"
        ></v-text-field>
        <v-text-field type="number"
            label = "Discount"
            v-model = "item.activeDiscount"
            prepend-icon="attach_money"
            min="0"
            max="1000"
        ></v-text-field>

        <v-menu>
            <v-text-field label="Start date"
                slot="activator"
                :value="formatDate(item.startDate)"
                prepend-icon ="event"></v-text-field>
            <v-date-picker v-model="item.startDate"></v-date-picker>
        </v-menu>

        <v-menu>
            <v-text-field label="End date"
                slot="activator"
                :value="formatDate(item.endDate)"
                prepend-icon ="event"></v-text-field>
            <v-date-picker v-model="item.endDate"></v-date-picker>
        </v-menu>
        <v-combobox v-model="car_name" :items="this.car_items" label="Select car">
        </v-combobox>

        <v-btn @click="add_reservation">
            <v-icon left>done</v-icon>
            <span>Add reservation</span>
        </v-btn>
    </div>
</template>

<script>
import axios from 'axios';
import format from 'date-fns/format';

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

            },

            reservations: [],

            priceListSet:{
                company_id: null,
                price_list_id: null,
            },

            car_name:null,
            car_items:[],

        };
    },

    methods:{
            formatDate(date){
                return date ? format(date,'Do MMM YYYY') : '';
            },

            add_reservation(){
                        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
                        this.user.username = localStorage.getItem('username');

                        this.rentACarCompany.cars.forEach(element=>{
                            if(this.car_name == element.name){
                                this.item.car = element;
                            }
                        });


                        axios.post("http://localhost:8080/api/rentACarCompanies/addReservation",this.item,header)
                        .then(response => {
                            console.log(response.data)
                        })
                        .catch(error => console.log(error.data));



           },
    },

    mounted(){
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.rentACarCompany = response.data;
            console.log(response.data)

           if(this.rentACarCompany.cars != 0){ this.car_name = this.rentACarCompany.cars[0].name;}
           this.rentACarCompany.cars.forEach(element => {
                this.car_items.push(element.name);

           });

        })
        .catch(error => console.log('mucak'));



    },



}

</script>

