<template>
    <div>
        
        <v-combobox v-model="carName" :items="this.car_lists_item" label="Select vehicle you want to edit" @change="onChange($event)">
        </v-combobox>

        <v-text-field label="Vehicle name" v-model="car.name" prepend-icon="directions_cars"></v-text-field>
            <v-text-field label="Vehicle brand" v-model="car.brand" prepend-icon="directions_cars" ></v-text-field>
            <v-text-field type="number" label="Manufacture year" v-model="car.manufYear" prepend-icon="build" min="1769" ></v-text-field>
            <v-text-field type="number" label="Number of seats" v-model="car.seatsNumber" prepend-icon="people" min="1"></v-text-field>
            <v-combobox v-model="car.type" :items="types" label="Select vehicle type" prepend-icon="directions_cars">
        </v-combobox>

        <v-btn @click="editCar">
            <v-icon left>edit</v-icon>
            <span>Edit vehicle</span>
        </v-btn>
       
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'EditCar',
    props: [],
    components: {},
    
    data() {
        return {
            rentACarCompany:{
                id: null,
                name: null,
                priceList:[],
                cars:[],
            },
            car:{
                name: null,
                id: null,
                brand: null,
                manufYear: null,
                seatsNumber: null,
                companyDTO: null,
            },
            user:{
                username: null,
                id: null,
            },

            types:[
                "SUV",
                "TRUCK",
                "MINIVAN",
                "SPORT"
            ],

            carName:null,
            car_lists:[],
            car_lists_item:[],
        };
    },

    methods: {
        editCar(){

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
            this.user.username = localStorage.getItem('username');


            //this.car_lists.forEach(element => {
             //   if (element.name === this.carName) {
             //       this.car.id = element.id;
             //       this.car.companyDTO = this.rentACarCompany;
             //   }
            //});

            console.log(this.car);
            axios.post("http://localhost:8080/api/rentACarCompanies/editCar",this.car,header)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Edit failed', {duration:5000});
                    return;
                } else {
                    this.$toasted.success(response.data, {duration:5000});
                    return;
                }
            })
            .catch(error =>{
                    if(error.response.status === 409){
                        console.log(error.response);
                    this.$toasted.error(error.response.data, {duration:5000});
                    return;
                }
               });

            },

        onChange:function(event){
            this.car_lists.forEach(element =>{
                if(element.name == this.carName){
                    this.car = element;
                }
            });
        },
    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.rentACarCompany = response.data;
            console.log(this.rentACarCompany);

        this.car_lists = this.rentACarCompany.cars;
        if(this.car_lists.length != 0){this.carName = this.car_lists[0].name; this.car = this.car_lists[0];}
        this.car_lists.forEach(element =>{
            this.car_lists_item.push(element.name);
        });

        })
        .catch(error => console.log(error));
    },
}
</script>
