<template>
    <div>
        
        <v-combobox v-model="branchOfficeName" :items="this.office_lists_item" label="Select office you want to delete">
        </v-combobox>

        <v-btn @click="deleteBranchOffice">
            <v-icon left>delete</v-icon>
            <span>Delete branch office</span>
        </v-btn>
       
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'DeleteBranchOffice',
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
        deleteBranchOffice(){

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
            this.user.username = localStorage.getItem('username');


            this.office_lists.forEach(element => {
                if (element.name === this.branchOfficeName) {
                    this.branchOffice.name = element.name;
                    this.branchOffice.id = element.id;
                    this.branchOffice.companyDTO = this.rentACarCompany;
                }
            });


            axios.post("http://localhost:8080/api/rentACarCompanies/deleteBranchOffice",this.branchOffice,header)
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


        axios.post("http://localhost:8080/api/rentACarCompanies/getAllOffices",this.rentACarCompany,header)
        .then(response =>{

            this.office_lists = response.data;
            if(this.office_lists.length != 0) { this.branchOfficeName = this.office_lists[0].name;}
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
