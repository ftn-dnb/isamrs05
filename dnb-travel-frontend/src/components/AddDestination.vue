<template>
	<div>
		<v-form ref="addDestinationForm">
			<v-text-field
				label="City"
				v-model="destination.city"
				:rules="inputRules"
			></v-text-field>
			<v-text-field
				label="Country"
				v-model="destination.country"
				:rules="inputRules"
			></v-text-field>
			<v-text-field
				label="Airport name"
				v-model="destination.airportName"
				:rules="inputRules"
			></v-text-field>
			<v-text-field
				label="Airport code"
				v-model="destination.airportCode"
				:rules="inputRulesAirportCode"
			></v-text-field>
			<v-text-field
				type="number"
				label="Longitude"
				v-model="destination.longitude"
				:rules="inputRules"
			></v-text-field>
			<v-text-field
				type="number"
				label="Latitude"
				v-model="destination.latitude"
				:rules="inputRules"
			></v-text-field>

			<v-btn @click="addDestination">
				<v-icon left>add</v-icon>
				<span>Add</span>
			</v-btn>
		</v-form>
	</div>
</template>

<script>

import axios from 'axios';

export default {
	name: 'AddDestination',

	props: {},
	components: {},

	data() {
		return {
			inputRules: [
				v => (v && v.length > 0) || 'Please fill out this field'
			],
			inputRulesAirportCode: [
				v => (v && v.length == 3) || 'Airport code must have 3 letters'
			],
			destination: {
				city: null,
				country: null,
				airportName: null,
				airportCode: null,
				longitude: null,
				latitude: null,
			},	
		}
	},

	methods: {
		addDestination() {
			if (!this.$refs.addDestinationForm.validate()) {
				return;
			}

			const header = {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`}}

			axios.post('http://localhost:8080/api/destinations', this.destination, header)
            .then(response => {
                this.$toasted.success('Destination was successfully created.', {duration:5000});
            }).catch(error => {
                this.$toasted.error('There was an error while creating new destination.', {duration:5000});
            });
		},
	},

	mounted() {
	}
}	
</script>

<style>
</style>