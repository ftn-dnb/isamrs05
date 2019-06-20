<template>
    <div>
        <v-container align-center>
            <h1 class="headline text-md-center">
            {{hotel.name}}<v-rating v-model="hotel.rating" readonly></v-rating>
            </h1>
            <br>
            <h2 class="subheading grey--text text-darken-3 text-md-center">{{hotel.address.name}}
                 {{hotel.address.street_number}}, {{hotel.address.postal_code}} {{hotel.address.locality}}, {{hotel.address.country}}
            </h2>
        </v-container>
        <v-container grid-list-md text-xs-center>
            <v-layout row wrap>
                <v-flex xs4>
                    <v-menu
                        lazy
                        transition="scale-transition">
                        <template v-slot:activator="{ on }">
                            <v-text-field
                                v-model="itemFilter.date_arrival"
                                label="Arrival date"
                                prepend-icon="event"
                                readonly
                                v-on="on"
                            ></v-text-field>
                        </template>
                        <v-date-picker v-model="itemFilter.date_arrival">
                        </v-date-picker>
                    </v-menu>

                    <v-slider
                        label="Overnight stays"
                        v-model="itemFilter.overnight_stays"
                        thumb-label="always"
                        min="1"
                        max="7"
                    ></v-slider>

                    <v-slider
                        label="Number of guests"
                        v-model="itemFilter.number_guests"
                        thumb-label="always"
                        min="1"
                        max="7"
                    ></v-slider>

                    <v-btn small @click="searchItems">
                        <v-icon left>search</v-icon>
                        <span>Search</span>
                    </v-btn>

                    <v-btn small @click="clearSearch">
                        <v-icon left>clear</v-icon>
                        <span>Clear</span>
                    </v-btn>
                </v-flex>
                <v-flex>
                    <GmapMap
                        :center="center"
                        :zoom="7"
                        map-type-id="terrain"
                        style="width: 500px; height: 300px"
                    >
                        <GmapMarker
                            :key="index"
                            v-for="(m, index) in markers"
                            :position="m.position"
                            :clickable="true"
                            :draggable="true"
                            @click="center=m.position"
                        />
                    </GmapMap>
                </v-flex>
            </v-layout>
            <v-layout v-if="flight_res != null" align-center justify-center>
                <v-combobox max-width="100" label="Choose additional service"
                                            v-model="serviceToAdd"
                                            :items="hotel.additionalServices"
                                            item-text="serviceName"
                                            return-object>
                                            </v-combobox>
                                            <v-btn @click="addServiceToReservation">
                                                <v-icon>add</v-icon>
                                                <span>Add service</span>
                                            </v-btn>
                                            <v-btn @click="clearForm">
                                                <v-icon>clear</v-icon>
                                                <span>Clear</span>
                                            </v-btn>
            </v-layout>
            <v-layout v-if="flight_res != null" align-center justify-center>
                <v-btn small @click="make_reservation">
                    <v-icon left>hotel</v-icon>
                    <span>Make Reservation</span>
                </v-btn>
            </v-layout>
            
        </v-container>
        
        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="element in priceList_normal" :key="element.id">
                <v-card flat class="text-xs-center ma-3">
                    <v-card-text>
                        <div class="subheading">
                            Room {{element.room.roomNumber}}.
                        </div>
                        <div class="grey--text">
                            {{element.room.floor}}-th floor, &nbsp;&nbsp;{{element.room.capacity}}-beds.<br/>
                        </div>
                        <div>
                            <v-rating v-model="element.room.rating" readonly></v-rating>
                        </div>
                        <div class="grey--text">
                            {{formatDate(element.startDate)}} &nbsp;&nbsp;->&nbsp;&nbsp;  {{formatDate(element.endDate)}}
                        </div>
                        <div v-if="flight_res != null">
                            <v-checkbox v-model="chosen_map[element.id]" label="Reserve Room"></v-checkbox>
                        </div>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
