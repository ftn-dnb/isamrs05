<template>
    <div>
        
        <v-combobox v-model="branchOfficeName" :items="this.office_lists_item" label="Select main office">
        </v-combobox>

        <v-btn @click="setMainBranchOffice">
            <v-icon left>done</v-icon>
            <span>Set active price list</span>
        </v-btn>
       
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'SetMainBranchOffice',
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
