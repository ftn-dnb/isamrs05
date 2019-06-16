<template>
    <div>
        
        <h3 class="subheading grey--text">Airline company settings</h3>

        <v-form ref="airlineUpdateForm">
            <v-text-field label="Name" v-model="airline.name" prepend-icon="work" :rules="inputRules"></v-text-field>
            <v-text-field label="Street" v-model="airline.address.streetName" prepend-icon="place" :rules="inputRules"></v-text-field>
            <v-text-field label="Street number" v-model="airline.address.streetNumber" prepend-icon="place" :rules="inputRules"></v-text-field>
            <v-text-field label="City" v-model="airline.address.city" prepend-icon="place" :rules="inputRules"></v-text-field>
            <v-text-field label="Country" v-model="airline.address.country" prepend-icon="place" :rules="inputRules"></v-text-field>
            <v-text-field label="Postal code" v-model="airline.address.postalCode" prepend-icon="place" :rules="inputRules"></v-text-field>
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
                    id: null,
                    streetName: null,
                    streetNumber: null,
                    city: null,
                    country: null,
                    postalCode: null
                },
                description: null,
            },
        };
    },

    methods: {
        editInfo() {
            if (!this.$refs.airlineUpdateForm.validate()) {
                return;
            }

            axios.put('http://localhost:8080/api/airlines', this.airline)
            .then(response => this.$toasted.success('Profile successfully updated.', {duration:5000}))
            .catch(error => this.$toasted.error('Error while updating airline data.', {duration:5000}));
        },
    },

    mounted() {
        // @TODO: pokupiti podatke odgovarajuce aviokompanije na osnovu podataka
        // njenog administratora
        // Zasad se uzima predefinisana vrednost iz baze
        //axios.get("http://localhost:8080/api/airlines/22").then(response => this.airline = response.data);

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
