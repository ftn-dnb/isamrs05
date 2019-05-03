<template>
    <div>
        <h3 class="subheading grey--text">Profile settings</h3>
        
        <v-form ref="userUpdateForm">
            <v-text-field label="Username" v-model="user.username" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="First name" v-model="user.firstName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Last name" v-model="user.lastName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field type="email" label="E-mail" v-model="user.email" prepend-icon="email" :rules="inputRules"></v-text-field>
            <v-text-field type="password" label="Password" v-model="user.password" prepend-icon="lock" :rules="inputRules"></v-text-field>
            <v-text-field type="password" label="Repeat password" v-model="user.repeatPassword"prepend-icon="lock" :rules="passwordInputRules"></v-text-field>
            
            <v-btn @click="editProfile">
                <v-icon left>update</v-icon>
                <span>Update profile</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'UserSettings',
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
            if (!this.$refs.userUpdateForm.validate()) {
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

