<template>
    <div>
        
        <v-combobox v-model="branchOfficeName" :items="this.office_lists_item" label="Select office you want to edit"  @change="onChange($event)">
        </v-combobox>

        <v-text-field label="Branch office name" v-model="branchOffice.name" prepend-icon="person" ></v-text-field>
        <v-text-field label="Street Name" v-model="branchOffice.address.streetName" prepend-icon="person"></v-text-field>
        <v-text-field label="Street Number" v-model="branchOffice.address.streetNumber" prepend-icon="person"></v-text-field>
        <v-text-field label="City" v-model="branchOffice.address.city" prepend-icon="person"></v-text-field>
        <v-text-field label="Country" v-model="branchOffice.address.country" prepend-icon="person"></v-text-field>
        <v-text-field label="Postal Code" v-model="branchOffice.address.postalCode" prepend-icon="person"></v-text-field>

        <v-btn @click="editBranchOffice">
            <v-icon left>done</v-icon>
            <span>Edit branch office</span>
        </v-btn>
       
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'EditBranchOffice',
    props: [],
    components: {},
    
    data() {
        return {
            rentACarCompany:{
                id: null,
                name: null,
                priceList:[],
            },
            branchOffice:{
                name: null,
                id: null,
                address: {},
                companyDTO: null,
            },
            user:{
                username: null,
                id: null,
            },

            branchOfficeName:null,
            office_lists:[],
            office_lists_item:[],
        };
    },

    methods: {
        editBranchOffice(){

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
            this.user.username = localStorage.getItem('username');


            this.office_lists.forEach(element => {
                if (element.name === this.branchOfficeName) {
                    this.branchOffice.companyDTO = this.rentACarCompany;
                }
            });

            axios.post("http://localhost:8080/api/rentACarCompanies/changeBranchOffice",this.branchOffice,header)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Edit failed', {duration:5000});
                    return;
                } else {
                    this.$toasted.success(response.data, {duration:5000});
                    return;
                };
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
            this.office_lists.forEach(element =>{
                if(element.name == this.branchOfficeName){
                    this.branchOffice = element;
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


        axios.post("http://localhost:8080/api/rentACarCompanies/getAllOffices",this.rentACarCompany,header)
        .then(response =>{

            this.office_lists = response.data;
            if(this.office_lists.length != 0) {this.branchOffice = this.office_lists[0];  this.branchOfficeName = this.branchOffice.name;}
            this.office_lists.forEach(element =>{
                this.office_lists_item.push(element.name);
            });



        })
        .catch(error => console.log(error));


        })
        .catch(error => console.log(error));
    },
}
</script>
