<template>
    <div>
        <h3>Podešavanja profila aviokompanije {{ airline.name }}</h3>

        <table>
            <tr>
                <td>Naziv:</td>
                <td><input type="text" v-model="airline.name" /></td>
            </tr>
            <tr>
                <td>Ulica:</td>
                <td><input type="text" v-model="airline.address.streetName" /></td>
            </tr>
            <tr>
                <td>Broj:</td>
                <td><input type="number" v-model="airline.address.streetNumber" /></td>
            </tr>
            <tr>
                <td>Grad:</td>
                <td><input type="text" v-model="airline.address.city" /></td>
            </tr>
            <tr>
                <td>Drzava:</td>
                <td><input type="text" v-model="airline.address.country" /></td>
            </tr>
            <tr>
                <td>Postanski broj:</td>
                <td><input type="text" v-model="airline.address.postalCode" /></td>
            </tr>
            <tr>
                <td>Promotivni opis</td>
                <td><textarea v-model="airline.description"></textarea></td>
            </tr>
            <tr>
                <th colspan="2"><input type="submit" value="Izmeni profil" @click="editInfo" /></th>
            </tr>
        </table>
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
            if (!this.checkForm()) {
                return;
            }

            axios.put('http://localhost:8080/api/airlines', this.airline)
            .then(response => this.$toasted.success('Profile successfully updated.', {duration:5000}))
            .catch(error => this.$toasted.error('Error while updating airline data.', {duration:5000}));
        },

        checkForm() {
            if (!this.airline.name) {
                this.$toasted.info('Morate uneti ime aviokompanije.', {duration:5000});
                return false;
            } else if (!this.airline.description) {
                this.$toasted.info('Morate uneti promotivni opis kompanije.', {duration:5000});
                return false;
            } else if (!this.airline.address.streetName) {
                this.$toasted.info('Morate uneti naziv ulice.', {duration:5000});
                return false;
            } else if (!this.airline.address.streetNumber) {
                this.$toasted.info('Morate uneti ulicni broj.', {duration:5000});
                return false;
            } else if (!this.airline.address.city) {
                this.$toasted.info('Morate uneti naziv grada.', {duration:5000});
                return false;
            } else if (!this.airline.address.country) {
                this.$toasted.info('Morate uneti naziv drzave.', {duration:5000});
                return false;
            } else if (!this.airline.address.postalCode) {
                this.$toasted.info('Morate uneti postanski broj grada.', {duration:5000});
                return false;
            }

            return true;
        },
    },

    mounted() {
        // @TODO: pokupiti podatke odgovarajuce aviokompanije na osnovu podataka
        // njenog administratora
        // Zasad se uzima predefinisana vrednost iz baze
        axios.get("http://localhost:8080/api/airlines/22").then(response => this.airline = response.data);
    }
}
</script>

<style>

</style>
