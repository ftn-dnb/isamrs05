<template>
    <div align="center">
        <v-tabs fixed-tabs>
            <v-tab>
                Ratings
            </v-tab>
            <v-tab>
                Income
            </v-tab>
            <v-tab>
                Reservations
            </v-tab>
            
            <v-tab-item>
                <div>
                    <br />
                    <h2 class="grey--text">Hotel rating &nbsp;</h2>
                    <v-rating v-model="hotel.rating" readonly></v-rating>
                    
                    <v-data-table :headers="tableHeaders" :items="hotel.rooms">
                        <template v-slot:items="props">
                            <td>{{ props.item.roomNumber }}</td>
                            <td>{{ props.item.capacity }}</td>
                            <td>{{ props.item.floor }}</td>
                            <td>{{ props.item.rating }} / 5</td>
                        </template>
                    </v-data-table>
                </div>
            </v-tab-item>

            <v-tab-item>
                <v-menu>
                    <v-text-field label="From" slot="activator" prepend-icon="event" v-model="filter.dateFrom"></v-text-field>
                    <v-date-picker v-model="filter.dateFrom"></v-date-picker>
                </v-menu>
                <v-menu>
                    <v-text-field label="To" slot="activator" prepend-icon="event" v-model="filter.dateTo"></v-text-field>
                    <v-date-picker v-model="filter.dateTo"></v-date-picker>
                </v-menu>

                <v-btn @click="showIncome" small>
                    <v-icon left>search</v-icon>
                    <span>Search</span>
                </v-btn>

                <h3 v-if="incomeReportError">No data available</h3>
                <GChart v-else-if="!incomeReportError"
                    type="LineChart"
                    :data="chartDataIncome"
                />
            </v-tab-item>

            <v-tab-item>
                <v-menu>
                    <v-text-field label="To" slot="activator" prepend-icon="event" v-model="filter.dateReservations"></v-text-field>
                    <v-date-picker v-model="filter.dateReservations"></v-date-picker>
                </v-menu>

                <v-flex xs4>
                    <v-select
                        label="Report type"
                        :items="['Day', 'Week', 'Month']"
                        v-model="filter.reservationsCriteria"
                    ></v-select>
                </v-flex>

                <v-btn @click="showReservationStats" small>
                    <v-icon left>search</v-icon>
                    <span>Search</span>
                </v-btn>

                <h3 v-if="reservationsReportError">No data available</h3>
                <GChart v-else-if="!reservationsReportError"
                    type="LineChart"
                    :data="chartDataReservations"
                />
            </v-tab-item>
        </v-tabs>
    </div>
</template>

<script>

import axios from 'axios';
import GChart from 'vue-google-charts';

export default {
    name: 'HotelStats',
    props: {},
    components: {},
    
    data() {
        return {
            filter: {
                hotel_id: null,
                dateFrom: null,
                dateTo: null,
                dateReservations: null,
                reservationsCriteria: 'Week',
            },
            hotel: {},
            tableHeaders: [
                { text: 'Room', value: 'roomNumber' },
                { text: 'Capacity', value: 'capacity' },
                { text: 'Floor', value: 'floor' },
                { text: 'Rating', value: 'rating' },
            ],
            chartDataIncome: [['Date', 'Income'], [0, 0]],
            chartDataReservations: [['Date', 'Number of reservations'], [0, 0]],
            incomeReportError: false,
            reservationsReportError: false,
        };
    },

    methods: {
        showIncome() {
            const header = {headers:{"Authorization":`Bearer ${localStorage.getItem('user-token')}`} };

            this.filter.hotel_id = this.hotel.id;

            axios.post('http://localhost:8080/api/hotels/stats', this.filter, header)
            .then(response => {
                if (response.data.length === 0) {
                    this.incomeReportError = true;
                    return;
                }

                this.chartDataIncome = [['Date', 'Income']];
                this.incomeReportError = false;

                for (let inc of response.data) {
                    this.chartDataIncome.push([inc.date, inc.income]);
                }
            });
        },

        showReservationStats() {
            const header = {headers:{"Authorization":`Bearer ${localStorage.getItem('user-token')}`} };

            this.filter.hotel_id = this.hotel.id;

            axios.post('http://localhost:8080/api/hotels/reservation_stats', this.filter, header)
            .then(response => {
                if (response.data.length === 0) {
                    this.reservationsReportError = true;
                    return;
                }

                this.chartDataReservations = [['Date', 'Number of reservations']];
                this.reservationsReportError = false;

                for (let res of response.data) {
                    this.chartDataReservations.push([res.date, res.number]);
                }
            });
        },
    },

    mounted() {
        const username = localStorage.getItem('username');
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        axios.post('http://localhost:8080/api/hotels/findHotelByAdmin', username, header)
        .then(response => {
            this.hotel = response.data;
            console.log(this.hotel);

            this.showIncome();
            this.showReservationStats();
        });
    }
}
</script>

<style>

</style>
