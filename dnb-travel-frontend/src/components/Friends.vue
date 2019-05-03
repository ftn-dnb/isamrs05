<template>
    <div>
        <v-container grid-list-md text-xs-center>
            <v-layout row wrap>
                <v-flex xs4>
                    <v-text-field label="Search first and last name" v-model="searchUser"></v-text-field>
                </v-flex>
                <v-flex xs2>
                    <v-btn @click="findUsers">
                        <v-icon left>search</v-icon>
                        <span>Search</span>
                    </v-btn>
                </v-flex>
            </v-layout>   
        </v-container>

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="user in searchedUsers" :key="user.username">
                <v-card flat class="text-xs-center ma-3">
                    <v-responsive class="pt-4">
                        <v-icon size="70">person</v-icon>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{user.firstName}} {{user.lastName}}</div>
                        <div class="grey--text">{{user.username}}</div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small @click="addFriend(user.username)">
                            <v-icon left>add</v-icon>
                            <span>Add</span>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>


        <h3 class="subheading">Friend requests</h3>

        <div v-if="getNumOfFriendsWithStatus('REQUEST') == 0" class="grey--text">
            You don't have any new friend requests
        </div>

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="friend in friendships" :key="friend.friendUsername">
                <v-card flat class="text-xs-center ma-3" v-if="friend.status == 'REQUEST'">
                    <v-responsive class="pt-4">
                        <v-icon size="70">person</v-icon>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{friend.friendFirstName}} {{friend.friendLastName}}</div>
                        <div class="grey--text">{{friend.friendUsername}}</div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small @click="acceptFriendship(friend.friendUsername)">
                            <v-icon left>person_add</v-icon>
                            <span>Accept</span>
                        </v-btn>
                        <v-btn flat small @click="declineFriendship(friend.friendUsername)">
                            <v-icon left>person_add_disabled</v-icon>
                            <span>Decline</span>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>


        <h3 class="subheading">Friends</h3>

        <div v-if="getNumOfFriendsWithStatus('ACCEPTED') == 0" class="grey--text">
            Currently you don't have any friends
        </div>

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="friend in friendships" :key="friend.friendUsername">
                <v-card flat class="text-xs-center ma-3" v-if="friend.status == 'ACCEPTED'">
                    <v-responsive class="pt-4">
                        <v-icon size="70">person</v-icon>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{friend.friendFirstName}} {{friend.friendLastName}}</div>
                        <div class="grey--text">{{friend.friendUsername}}</div>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn flat small @click="unfriend(friend.friendUsername)">
                            <v-icon left>remove_circle</v-icon>
                            <span>Remove</span>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>


        <h3 class="subheading">Pending requests</h3>

        <div v-if="getNumOfFriendsWithStatus('PENDING') == 0" class="grey--text">
            Currently there isn't any pending requests
        </div>

        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg3 v-for="friend in friendships" :key="friend.friendUsername">
                <v-card flat class="text-xs-center ma-3" v-if="friend.status == 'PENDING'">
                    <v-responsive class="pt-4">
                        <v-icon size="70">person</v-icon>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{friend.friendFirstName}} {{friend.friendLastName}}</div>
                        <div class="grey--text">{{friend.friendUsername}}</div>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>

import axios from 'axios';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
    name: 'Friends',
    props: [],

    data() {
        return {
            connected: false,
            searchUser: null,
            friendships: [],
            searchedUsers: [],
        };
    },

    methods: {
        findUsers() {
            const header = {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`}};

            axios.get(`http://localhost:8080/api/users/find/${this.searchUser}`, header)
            .then(response => {
                this.searchedUsers = response.data;

                if (this.searchedUsers == '') {
                    this.$toasted.info('There are no users that match given name.', {duration:5000});
                }
            })
            .catch(error => this.$toasted.error('Error while getting users.', {duration:5000}));
        },

        unfriend(friendUsername) {
            const header = {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`, "Content-Type": "text/plain"}};

            axios.post('http://localhost:8080/api/users/friends/removeFriend', friendUsername, header)
            .then(response => {
                this.friendships = response.data.friendships;
                this.$toasted.success('User is removed from friends list.', {duration: 5000});
            })
            .catch(error => this.$toasted.error('There was an error while removing friend.', {duration:5000}));
        },

        send() {
            console.log("Send message:" + this.send_message);
            if (this.stompClient && this.stompClient.connected) {
                const msg = { name: this.send_message };
                this.stompClient.send("/app/hello", JSON.stringify(msg), {});
            }
        },

        addFriend(friendUsername) {
            if (friendUsername == localStorage.getItem('username')) {
                this.$toasted.error("You can't add yourself as a friend.", {duration:5000});
                return;
            }

            const header = {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`, "Content-Type": "text/plain"}};

            axios.post(`http://localhost:8080/api/users/friends/addFriend`, friendUsername, header)
            .then(response => {
                this.friendships = response.data.friendships;
                this.searchedUsers = [];
                this.searchUser = '';
            })
            .catch(error => this.$toasted.error('There was an error while adding new friend.', {duration:5000}));
        },

        acceptFriendship(friendUsername) {
            const header = {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`, "Content-Type": "text/plain"}};

            axios.post('http://localhost:8080/api/users/friends/acceptFriend', friendUsername, header)
            .then(response => this.friendships = response.data.friendships)
            .catch(error => this.$toasted.error('Eror while accepting friend request.', {duration:5000}));
        },

        declineFriendship(friendUsername) {
            const header = {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`, "Content-Type": "text/plain"}};

            axios.post('http://localhost:8080/api/users/friends/declineFriend', friendUsername, header)
            .then(response => this.friendships = response.data.friendships)
            .catch(error => this.$toasted.error('Eror while declining friend request.', {duration:5000}));
        },

        connect() {
            const header = {'X-Authorization' : localStorage.getItem('user-token')};

            this.socket = new SockJS("http://localhost:8080/websockets");
            this.stompClient = Stomp.over(this.socket);
            this.stompClient.connect(header, frame => {
                    this.connected = true;
                    this.stompClient.subscribe(`/topic/${localStorage.getItem('username')}`, tick => {
                        this.getInfoAboutFriends();
                    });
                },
                error => { this.connected = false; }
            );
        },

        disconnect() {
            if (this.stompClient) {
                this.stompClient.disconnect();
            }
            this.connected = false;
        },

        getInfoAboutFriends() {
            const header = {headers: {"Authorization": `Bearer ${localStorage.getItem('user-token')}`}};

            axios.get(`http://localhost:8080/api/users/info/${localStorage.getItem('username')}`, header)
            .then(response => this.friendships = response.data.friendships)
            .catch(error => this.$toasted.error('Error while loading data about friends.', {duration:5000}));
        },

        getNumOfFriendsWithStatus(status) {
            return this.friendships.filter(f => f.status === status).length;
        },
    },

    mounted() {
        this.getInfoAboutFriends();
        this.connect();
    }
}
</script>

<style scoped>

</style>

