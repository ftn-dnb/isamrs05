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
            .then(response => {
                if (response.data === '') {
                    alert('Doslo je do greske prilikom izmene profila aviokompanije');
                    return;
                }

                alert('Profil aviokompanije je uspesno izmenjen');
            });
        },

        checkForm() {
            if (!this.airline.name) {
                alert('Morate uneti ime aviokompanije.');
                return false;
            } else if (!this.airline.description) {
                alert('Morate uneti promotivni opis kompanije.');
                return false;
            } else if (!this.airline.address.streetName) {
                alert('Morate uneti naziv ulice.');
                return false;
            } else if (!this.airline.address.streetNumber) {
                alert('Morate uneti ulicni broj.');
                return false;
            } else if (!this.airline.address.city) {
                alert('Morate uneti naziv grada.');
                return false;
            } else if (!this.airline.address.country) {
                alert('Morate uneti naziv drzave.');
                return false;
            } else if (!this.airline.address.postalCode) {
                alert('Morate uneti postanski broj grada.');
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
