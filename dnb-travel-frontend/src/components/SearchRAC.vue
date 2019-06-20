<template>
    <div>
        
        <v-combobox v-model="city_name" :items="this.city_list" label="Filter by city name">
        </v-combobox>

        <v-btn @click="">
            <v-icon left>done</v-icon>
            <span>Filter</span>
        </v-btn>
       
        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="company in this.company_list" :key="company.id">
                <v-card flat class="text-xs-center ma-3" v-if="company.address != null">
                    <v-card-text >
                        <div class="subheading">
                            {{company.name}}, {{company.address.city}}
                        </div>
                        <div class="grey--text">
                            {{company.description}}.<br/>
                            Rating:
                        </div>
                        <div>
                            <v-rating v-model="company.rating" readonly></v-rating>
                        </div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small router :to="{ name: 'RACDetailedView', params: { racID: company.id} }" >
                            <v-icon left>info</v-icon>
                            <span>Company</span>
                        </v-btn>
                        <v-btn flat small router :to="{ name: 'RACFast', params: { racID: company.id} }" >
                            <v-icon left>flash_on</v-icon>
                            <span>Fast</span>
                        </v-btn>
                    </v-card-actions>


                </v-card>
            </v-flex>
        </v-layout>

    </div>
</template>

<script>

import axios from 'axios';
import GChart from 'vue-google-charts';

export default {
    name: 'SearchRAC',
    props: {
    racID:null,
    users:null,
    flight:null,
    }, 
    components: {},
    
    data() {
        return {

            cityName: null,
            rentACarCompany:{
                id: null,
                name: null,
                rating: null,
                address: {},
            },

            company_list:[],
            city_list:[],
            city_name: null,

        };
    },

    methods: {
        setMainBranchOffice(){

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
            this.user.username = localStorage.getItem('username');


            this.office_lists.forEach(element => {
                if (element.name === this.branchOfficeName) {
                    this.branchOffice.name = element.name;
                    this.branchOffice.id = element.id;
                    this.branchOffice.companyDTO = this.rentACarCompany;
                }
            });


            axios.post("http://localhost:8080/api/rentACarCompanies/setMainBranchOffice",this.branchOffice,header)
            .then(response => {
                console.log(response.data);
            })
            .catch(error => console.log('mucak'));

            },
    },

    mounted(){

        axios.get("http://localhost:8080/api/rentACarCompanies/getAllCompanies")
        .then(response =>{

            this.company_list = response.data;

            console.log(this.company_list);
            /*this.company_list.forEach(element=>{
                if(element.address == null){
                    var index = this.company_list.indexOf(element);
                    if(index > -1) {
                        this.company_list.splice(index,1);
                        console.log("op");
                    }
                }
            })*/


            if(this.company_list.length != 0){ this.city_name = this.company_list[0].mainOffice.address.city;}
            this.company_list.forEach(element => {
                this.city_list.push(element.mainOffice.address.city);
            });

        })
        .catch(error => console.log(error));

    },
}
</script>
