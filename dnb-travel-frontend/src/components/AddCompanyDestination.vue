<template>
	<div>
		<h1 class="headline">Add destinations to your company</h1>

		<v-form ref="addDestinationForm">
			<v-combobox label="Select destination to add" 
	            v-model="destToAdd" 
	            :items="destinations" 
	            item-text="airportName" 
	            :rules="inputRule"
	            return-object
	            prepend-icon="map"
	        ></v-combobox>

	        <v-btn @click="addDestination">
				<v-icon left>add</v-icon>
				<span>Add</span>
	        </v-btn>
		</v-form>

		<br />
		<h3 class="subheading grey--text text--darken-3">Current destinations where airline works</h3>

		<v-list>
            <v-list-tile class="my-1" v-for="dest in airlineDestinations">
                <v-list-tile-content>
                    <v-list-tile-title>{{dest.city}}, {{dest.country}}</v-list-tile-title>
                    <v-list-tile-sub-title>{{dest.airportName}}, {{dest.airportCode}}</v-list-tile-sub-title>
                </v-list-tile-content>
            </v-list-tile>
        </v-list>
	</div>
</template>

<script>

import axios from 'axios';

export default {
	name: 'AddCompanyDestination',
	props: [],
	components: {},

	data() {
		return {
			header: {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`}},
			inputRule: [
				v => (v && v !== null) || 'Please fill out this field'
			],
			destinations: [],
			airlineDestinations: [],
			destToAdd: null,
			airlineId: null,
		};
	},

	methods: {
		addDestination() {
			if (!this.$refs.addDestinationForm.validate()) {
				return;
			}

			axios.post(`http://localhost:8080/api/airlines/${this.airlineId}/addDestination`, this.destToAdd, this.header)
			.then(response => this.airlineDestinations = response.data.destinations)
			.catch(error => this.$toasted.error('There was and error while adding new destination', {duration: 5000}));
		},
	},

	mounted() {
		const username = localStorage.getItem('username');

		axios.post(`http://localhost:8080/api/airlines/company/${username}`, {}, this.header)
		.then(response => {
			this.airlineId = response.data.id;
			this.airlineDestinations = response.data.destinations;
		})
		.catch(error => this.$toasted.error('Error while loading airline data', {duration:5000}));

        axios.get('http://localhost:8080/api/destinations')
        .then(response => this.destinations = response.data);
	}
}

</script>

<style>
</style>