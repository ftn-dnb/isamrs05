<template>
    <div>



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
                id: null,
            },

           chartDataIncome: [['Date', 'Income'], [0, 0]],
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

        console.log(this.user);

        axios.post("http://localhost:8080/api/rentACarCompanies/getUserReservationsCar",this.user,header)
        .then(response => {
            this.company = response.data;
        })
        .catch(error => console.log('mucak'));
    },



}

</script>

