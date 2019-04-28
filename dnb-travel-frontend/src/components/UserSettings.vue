<template>
    <div>
        <h3>Profile settings</h3>
        
        <table>
            <tr>
                <td>Username</td>
                <td><input type="text" v-model="user.username" disabled></td>
            </tr>
            <tr>
                <td>First name</td>
                <td><input type="text" v-model="user.firstName" /></td>
            </tr>
            <tr>
                <td>Last name</td>
                <td><input type="text" v-model="user.lastName" /></td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td><input type="email" v-model="user.email" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" v-model="user.password" /></td>
            </tr>
            <tr>
                <td>Repeat password</td>
                <td><input type="password" v-model="user.repeatPassword" /></td>
            </tr>
            <tr>
                <th colspan="2"><input type="button" value="Update" v-on:click="editProfile()" /></th>
            </tr>
        </table>
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'UserSettings',
    props: [],
    
    data() {
        return {
            user: {
                id: null,
                firstName: null,
                lastName: null,
                email: null,
                password: null,
                repeatPassword: null,
            },
        };
    },

    methods: {
        editProfile() {
            if (!this.checkForm()) {
                return;
            }

            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            
            axios.put('http://localhost:8080/api/users', this.user, header)
            .then(response => { 

                axios.post('http://localhost:8080/auth/refresh', {}, header)
                .then(response => localStorage.setItem('user-token', response.data.accessToken))
                .catch(error => this.$toasted.error('Error while getting new token.', {duration:5000}));
                
                this.$toasted.success('Profile successfully updated.', {duration:5000});
            })
            .catch(error => this.$toasted.error('Error while updating user profile.', {duration:5000}));
        },

        checkForm() {
            if (!this.user.firstName) {
                this.$toasted.info('Please enter first name.', {duration:5000});
                return false;
            } else if (!this.user.lastName) {
                this.$toasted.info('Please enter last name.', {duration:5000});
                return false;
            } else if (!this.user.email) {
                this.$toasted.info('Please enter e-mail.', {duration:5000});
                return false;
            } else if (!this.user.password) {
                this.$toasted.info('Please enter password.', {duration:5000});
                return false;
            } else if (!this.user.repeatPassword) {
                this.$toasted.info('Please enter password confirmation.', {duration:5000});
                return false;
            } else if (this.user.password !== this.user.repeatPassword) {
                this.$toasted.info('Two passwords are not the same.', {duration:5000});
                return false;
            }

            return true;
        }
    },

    mounted() {
        const username = localStorage.getItem('username');
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
        
        axios.get(`http://localhost:8080/api/users/info/${username}`, header)
        .then(response => {
            this.user = response.data;
            this.user.password = '';
        })
        .catch(error => this.$toasted.error('Error while loading user information.', {duration:5000}));
    }
}
</script>

<style scoped>

</style>

