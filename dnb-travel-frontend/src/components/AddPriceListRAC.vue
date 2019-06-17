<template>
    <div>
        <v-form ref = "addForm">
            <v-text-field label="Price list name" v-model="priceList.name" :rules="inputRules"></v-text-field>

            <v-btn @click="addPriceList">
                <v-icon left>done</v-icon>
                <span>Add new price list</span>
            </v-btn>

        </v-form>
    </div>
</template>

<script>
import axios from 'axios';

export default{
    name: 'AddPriceListRAC',
    props: {},
    components: {},

    data(){
        return {
            rentACarCompany:{
                id: null,
                name: null,
            },
            priceList:{
                company_id: null,
                name: null,
            },
            user:{
                username: null,
                id: null,
            },
            inputRules:[
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
        };
    },

    methods:{
        addPriceList(){
            if(!this.$refs.addForm.validate()){
                return;
            }

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

            this.priceList.company_id = this.rentACarCompany.id;

            axios.post("http://localhost:8080/api/rentACarCompanies/addPriceList",this.priceList,header)
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
            this.rentACarCompany = response.data;
            console.log(this.rentACarCompany);
        })
        .catch(error => console.log('mucak'));
    },



}

</script>

