<template>
    <div>
        <h3>Podešavanja profila</h3>
        
        <table>
            <tr>
                <td>Ime:</td>
                <td><input type="text" v-model="user.firstName" requeired /></td>
            </tr>
            <tr>
                <td>Prezime:</td>
                <td><input type="text" v-model="user.lastName" /></td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td><input type="email" v-model="user.email" /></td>
            </tr>
            <tr>
                <td>Sifra</td>
                <td><input type="password" v-model="user.password" /></td>
            </tr>
            <tr>
                <td>Ponovite sifru</td>
                <td><input type="password" v-model="user.repeatPassword" /></td>
            </tr>
            <tr>
                <th colspan="2"><input type="button" value="Izmeni profil" v-on:click="editProfile()" /></th>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'UserSettings',
    props: [],
    
    data() {
        return {
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
            if (!this.checkForm()) {
                return;
            }

            axios.put('http://localhost:8080/api/users', this.user)
            .then(response => {
                if (response.data === '') {
                    alert('Error while trying to change user.');
                    return;
                }

                alert('Hotel profile successfully changed.');
            });
        },

        checkForm() {
            if (!this.user.firstName) {
                alert('Niste uneli ime.');
                return false;
            } else if (!this.user.lastName) {
                alert('Niste uneli prezime.');
                return false;
            } else if (!this.user.email) {
                alert('Niste uneli e-mail.');
                return false;
            } else if (!this.user.password) {
                alert('Niste uneli sifru.');
                return false;
            } else if (!this.user.repeatPassword) {
                alert('Niste uneli proveru sifre.');
                return false;
            } else if (this.user.password !== this.user.repeatPassword) {
                alert('Sifre koje ste uneli se ne podudaraju.');
                return false;
            }

            return true;
        }
    },

    mounted() {
        // @TODO: pokupiti podatke korisnika preko cookie-a za ulogovanog korisnika
        // Zasad uzima predefinisanog korisnika 1 iz baze
        axios.get('http://localhost:8080/api/users/1')
        .then(response => this.user = response.data);
    }
}
</script>

<style scoped>

</style>

