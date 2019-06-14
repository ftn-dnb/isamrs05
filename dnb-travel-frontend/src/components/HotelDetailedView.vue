<template>
    <div>

    </div>
</template>

<script>
import axios from'axios';
export default {
    name: 'HotelDetailedView',
    props: {
        hotelID: null,
    },
    components: {},

    data() {
        return {
            userLoggedIn: false,
            hotel: {},
            priceList: {}
        }
    },

    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

        axios.get(`http://localhost:8080/api/hotels/${this.hotelID}`, header)
        .then(response => this.hotel = response.data)
        .catch(error => this.$roasted.error('Error while getting hotel.', {duration: 5000}));

        axios.get('http://localhost:8080/api/rooms/priceList/' + this.hotel.currentPriceListID, header)
        .then(response => this.priceList = response.data)
        .catch(error => this.$roasted.error('Errpr while getting price list.', {duration: 5000}));
    }
}
</script>
