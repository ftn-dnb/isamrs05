<template>
	<div>
		<h1 class="headline grey--text">Add seats for fast reservations</h1>
		
		<br />

		<v-combobox label="Select flight" 
            v-model="flightToEdit" 
            :items="flights" 
            item-text="textToShow" 
            return-object
            prepend-icon="flight_takeoff"
            @change="flightChange"
        ></v-combobox>

        <br />

        <div v-if="flightToEdit">
	        <h1 class="headline text-md-center">
	            {{flightToEdit.startDestination.city}}, {{flightToEdit.startDestination.country}}
	            <v-icon>arrow_forward</v-icon>
	            {{flightToEdit.endDestination.city}}, {{flightToEdit.endDestination.country}}
	        </h1>
	        <h3 class="subheading grey--text text--darken-3 text-md-center">
	            {{flightToEdit.startDestination.airportName}}, {{flightToEdit.startDestination.airportCode}}
	            <v-icon>arrow_forward</v-icon>
	            {{flightToEdit.endDestination.airportName}}, {{flightToEdit.endDestination.airportCode}}
	        </h3>

	        <br />

	        <h3 class="subheading grey--text text--darken-3 text-md-center">
	            <v-icon>flight_takeoff</v-icon>
	            <b>Take-off date & time:</b> {{flightToEdit.startDateTime}}
	        </h3>

	        <h3 class="subheading grey--text text--darken-3 text-md-center">
	            <v-icon>flight_land</v-icon>
	            <b>Landing date & time:</b> {{flightToEdit.endDateTime}}
	        </h3>

	        <br />

			<div>
	            <v-stage ref="stage" :config="configKonva">
	                <v-layer ref="layer">
	                    <v-rect v-for="item in seats" :config="item" @click="seatClick(item)"></v-rect>
	                    <v-text v-for="item in seatsText" :config="item" :key="item.id" @click="seatClick(item)"></v-text>
	                </v-layer>
	            </v-stage>
	        </div>

			<v-dialog v-model="dialog" width="500">
				<template v-slot:activator="{ on }">
					<v-btn v-on="on">
						<v-icon left>add</v-icon>
						<span>Add seats</span>
					</v-btn>
				</template>

				<v-card>
					<v-card-title class="headline grey lighten-2" primary-title>
						Price for seats
					</v-card-title>

					<v-card-text>
						<v-form ref="priceForm">
							<v-text-field v-model="pricePerSeat" label="Price per seat" type="number" min="0" :rules="priceRule">
							</v-text-field>
						</v-form>
					</v-card-text>

					<v-divider></v-divider>

					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn @click="addSeats" small>
							<v-icon left>add</v-icon>
							<span>Add seats</span>
						</v-btn>
					</v-card-actions>
				</v-card>
			</v-dialog>
        </div>
	</div>	
</template>

<script>

import axios from 'axios';
	
export default {

	name: 'FlightFastReservationForm',

	props: {},

	components: {},

	data() {
		return {
			priceRule: [
				v => (v !== null) || 'Please fill out this field',
                v => (v && v >= 0) || 'Price can\'t be negative number'
			],
			pricePerSeat: 0,
			dialog: false,
			flights: [],
			flightToEdit: null,
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
		addSeats() {
			if (this.selectedSeats.length === 0) {
				this.$toasted.error("Please select at least one seat", {duration:5000});
				return;
			}		
			
			if (!this.$refs.priceForm.validate()) {
				return;
			}

			const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

			this.selectedSeats.forEach(seat => seat.price = this.pricePerSeat);

			axios.post(`http://localhost:8080/api/flights/${this.flightToEdit.id}/fastSeats`, this.selectedSeats, header)
			.then(response => {
				this.flightToEdit = response.data;

				this.flightToEdit.textToShow = this.flightToEdit.startDestination.city + ", " + this.flightToEdit.startDestination.country + " -> " + this.flightToEdit.endDestination.city + ", " + this.flightToEdit.endDestination.country;

				this.$toasted.success('Seats are successfully added to fast reservations', {duration:5000});
				this.dialog = false;
				this.selectedSeats = [];
				this.pricePerSeat = 0;
			})
			.catch(error => this.$toasted.error('Error while adding seats to fast reservations', {duration:5000}));
		},

		flightChange() {
			this.createSeatsView();
		},

		configureKonva(rectWidth, marginSize) {
            this.configKonva.width = this.flightToEdit.airplane.numOfColumns * (rectWidth + marginSize);
            this.configKonva.height = this.flightToEdit.airplane.numOfRows * (rectWidth + marginSize);
        },

        createSeatsView() {
            const rectSize = 35;
            const margin = 5;

            this.configureKonva(rectSize, margin);

            for (let i = 0; i < this.flightToEdit.airplane.numOfColumns; ++i) {
                for (let j = 0; j < this.flightToEdit.airplane.numOfRows; ++j) {
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

            for (let reservation of this.flightToEdit.reservations) {
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
	},

	mounted() {
		axios.get('http://localhost:8080/api/airlines/22')
		.then(response => {
			this.flights = response.data.flights;
			
			for (let f of this.flights) {
				f.textToShow = f.startDestination.city + ", " + f.startDestination.country + " -> " + f.endDestination.city + ", " + f.endDestination.country;
			}
		});
	}
}

</script>

<style>
	
</style>