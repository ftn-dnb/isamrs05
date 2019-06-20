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
            <h3 class="subheading grey--text">Choose address</h3>
            <vuetify-google-autocomplete
                id="map"
                append-icon="search"
                placeholder="Start typing"
                v-on:placechanged="getAddressData"
                >
            </vuetify-google-autocomplete>
        
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
            address_autocomplete: null,
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            airline: {
                id: null,
                name: null,
                description: null,
                rating: null,
                address: {
                    place_id: null,
                    country: null,
                    locality: null,
                    name: null,
                    postal_code: null,
                    latitude: null,
                    longitude: null,
                },
                administrator: null
            },
            administrator_username: null,
            airline_admins: [],
            airline_admins_item: []
        }
    },
    methods: {
        getAddressData(addressData, placeResultData, id) {
            this.address_autocomplete = addressData;
            console.log(this.address_autocomplete);
        },
        airline_register() {
            if (!this.$refs.airlineAddForm.validate()) {
                return;
            }

            if (this.address_autocomplete == null) {
                this.$toasted.error('Address not chosen.', {duration:2000});
                return;
            }
            if (this.administrator_username == null) {
                this.$toasted.error('Airline admin not available.', {duration:2000});
                return;
            }

            this.airline.address.place_id = this.address_autocomplete.place_id;
            this.airline.address.country = this.address_autocomplete.country;
            this.airline.address.locality = this.address_autocomplete.locality;
            this.airline.address.name = this.address_autocomplete.name;
            if (this.address_autocomplete.postal_code == null) {
                this.airline.address.postal_code = "";
            } else {
                this.airline.address.postal_code = this.address_autocomplete.postal_code;
            }
            
            this.airline.address.latitude = this.address_autocomplete.latitude;
            this.airline.address.longitude = this.address_autocomplete.longitude;

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
                    axios.get('http://localhost:8080/api/users/airlineAdmins', header)
                    .then(response => {
                        this.airline_admins = response.data;
                        this.airline_admins_item = [];
                        this.administrator_username = null;
                        if (response.data.length != 0) { this.administrator_username = response.data[0].username; }
                            this.airline_admins.forEach(element => {
                            this.airline_admins_item.push(element.username);
                        });
                    });
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

