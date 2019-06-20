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
            <h3 class="subheading grey--text">Choose address</h3>
            <vuetify-google-autocomplete
                id="map"
                append-icon="search"
                placeholder="Start typing"
                v-on:placechanged="getAddressData"
                >
            </vuetify-google-autocomplete>
        
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
            address_autocomplete: null,
            
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            hotel: {
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
            hotel_admins: [],
            hotel_admins_item: []
        }
    },
    methods: {
        getAddressData(addressData, placeResultData, id) {
            this.address_autocomplete = addressData;
            console.log(this.address_autocomplete);
        },
        hotel_register() {
            if (!this.$refs.hotelAddForm.validate()) {
                return;
            }
            if (this.address_autocomplete == null) {
                this.$toasted.error('Address not chosen.', {duration:2000});
                return;
            }
            if (this.administrator_username == null) {
                this.$toasted.error('Hotel admin not available.', {duration:2000});
                return;
            }

            this.hotel.address.place_id = this.address_autocomplete.place_id;
            this.hotel.address.country = this.address_autocomplete.country;
            this.hotel.address.locality = this.address_autocomplete.locality;
            this.hotel.address.name = this.address_autocomplete.name;
            if (this.address_autocomplete.postal_code == null) {
                this.hotel.address.postal_code = "";
            } else {
                this.hotel.address.postal_code = this.address_autocomplete.postal_code;
            }
            
            this.hotel.address.latitude = this.address_autocomplete.latitude;
            this.hotel.address.longitude = this.address_autocomplete.longitude;

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
                    axios.get('http://localhost:8080/api/users/hotelAdmins', header)
                    .then(response => {
                        this.hotel_admins = response.data;
                        this.hotel_admins_item = [];
                        this.administrator_username = null;
                        if (response.data.length != 0) { this.administrator_username = response.data[0].username; }
                            this.hotel_admins.forEach(element => {
                            this.hotel_admins_item.push(element.username);
                        });
                    });
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

