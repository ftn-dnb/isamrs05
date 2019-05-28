<template>
    <div>
        <h3 class="subheading grey--text">Add New Airline</h3>    
        <v-form ref="airlineAddForm">
            <v-text-field label="Name" v-model="airline.name" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Description" v-model="airline.description" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-container fluid>
                <v-layout wrap>
                    <v-flex xs12>
                        <v-combobox v-model="administrator_username" :items="this.airline_admins_item" label="Select airline admin">

                        </v-combobox>
                    </v-flex>
                </v-layout>
            </v-container>
            <h3 class="subheading grey--text">Address information</h3>
            <v-text-field label="Street Name" v-model="airline.address.streetName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Street Number" v-model="airline.address.streetNumber" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="City" v-model="airline.address.city" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Country" v-model="airline.address.country" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Postal Code" v-model="airline.address.postalCode" prepend-icon="person" :rules="inputRules"></v-text-field>
        
            <v-btn @click="airline_register">
                <v-icon left>person_add</v-icon>
                <span>Add New Airline</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    name: 'AddAirline',
    props: {},
    components: {},

    data() {
        return {
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            airline: {
                id: null,
                name: null,
                description: null,
                rating: null,
                address: {
                    id: null,
                    streetName: null,
                    streetNumber: null,
                    city: null,
                    country: null,
                    postalCode: null
                },
                administrator: null
            },
            administrator_username: null,
            airline_admins: [],
            airline_admins_item: []
        }
    },
    methods: {
        airline_register() {
            if (!this.$refs.airlineAddForm.validate()) {
                return;
            }
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            this.airline.rating = 0.0;
            this.airline_admins.forEach(element => {
                if (element.username === this.administrator_username) {
                    this.airline.administrator = element;
                }
            });

            axios.post('http://localhost:8080/api/airlines', this.airline, header)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Error while registering airline.', {duration:5000});
                } else {
                    this.$toasted.success('Airline successfully registered.', {duration:5000});
                }
            })
            .catch(error => this.$toasted.error('Error while registering airline.', {duration:5000}));
            
        }
    },
    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
        axios.get('http://localhost:8080/api/users/airlineAdmins', header)
        .then(response => { 
            this.airline_admins = response.data;
            if (response.data.length != 0) { this.administrator_username = response.data[0].username; }
            this.airline_admins.forEach(element => {
                this.airline_admins_item.push(element.username);
            });
        });
    }
}
</script>

<style>

</style>

