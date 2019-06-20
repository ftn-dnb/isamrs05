<template>
    <div>

            <v-menu>
                    <v-text-field label="Start date"
                        slot="activator"
                        :value="formatDate(filter.dateReservation)"
                        prepend-icon ="event"></v-text-field>
                        <v-date-picker v-model="filter.dateReservation"></v-date-picker>
            </v-menu>



            <v-select
                    label="Report type"
                    :items="['Day', 'Week', 'Month']"
                    v-model="filter.reservationCriteria"
            ></v-select>
        

                <GChart
                    type="LineChart"
                    :data="chartDataReservations"
                />

            <v-btn @click="getStats">
                <v-icon left>done</v-icon>
                <span>Get stats</span>
            </v-btn>

    </div>
</template>

<script>
import axios from 'axios';
import format from 'date-fns/format';

export default{
    name: 'RACReservationStats',
    props: {},
    components: {},

    data(){
        return {
           company:{
            id:null,

           },


           user:{
                username: null,
                id: null,
            },

            filter:{
                id:null,
                dateReservation:null,
                reservationCriteria:null,
            },

            chartDataReservations: [['Date', 'Number of reservations'], [0, 0]],
        };
    },

    methods:{
        getStats(){
            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

            this.filter.id = this.company.id;
            console.log(this.filter.reservationCriteria);
            axios.post("http://localhost:8080/api/rentACarCompanies/getReservationStats",this.filter,header)
            .then(response =>{
                console.log(response.data);


                this.chartDataReservations = [['Date', 'Number of reservations']];

                for (let res of response.data) {
                    this.chartDataReservations.push([res.date, res.number]);
                }

            });
        },
    formatDate(date){
        return date ? format(date,'Do MMM YYYY') : '';
        },
    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        console.log(this.user);

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.company = response.data;
        })
        .catch(error => console.log('mucak'));
    },



}

</script>

