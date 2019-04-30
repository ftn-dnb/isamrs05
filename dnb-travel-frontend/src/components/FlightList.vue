<template>
    <div>

        <table>
            <tr>
                <td>
                    From <br />
                    <select v-model="filterSearch.startDestination" >
                        <option :value="null">Clear</option>
                        <option v-for="dest in destinations" :value="dest">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>
                <td>
                    To <br />
                    <select v-model="filterSearch.endDestination">
                        <option :value="null">Clear</option>
                        <option v-for="dest in destinations" :value="dest">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    Depart <br />
                    <input type="date" v-model="filterSearch.startDate" />
                </td>
                <td>
                    Return <br />
                    <input type="date" v-model="filterSearch.endDate" />
                </td>
            </tr>
            <tr>
                <td>
                    Max duration {{filterSearch.travelTime}}h <br />
                    <input type="range" v-model="filterSearch.travelTime" min="0" max="50" />
                </td>
                <td>
                    Max length {{filterSearch.travelLength}}km <br />
                    <input type="range" v-model="filterSearch.travelLength" min="0" max="20000" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="checkbox" v-model="filterSearch.isDirect" /> Direct flight
                </td>
                <td>
                    <input type="checkbox" v-model="filterSearch.isOneWay" /> One way 
                </td>
            </tr>
            <tr>
                <td>
                    Min. price <br />
                    <input type="number" v-model="filterSearch.minPrice" placeholder="Minimum" min="0" />
                </td>
                <td>
                    Max. price <br />
                    <input type="number" v-model="filterSearch.maxPrice" placeholder="Maximum" min="0" />
                </td>
            </tr>
            <tr>
                <td>
                    Number of people <br />
                    <input type="number" v-model="filterSearch.numOfPeople" min="0" />
                </td>
                <td>
                    Airline <br />
                    <select v-model="filterSearch.airlineId">
                        <option :value="null">Clear</option>
                        <option v-for="airline in airlines" :value="airline.id">
                            {{airline.name}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" @click="searchFlights" value="Search" />
                </td>
            </tr>
        </table>

        <table border="1">
            <tr>
                <th>Start destination</th>
                <th>End destination</th>
                <th>Take-off date & time</th>
                <th>Landing date & time</th>
                <th>Length (km)</th>
                <th>Travel time (h)</th>
                <th>Num. of transits</td>
                <th v-if="airlineId == null">Airline</th>
                <th>Rating</th>
                <th>&nbsp;</th>
            </tr>
            <tr v-for="flight in flights">
                <td>{{flight.startDestination.city}}, {{flight.startDestination.country}}</td>
                <td>{{flight.endDestination.city}}, {{flight.endDestination.country}}</td>
                <td>{{flight.startDateTime}}</td>
                <td>{{flight.endDateTime}}</td>
                <td>{{flight.travelLength}}</td>
                <td>{{flight.travelTime}}</td>
                <td>{{flight.transits.length}}</td>
                <td v-if="airlineId == null">{{flight.airlineName}}</td>
                <td>{{flight.rating}}</td>
                <td>
                    <router-link :to="{ name: 'FlightDetailedView', params: { flightId: flight.id } }">See details</router-link>
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'FlightList',
    props: {
        airlineId: null
    },
    components: {},
    
    data() {
        return {
            minDate: null,
            airlines: [],
            flights: [],
            destinations: [],
            filterSearch: {
                airlineId: null,
                startDestination: null,
                endDestination: null,
                startDate: null,
                endDate: null,
                travelTime: null,
                travelLength: null,
                minPrice: null,
                maxPrice: null,
                isDirect: null,
                isOneWay: null,
                numOfPeople: null,
            },
        };
    },

    methods: {
        searchFlights() {
            axios.post('http://localhost:8080/api/flights', this.filterSearch)
            .then(response => {this.flights = response.data; })
            .catch(error => this.$toasted('Error while loading flights', {duration:5000}));
        },
    },

    mounted() {
        if (this.airlineId == null) {
            axios.get('http://localhost:8080/api/airlines').then(response => {
                this.airlines = response.data;

                // Copy flight info into new array
                for (let airline of this.airlines) {
                    for (let flight of airline.flights) {
                        flight.airlineName = airline.name;
                        this.flights.push(flight);
                    }
                }
            });
        } else {
            axios.get(`http://localhost:8080/api/airlines/${this.airlineId}/flights`)
            .then(response => this.flights = response.data)
        }

        axios.get('http://localhost:8080/api/destinations')
        .then(response => this.destinations = response.data)
        .catch(error => this.$toasted.error('Error while loading destinations', {duration:5000}));

        let now = new Date();
        const today = now.toISOString().substring(0,10);
        this.searchStartDate = this.searchEndDate = today;

        this.filterSearch.airlineId = this.airlineId;
    }
}
</script>

<style>

</style>
