<template>
    <div>
        <h3>Podešavanja profila</h3>
        
        <table>
            <tr>
                <td>Ime:</td>
                <td><input type="text" v-model="user.firstName" /></td>
            </tr>
            <tr>
                <td>Prezime:</td>
                <td><input type="text" v-model="user.lastName" /></td>
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
            },
        };
    },

    methods: {
        editProfile() {
            axios.put('http://localhost:8080/api/users/' + this.user.id, this.user)
            .then(response => {
                if (response.data === '') {
                    alert('Error while trying to change user.');
                    return;
                }

                alert('Hotel profile successfully changed.');
            });
        },
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

