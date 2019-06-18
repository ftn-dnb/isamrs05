<template>
    <div>
        
        <v-text-field label="Branch office name" v-model="branchOffice.name" prepend-icon="person"></v-text-field>
        <v-text-field label="Street Name" v-model="branchOffice.address.streetName" prepend-icon="person"></v-text-field>
        <v-text-field label="Street Number" v-model="branchOffice.address.streetNumber" prepend-icon="person"></v-text-field>
        <v-text-field label="City" v-model="branchOffice.address.city" prepend-icon="person"></v-text-field>
        <v-text-field label="Country" v-model="branchOffice.address.country" prepend-icon="person"></v-text-field>
        <v-text-field label="Postal Code" v-model="branchOffice.address.postalCode" prepend-icon="person"></v-text-field>

        <v-btn @click="add_branch_office">
            <v-icon left>done</v-icon>
            <span>Add new branch office</span>
        </v-btn>
       
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'AddBranchOffice',
    props: [],
    components: {},
    
    data() {
        return {
            rentACarCompany:{
                id: null,
                name: null,
            },
            user:{
                username: null,
                id: null,
            },
            branchOffice:{
                name: null,
                address: {
                    streetName: null,
                    streetNumber: null,
                    city: null,
                    country: null,
                    postalCode: null,
                },
                companyDTO: null,
            }

        };
    },

    methods: {
        add_branch_office(){
            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
            this.user.username = localStorage.getItem('username');

            axios.post("http://localhost:8080/api/rentACarCompanies/addBranchOffice",this.branchOffice,header)
            .then(response => {
            console.log(response.data);

            })
            .catch(error => console.log(error));

        },
    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.rentACarCompany = response.data;

            this.branchOffice.companyDTO = response.data;

        })
        .catch(error => console.log(error));
    },
}
</script>
