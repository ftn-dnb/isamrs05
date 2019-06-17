<template>
    <div>
        
        <v-combobox v-model="price_list_name" :items="this.price_lists_item" label="Select price list you want to be active">
        </v-combobox>

        <v-btn @click="set_price_list">
            <v-icon left>done</v-icon>
            <span>Set active price list</span>
        </v-btn>
       
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'SetActivePriceListRAC',
    props: [],
    components: {},
    
    data() {
        return {
            rentACarCompany:{
                id: null,
                name: null,
                priceList:[],
            },
            priceList:{
                company_id: null,
                price_list_id: null,
                name: null,
            },
            user:{
                username: null,
                id: null,
            },

            price_list_name:null,
            price_lists:[],
            price_lists_item:[],
        };
    },

    methods: {
        set_price_list(){

            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
            this.user.username = localStorage.getItem('username');

            console.log("selektovani item");
            console.log(this.price_list_name);

            this.price_lists.forEach(element => {
                if (element.name === this.price_list_name) {
                    this.priceList.name = element.name;
                    this.priceList.company_id = this.rentACarCompany.id;
                    this.priceList.price_list_id = element.id;
                }
            });

            console.log(this.priceList);

            axios.post("http://localhost:8080/api/rentACarCompanies/setPriceList",this.priceList,header)
            .then(response => {
                console.log(response.data);
            })
            .catch(error => console.log('mucak'));

            },
    },

    mounted(){

        const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};
        this.user.username = localStorage.getItem('username');

        axios.post("http://localhost:8080/api/rentACarCompanies/company",this.user,header)
        .then(response => {
            this.rentACarCompany = response.data;


           this.price_lists = this.rentACarCompany.priceList;
           console.log(this.price_lists);

           if(this.price_lists.length != 0){ this.price_list_name = this.price_lists[0].name;}
           this.price_lists.forEach(element => {
                this.price_lists_item.push(element.name);

           });
           console.log(this.price_lists_item);
        })
        .catch(error => console.log(error));
    },
}
</script>
