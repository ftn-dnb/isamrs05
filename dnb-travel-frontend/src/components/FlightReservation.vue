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

        	<h3 class="subheading grey--text">Invite friends</h3>

        	<v-layout row wrap>
	            <v-flex xs12 sm6 md4 lg3 v-for="friend in friendships" :key="friend.friendUsername">
	                <v-card flat class="text-xs-center ma-3" v-if="friend.status == 'ACCEPTED'">
	                    <v-responsive class="pt-4">
	                        <v-icon size="70">person</v-icon>
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

	        <div v-if="invitedFriends.length !== 0">
        		
	        	<br />
	        	<h3 class="subheading grey--text">You invited</h3>

	        	<v-layout row wrap>
		            <v-flex xs12 sm6 md4 lg3 v-for="friend in invitedFriends" :key="friend.friendUsername">
		                <v-card flat class="text-xs-center ma-3">
		                    <v-responsive class="pt-4">
		                        <v-icon size="70">person</v-icon>
		                    </v-responsive>
		                    <v-card-text>
		                        <div class="subheading">{{friend.friendFirstName}} {{friend.friendLastName}}</div>
		                        <div class="grey--text">{{friend.friendUsername}}</div>
		                    </v-card-text>
		                    <v-card-actions>
		                        <v-btn flat small @click="uninviteFriend(friend)">
		                            <v-icon left>person_add_disabled</v-icon>
		                            <span>Uninvite</span>
		                        </v-btn>
		                    </v-card-actions>
		                </v-card>
		            </v-flex>
		        </v-layout>
        	</div>

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
        	<v-form ref="usersDataForm">
        		<div>
        			<h3 class="subheading grey--text">My information</h3>
					<v-text-field label="First name" v-model="user.firstName" :rules="inputRule">
					</v-text-field>

					<v-text-field label="Last name" v-model="user.lastName" :rules="inputRule">
					</v-text-field>

					<v-text-field label="Passport number" v-model="user.passport" :rules="inputRule">
					</v-text-field>
        		</div>
        		<br />

        		<div v-for="friend in invitedFriends">
        			<h3 class="subheading grey--text">Data for {{friend.friendUsername}}</h3>

					<v-text-field label="First name" v-model="friend.friendFirstName" :rules="inputRule">
					</v-text-field>

					<v-text-field label="Last name" v-model="friend.friendLastName" :rules="inputRule">
					</v-text-field>

					<v-text-field label="Passport number" v-model="friend.passport" :rules="inputRule">
					</v-text-field>

					<br />
        		</div>

        		<div v-for="(friend, index) in invitedNonRegistered">
        			<h3 class="subheading grey--text">Friend #{{index + 1}}</h3>

					<v-text-field label="First name" v-model="friend.firstName" :rules="inputRule">
					</v-text-field>

					<v-text-field label="Last name" v-model="friend.lastName" :rules="inputRule">
					</v-text-field>

					<v-text-field label="Passport number" v-model="friend.passport" :rules="inputRule">
					</v-text-field>

					<br />
        		</div>
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

export default {
	name: 'FlightReservation',
	
	props: {
		flightId: null,
	},

	components: {},

	data() {
		return {
			inputRule: [
				v => (v && v.length !== 0) || 'Please fill out this field'
			],
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
            invitedNonRegistered: [],
            seats: [],
            seatsText: [],
            configKonva: {
                width: 700,
                height: 700,
            },
            user: {
            	username: null,
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
			this.invitedNonRegistered = [];
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
			// add users that are not registered on this app
			const numOfNonReg = this.selectedSeats.length - this.invitedFriends.length - this.invitedNonRegistered.length - 1;

			for (let i = 0; i < numOfNonReg; i++) {
				this.invitedNonRegistered.push({firstName: null, lastName: null, passport: null});
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
        		this.$toasted.error('This seat is already taken', {duration:5000});
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
        	const index = this.friendships.findIndex(e => e.friendUsername === friend.friendUsername);
        	this.friendships.splice(index, 1);
        	this.invitedFriends.push(friend);
        },

        uninviteFriend(friend) {
        	const index = this.invitedFriends.findIndex(e => e.friendUsername === friend.friendUsername);
        	this.invitedFriends.splice(index, 1);
        	this.friendships.push(friend);
        },

        buyTickets() {
        	if (!this.$refs.usersDataForm.validate()) {
        		return;
        	}

        	let reservations = {
        		seats: this.selectedSeats,
        		users: []
        	};

        	reservations.users.push({
        		username: this.user.username,
        		firstName: this.user.firstName,
        		lastName: this.user.lastName,
        		passport: this.user.passport
        	});

            for (let fr of this.invitedFriends) {
            	reservations.users.push({
            		username: fr.friendUsername,
            		firstName: fr.friendFirstName,
            		lastName: fr.friendLastName,
            		passport: fr.passport
            	})
            }

            for (let fr of this.invitedNonRegistered) {
            	reservations.users.push({
            		username: null,
            		firstName: fr.firstName,
            		lastName: fr.lastName,
            		passport: fr.passport
            	});
            }

            console.log(reservations);

        	this.$toasted.success('Flight reservation finished', {duration:5000});
        }
	},

	mounted() {
		axios.get(`http://localhost:8080/api/flights/${this.flightId}`)
        .then(response => {
            this.flight = response.data;
            this.createSeatsView();
        })
        .catch(error => {
        	this.$toasted.error("There was an error while getting the information about the flight", {duration: 5000});
        });

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        axios.get(`http://localhost:8080/api/users/info/${localStorage.getItem('username')}`, header)
        .then(response => {
        	this.friendships = response.data.friendships;
        	this.friendships.forEach(fr => fr.passport = null)
        	this.user = response.data;
        })
        .catch(error => this.$toasted.error('Error while loading data about friends.',{duration:5000}));
	
	}
}

</script>

<style>

</style>