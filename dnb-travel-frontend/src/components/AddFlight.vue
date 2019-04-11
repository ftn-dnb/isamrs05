<template>
    <div>
        <h3>Your company is: {{airline.name}}</h3>
        <h3>Fill out this form to add new flight</h3>

        <table>
            <tr>
                <td>Start destination:</td>
                <td>
                    <select v-model="flightToAdd.startDestination">
                        <option v-for="dest in airline.destinations">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>End destination:</td>
                <td>
                    <select v-model="flightToAdd.endDestination">
                        <option v-for="dest in airline.destinations">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Take off date & time</td>
                <td><input type="datetime-local" v-model="flightToAdd.startDateTime" /></td>
            </tr>
            <tr>
                <td>Landing date & time</td>
                <td><input type="datetime-local" v-model="flightToAdd.endDateTime" /></td>
            </tr>
            <tr>
                <td>Transit destinations:</td>
                <td>
                    <select multiple>
                        <option v-for="dest in destinations">
                            {{dest.airportName}}, {{dest.city}}, {{dest.country}}
                        </option>
                    </select>
                </td>                
            </tr>
            <tr>
                <td>Airplane:</td>
                <td>
                    <select>
                        <option v-for="ap in airplanes">{{ap.name}}, {{ap.numOfRows * ap.numOfColumns}} seats</option>
                    </select>
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
                travelTime: null,
            },
        };
    },

    methods: {
        addFlight() {
            // dodati proveru forme
            // ne moze biti ista pocetna i kranja destinacija
            // u transit ne smeju biti pocetna i krajnja destinacija
            // odabrano vreme
            // transit destinations moze a i ne mora biti odabrano
            // odabran avion

            axios.post('http://localhost:8080/api/airlines/' + this.airline.id + '/flights', this.flightToAdd).then(response => {
                console.log(reponse.data);
            });

        },
    },

    mounted() {
        // Dobaviti aviokompaniju za ulogovanog admina - pa se preko toga dobave i destinacije
        // @TODO: popraviti ovo da se ne dovaljva prva aviokompanija vec ona za koju je korisnik admin
        axios.get('http://localhost:8080/api/airlines/22').then(response => this.airline = response.data);

        // Get list of destinations that can be used for list of transits for flight
        axios.get('http://localhost:8080/api/destinations').then(response => this.destinations = response.data);

        // napraviti posebnu komponentu koja ce sluziti za dodavanje novih destinacija za poslovanj

        // Get list of airplanes
        axios.get('http://localhost:8080/api/airplanes').then(response => this.airplanes = response.data);
    }
}
</script>

<style>

</style>
