<template>
    <div>

    <v-layout row wrap>
        <v-flex xs12 sm6 md4 lg3 v-for="item in items">
            <v-card flat class="text-xs-center ma-3">
                <v-card-text>
                    <div class="subbheading">
                        {{formatDate(item.beginDate)}},{{formatDate(item.endDate)}}
                    </div>
                    <div>
                    <v-btn  @click="deleteReservation(item)">
                        <v-icon left>delete</v-icon>
                        <span>Cancel</span>
                    </v-btn>
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

export default{
    name: 'ListHotelReservations',
    props: {},
    components: {},

    data(){
        return {

            user:{
                username: null,
            },

            items:[],

            cancelData:{
                username:null,
                reservationId:null,
                itemId:null,
            }
        };
    },

    methods:{

    formatDate(date){
        return date ? format(date,'Do MMM YYYY') : '';
        },
    deleteReservation(item){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

        this.cancelData.username = this.user.username;
        this.cancelData.itemId = item.itmeId;
        this.cancelData.reservationId = item.reservationId;

        axios.post("http://localhost:8080/api/hotels/deleteUserReservation",this.cancelData,header)
        .then(response => {
            console.log(this.items);

        })
        .catch(error => console.log('mucak'));
    },

    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');


        axios.post("http://localhost:8080/api/hotels/getUserReservations",this.user,header)
        .then(response => {
            this.items = response.data;
            console.log(this.items);

        })
        .catch(error => console.log('mucak'));
    },



}

</script>

