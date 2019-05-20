<template>
	<div>
		<h1 class="headline grey--text text--darken-3 text-md-center">
			Flight reservation
		</h1>

		<br />
		<h1 class="headline text-md-center">
            {{flight.startDestination.city}}, {{flight.startDestination.country}}
            <v-icon>arrow_forward</v-icon>
            {{flight.endDestination.city}}, {{flight.endDestination.country}}
        </h1>
        <h3 class="subheading grey--text text--darken-3 text-md-center">
            {{flight.startDestination.airportName}}, {{flight.startDestination.airportCode}}
            <v-icon>arrow_forward</v-icon>
            {{flight.endDestination.airportName}}, {{flight.endDestination.airportCode}}
        </h3>

        <br />

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>flight_takeoff</v-icon>
            <b>Take-off date & time:</b> {{flight.startDateTime}}
        </h3>

        <h3 class="subheading grey--text text--darken-3 text-md-center">
            <v-icon>flight_land</v-icon>
            <b>Landing date & time:</b> {{flight.endDateTime}}
        </h3>

        <br />
        <br />

        <div v-if="reservationSeats">
        	<h3 class="subheading">Choose seats:</h3>
        	<br />
	        <div>
	            <v-stage ref="stage" :config="configKonva">
	                <v-layer ref="layer">
	                    <v-rect v-for="item in seats" :config="item" :key="item.id"></v-rect>
	                    <v-text v-for="item in seatsText" :config="item" :key="item.id"></v-text>
	                </v-layer>
	            </v-stage>
	        </div>

        	<v-btn @click="nextFromReservationSeats">
        		<v-icon left>arrow_forward</v-icon>
        		<span>Next</span>
        	</v-btn>
        </div>
        <div v-else-if="reservationFriends">
        	pozivanje prijatelja

        	<v-btn @click="backFromReservationFriends">
        		<v-icon left>arrow_back</v-icon>
        		<span>Back</span>
        	</v-btn>

        	<v-btn @click="nextFromReservationFriends">
        		<v-icon left>arrow_forward</v-icon>
        		<span>Next</span>
        	</v-btn>

        </div>
        <div v-else-if="reservationUserInfo">
        	unosenje podataka

        	<v-btn @click="backFromReservationUserInfo">
        		<v-icon left>arrow_back</v-icon>
        		<span>Back</span>
        	</v-btn>
        </div>

	</div>
</template>

<script>

import axios from 'axios';
import FlightReservationSeats from './FlightReservationSeats.vue';

export default {
	name: 'FlightReservation',
	
	props: {
		flightId: null,
	},

	components: {
		FlightReservationSeats
	},

	data() {
		return {
			reservationSeats: true,
			reservationFriends: false,
			reservationUserInfo: false,
			flight: {
                id: null,
                startDateTime: null,
                endDateTime: null,
                startDestination: {
                    city: null,
                    country: null,
                    airportName: null,
                    airportCode: null,
                },
                endDestination: {
                    city: null,
                    country: null,
                    airportName: null,
                    airportCode: null,
                },    
                airlineId: null,
                airplane: {
                    name: null
                },
                prices: [], 
                reservations: []      
            },
            selectedSeats: [],
            seats: [],
            seatsText: [],

            configKonva: {
                width: 700,
                height: 700,
            },
		}
	},

	methods: {
		backFromReservationFriends() {
			this.reservationSeats = true;
			this.reservationFriends = false;
			this.reservationUserInfo = false;
		},

		backFromReservationUserInfo() {
			this.reservationUserInfo = false;

			// proveriti ako je korisnik pozvao prijatelje na koju stranicu da ga vrati
			this.reservationFriends = true;
		},

		nextFromReservationSeats() {

			if (this.selectedSeats.length === 0) {
				this.$toasted.info('Please choose at least 1 seat', {duration: 5000});
			} else if (this.selectedSeats.length === 1) {
				this.reservationSeats = false;
				this.reservationFriends = false;
				this.reservationUserInfo = true;
			} else {
				this.reservationSeats = false;
				this.reservationFriends = true;
				this.reservationUserInfo = false;
			}
		},

		nextFromReservationFriends() {
			this.reservationFriends = false;
			this.reservationUserInfo = true;
		},

		configureKonva(rectWidth, marginSize) {
            this.configKonva.width = this.flight.airplane.numOfColumns * (rectWidth + marginSize);
            this.configKonva.height = this.flight.airplane.numOfRows * (rectWidth + marginSize);
        },

        createSeatsView() {
            const rectSize = 35;
            const margin = 5;

            this.configureKonva(rectSize, margin);

            for (let i = 0; i < this.flight.airplane.numOfColumns; ++i) {
                for (let j = 0; j < this.flight.airplane.numOfRows; ++j) {
                    this.seats.push({
                        x: i * (rectSize + margin),
                        y: j * (rectSize + margin),
                        width: rectSize,
                        height: rectSize,
                        fill: 'green',
                        stroke: 'black',
                    });

                    this.seatsText.push({
                        x: i * (rectSize + margin) + 3,
                        y: j * (rectSize + margin) + 12,
                        text: `${j + 1}/${i + 1}`,
                        fontSize: 12,
                        fill: 'white'
                    })
                }
            }

            for (let reservation of this.flight.reservations) {
                this.seats.push({
                    x: (reservation.seatColumn - 1) * (rectSize + margin),
                    y: (reservation.seatRow - 1) * (rectSize + margin),
                    width: rectSize,
                    height: rectSize,
                    fill: 'red',
                    stroke: 'black'
                });
            }
        },
	},

	mounted() {
		axios.get(`http://localhost:8080/api/flights/${this.flightId}`)
        .then(response => {
            this.flight = response.data;
            this.createSeatsView();
        }).catch(error => {
        	this.$toasted.error("There was an error while getting the information about the flight", {duration: 5000});
        });
	}
}

</script>

<style>

</style>