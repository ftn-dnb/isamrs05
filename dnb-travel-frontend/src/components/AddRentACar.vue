<template>
    <div>
        <h3 class="subheading grey--text">Add New Rent A Car</h3>    
        <v-form ref="racAddForm">
            <v-text-field label="Name" v-model="rent_a_car.name" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Description" v-model="rent_a_car.description" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-container fluid>
                <v-layout wrap>
                    <v-flex xs12>
                        <v-combobox v-model="administrator_username" :items="this.rac_admins_item" label="Select hotel admin">

                        </v-combobox>
                    </v-flex>
                </v-layout>
            </v-container>
            <h3 class="subheading grey--text">Address information</h3>
        
            <v-btn @click="rac_register">
                <v-icon left>person_add</v-icon>
                <span>Add New Rent A Car</span>
            </v-btn>
        </v-form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    name: 'AddRentACar',
    props: {},
    components: {},

    data() {
        return {
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            rent_a_car: {
                id: null,
                name: null,
                description: null,
                rating: null,
                administrator: null
            },
            administrator_username: null,
            rac_admins: [],
            rac_admins_item: []
        }
    },
    methods: {
        rac_register() {
            if (!this.$refs.racAddForm.validate()) {
                return;
            }
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            this.rent_a_car.rating = 0.0;
            this.rac_admins.forEach(element => {
                if (element.username === this.administrator_username) {
                    this.rent_a_car.administrator = element;
                }
            });

            axios.post('http://localhost:8080/api/rentACarCompanies/add', this.rent_a_car, header)
            .then(response => {
                if (response.data === '') {
                    this.$toasted.error('Error while registering rent a car company.', {duration:5000});
                } else {
                    this.$toasted.success('Rent a car company successfully registered.', {duration:5000});
                }
            })
            .catch(error => this.$toasted.error('Error while registering rent a car company.', {duration:5000}));
            
        }
    },
    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
        axios.get('http://localhost:8080/api/users/racAdmins', header)
        .then(response => { 
            this.rac_admins = response.data;
            if (response.data.length != 0) { this.administrator_username = response.data[0].username; }
            this.rac_admins.forEach(element => {
                this.rac_admins_item.push(element.username);
            });
        });
    }
}
</script>

<style>

</style>

