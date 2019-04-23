<template>
    <div>
        <table>
            <tr>
                <td>Username: </td><td><input type="text" placeholder="username" v-model="user.username" /></td>
            </tr>
            <tr>
                <td>Password: </td><td><input type="text" placeholder="password" v-model="user.password"/></td>
            </tr>
            <tr>
                <td>Repeat Password: </td><td><input type="text" placeholder="password" v-model="user.repeatPassword"/></td>
            </tr>  
            <tr>
                <td>First Name: </td><td><input type="text" placeholder="first_name" v-model="user.firstName"/></td>
            </tr>    
            <tr>    
                <td>Last Name: </td><td><input type="text" placeholder="last_name" v-model="user.lastName"/></td>
            </tr>    
            <tr>    
                <td>Email: </td>
                <td><input type="text" placeholder="email" v-model="user.email"/></td>
            </tr>
            <tr>
                <th colspan="2"><input type="submit" value="Register" @click="register()"></th>
            </tr>
        </table>
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
            user:{
                id: null,
                username: null,
                password: null,
                firstName: null,
                lastName: null,
                email: null,
                role: '1'
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
            } else if(!this.user.repeatPassword){
                alert('Please re-enter password');
                return false; 
            } else if(!this.user.firstName){
                alert('Please enter first name');
                return false;
            } else if(!this.user.lastName){
                alert('Please enter last name');
                return false;
            } else if(!this.user.email){
                alert('Please enter email'); // proveriti da li je validan podatak
                return false;
            }
            return true;
        },

        register(){
            if(!this.checkForm()){
                return;
            }
            axios.post('http://localhost:8080/api/users/register'
            ,this.user)
            .then(response => {
                if (response.data === '') {
                    alert('Error');
                    return;
                } else {
                    alert('Successful registration');
                    return;
                }
            })
        },

    },

    mounted(){

    }
}

</script>

<style>
</style>