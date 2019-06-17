<template>
<div>
    <v-container>
        <v-form ref="form">
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
        </v-form>
    </v-container>

    <v-layout row wrap>
        <v-flex xs12 sm6 md4 lg3 v-for="item in cars">
            <v-card flat class="text-xs-center ma-3">
                <v-card-title>
                   
                </v-card-title>
                <v-card-text>
                    <div class="subbheading">
                        {{item.car.name}},{{item.car.brand}}
                    </div>
                    <div>
                        <v-icon>people</v-icon>
                        {{item.car.seatsNumber}}
                        <v-icon>directions_car</v-icon>
                        {{item.car.type}}
                    </div>
                    <div>
                        <v-icon>attach_money</v-icon>
                        {{item.pricePerDay}}
                    </div>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>

</div>
</template>

<script>

import axios from 'axios';
import format from 'date-fns/format';

export default {
  name: 'RACDetailedView',
  props: {
    racID:null,
  },
  components: {},

  data () {
    return {
        carTypes:["SUV","TRUCK","SPORT","MINIVAN"],
        cars: [],
        carSearch:{
            startDate: null,
            endDate: null,
            pricePerDay: null,
            seatsNumber: null,
            type: null,
            brand: null,
            id: null,
        },
        rentACarCompanies:[],
        
    }
  },
  methods:{

    formatDate(date){
        return date ? format(date,'Do MMM YYYY') : '';
    },

    searchCars(){

        this.carSearch.id = this.racID;
        axios.post("http://localhost:8080/api/rentACarCompanies/carSearch",this.carSearch)
        .then(response =>{
            if(response.data ===''){
                this.$toasted.info('There is no cars with current parameters', {duration:5000});
                return;
            }

            console.log(response.data);
            this.cars = response.data;
            this.$refs.form.reset()

        })
    },
  },
    mounted(){
        console.log(this.racID);
        axios.get(`http://localhost:8080/api/rentACarCompanies/${this.racID}`)
        .then(response =>{
            this.rentACarCompanies = response.data;
            console.log(this.rentACarCompanies);
        })
    },

}
</script>
