<template>
    <div>
        <h3 class="subheading grey--text">Add new flight</h3>

        <v-form ref="addNewFlightForm">
            <v-combobox label="Select start destination" 
                v-model="flightToAdd.startDestination" 
                :items="airline.destinations" 
                item-text="airportName" 
                :rules="inputRule"
                return-object
                prepend-icon="flight_takeoff"
            ></v-combobox>

            <v-combobox label="Select end destination" 
                v-model="flightToAdd.endDestination" 
                :items="airline.destinations" 
                item-text="airportName"
                :rules="inputRule"
                return-object
                prepend-icon="flight_land"
            ></v-combobox>

            <v-menu>
                <v-text-field label="Departure date" 
                    slot="activator" 
                    :value="startDate"
                    prepend-icon="event"
                    :rules="flightRuleStartDate"
                ></v-text-field>
                <v-date-picker v-model="startDate">
                </v-date-picker>
            </v-menu>
            <v-menu>
                <v-text-field label="Departure time" 
                    slot="activator" 
                    :value="startTime"
                    prepend-icon="schedule"
                    :rules="inputRule"
                    ></v-text-field>
                <v-time-picker v-model="startTime">
                </v-time-picker>
            </v-menu>
            <br/>
            <v-menu>
                <v-text-field label="Return date" 
                    slot="activator" 
                    :value="endDate"
                    prepend-icon="event"
                    :rules="flightRuleEndDate"
                    ></v-text-field>
                <v-date-picker v-model="endDate">
                </v-date-picker>
            </v-menu>
            <v-menu>
                <v-text-field label="Return time" 
                    slot="activator" 
                    :value="endTime"
                    prepend-icon="schedule"
                    :rules="inputRule"
                    ></v-text-field>
                <v-time-picker v-model="endTime">
                </v-time-picker>
            </v-menu>

            <v-select label="Transits" 
                v-model="flightToAdd.transits" 
                :items="destinations" 
                multiple 
                attach 
                chips 
                item-text="airportName" 
                return-object
                prepend-icon="transit_enterexit"
            ></v-select>

            <v-combobox label="Airplane" 
                v-model="flightToAdd.airplane" 
                :items="airplanes" 
                item-text="name" 
                return-object
                prepend-icon="local_airport"
                :rules="inputRule"
            ></v-combobox>

            <v-text-field type="number"
                label="Travel length (km)" 
                v-model="flightToAdd.travelLength" 
                min="0" 
                prepend-icon="directions_walk"
                :rules="travelLengthRule"
            ></v-text-field>

            <v-checkbox label="One-way" v-model="flightToAdd.isOneWay"></v-checkbox>

            <v-data-table :headers="priceListHeaders" :items="flightToAdd.prices">
                <template v-slot:items="props">
                    <td>{{ props.item.startDate }}</td>
                    <td>{{ props.item.endDate }}</td>
                    <td>{{ props.item.price }}</td>
                    <td>{{ props.item.activeDiscount }}</td>
                </template>

                <template v-slot:footer>
                    <td :colspan="priceListHeaders.length">
                        <v-dialog v-model="dialog" width="500">
                            <template v-slot:activator="{ on }">
                                <v-btn v-on="on">
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
                                        <v-menu>
                                            <v-text-field label="Start date" 
                                                slot="activator" 
                                                :value="priceItem.startDate"
                                                :rules="priceItemStartDate"
                                                prepend-icon="event"
                                                ></v-text-field>
                                            <v-date-picker v-model="priceItem.startDate">
                                            </v-date-picker>
                                        </v-menu>

                                        <v-menu>
                                            <v-text-field label="End date" 
                                                slot="activator" 
                                                :value="priceItem.endDate"
                                                :rules="priceItemEndDate"
                                                prepend-icon="event"
                                            ></v-text-field>
                                            <v-date-picker v-model="priceItem.endDate"></v-date-picker>
                                        </v-menu>

                                        <v-text-field type="number" 
                                            label="Price" 
                                            v-model="priceItem.price" min="0"
                                            :rules="priceItemPrice"
                                            prepend-icon="attach_money"
                                        ></v-text-field>
                                        
                                        <v-text-field type="number" 
                                            label="Discount %" 
                                            v-model="priceItem.activeDiscount" 
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
            
            <v-btn @click="addFlight">
                <v-icon left>add</v-icon>
                <span>Add flight</span>
            </v-btn>
        </v-form>        

    </div>
</template>

<script>

import axios from 'axios';
import format from 'date-fns/format';

