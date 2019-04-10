<template>
    <div>
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
                        <option v-for="ap in airplanes">{{ap.name}}</option>
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
            }
        };
    },

    methods: {
        addFlight() {
            console.log("Adding flight");
        },
    },

    mounted() {
        // napraviti posebnu komponentu koja ce sluziti za dodavanje novih destinacija za poslovanj

        // Dobaviti aviokompaniju za ulogovanog admina - pa se preko toga dobave i destinacije

        // Dobaviti listu aviona
    }
}
</script>

<style>

</style>
