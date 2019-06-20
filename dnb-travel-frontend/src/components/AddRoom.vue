<template>
    <div>
        <h3 class="subheading grey--text">Add New Room</h3>
        <v-btn @click="refreshRooms">
            <v-icon left>refresh</v-icon>
            <span>Refresh Room Information</span>
        </v-btn>  
        <v-form ref="roomAddForm">
            
            <v-text-field label="Room Number" v-model="room.roomNumber" prepend-icon="person" :rules="roomRules"></v-text-field>
            <v-text-field label="Floor" v-model="room.floor" prepend-icon="person" :rules="inputRules"></v-text-field>
            <v-text-field label="Capacity" v-model="room.capacity" prepend-icon="person" :rules="inputRules"></v-text-field>
        
            <v-btn @click="room_add">
                <v-icon left>person_add</v-icon>
                <span>Add New Room</span>
            </v-btn>
        </v-form>

        <h3 class="subheading grey--text">Additional Services Settings</h3>

            <v-data-table :headers="roomHeaders"
                :items="rooms">
                <template v-slot:items="props">
                    <td>{{ props.item.roomNumber }}</td>
                    <td>{{ props.item.floor }}</td>
                    <td>{{ props.item.capacity }}</td>
                    <td>{{ props.item.rating }}</td>
                    <td>
                        <v-btn @click="removeRoom(props.item.id)">
                            <v-icon>remove_circle</v-icon>
                        </v-btn>
                    </td>
                    <td>
                        <v-btn @click="modifyRoom(props.item.id)">
                            <v-icon left>settings</v-icon>
                            <span>Modify</span>
                        </v-btn>
                    </td>
                </template>
            </v-data-table>
    </div>
</template>

<script>
import axios from 'axios';
import { error } from 'util';
export default {
    name: 'AddRoom',
    props: {},
    components: {},

    data() {
        return {
            roomHeaders : [
                {text: 'Room', value: 'roomNumber'},
                {text: 'Floor', value: 'floor'},
                {text: 'Capacity', value: 'capacity'},
                {text: 'Rating', value: 'rating'},
                {text: '', value: 'id'},
                {text: '', value: 'id'},
            ],
            inputRules: [
                v => (v && v.length > 0) || 'Please fill out this field'
            ],
            roomRules: [
                v => (v && v.length > 0) || 'Please fill out this field',
                v => (!this.room_numbers.includes(v)) || 'Room number already exists in current hotel.'
            ],
            room: {
                id: null,
                roomNumber: null,
                floor: null,
                rating: null,
                capacity: null,
                hotelID: null
            },
            hotel: {
                rooms: []
            },
            rooms: [],
            room_numbers: [],
            dialog: false
        }
    },
    methods: {
        removeRoom(id) {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            this.room.id = id;
            this.room.hotelID = this.hotel.id;

            axios.put('http://localhost:8080/api/rooms/delete' , this.room, header)
            .then(response => {
                this.rooms = response.data;
                this.room_numbers = [];
                this.rooms.forEach(element => {
                    this.room_numbers.push(element.roomNumber.toString());
                });
                console.log(response.data);
            })
        },
        modifyRoom(id) {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            this.room.id = id;
            this.room.hotelID = this.hotel.id;

            axios.put('http://localhost:8080/api/rooms/', this.room, header)
            .then(response => {
                this.rooms = response.data;
                this.room_numbers = [];
                this.rooms.forEach(element => {
                    this.room_numbers.push(element.roomNumber.toString());
                });
                console.log(response.data);
            })
            .catch(error => this.$toasted.error('Error while collecting rooms information.', {duration:3000}));
        },
        room_add() {
            if (!this.$refs.roomAddForm.validate()) {
                return;
            }
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };

            this.room.rating = 0.0;

            axios.post('http://localhost:8080/api/rooms/add', this.room, header)
            .then(response => {
                this.$toasted.success('Room successfully registered.', {duration:5000});
                this.rooms.push(response.data);
                this.room_numbers.push(response.data.roomNumber.toString());
                //console.log(this.room_numbers);
                //console.log(this.room_numbers.includes(response.data.roomNumber));
                
            })
            .catch(error => this.$toasted.error('Error while registering room.', {duration:5000}));
            
        },
        refreshRooms() {
            const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
            
            axios.get('http://localhost:8080/api/rooms/all/' + this.room.hotelID, header)
            .then(response => {
                this.rooms = response.data;
                this.room_numbers = [];
                this.rooms.forEach(element => {
                    this.room_numbers.push(element.roomNumber.toString());
                });
                console.log(this.room_numbers);
            })
            .catch(error => this.$toasted.error('Error while collecting rooms information.', {duration:5000}));
        }
    },
    mounted() {
        const header = { headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} };
        const username = localStorage.getItem('username');
        axios.post('http://localhost:8080/api/hotels/findHotelByAdmin', username, header)
        .then(response => { 
            this.room.hotelID = response.data.id;
            this.hotel = response.data;
            //refreshRooms();
        })
        .catch(error => this.$toasted.error('Admin ' + username + ' has no hotels assigned.', {duration:5000}));
    }
}
</script>

<style>

</style>

