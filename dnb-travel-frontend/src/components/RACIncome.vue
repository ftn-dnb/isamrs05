<template>
    <div>

            <v-menu>
                    <v-text-field label="Start date"
                        slot="activator"
                        :value="formatDate(incomeSearch.beginDate)"
                        prepend-icon ="event"></v-text-field>
                        <v-date-picker v-model="incomeSearch.beginDate"></v-date-picker>
            </v-menu>

            <v-menu>
                    <v-text-field label="End date"
                        slot="activator"
                        :value="formatDate(incomeSearch.endDate)"
                        prepend-icon ="event"></v-text-field>
                        <v-date-picker v-model="incomeSearch.endDate"></v-date-picker>
            </v-menu>

                <GChart
                    type="LineChart"
                    :data="chartDataIncome"
                />

            <v-btn @click="getIncome">
                <v-icon left>done</v-icon>
                <span>Add vehicle</span>
            </v-btn>

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

           incomeSearch:{
            id:null,
            beginDate: null,
            endDate: null,
           },

           user:{
                username: null,
                id: null,
            },

           chartDataIncome: [['Date', 'Income'], [0, 0]],
        };
    },

    methods:{
        getIncome(){
            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

            this.incomeSearch.id = this.company.id;

            axios.post("http://localhost:8080/api/rentACarCompanies/getIncome",this.incomeSearch,header)
            .then(response =>{
                console.log(response.data);

                this.chartDataIncome = [['Date', 'Income']];


                for (let inc of response.data.income) {
                    this.chartDataIncome.push([inc.date, inc.income]);
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

