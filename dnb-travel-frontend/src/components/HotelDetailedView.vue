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
            hotel: {}
        }
    },

    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

        axios.get(`http://localhost:8080/api/hotels/${this.hotelID}`, header)
        .then(response => this.hotel = response.data)
        .catch(error => this.$roasted.error('Error while getting hotel.', {duration: 5000}));

        console.log(this.hotel);
    }
}
</script>
