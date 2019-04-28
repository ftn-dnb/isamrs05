<template>
    <div>
        <h3>Your company is: {{airline.name}}</h3>
        <h3>Fill out this form to add new flight</h3>

        <table>
            <tr>
                <td>Start destination</td>
                <td>
                    <select v-model="flightToAdd.startDestination">
                        <option v-for="dest in airline.destinations" :value="dest">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>End destination</td>
                <td>
                    <select v-model="flightToAdd.endDestination">
                        <option v-for="dest in airline.destinations" :value="dest">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Take off date & time</td>
                <td><input type="datetime-local" v-model="flightToAdd.startDateTime" :min="minDateTime" /></td>
            </tr>
            <tr>
                <td>Landing date & time</td>
                <td><input type="datetime-local" v-model="flightToAdd.endDateTime" :min="minDateTime" /></td>
            </tr>
            <tr>
                <td>Transit destinations</td>
                <td>
                    <select multiple v-model="flightToAdd.transits">
                        <option v-for="dest in destinations" :value="dest">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>                
            </tr>
            <tr>
                <td>Airplane</td>
                <td>
                    <select v-model="flightToAdd.airplane">
                        <option v-for="ap in airplanes" :value="ap">
                            {{ap.name}}, {{ap.numOfRows * ap.numOfColumns}} seats
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Travel length (km)</td>
                <td>
                    <input type="number" v-model="flightToAdd.travelLength" /> 
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox" v-model="flightToAdd.isOneWay" /> One-way
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <table>
                        <caption>Price list</caption>
                        <tr>
                            <th>From</th>
                            <th>To</th>
                            <th>Price</th>
                            <th>Discount</th>
                            <th>&nbsp;</th>
                        </tr>
                        <tr v-for="price in flightToAdd.prices">
                            <td>{{price.startDate}}</td>
                            <td>{{price.endDate}}</td>
                            <td>{{price.price}}</td>
                            <td>{{price.activeDiscount}}</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <input type="date" v-model="priceItem.startDate" :min="minDate" />
                            </td>
                            <td>
                                <input type="date" v-model="priceItem.endDate" :min="minDate" />
                            </td>
                            <td>
                                <input type="number" v-model="priceItem.price" min="0" />
                            </td>
                            <td>
                                <input type="number" v-model="priceItem.activeDiscount" min="0" max="100" />
                            </td>
                            <td>
                                <input type="button" value="Add price item" @click="addPriceListItem" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="button" value="Add flight" @click="addFlight" />
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'AddFlight',
    props: {},
    components: {},
    
    data() {
        return {
            minDate: null,
            minDateTime: null,
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
                price: 0,
                activeDiscount: 0,
            },
        };
    },

    methods: {
        addPriceListItem() {
            if (!this.checkPriceListForm()) {
                return;
            }

            this.flightToAdd.prices.push(JSON.parse(JSON.stringify(this.priceItem)));
            this.priceItem.startDate = null;
            this.priceItem.endDate = null;
            this.priceItem.price = 0;
            this.priceItem.activeDiscount = 0;
        },

        addFlight() {
            if (!this.checkAddFlightForm()) {
                return;
            }

            this.flightToAdd.travelTime = this.getHoursDifference(this.flightToAdd.startDateTime, this.flightToAdd.endDateTime);
            this.flightToAdd.airlineId = 22; // @TODO promeniti

            axios.post('http://localhost:8080/api/airlines/' + this.airline.id + '/flights', this.flightToAdd, { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} })
            .then(response => {
                alert('Flight successfuly created.');

            }).catch(error => {
                alert('There was an error while creating new flight.');
            });

        },

        checkAddFlightForm() {
            if (this.flightToAdd.startDestination == null) {
                alert('You must choose start destination.');
                return false;
            } else if (this.flightToAdd.endDestination == null) {
                alert('You must choose end destination.');
                return false;
            } else if (this.flightToAdd.startDateTime == null) {
                alert('You must pick take off date and time.');
                return false;
            } else if (this.flightToAdd.endDateTime == null) {
                alert('You must pick landing date and time.');
                return false;
            } else if (this.flightToAdd.airplane == null) {
                alert('You must choose airplane for this flight.');
                return false;
            } else if (this.flightToAdd.prices.length === 0) {
                alert('You must enter at least one price list item.');
                return false;
            } else if (this.flightToAdd.travelLength === '' || this.flightToAdd.travelLength == null) {
                alert('You must enter travel length.');
                return false;
            } else if (this.isDateGreaterThan(this.flightToAdd.startDateTime, this.flightToAdd.endDateTime)) {
                alert('Takeoff date and time must be before landing date and time');
                return false;
            } else if (this.flightToAdd.startDestination.id === this.flightToAdd.endDestination.id) {
                alert('Start and end destinations must be different.');
                return false;
            } else if (this.isDestinationInTransits(this.flightToAdd.startDestination.id) != null) {
                alert('Start destination can\' be in transits list.');
                return false;
            } else if (this.isDestinationInTransits(this.flightToAdd.endDestination.id) != null) {
                alert('End destination can\' be in transits list.');
                return false;
            }

            return true;
        },

        checkPriceListForm() {
            if (this.priceItem.startDate == null) {
                alert('You must choose start date for price list item.');
                return false;
            } else if (this.priceItem.endDate == null) {
                alert('You must choose end date for price list item.');
                return false;
            } else if (this.priceItem.price === '') {
                alert('You must enter price.');
                return false;
            } else if (this.priceItem.price < 0) {
                alert('Price must be above 0.');
                return false;
            } else if (this.priceItem.activeDiscount === '') {
                alert('You must enter discount.');
                return false;
            } else if (this.priceItem.activeDiscount < 0 || this.priceItem.activeDiscount > 100) {
                alert('Discount must be between 0 and 100.');
                return false;
            } else if (this.isDateGreaterThan(this.priceItem.startDate, this.priceItem.endDate)) {
                alert('Start date must be before end date.');
                return false;
            } 

            return true;
        },

        isDateGreaterThan(date1, date2) {
            const d1 = new Date(date1);
            const d2 = new Date(date2);
            return d1.getTime() > d2.getTime();
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
        // Dobaviti aviokompaniju za ulogovanog admina - pa se preko toga dobave i destinacije
        // @TODO: popraviti ovo da se ne dovaljva prva aviokompanija vec ona za koju je korisnik admin
        axios.get('http://localhost:8080/api/airlines/22').then(response => this.airline = response.data);

        // Get list of destinations that can be used for list of transits for flight
        axios.get('http://localhost:8080/api/destinations').then(response => this.destinations = response.data);

        // Get list of airplanes
        axios.get('http://localhost:8080/api/airplanes').then(response => this.airplanes = response.data);
    
        let now = new Date();
        this.minDate = now.toISOString().substring(0,10);
        this.minDateTime = now.toISOString().substring(0, 16); 
        this.flightToAdd.startDateTime = this.flightToAdd.endDateTime = this.minDateTime;
        this.priceItem.startDate = this.priceItem.endDate = this.minDate;
    }
}
</script>

<style>

</style>
