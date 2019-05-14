<template>
    <v-container>
        <v-layout row wrap>
            
            <v-flex>
                 <v-text-field type="number"
                    label = "Max price"
                    v-model = "carSearch.pricePerDay"
                    prepend-icon="attach_money"
                    min="0"
                    max="1000"
                ></v-text-field>

                <v-text-field type="number"
                        label="Seats"
                        v-model="carSearch.seatsNumber"
                        min="0"
                        prepend-icon="people"
                ></v-text-field>

                <v-text-field type = "text"
                    label="Car brand"
                    v-model="carSearch.brand"
                     prepend-icon="directions_car"
                ></v-text-field>
            </v-flex>
            <v-flex>
                <v-menu>
                <v-text-field label="Start date"
                    slot="activator"
                    :value="formatDate(carSearch.startDate)"
                    prepend-icon ="event"></v-text-field>
                    <v-date-picker v-model="carSearch.startDate"></v-date-picker>
                </v-menu>

                <v-menu>
                <v-text-field label="End date"
                    slot="activator"
                    :value="formatDate(carSearch.endDate)"
                    prepend-icon ="event"></v-text-field>
                    <v-date-picker v-model="carSearch.endDate"></v-date-picker>
                </v-menu>

                <v-combobox label="Select type" 
                        v-model="carSearch.type" 
                        :items="carTypes" 
                        item-text="name" 
                        prepend-icon="directions_car"
                    ></v-combobox>
                <v-btn @click="searchCars()">
                    <v-icon left>search</v-icon>
                    <span>Search</span>
                </v-btn>
            </v-flex>
            
               
            

    </v-layout>
    </v-container>
</template>

<script>

import axios from 'axios';
import format from 'date-fns/format';

export default {
  name: 'CarSearch',
  props: {},
  components: {},

  data () {
    return {
        carSearch:{
            startDate: null,
            endDate: null,
            pricePerDay: null,
            seatsNumber: null,
            type: null,
            brand: null,
        },
        carTypes:["SUV","TRUCK","SPORT","MINIVAN"],
    }
  },
  methods:{
    mounted(){

    },
    formatDate(date){
        return date ? format(date,'Do MMM YYYY') : '';
    },

    searchCars(){
        axios.post("http://localhost:8080/api/rentACarCompanies/carSearch",this.carSearch)
        .then(response =>{
            if(response.data ===''){
                this.$toasted.info('There is no cars with current parameters', {duration:5000});
                return;
            }

            console.log(response.data);

        })
    },
  },

}
</script>
