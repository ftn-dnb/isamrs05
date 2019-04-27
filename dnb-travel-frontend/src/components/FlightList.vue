<template>
    <div>
        <div>
            <select v-model="filterSearch.startDestination" >
                <option disabled >Select start destination</option>
                <option v-for="dest in destinations" :value="dest">
                    {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                </option>
            </select>

            TO

            <select v-model="filterSearch.endDestination">
                <option disabled >Select end destination</option>
                <option v-for="dest in destinations" :value="dest">
                    {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                </option>
            </select>

            <br />

            <input type="date" v-model="filterSearch.startDate" />
            TO
            <input type="date" v-model="filterSearch.endDate" />
            
            <input type="button" @click="searchFlights" value="Search" />
        </div>

        <div>
            <div>
                Transits <br />
                <input type="checkbox" v-model="filterSearch.isDirect" /> Direct <br />
                <input type="checkbox" v-model="filterSearch.is1Transit" /> 1 <br />
                <input type="checkbox" v-model="filterSearch.is2Transits" /> 2 <br />
                <input type="checkbox" v-model="filterSearch.is3PlusTransits" /> 3+ <br />
            </div>

            <div>
                Max duration {{filterSearch.travelTime}}h <br />
                <input type="range" v-model="filterSearch.travelTime" min="0" max="50" />
            </div>

            <div>
                Max length {{filterSearch.travelLength}}km <br />
                <input type="range" v-model="filterSearch.travelLength" min="0" max="20000" />
            </div>

            <div>
                Price range <br />
                <input type="number" v-model="filterSearch.minPrice" placeholder="Minimum" /> <br />
                <input type="number" v-model="filterSearch.maxPrice" placeholder="Maximum" />
            </div>

        </div>

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
                is1Transit: null,
                is2Transits: null,
                is3PlusTransits: null,
            },
        };
    },

    methods: {
        searchFlights() {

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
        .catch(error => alert('Error while loading destinations'));

        let now = new Date();
        const today = now.toISOString().substring(0,10);
        this.searchStartDate = this.searchEndDate = today;

        this.filterSearch.airlineId = this.airlineId;
    }
}
</script>

<style>

</style>
