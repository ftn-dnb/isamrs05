<template>
    <div>

    <v-layout row wrap>
        <v-flex xs12 sm6 md4 lg3 v-for="item in items">
            <v-card flat class="text-xs-center ma-3">
                <v-card-text>
                    <div class="subbheading">
                        {{formatDate(item.begin)}},{{formatDate(item.end)}}
                    </div>

                    <v-btn  @click="deleteReservation(item)">
                        <v-icon left>delete</v-icon>
                        <span>Cancel</span>
                    </v-btn>
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
    name: 'RACIncome',
    props: {},
    components: {},

    data(){
        return {
           company:{
            id:null,

           },

            user:{
                username: null,
            },

            items:[],

            cancelItem:{
                id:null,
                reservationId:null,
                companyId: null,
            },
        };
    },

    methods:{

    formatDate(date){
        return date ? format(date,'Do MMM YYYY') : '';
        },

    deleteReservation(item){
        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        console.log(this.item);

        this.cancelItem.id = item.user_id;
        this.cancelItem.reservationId = item.id;
        this.cancelItem.companyId = item.company_id;

        console.log(this.cancelItem);

        axios.post("http://localhost:8080/api/rentACarCompanies/cancelCarReservation",this.cancelItem,header)
        .then(response => {
            this.$toasted.success(response.data, {duration:5000});
        })
        .catch(error => console.log('mucak'));

        },
    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');


        axios.post("http://localhost:8080/api/rentACarCompanies/getUserReservationsCar",this.user,header)
        .then(response => {
            this.items = response.data;
            console.log(this.items);

        })
        .catch(error => console.log('mucak'));
    },



}

</script>

