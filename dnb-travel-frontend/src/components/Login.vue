<template>
    <div>
        <h3>Login</h3>
        <table>
            <tr>
                 <td><input type="text" placeholder="username" v-model="user.username" /></td>
                 <td><input type="text" placeholder="password" v-model="user.password"/></td>
            </tr>
            <tr>
                <th colspan="2"><input type="submit" value="Log in" @click="login()"></th>
            </tr>
        </table>
    </div>
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
            user:{
                username: null,
                password: null,
            },
        };
    },

    methods:{
        checkForm(){
            if(!this.user.username){
                alert('Please enter username');
                return false;
            } else if(!this.user.password){
                alert('Please enter password');
                return false;
            }
            return true;
        },

        login(){
            
            if(!this.checkForm()){
                return;
            }

            axios.post('http://localhost:8080/auth/login', this.user).then(response =>{
                if(response.data === ''){
                    alert('User does not exist');
                    return;
                }

                alert('Successful login');
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
                	default: alert('User not recognized');
                }
                
                
            })
            .catch(error =>{
            	if(error.response.status === 401){
            	console.log(error.response);
            		alert(error.response.data);
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
