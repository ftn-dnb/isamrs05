<template>
    <div>
        
        <v-combobox v-model="carName" :items="this.car_lists_item" label="Select vehicle you want to delete">
        </v-combobox>

        <v-btn @click="deleteCar">
            <v-icon left>delete</v-icon>
            <span>Delete vehicle</span>
        </v-btn>
       
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'DeleteCar',
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
                companyDTO: null,
            },
            user:{
                username: null,
                id: null,
            },

            carName:null,
            car_lists:[],
            car_lists_item:[],
        };
    },

    methods: {
        deleteCar(){

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
            this.user.username = localStorage.getItem('username');


            this.car_lists.forEach(element => {
                if (element.name === this.carName) {
                    this.car.name = element.name;
                    this.car.id = element.id;
                    this.car.companyDTO = this.rentACarCompany;
                }
            });


            axios.post("http://localhost:8080/api/rentACarCompanies/deleteCar",this.car,header)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Delete failed', {duration:5000});
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
    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.rentACarCompany = response.data;
            console.log(this.rentACarCompany);

        this.car_lists = this.rentACarCompany.cars;
        if(this.car_lists.length != 0){this.carName = this.car_lists[0].name;}
        this.car_lists.forEach(element =>{
            this.car_lists_item.push(element.name);
        });

        })
        .catch(error => console.log(error));
    },
}
</script>
