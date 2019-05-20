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
	                    <v-rect v-for="item in seats" :config="item" @click="seatClick(item)"></v-rect>
	                    <v-text v-for="item in seatsText" :config="item" :key="item.id" @click="seatClick(item)"></v-text>
	                </v-layer>
	            </v-stage>
	        </div>

        	<v-btn @click="nextFromReservationSeats">
        		<v-icon left>arrow_forward</v-icon>
        		<span>Next</span>
        	</v-btn>
        </div>
        <div v-else-if="reservationFriends">
        	<v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="friend in friendships" :key="friend.friendUsername">
                <v-card flat class="text-xs-center ma-3" v-if="friend.status == 'ACCEPTED'">
                    <v-responsive class="pt-4">
                        <v-icon size="50">person</v-icon>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{friend.friendFirstName}} {{friend.friendLastName}}</div>
                        <div class="grey--text">{{friend.friendUsername}}</div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small @click="inviteFriend(friend)">
                            <v-icon left>person_add</v-icon>
                            <span>Invite</span>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>

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
        	<v-form>
				<h3 class="subheading grey--text">My information</h3>
				<v-text-field label="First name" v-model="user.firstName">
				</v-text-field>

				<v-text-field label="Last name" v-model="user.lastName">
				</v-text-field>

				<v-text-field label="Passport number">
				</v-text-field>
        	</v-form>

        	<br />

        	<v-form v-for="friend in invitedFriends">
        		<h3 class="subheading grey--text">Data for {{friend.friendUsername}}</h3>
				<v-text-field label="First name" v-model="friend.friendFirstName">
				</v-text-field>

				<v-text-field label="Last name" v-model="friend.friendLastName">
				</v-text-field>

				<v-text-field label="Passport number">
				</v-text-field>
        	</v-form>

        	<v-btn @click="backFromReservationUserInfo">
        		<v-icon left>arrow_back</v-icon>
        		<span>Back</span>
        	</v-btn>
        	<v-btn @click="buyTickets">
        		<v-icon left>attach_money</v-icon>
        		<span>Buy tickets</span>
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

	components: {},

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
            friendships: [],
            selectedSeats: [],
            invitedFriends: [],
            seats: [],
            seatsText: [],
            configKonva: {
                width: 700,
                height: 700,
            },
            user: {
            	firstName: null,
            	lastName: null,
            	passport: null,
            }
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

			if (this.selectedSeats.length === 1) {
				this.reservationSeats = true;
				this.reservationFriends = false;
			} else {
				this.reservationSeats = false;
				this.reservationFriends = true;
			}
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
			if (this.selectedSeats.length - 1 !== this.invitedFriends.length) {
				
				this.$toasted.error(`You need to invite ${this.selectedSeats.length - this.invitedFriends.length - 1} more friends`, {duration:5000});
				return;
			}

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
                    let seat =  {
                    	seatX: j + 1,
                    	seatY: i + 1,
                    	taken: false,
                        x: i * (rectSize + margin),
                        y: j * (rectSize + margin),
                        width: rectSize,
                        height: rectSize,
                        fill: 'green	',
                        stroke: 'black',
                    };

                    this.seats.push(seat);

                    this.seatsText.push({
                        x: i * (rectSize + margin) + 3,
                        y: j * (rectSize + margin) + 3,
                        text: `${j + 1}/${i + 1}`,
                        fontSize: 12,
                        fill: 'white',
                        isText: true
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
                    stroke: 'black',
                    reserved: true
                });
            }
        },

        seatClick(item) {
        	if (item.reserved) {
        		this.$toasted.error('This seat is already taken');
        		return;
        	}

        	if (item.isText) {
        		return;
        	}

        	const seatX = item.seatX;
        	const seatY = item.seatY;

        	if (item.taken) {
	       		item.fill = 'green';
	       		
	       		for (let i = 0; i < this.selectedSeats.length; ++i) {
	       			if (this.selectedSeats[i].x === seatX && this.selectedSeats[i].y === seatY) {
	       				this.selectedSeats.splice(i, 1);
	       				break;
	       			}
	       		}

	       		item.taken = false;
	       	} else {
	       		this.selectedSeats.push({x: seatX, y: seatY});
	       		item.fill = 'blue'
	       		item.taken = true;
	        }
        },

        inviteFriend(friend) {
        	// neka provera da ne moze 2x nekog da pozove
        	this.invitedFriends.push(friend);
        	this.$toasted.show('You invited ' + friend.friendFirstName + ' ' + friend.friendLastName, {duration:5000});
        },

        buyTickets() {
        	this.$toasted.success('Flight reservation finished', {duration:5000});
        }
	},

	mounted() {
		axios.get(`http://localhost:8080/api/flights/${this.flightId}`)
        .then(response => {
            this.flight = response.data;
            this.createSeatsView();
        })
        // .catch(error => {
        // 	this.$toasted.error("There was an error while getting the information about the flight", {duration: 5000});
        // });

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        axios.get(`http://localhost:8080/api/users/info/${localStorage.getItem('username')}`, header)
        .then(response => {
        	this.friendships = response.data.friendships;
        	this.user = response.data;
        })
        .catch(error => this.$toasted.error('Error while loading data about friends.',{duration:5000}));
	
	}
}

</script>

<style>

</style>