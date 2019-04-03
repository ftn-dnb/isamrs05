<template>
    <div>
        <h3>Hotel profile registration</h3>
        <table>
            <tr>
                <td>Id:</td>
                <td><input type="text" disabled v-model="hotel.id" /></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input type="text" v-model="hotel.name" /></td>
            </tr>
            <tr>
                <td>Address:</td>
                <td><input type="text" v-model="hotel.address" /></td>
            </tr>
            <tr>
                <th colspan="2"><input type="button" value="Register profile" v-on:click="registerHotel()" /></th>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'HotelSettings',
    props: [],
    data() {
        return {
            hotel: {
                id: null,
                name: null,
                address: null
            }
        };
    },
    methods: {
        registerHotel() {
            if (!this.validateForm()) {
                return;
            }
            axios.post('http://localhost:8080/api/hotels/', this.hotel)
            .then(response => {
                if (response.data === '') {
                    alert('Doslo je do greske prilikom izmene profila');
                    return;
                }

                alert('Profil je uspesno izmenjen');
            });
        },
        validateForm() {
            if (!this.hotel.name) {
                alert('Hotel name is empty.');
                return false;
            } else if (!this.hotel.address) {
                alert('Hotel address is empty.');
                return false;
            }
        }
    }
}
</script>

<style scoped>

</style>