export default {
    name: 'AddFlight',
    props: {},
    components: {},
    
    data() {
        return {
            dialog: false,
            priceListHeaders: [
                { text: 'From', value: 'startDate' },
                { text: 'To', value: 'endDate' },
                { text: 'Price', value: 'price' },
                { text: 'Discount %', value: 'activeDiscount' },
            ],
            priceItemStartDate: [
                v => (v && v.length > 0) || 'Please select start date',
                v => (v && this.isDateGreaterThan(this.priceItem.endDate, this.priceItem.startDate)) || 'Start date must be before end date'
            ],
            priceItemEndDate: [
                v => (v && v.length > 0) || 'Please select end date',
                v => (v && this.isDateGreaterThan(this.priceItem.endDate, this.priceItem.startDate)) || 'End date must be after start date'
            ],
            priceItemPrice: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (v && v >= 0) || 'Price can\'t be below 0'
            ],
            priceItemDiscount: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (v && v >= 0 && v <= 100) || 'Discount must be between 0 and 100 %'
            ],
            inputRule: [
                v => (v !== null) || 'Please fill out this field',
            ],
            flightRuleStartDate: [
                v => (v && v.length > 0) || 'Please select start date',
                v => (v && this.isDateGreaterThan(this.endDate, this.startDate)) || 'Start date must be before end date'
            ],
            flightRuleEndDate: [
                v => (v && v.length > 0) || 'Please select end date',
                v => (v && this.isDateGreaterThan(this.endDate, this.startDate)) || 'End date must be after start date'
            ],
            travelLengthRule: [
                v => (v !== null) || 'Please fill out this field',
                v => (v && v >= 0) || 'Travel length can\'t be negative number'
            ],

            minDate: null,
            minDateTime: null,
            startTime: null,
            endTime: null,
            startDate: null,
            endDate: null,
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
            destinations: [],
            airplanes: [],
            flightToAdd: {
                startDestination: null,
                endDestination: null,
                startDateTime: null,
                endDateTime: null,
                travelLength: null,
                travelTime: null,
                transits: [],
                airlineId: null,
                prices: [],
                airplane: null,
                isOneWay: null,
            },
            priceItem: {
                startDate: null,
                endDate: null,
                price: null,
                activeDiscount: null,
            },
        };
    },

    methods: {
        addPriceListItem() {
            if (!this.$refs.addPriceListItemForm.validate()) {
                return;
            }

            this.flightToAdd.prices.push(JSON.parse(JSON.stringify(this.priceItem)));
            this.priceItem.startDate = null;
            this.priceItem.endDate = null;
            this.priceItem.price = null;
            this.priceItem.activeDiscount = null;

            this.dialog = false;
        },

        addFlight() {
            if (!this.$refs.addNewFlightForm.validate()) {
                return;
            }

            this.flightToAdd.travelTime = this.getHoursDifference(this.flightToAdd.startDateTime, this.flightToAdd.endDateTime);
            this.flightToAdd.airlineId = 22; // @TODO promeniti

            let startDateTime = new Date(this.startDate);
            startDateTime.setHours(this.startTime.split(':')[0]);
            startDateTime.setMinutes(this.startTime.split(':')[1])

            let endDateTime = new Date(this.endDate);
            endDateTime.setHours(this.endTime.split(':')[0]);
            endDateTime.setMinutes(this.endTime.split(':')[1]);

            this.flightToAdd.startDateTime = startDateTime;
            this.flightToAdd.endDateTime = endDateTime;

            if (!this.checkAddFlightForm()) {
                return;
            }

            axios.post('http://localhost:8080/api/airlines/' + this.airline.id + '/flights', this.flightToAdd, { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} })
            .then(response => {
                this.$toasted.success('Flight successfuly created.', {duration:5000});
            }).catch(error => {
                this.$toasted.error('There was an error while creating new flight.', {duration:5000});
            });

        },

        checkAddFlightForm() {
            if (this.flightToAdd.prices.length === 0) {
                this.$toasted.info('You must enter at least one price list item.', {duration:5000});
                return false;
            } else if (this.isDateGreaterThan(this.flightToAdd.startDateTime, this.flightToAdd.endDateTime)) {
                this.$toasted.info('Takeoff date and time must be before landing date and time', {duration:5000});
                return false;
            } else if (this.flightToAdd.startDestination.id === this.flightToAdd.endDestination.id) {
                this.$toasted.info('Start and end destinations must be different.', {duration:5000});
                return false;
            } else if (this.isDestinationInTransits(this.flightToAdd.startDestination.id) != null) {
                this.$toasted.info('Start destination can\' be in transits list.', {duration:5000});
                return false;
            } else if (this.isDestinationInTransits(this.flightToAdd.endDestination.id) != null) {
                this.$toasted.info('End destination can\' be in transits list.', {duration:5000});
                return false;
            }

            return true;
        },

        formattedDate(date) {
            return date ? format(date, 'Do MMM YYYY') : '';
        },

        isDateGreaterThan(date1, date2) {
            const d1 = new Date(date1);
            const d2 = new Date(date2);
            return d1.getTime() >= d2.getTime();
        },

        isDestinationInTransits(destinationId) {
            return this.flightToAdd.transits.find(dest => dest.id === destinationId);
        },

        getHoursDifference(date1, date2) {
            let startDateTime = Date.parse(date1);
            let endDateTime = Date.parse(date2);
            return Math.abs(endDateTime - startDateTime) / (60 * 60 * 1000);
        },
    },

    mounted() {
        const username = localStorage.getItem('username');
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        axios.post(`http://localhost:8080/api/airlines/company/${username}`, {}, header)
        .then(response => this.airline = response.data)
        .catch(error => this.$toasted.error('Error while loading airline data', {duration:5000}));

        // Get list of destinations that can be used for list of transits for flight
        axios.get('http://localhost:8080/api/destinations').then(response => this.destinations = response.data);

        // Get list of airplanes
        axios.get('http://localhost:8080/api/airplanes').then(response => this.airplanes = response.data);
    
        let now = new Date();
        this.minDate = now.toISOString().substring(0,10);
        this.minDateTime = now.toISOString().substring(0, 16); 
        //this.flightToAdd.startDateTime = this.flightToAdd.endDateTime = this.minDateTime;
        this.priceItem.startDate = this.priceItem.endDate = this.minDate;
    }
}
</script>

<style>

</style>
