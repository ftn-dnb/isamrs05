<template>
    <div>
        ADMIN SYSTEM
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
                <td>Email: </td><td><input type="text" placeholder="email" v-model="user.email"/></td>
            </tr>
            <tr>    
                <td>Role: </td>
                <td>
                    <select id="admin_select">
                        <option value="sys_admin">System Administrator</option>
                        <option value="user">User</option>
                        <option value="hotel_admin">Hotel Admin</option>
                        <option value="rac_admin">Rent-A-Car Admin</option>
                        <option value="airline_admin">Airline Admin</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th colspan="2"><input type="submit" value="Register Sys Admin" @click="sysadmin_register()"></th>
            </tr>
        </table>
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
            user:{
                id: null,
                username: null,
                password: null,
                firstName: null,
                lastName: null,
                email: null,
                role: null
            },
        };
    },

    methods:{ 
        sysadmin_register() {

            //let auth_axios = {
            //    headers: {
            //        'Authorization' : 
            //    }
            //}
            var el = document.getElementById("admin_select");
            var option_val = el.options[el.selectedIndex].value;
            if (option_val === "sys_admin") { 
                this.user.role = 2;
            } else if (option_val === "user") {
                this.user.role = 1;
            } else if (option_val === "hotel_admin") {
                this.user.role = 4;
            } else if (option_val === "rac_admin") {
                this.user.role = 5;
            } else if (option_val === "airline_admin") {
                this.user.role = 3;
            }
            axios.post('http://localhost:8080/api/users/sysadmin_add'
            ,this.user
            ,{ 
                headers: {
                    "Authorization" : `Bearer ${localStorage.getItem('user-token')}`
                    } 
            })
            .then(response => {
                if (response.data === '') {
                    alert('Error');
                    return;
                } else {
                    alert(response.data.password);
                    return;
                }
            })
        }
    },

    mounted(){

    }
}

</script>

<style>
</style>