import axios from'axios';
export default {
    name: 'HotelDetailedView',
    props: {
        hotelID: null,
        flight_res: null,
        users: null,
    },
    components: {},

    data() {
        return {
            userLoggedIn: false,
            hotel: {
                address: {
                    streetName: null,
                    streetNumber: null,
                    city: null,
                    country: null,
                    postalCode: null,
                }
            },
            priceList: {},
            priceList_normal: [],
            chosen_map: {},
            itemFilter: {
                hotel_id: null,
                date_arrival: null,
                overnight_stays: null,
                number_guests: null
            },
            hotelReservation: {
                hotel_id: null,
                date_arrival: null,
                overnight_stays: null,
                items: [],
                users: [],
                additionalServices: []
            },
            serviceToAdd: {
                hotel_id: null,
                room_id: null,
                serviceName: null,
                servicePrice: null,
            },
            center: {},
            markers: []
        }
    },

    methods: {
        searchItems() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            if (this.itemFilter.date_arrival == null) {
                this.$toasted.error('Arrival date is not selected.', {duration: 2000});
                return;
            }

            axios.post('http://localhost:8080/api/rooms/filterItems', this.itemFilter, header)
            .then(response => {
                this.priceList.hotelPriceListItems = response.data;
                this.priceList_normal = [];
                this.priceList.hotelPriceListItems.forEach(element => {
                    if (element.activeDiscount == 0) {
                        this.priceList_normal.push(element);
                    }
                });
                if (this.priceList_normal.length == 0) {
                    this.$toasted.info('No results found.', {duration:2000});
                }
            })
            .catch(error => this.$toasted.error('Error while retrieving filtered items.', {duration:5000}));
        },
        formatDate(date) {
            return date.split("T")[0]
        },

        clearSearch() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            this.$toasted.success('Success', {duration: 4000})

            axios.get('http://localhost:8080/api/rooms/priceList/' + this.hotel.currentPriceListID, header)
            .then(response => {
                this.priceList = response.data;
                this.priceList_normal = [];
                this.priceList.hotelPriceListItems.forEach(element => {
                    if (element.activeDiscount == 0) {
                        this.priceList_normal.push(element);
                    }
                });
            })
            .catch(error => this.$toasted.error('Errpr while getting price list.', {duration: 5000}));
        },

        addServiceToReservation() {
            var serviceNames = [];
            this.hotelReservation.additionalServices.forEach(element => {
                serviceNames.push(element.serviceName);
            });
            if (serviceNames.includes(this.serviceToAdd.serviceName)) {
                this.$toasted.error('Service already selected.', {duration:2000});
                return;
            }
            this.hotelReservation.additionalServices.push(this.serviceToAdd);
            this.$toasted.success('Service successfully selected.', {duration:2000});
        },

        clearForm() {
            this.hotelReservation.additionalServices = [];
            this.$toasted.success('Additional services cleared from reservation.', {duration:2000});
        },

        make_reservation() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            var reserved = [];
            this.priceList_normal.forEach(element => {
                if (this.chosen_map[element.id]) {
                    reserved.push(element);
                }
            });
            if (reserved.length == 0) {
                this.$toasted.error('Nije odabrana ni jedna stavka.', {duration:2000});
                return;
            }
            var beds_reserved = 0;
            reserved.forEach(element => {
                beds_reserved = beds_reserved + element.room.capacity;
            });
            if (beds_reserved > this.users.length) {
                this.$toasted.error('Vise kreveta nego korisnika.', {duration:2000});
                return;
            }
            if (beds_reserved < this.users.length) {
                this.$toasted.error('Nedovoljno izabranih kreveta.', {duration:2000});
                return;
            }
            if (new Date(this.itemFilter.date_arrival) < new Date(this.flight_res.endDateTime)) {
                this.$toasted.error('Datum je prije aktuelnog dolaska.', {duration:2000});
                return;
            }
            this.hotelReservation.hotel_id = this.itemFilter.hotel_id;
            this.hotelReservation.date_arrival = this.itemFilter.date_arrival;
            this.hotelReservation.overnight_stays = this.itemFilter.overnight_stays;
            this.hotelReservation.items = reserved;
            this.hotelReservation.users = this.users;

            axios.post('http://localhost:8080/api/hotels/addHotelReservation', this.hotelReservation, header)
            .then(response => {
                this.clearSearch();
                this.priceList_normal.forEach(element => {
                    this.chosen_map[element.id] = false;
                    this.$router.push({name: "SearchRAC", params: {flight: this.flight, users: reservations.users}});
                })
            })
            .catch(error => console.log(error));

        }
    },

    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

        this.itemFilter.hotel_id = this.hotelID;

        axios.get(`http://localhost:8080/api/hotels/${this.hotelID}`, header)
        .then(response => {
            this.hotel = response.data;
            this.center = {
                lat: this.hotel.address.latitude,
                lng: this.hotel.address.longitude
            },
            this.markers = [
                {
                    position: {
                        lat: this.hotel.address.latitude,
                        lng: this.hotel.address.longitude
                    }
                }
            ]
            axios.get('http://localhost:8080/api/rooms/priceList/' + this.hotel.currentPriceListID, header)
            .then(response => {
                this.priceList = response.data;
                this.priceList.hotelPriceListItems.forEach(element => {
                    if (element.activeDiscount == 0) {
                        this.priceList_normal.push(element);
                        this.chosen_map[element.id] = false;
                    }

                });
            })
            .catch(error => this.$roasted.error('Errpr while getting price list.', {duration: 5000}));
        })
        .catch(error => this.$roasted.error('Error while getting hotel.', {duration: 5000}));
    }
}
</script>
