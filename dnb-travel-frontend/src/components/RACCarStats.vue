<template>
    <div>

        <h1 class="headline text-md-center">
            {{this.company.name}}<v-rating v-model="this.company.rating" readonly></v-rating>
        </h1>


    <v-layout row wrap>
        <v-flex xs12 sm6 md4 lg3 v-for="item in this.cars">
            <v-card flat class="text-xs-center ma-3">
                <v-card-text>
                    <div>
                        <v-rating v-model="item.rating" readonly></v-rating>
                    </div>
                    <div class="subbheading">
                        {{item.name}},{{item.brand}}
                    </div>
                    <div>
                        <v-icon>people</v-icon>
                        {{item.seatsNumber}}
                        <v-icon>directions_car</v-icon>
                        {{item.type}}
                    </div>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>

    </div>
</template>

<script>
import axios from 'axios';

export default{
    name: 'RACCarStats',
    props: {},
    components: {},

    data(){
        return {
           company:{
            id:null,
            cars:[],
            name:null,
            rating:null,
           },

           incomeSearch:{
            id:null,
            beginDate: null,
            endDate: null,
           },

            user:{
                id: null,
                username: null,
            },

            cars:[],

        };
    },

    methods:{
    },

    mounted(){
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.company = response.data;
            
            this.cars =  this.company.cars;

            console.log(this.cars);
        })
        .catch(error => console.log('mucak'));
    },



}

</script>

