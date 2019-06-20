<template>
    <div>

    <v-layout row wrap>
        <v-flex xs12 sm6 md4 lg3 v-for="item in items">
            <v-card flat class="text-xs-center ma-3">
                <v-card-text>
                    <div class="subbheading">
                        {{formatDate(item.reservationDate)}}
                    </div>
                    <div>
                        {{item.price}}
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
    name: 'ListFlightReservations',
    props: {},
    components: {},

    data(){
        return {

            user:{
                username: null,
            },

            items:[],

        };
    },

    methods:{

    formatDate(date){
        return date ? format(date,'Do MMM YYYY') : '';
        },

    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');


        axios.post("http://localhost:8080/api/flights/listAllReservations",this.user,header)
        .then(response => {
            this.items = response.data;
            console.log(this.items);

        })
        .catch(error => console.log('mucak'));
    },



}

</script>

