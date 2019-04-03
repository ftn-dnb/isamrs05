<template>
    <div>
        <h3>Hotel profile change</h3>
        <table>
            <tr>
                <td>Id:</td>
                <td><input type="text" v-model="hotel.id" /></td>
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
                <th colspan="2"><input type="button" value="Change profile" v-on:click="changeHotel()" /></th>
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
        changeHotel() {
            if (!this.validateForm()) {
                return;
            }
            axios.put('http://localhost:8080/api/hotels/' + this.hotel.id, this.hotel)
            .then(response => {
                if (response.data === '') {
                    alert('Doslo je do greske prilikom izmene profila');
                    return;
                }

                alert('Profil je uspesno izmenjen');
            });
        },
        validateForm() {
            if (!this.hotel.id) {
                alert('Hotel id is empty.');
                return false;
            } else if (!this.hotel.name) {
                alert('Hotel name is empty.');
                return false;
            } else if (!this.hotel.address) {
                alert('Hotel address is empty.');
                return false;
            }
        }
    },
    mounted() {
        axios.get('http://localhost:8080/api/hotels/all/')
        .then(response => this.user = response.data);
    }
}
</script>

<style scoped>

</style>
