<template>
    <div>
        <v-form ref = "addForm">
            <v-text-field label="Vehicle name" v-model="car.name" prepend-icon="directions_cars" :rules="inputRules"></v-text-field>
            <v-text-field label="Vehicle brand" v-model="car.brand" prepend-icon="directions_cars" :rules="inputRules"></v-text-field>
            <v-text-field type="number" label="Manufacture year" v-model="car.manufYear" prepend-icon="build" min="1769" :rules="manufYearRule"></v-text-field>
            <v-text-field type="number" label="Number of seats" v-model="car.seatsNumber" prepend-icon="people" min="1"></v-text-field>
            <v-combobox v-model="car.type" :items="types" label="Select vehicle type" prepend-icon="directions_cars">
            </v-combobox>

            <v-btn @click="addVehicle">
                <v-icon left>done</v-icon>
                <span>Add vehicle</span>
            </v-btn>

        </v-form>
    </div>
</template>

<script>
import axios from 'axios';

export default{
    name: 'AddCar',
    props: {},
    components: {},

    data(){
        return {
            car:{
                name: null,
                brand: null,
                manufYear: null,
                seatsNumber: null,
                type: null,
                comapny:{
                    id: null,
                }
            },
            types:[
                "SUV",
                "TRUCK",
                "MINIVAN",
                "SPORT"
            ],
            user:{
                username: null,
                id: null,
            },
            inputRules:[
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            manufYearRule:[
                v => (v >= 1769) || 'Please check input year'
            ],
        };
    },

    methods:{
        addVehicle(){
            if(!this.$refs.addForm.validate()){
                return;
            }

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

            axios.post("http://localhost:8080/api/rentACarCompanies/addCar",this.car,header)
            .then(response =>{
                console.log(response.data);
            });
        },
    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.car.company = response.data;
            console.log(this.car.company);
        })
        .catch(error => console.log('mucak'));
    },



}

</script>

<style>
    
</style>
