<template>
<div>
    <div>
        <h3 class="subheading grey--text">PriceList Settings</h3>

        <v-form ref="addNewPriceListForm">
            <v-combobox label="Select existing price list"
                v-model="priceList"
                :items="priceLists"
                item-text="description"
                return-object>
            </v-combobox>
            <v-btn @click="setAsCurrentList">
                <v-icon left>done</v-icon>
                <span>SET AS CURRENT LIST</span>
            </v-btn>
        </v-form>
        <v-data-table :headers="priceListHeaders"
            :items="priceList.hotelPriceListItems">
            <template v-slot:items="props">
                <td>{{ props.item.room.roomNumber }}</td>
                <td>{{ props.item.startDate }}</td>
                <td>{{ props.item.endDate }}</td>
                <td>{{ props.item.pricePerDay }}</td>
                <td>{{ props.item.activeDiscount }}</td>
                <td>
                    <v-btn @click="removeItem(props.item.id)">
                        <v-icon>remove_circle</v-icon>
                    </v-btn>
                </td>
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
                                        <v-layout align-center justify-center row>
                                            <v-combobox label="Choose additional service"
                                            v-model="serviceToAdd"
                                            :items="hotel.additionalServices"
                                            item-text="serviceName"
                                            return-object>
                                            </v-combobox>
                                            <v-btn @click="addServiceToItem">
                                                <v-icon>add</v-icon>
                                                <span>Add service</span>
                                            </v-btn>
                                            <v-btn @click="clearForm">
                                                <v-icon>clear</v-icon>
                                                <span>Clear</span>
                                            </v-btn>
                                        </v-layout>
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
        <div>
            <h3 class="subheading grey--text">Additional Services Settings</h3>

            <v-data-table :headers="additionalServicesHeaders"
                :items="hotel.additionalServices">
                <template v-slot:items="props">
                    <td>{{ props.item.serviceName }}</td>
                    <td>{{ props.item.servicePrice }}</td>
                    <td>
                        <v-btn @click="removeService(props.item.id)">
                            <v-icon>remove_circle</v-icon>
                        </v-btn>
                    </td>
                </template>
                <template v-slot:footer>
                    <td :colspan="additionalServicesHeaders.length">
                        <v-dialog v-model="dialog_serv" width="500">
                            <template v-slot:activator="{ on }">
                                <v-btn v-on="on">
                                    <v-icon left>add_circle</v-icon>
                                    <span>Add item</span>
                                </v-btn>
                            </template>

                            <v-card>
                                    <v-card-title class="headline grey--text lighten-2" primary-title>
                                        Add new additional service
                                    </v-card-title>

                                    <v-card-text>
                                        <v-form ref="addServiceForm">

                                            <v-text-field type="text" 
                                                label="Service Name" 
                                                v-model="additionalService.serviceName" min="0"
                                                :rules="defaultRule"
                                                prepend-icon="attach_money"
                                            ></v-text-field>
                                            
                                            <v-text-field type="number" 
                                                label="Service Price" 
                                                v-model="additionalService.servicePrice" 
                                                min="0" 
                                                max="100"
                                                :rules="priceItemPrice"
                                                prepend-icon="attach_money"
                                            ></v-text-field>
                                        </v-form>
                                    </v-card-text>

                                    <v-divider></v-divider>

                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn @click="addAdditionalService">
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
            hotel: {
                additionalServices: []
            },
            priceLists: [],
            priceList: {},
            priceListItem: {
                id: null,
                activeDiscount: null,
                startDate: null,
                endDate: null,
                pricePerDay: null,
                room: null,
                hotelPriceListID: null,
                additionalServices: []
            },
            priceListHeaders: [
                {text: 'Room', value: 'roomNumber'},
                {text: 'From', value: 'startDate'},
                {text: 'To', value: 'endDate'},
                {text: 'Price', value: 'pricePerDay'},
                {text: 'Discount %', value: 'activeDiscount'},
                {text: '', value: 'id'}
            ],
            additionalServicesHeaders: [
                {text: 'Service Name', value: 'serviceName'},
                {text: 'Service Price', value: 'servicePrice'},
                {text: '', value: 'id'}
            ],

            room: null,
            rooms: [],
            dialog: false,
            dialog_serv: false,

            additionalService: {
                hotel_id: null,
                room_id: null,
                serviceName: null,
                servicePrice: null,
            },

            serviceToAdd: {
                hotel_id: null,
                room_id: null,
                serviceName: null,
                servicePrice: null,
            },

            defaultRule: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
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
                v => (v && this.isDateGreaterThan(this.priceListItem.endDate, this.priceListItem.startDate)) || 'Start date must be before end date',
            ],
            priceItemEndDate: [
                v => (v && v.length > 0) || 'Please select end date',
                v => (v && this.isDateGreaterThan(this.priceListItem.endDate, this.priceListItem.startDate)) || 'End date must be after start date'
            ],
        };
    },

    methods: {
        setAsCurrentList() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.get('http://localhost:8080/api/hotels/setPriceList/' + this.hotel.id + '/' + this.priceList.id, header)
            .then(response => this.$toasted.success('PriceList successfully set.', {duration:5000}))
            .catch(error => this.$toasted.error('PriceList already set.', {duration:5000}));
        },
        getRooms() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.get('http://localhost:8080/api/rooms/all/' + this.hotel.id, header)
            .then(response => this.rooms = response.data);
        },
        isDateGreaterThan(date1, date2) {
            if (date1 == null || date2 == null) return true;
            const d1 = new Date(date1);
            const d2 = new Date(date2);
            return d1.getTime() > d2.getTime();
        },
        addPriceListItem() {
            console.log(this.room);
            if (!this.$refs.addPriceListItemForm.validate()) {
                return;
            }
            if (this.priceListItem.additionalServices.length != 0 && this.priceListItem.activeDiscount == 0) {
                this.$toasted.error('Must add discount. Otherwise clear.', {duration:2000});
                return;
            }
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            this.priceListItem.hotelPriceListID = this.priceList.id;
            this.priceListItem.room = this.room;

            if (!this.checkPriceListItemIntegrity(this.priceListItem)) {
                alert('PriceListItem constraint problem.');
                return;
            }
            
            axios.post('http://localhost:8080/api/rooms/addPriceListItem/', this.priceListItem, header)
            .then(response => {
                console.log(response.data);
                this.formatDates(response.data);
                this.priceList.hotelPriceListItems.push(response.data);
                this.refreshPriceListItems();
            });
        },
        addAdditionalService() {
            if (!this.$refs.addServiceForm.validate()) {
                return;
            }
            var servicesNames = [];
            this.hotel.additionalServices.forEach(element => {
                servicesNames.push(element.serviceName);
            });
            if (servicesNames.includes(this.additionalService.serviceName)) {
                this.$toasted.error('Service with that name already exists.', {duration:2000});
                return;
            }
            this.additionalService.hotel_id = this.hotel.id;

            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.post('http://localhost:8080/api/hotels/addAdditionalService', this.additionalService, header)
            .then(response => {
                this.$toasted.success('Service successfully added.', {duration:2000});
                this.hotel = response.data;
            })
            .catch(error => this.$toasted.error('Error while adding service.', {duration:2000}));
            
        },
        addServiceToItem() {
            var serviceNames = [];
            this.priceListItem.additionalServices.forEach(element => {
                serviceNames.push(element.serviceName);
            });
            if (serviceNames.includes(this.serviceToAdd.serviceName)) {
                this.$toasted.error('Service already selected.', {duration:2000});
                return;
            }
            this.priceListItem.additionalServices.push(this.serviceToAdd);
            this.$toasted.success('Service successfully selected.', {duration:2000});
        },
        clearForm() {
            this.priceListItem.activeDiscount = null;
            this.priceListItem.startDate = null;
            this.priceListItem.endDate = null;
            this.priceListItem.pricePerDay = null;
            this.priceListItem.roomID = null;
            this.priceListItem.roomNumber = null;
            this.priceListItem.additionalServices = []
        },
        refreshPriceListItems() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.get('http://localhost:8080/api/rooms/prices/' + this.hotel.id, header)
            .then(response => {
                this.priceLists = response.data;
                this.priceLists.forEach(element => {
                    element.hotelPriceListItems.forEach(item => this.formatDates(item));
                });
                this.activeDiscount = null;
                this.startDate = null;
                this.endDate = null;
                this.pricePerDay = null;
                this.roomID = null;
                this.roomNumber = null;
                this.priceListItem.additionalServices = []
            });
        },
        formatDates(priceListItem) {
            let date1 = priceListItem.startDate;
            let date2 = priceListItem.endDate;
            priceListItem.startDate = date1.split("T")[0]
            priceListItem.endDate = date2.split("T")[0]
        },
        checkPriceListItemIntegrity(item) {
            let sameRoomItems = []
            let flag = true;
            this.priceList.hotelPriceListItems.forEach(element => {
                if (element.room.id == item.room.id) sameRoomItems.push(element);
            });
            if (sameRoomItems.length == 0) flag = true;
            sameRoomItems.forEach(element => {
                const date_start_element = new Date(element.startDate);
                const date_end_element = new Date(element.endDate);
                const date_start_item = new Date(item.startDate);
                const date_end_item = new Date(item.endDate);
                flag = true;
                if (date_start_element <= date_start_item && date_start_item < date_end_element) {flag = false; return;}
                if (date_start_element < date_end_item && date_end_item <= date_end_element) {flag = false; return;}
                if (date_start_item <= date_start_element && date_end_element <= date_end_item) {flag = false; return;}
            });
            return flag;
        },
        removeService(id) {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.delete('http://localhost:8080/api/hotels/deleteService/' + id + '/' + this.hotel.id, header)
            .then(response => {
                this.$toasted.success('Service ' + response.data + ' successfully deleted.', {duration:2000});
                this.hotel = response.data;
                console.log(this.hotel)
            })
            .catch(error => this.$toasted.error('Error while deleting service.', {duration:2000}))
        },
        removeItem(id) {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            axios.delete('http://localhost:8080/api/rooms/deleteItem/' + id, header)
            .then(response => {
                this.$toasted.success('Item ' + response.data + ' successfully deleted.', {duration:2000});
                this.priceList = response.data;
                console.log(this.priceList)
            })
            .catch(error => this.$toasted.error('Error while deleting item(active reservations).', {duration:2000}))
        }
    },

    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
        const username = localStorage.getItem('username');
        axios.post('http://localhost:8080/api/hotels/findHotelByAdmin', username, header)
        .then(response => { 
            this.hotel = response.data;
            console.log(this.hotel);
            axios.get('http://localhost:8080/api/rooms/prices/' + this.hotel.id, header)
            .then(response => {
                this.priceLists = response.data;
                this.priceLists.forEach(element => {
                    element.hotelPriceListItems.forEach(item => this.formatDates(item));
                });
                this.priceList = this.priceLists[0];
            });
        });
    }
}
</script>

<style>

</style>
