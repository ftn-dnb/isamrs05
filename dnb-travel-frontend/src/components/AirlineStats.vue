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
                bb
            </v-tab-item>

            <v-tab-item>
                cc
            </v-tab-item>
        </v-tabs>
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'AirlineStats',
    props: {},
    components: {},
    
    data() {
        return {
            airline: {
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
        };
    },

    methods: {
    },

    mounted() {
        // @TODO: pokupiti podatke odgovarajuce aviokompanije na osnovu podataka
        // njenog administratora
        // Zasad se uzima predefinisana vrednost iz baze
        axios.get("http://localhost:8080/api/airlines/22")
        .then(response => {
            this.airline = response.data;
            this.airline.ratingInt = parseInt(this.airline.rating);
        });
    }
}
</script>

<style>

</style>
