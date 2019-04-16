<template>
    <div>
        <h3> Settings for Rent-a-car company: {{rentACarCompany.name}} </h3>
        <table>
            <tr>
                <td>Company name:</td>
                <td><input type="text" v-model="rentACarCompany.name" /></td>
            </tr>
            <tr>
                <td>
                    Company description:
                </td>
                <td><textarea v-model="rentACarCompany.description"></textarea></td>
            </tr>
            <tr>
                <th colspan="2"><input type="submit" value="Change profile" @click="editInfo" /></th>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';

export default{
    name: 'RentACarCompanySettings',
    props: {},
    components: {},

    data(){
        return {
            rentACarCompany: {
                id: null,
                name: null,
                description: null,
            },
        };
    },

    methods:{
        editInfo(){
            if(!this.checkForm()){
                return;
            }

            axios.put('http://localhost:8080/api/rentACarCompanies/1', this.rentACarCompany)
            .then(response => {
                if(response.data === ''){
                    alert('Error: Message');
                    return
                }

                alert('Successful update');
            });
        },

        checkForm(){
            if(!this.rentACarCompany.name){
                alert('Please enter company name');
                return false;
            } else if(!this.rentACarCompany.description){
                alert('Please enter company description');
                return false;
            }

            return true; 
        }
    },

    mounted(){
        axios.get("http://localhost:8080/api/rentACarCompanies/1",{ headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} }).then(response => this.rentACarCompany = response.data);
        console.log(localStorage.getItem('user-token'));
        //axios({
        //   method:'get',
        //    url:'http://localhost:8080/api/rentACarCompanies/1',
         //   responseType:'application/json',
         //   headers:{"authorization" : `Bearer ${localStorage.getItem('user-token')}`}
          //  })
          //  .then(response => this.rentACarCompany = response.data);

    }



}

</script>

<style>
    
</style>
