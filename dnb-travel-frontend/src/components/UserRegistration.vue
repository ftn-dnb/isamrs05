<template>
    <div>
        <v-form ref="registerForm">
            <v-text-field label="Username" v-model="user.username" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="First name" v-model="user.firstName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Last name" v-model="user.lastName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field type="email" label="E-mail" v-model="user.email" prepend-icon="email" :rules="inputRules"></v-text-field>
            <v-text-field type="password" label="Password" v-model="user.password" prepend-icon="lock" :rules="inputRules"></v-text-field>
            <v-text-field type="password" label="Repeat password" v-model="user.repeatPassword"prepend-icon="lock" :rules="passwordInputRules"></v-text-field>
            
            <v-btn @click="register">
                <v-icon left>done</v-icon>
                <span>Register</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>
import axios from 'axios';


export default{
    name:'UserRegistration',
    props: {},
    components: {},

    data(){
        return{
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            passwordInputRules: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (v && v === this.user.password) || 'Your passwords don\'t match'
            ],
            user:{
                id: null,
                username: null,
                password: null,
                firstName: null,
                lastName: null,
                email: null,

                role: 'ROLE_USER'
            },
        };
    },

    methods:{ 
        register(){
            if (!this.$refs.registerForm.validate()) {
                return;
            }

            axios.post('http://localhost:8080/api/users/register'
            ,this.user)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Registration failed', {duration:5000});
                    return;
                } else {
                    this.$toasted.success('Successful registration', {duration:5000});
                    return;
                }
            }).catch(error =>{
                if(error.response.status === 409){
                console.log(error.response);
                    this.$toasted.error(error.response.data, {duration:5000});
                    return;
                }
            });
        },

    },

    mounted(){

    }
}

</script>

<style>
</style>