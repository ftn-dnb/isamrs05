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
                    <h2 class="grey--text">Company rating &nbsp;</h2>
                    <v-icon v-for="i in airline.ratingInt">star</v-icon>
                    <v-icon v-for="i in 5 - airline.ratingInt">star_border</v-icon>

                    <v-data-table :headers="tableHeaders" :items="airline.flights">
                        <template v-slot:items="props">
                            <td>{{ props.item.startDestination.airportName }}</td>
                            <td>{{ props.item.endDestination.airportName }}</td>
                            <td>{{ props.item.startDateTime }}</td>
                            <td>{{ props.item.endDateTime }}</td>
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
    name: 'AirlineStats',
    props: {},
    components: {},
    
    data() {
        return {
            filter: {
                airlineId: null,
                dateFrom: null,
                dateTo: null,
                dateReservations: null,
                reservationsCriteria: 'Week',
            },
            airline: {
                id: null,
                rating: 0,
                ratingInt: 0,
                flights: [],
            },
            tableHeaders: [
                { text: 'Start destination', value: 'startDestination' },
                { text: 'End destination', value: 'endDestination' },
                { text: 'Depart date time', value: 'startDateTime' },
                { text: 'Return date time', value: 'endDateTime' },
                { text: 'Rating', value: 'rating' },
            ],
            chartDataIncome: [['Date', 'Income'], [0, 0]],
            chartDataReservations: [['Date', 'Number of reservations'] [0, 0]],
            incomeReportError: false,
            reservationsReportError: false,
        };
    },

    methods: {
        showIncome() {
            const header = {headers:{"Authorization":`Bearer ${localStorage.getItem('user-token')}`} };

            this.filter.airlineId = this.airline.id;

            axios.post('http://localhost:8080/api/airlines/stats', this.filter, header)
            .then(response => {
                if (response.data.income.length === 0) {
                    this.incomeReportError = true;
                    return;
                }

                this.chartDataIncome = [['Date', 'Income']];
                this.incomeReportError = false;

                for (let inc of response.data.income) {
                    this.chartDataIncome.push([inc.date, inc.income]);
                }
            });
        },

        showReservationStats() {
            const header = {headers:{"Authorization":`Bearer ${localStorage.getItem('user-token')}`} };

            this.filter.airlineId = this.airline.id;

            axios.post('http://localhost:8080/api/airlines/stats/reservations', this.filter, header)
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

        axios.post(`http://localhost:8080/api/airlines/company/${username}`, {}, header)
        .then(response => {
            this.airline = response.data;
            this.airline.ratingInt = parseInt(this.airline.rating);
        });

        this.showIncome();
        this.showReservationStats();
    }
}
</script>

<style>

</style>
