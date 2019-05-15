<template>
    <div>
        <h3 class="subheading grey--text">Add New Hotel</h3>    
        <v-form ref="hotelAddForm">
            <v-text-field label="Name" v-model="hotel.name" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Description" v-model="hotel.description" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-container fluid>
                <v-layout wrap>
                    <v-flex xs12>
                        <v-combobox v-model="administrator_username" :items="this.hotel_admins_item" label="Select hotel admin">

                        </v-combobox>
                    </v-flex>
                </v-layout>
            </v-container>
            <h3 class="subheading grey--text">Address information</h3>
            <v-text-field label="Street Name" v-model="hotel.address.streetName" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Street Number" v-model="hotel.address.streetNumber" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="City" v-model="hotel.address.city" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Country" v-model="hotel.address.country" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Postal Code" v-model="hotel.address.postalCode" prepend-icon="person" :rules="inputRules"></v-text-field>
        
            <v-btn @click="hotel_register">
                <v-icon left>person_add</v-icon>
                <span>Add New Hotel</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    name: 'AddHotel',
    props: {},
    components: {},

    data() {
        return {
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            hotel: {
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
            hotel_admins: [],
            hotel_admins_item: []
        }
    },
    methods: {
        hotel_register() {
            if (!this.$refs.hotelAddForm.validate()) {
                return;
            }
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            this.hotel.rating = 0.0;
            this.hotel_admins.forEach(element => {
                if (element.username === this.administrator_username) {
                    this.hotel.administrator = element;
                }
            });

            axios.post('http://localhost:8080/api/hotels/add', this.hotel, header)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Error while registering hotel.', {duration:5000});
                } else {
                    this.$toasted.success('Hotel successfully registered.', {duration:5000});
                }
            })
            .catch(error => this.$toasted.error('Error while registering hotel.', {duration:5000}));
            
        }
    },
    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
        axios.get('http://localhost:8080/api/users/hotelAdmins', header)
        .then(response => { 
            this.hotel_admins = response.data;
            if (response.data.length != 0) { this.administrator_username = response.data[0].username; }
            this.hotel_admins.forEach(element => {
                this.hotel_admins_item.push(element.username);
            });
        });
    }
}
</script>

<style>

</style>

