<template>
    <div>
        <h3 class="subheading grey--text">Add New Admin</h3>
        <v-form ref="adminAddForm">
            <v-text-field label="Username" v-model="user.username" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="First Name" v-model="user.firstName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Last Name" v-model="user.lastName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field type="email" label="E-Mail" v-model="user.email" prepend-icon="email" :rules="emailRules"></v-text-field>
            <v-text-field type="password" label="Enter New Password" v-model="user.password" prepend-icon="lock" :rules="inputRules"></v-text-field>
            <v-text-field type="password" label="Repeat New Password" v-model="user.repeatPassword" prepend-icon="lock" :rules="passwordInputRules"></v-text-field>
            <v-container fluid>
                <v-layout wrap>
                    <v-flex xs12>
                        <v-combobox v-model="user.role" :items="roles" label="Select a admin role">
                        </v-combobox>
                    </v-flex>
                </v-layout>
            </v-container>
            
            <v-btn @click="sysadmin_register">
                <v-icon left>person_add</v-icon>
                <span>Add New Admin</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>
import axios from 'axios';


export default{
    name:'AdminSystem',
    props: {},
    components: {},

    data(){
        return{
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            passwordInputRules: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (v && v === this.user.password) || 'Repeated password doesn\'t match'
            ],
            pattern : /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
            emailRules: [
                v => (v && this.pattern.test(v)) || 'Email not valid.'
            ],
            user:{
                id: null,
                username: null,
                password: null,
                repeatPassword: null,
                firstName: null,
                lastName: null,
                email: null,
                role: "ROLE_SYSTEM_ADMIN"
            },
            roles: [
                "ROLE_SYSTEM_ADMIN",
                "ROLE_AIRLINE_ADMIN",
                "ROLE_HOTEL_ADMIN",
                "ROLE_RAC_ADMIN"
            ]
        };
    },

    methods:{ 
        sysadmin_register() {

            if (!this.$refs.adminAddForm.validate()) {
                return;
            }

            //var el = document.getElementById("admin_select");
           //var option_val = el.options[el.selectedIndex].value;
            //if (option_val === "sys_admin") { 
            //    this.user.role = 2;
            //} else if (option_val === "user") {
            //    this.user.role = 1;
            //} else if (option_val === "hotel_admin") {
            //    this.user.role = 4;
            //} else if (option_val === "rac_admin") {
            //    this.user.role = 5;
            //} else if (option_val === "airline_admin") {
            //    this.user.role = 3;
            //}

            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            axios.post('http://localhost:8080/api/users/sysadmin_add', this.user, header)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Error while adding admin.', {duration:5000});
                } else {
                    this.$toasted.success('Admin successfully added.', {duration:5000});
                }
            })
            .catch(error => this.$toasted.error('Error while adding admin.', {duration:5000}));
        }
    },

    mounted(){

    }
}

</script>

<style>
</style>