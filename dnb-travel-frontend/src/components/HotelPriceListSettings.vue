<template>
    <div>
        <h3 class="subheading grey--text">PriceList Settings</h3>

        <v-form ref="addNewPriceListForm">
            <v-combobox label="Select existing price list"
                v-model="priceList"
                :items="priceLists"
                item-text="description"
                return-object>
            </v-combobox>
        </v-form>
        <v-data-table :headers="priceListHeaders"
            :items="priceList.hotelPriceListItems">
            <template v-slot:items="props">
                <td>{{ props.item.startDate }}</td>
                <td>{{ props.item.endDate }}</td>
                <td>{{ props.item.pricePerDay }}</td>
                <td>{{ props.item.activeDiscount }}</td>
            </template>
            <template v-slot:footer>
                <td :colspan="priceListHeaders.length">
                    <v-dialog v-model="dialog" width="500">
                        <template v-slot:activator="{ on }">
                            <v-btn v-on="on" @click="getRooms">
                                <v-icon left>add_circle</v-icon>
                                <span>Add item</span>
                            </v-btn>
                        </template>

                        <v-card>
                                <v-card-title class="headline grey--text lighten-2" primary-title>
                                  Add new price list item
                                </v-card-title>

                                <v-card-text>
                                    <v-form ref="addPriceListItemForm">
                                        <v-combobox label="Select existing room number"
                                            v-model="room"
                                            :items="rooms"
                                            item-text="roomNumber"
                                            return-object>
                                        </v-combobox>
                                        <v-menu>
                                            <v-text-field label="Start date" 
                                                slot="activator" 
                                                :value="priceListItem.startDate"
                                                :rules="priceItemStartDate"
                                                prepend-icon="event"
                                                ></v-text-field>
                                            <v-date-picker v-model="priceListItem.startDate">
                                            </v-date-picker>
                                        </v-menu>

                                        <v-menu>
                                            <v-text-field label="End date" 
                                                slot="activator" 
                                                :value="priceListItem.endDate"
                                                :rules="priceItemEndDate"
                                                prepend-icon="event"
                                            ></v-text-field>
                                            <v-date-picker v-model="priceListItem.endDate"></v-date-picker>
                                        </v-menu>

                                        <v-text-field type="number" 
                                            label="Price" 
                                            v-model="priceListItem.pricePerDay" min="0"
                                            :rules="priceItemPrice"
                                            prepend-icon="attach_money"
                                        ></v-text-field>
                                        
                                        <v-text-field type="number" 
                                            label="Discount %" 
                                            v-model="priceListItem.activeDiscount" 
                                            min="0" 
                                            max="100"
                                            :rules="priceItemDiscount"
                                            prepend-icon="arrow_downward"
                                        ></v-text-field>
                                    </v-form>
                                </v-card-text>

                                <v-divider></v-divider>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn @click="addPriceListItem">
                                    <v-icon left>add</v-icon>
                                    <span>Add item</span>
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </td>
            </template>
        </v-data-table>
    </div>
</template>

<script>
import axios from 'axios';
import { error } from 'util';
export default {
    name: 'HotelPriceListSettings',
    props: {},
    components: {},

    data() {
        return {
            hotelID: null,
            priceLists: [],
            priceList: null,
            priceListItem: {
                id: null,
                activeDiscount: null,
                startDate: null,
                endDate: null,
                pricePerDay: null,
                roomID: null,
                hotelPriceListID: null
            },
            priceListHeaders: [
                {text: 'From', value: 'startDate'},
                {text: 'To', value: 'endDate'},
                {text: 'Price', value: 'pricePerDay'},
                {text: 'Discount %', value: 'activeDiscount'}
            ],
            room: null,
            rooms: [],
            dialog: false,
            priceItemPrice: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (v && v >= 0) || 'Price can\'t be below 0'
            ],
            priceItemDiscount: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (v && v >= 0 && v <= 100) || 'Discount must be between 0 and 100 %'
            ],
            priceItemStartDate: [
                v => (v && v.length > 0) || 'Please select start date',
                v => (v && this.isDateGreaterThan(this.priceListItem.endDate, this.priceListItem.startDate)) || 'Start date must be before end date'
            ],
            priceItemEndDate: [
                v => (v && v.length > 0) || 'Please select end date',
                v => (v && this.isDateGreaterThan(this.priceListItem.endDate, this.priceListItem.startDate)) || 'End date must be after start date'
            ],
        };
    },

    methods: {
        getRooms() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.get('http://localhost:8080/api/rooms/all/' + this.hotelID, header)
            .then(response => this.rooms = response.data);
        },
        isDateGreaterThan(date1, date2) {
            const d1 = new Date(date1);
            const d2 = new Date(date2);
            return d1.getTime() >= d2.getTime();
        },
        addPriceListItem() {
            console.log(this.room);
            if (!this.$refs.addPriceListItemForm.validate()) {
                return;
            }
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            this.priceListItem.hotelPriceListID = this.priceList.hotelID;
            this.priceListItem.roomID = this.room.id;
            axios.post('http://localhost:8080/api/rooms/addPriceListItem/', this.priceListItem, header)
            .then(response => {
                console.log(response.data);
                this.priceList.hotelPriceListItems.push(response.data);
                this.refreshPriceListItems();
            });
        },
        refreshPriceListItems() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.get('http://localhost:8080/api/rooms/prices/' + this.hotelID, header)
            .then(response => {
                this.priceLists = response.data;
                console.log(this.priceLists)
            });
        }
    },

    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
        const username = localStorage.getItem('username');
        axios.post('http://localhost:8080/api/hotels/findHotelByAdmin', username, header)
        .then(response => { 
            this.hotelID = response.data;
            axios.get('http://localhost:8080/api/rooms/prices/' + this.hotelID, header)
            .then(response => {
                this.priceLists = response.data;
                this.priceList = this.priceLists[0];
                console.log(this.priceLists);
            });
        });
    }
}
</script>

<style>

</style>
