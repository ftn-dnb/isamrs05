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
                    localStorage.removeItem('user-token');
                    alert('Error: Message');
                    return;
                }

                alert('Successful login');
                localStorage.setItem('user-token',response.data.accessToken);
                
            });
        }
    },

    mounted(){

    }
}

</script>

<style>
</style>
