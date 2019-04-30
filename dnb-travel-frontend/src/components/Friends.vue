<template>
    <div>
        <div>
            <input type="text" v-model="searchUser" placeholder="Friend's first and last name" />
            <input type="button" @click="findUsers" value="Search" /> 
        </div>  

        <table>
            <tr v-for="u in searchedUsers">
                <td>{{u.firstName}} {{u.lastName}} ({{u.username}})</td>
                <td>
                    <input type="button" value="Add friend" @click="addFriend(u.username)" />
                </td>
            </tr>
        </table>

        <table>
            <caption>List of friends</caption>

            <tr v-for="friend in friendships">
                <td v-if="friend.status == 'ACCEPTED'">{{friend.friendFirstName}} {{friend.friendLastName}} ({{friend.friendUsername}})</td>
                <td>
                    <span v-if="friend.status == 'ACCEPTED'">
                        <input type="button" value="Unfriend" @click="unfriend(friend.friendUsername)" />
                    </span>
                </td>
            </tr>
        </table>

        <table>
            <caption>Friend requests</caption>
            <tr v-for="friend in friendships">
                <td v-if="friend.status == 'REQUEST'">{{friend.friendFirstName}} {{friend.friendLastName}} ({{friend.friendUsername}})</td>
                <td v-if="friend.status == 'REQUEST'">
                    <input type="button" value="Accept" @click="acceptFriendship(friend.friendUsername)" />
                    <input type="button" value="Decline" @click="declineFriendship(friend.friendUsername)" />
                </td>
            </tr>
        </table>

        <table>
            <caption>Pending requests</caption>

            <tr v-for="friend in friendships">
                <td v-if="friend.status == 'PENDING'">{{friend.friendFirstName}} {{friend.friendLastName}} ({{friend.friendUsername}})</td>
            </tr>
        </table>
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
                    this.$toasted.info('There are no users with that match given name.', {duration:5000});
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
            .then(response => this.friendships = response.data.friendships)
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
    },

    mounted() {
        this.getInfoAboutFriends();
        this.connect();
    }
}
</script>

<style scoped>

</style>

