<template>
    <div>
        
        <h3 class="subheading grey--text">Airline company settings</h3>

        <v-form ref="airlineUpdateForm">
            <v-text-field label="Name" v-model="airline.name" prepend-icon="work" :rules="inputRules"></v-text-field>
            
            <h3 class="subheading grey--text">Choose address</h3>
            <vuetify-google-autocomplete
                id="map"
                append-icon="search"
                placeholder="Start typing"
                v-on:placechanged="getAddressData"
                >
            </vuetify-google-autocomplete>

            <v-textarea label="Description" v-model="airline.description" prepend-icon="description" :rules="inputRules"></v-textarea>

            <v-btn @click="editInfo">
                <v-icon left>update</v-icon>
                <span>Update company</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'AirlineSettings',
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
                address: {
                    place_id: null,
                    country: null,
                    locality: null,
                    name: null,
                    postal_code: null,
                    latitude: null,
                    longitude: null,
                },
                description: null,
            },
        };
    },

    methods: {
        getAddressData(addressData, placeResultData, id) {
            this.address_autocomplete = addressData;
            console.log(this.address_autocomplete);
        },
        editInfo() {
            if (!this.$refs.airlineUpdateForm.validate()) {
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

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

            axios.put('http://localhost:8080/api/airlines', this.airline, header)
            .then(response => this.$toasted.success('Profile successfully updated.', {duration:5000}))
            .catch(error => this.$toasted.error('Error while updating airline data.', {duration:5000}));
        },
    },

    mounted() {
        const username = localStorage.getItem('username');
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        axios.post(`http://localhost:8080/api/airlines/company/${username}`, {}, header)
        .then(response => this.airline = response.data)
        .catch(error => this.$toasted.error('Error while loading airline company', {duration:5000}));
    }
}
</script>

<style>

</style>
