<template>
    <div>
        <h3 class="subheading grey--text"> Rent a car company settings</h3>
        <v-form ref="companyUpdateForm">
            <v-text-field label = "Name" v-model="rentACarCompany.name" prepend-icon="work" :rules="inputRules"></v-text-field>
            <v-textarea label = "Description" v-model="rentACarCompany.description" prepend-icon="info" :rules="inputRules"></v-textarea>
            <v-btn @click="editInfo">
                <v-icon left>done</v-icon>
                <span>Update company</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>
import axios from 'axios';

export default{
    name: 'RentACarCompanySettings',
    props: {},
    components: {},

    data(){
        return {
            inputRules:[
                v =>(v && v.length > 0) || 'Please fill out this field'
            ],
            rentACarCompany: {
                id: null,
                name: null,
                description: null,
            },
        };
    },

    methods:{
        editInfo(){
            const header = {headers : {"Authorization": `Bearer ${localStorage.getItem('user-token')} `} };
            axios.put('http://localhost:8080/api/rentACarCompanies', this.rentACarCompany, header)
            .then(response => this.$toasted.success('Company successfully updated', {duration:5000}))
            .catch(error => this.$toasted.error('Error while updating compnay profile'));
        },
    },

    mounted(){

        if(localStorage.getItem('role') === 'ROLE_RAC_ADMIN'){
        axios.get("http://localhost:8080/api/rentACarCompanies/1",{ headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} })
        .then(response => this.rentACarCompany = response.data);
        console.log(localStorage.getItem('user-token'));
        }
        else{
            this.$router.push({path : '/'});
            alert('Invalid user');
        }

    }



}

</script>

<style>
    
</style>
