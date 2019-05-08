<template>
    <v-container>
        <v-layout align-center>
            <v-form ref="loginForm">
                <v-text-field type="text" placeholder="username" v-model="user.username" prepend-icon="person" :rules="inputRules" ></v-text-field>
                <v-text-field type="password" placeholder="password" v-model="user.password" prepend-icon="lock" :rules="inputRules"></v-text-field>
                <v-btn @click="login()">
                        Login
                </v-btn>
             </v-form>
        </v-layout>
    </v-container>
</template>

<script>
import axios from 'axios';
import {bus} from '../main';

export default{
    name:'Login',
    props: {},
    components: {},

    data(){
        return{
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            user:{
                username: null,
                password: null,
            },
        };
    },

    methods:{
        login(){
            
            if(!this.$refs.loginForm.validate()){
                return;
            }

            axios.post('http://localhost:8080/auth/login', this.user).then(response =>{
                if(response.data === ''){
                    alert('User does not exist');
                    return;
                }

                this.$toasted.success('Successful login', {duration:5000});
                localStorage.setItem('user-token',response.data.accessToken);
                localStorage.setItem('role', response.data.role);
                localStorage.setItem('username', this.user.username);
                bus.$emit('roleChanged', localStorage.getItem('role'));

                switch(response.data.role){
                	case 'ROLE_USER': this.$router.push({path : '/home'}); break;
                	case 'ROLE_SYSTEM_ADMIN': this.$router.push({path : '/adminSystem'}); break;
                	case 'ROLE_AIRLINE_ADMIN': this.$router.push({path : '/adminAirline'}); break;
                	case 'ROLE_HOTEL_ADMIN':  this.$router.push({path : '/adminHotel'}); break;
                	case 'ROLE_RAC_ADMIN': this.$router.push({path : '/adminRentACar'}); break;
                	default: this.$toasted.error('User not recognized', {duration:5000});
                }
                
                
            })
            .catch(error =>{
            	if(error.response.status === 401){
                    this.$toasted.error(error.response.data, {duration:5000});
            		return;
            	}
            });
        }
    },

    mounted(){

    }
}

</script>

<style>
</style>
