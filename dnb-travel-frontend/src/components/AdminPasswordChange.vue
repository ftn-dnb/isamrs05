<template>
    <v-container>
        <v-layout align-center>
            <v-form ref="passwordForm">
                Hello {{user.username}}, please enter new password 
                <v-text-field type="password" label="Password" v-model="user.password" prepend-icon="lock" :rules="inputRules"></v-text-field>
                <v-text-field type="password" label="Repeat password" v-model="user.repeatPassword"prepend-icon="lock" :rules="passwordInputRules"></v-text-field>
            
                <v-btn @click="changePassword">
                    <v-icon left>done</v-icon>
                    <span>Change password</span>
                </v-btn>
             </v-form>
        </v-layout>
    </v-container>
</template>

<script>

import axios from 'axios';

export default {
    name: 'AdminPasswordChange',
    props: [],
    components: {},
    
    data() {
        return {
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            passwordInputRules: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (v && v === this.user.password) || 'Your passwords don\'t match'
            ],
            user: {
                username: null,
                password: null,
            },
        };
    },

    methods: {
        changePassword() {
            if (!this.$refs.passwordForm.validate()) {
                return;
            }

            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            
            axios.post('http://localhost:8080/api/users/passwordChange', this.user, header)
            .then(response => { 

                axios.post('http://localhost:8080/auth/refresh', {}, header)
                .then(response => localStorage.setItem('user-token', response.data.accessToken))
                .catch(error => this.$toasted.error('Error while getting new token.', {duration:5000}));
                
                this.$toasted.success('Profile successfully updated.', {duration:5000});
                this.$router.push({path : '/login'}); 
            })
            .catch(error => this.$toasted.error('Error while updating user profile.', {duration:5000}));
        },
    },

    mounted() {
        
        this.user.username = localStorage.getItem('username');
    }
}
</script>

<style scoped>
</style>

