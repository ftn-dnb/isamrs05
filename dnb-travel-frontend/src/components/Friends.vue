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
            <caption>Pending requests</caption>

            <tr v-for="friend in friendships">
                <td v-if="friend.status == 'PENDING'">{{friend.friendFirstName}} {{friend.friendLastName}} ({{friend.friendUsername}})</td>
            </tr>
        </table>
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: 'Friends',
    props: [],

    data() {
        return {
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

        addFriend(friendUsername) {
            if (friendUsername == localStorage.getItem('username')) {
                this.$toasted.error("You can't add yourself as a friend.", {duration:5000});
                return;
            }

            console.log(friendUsername);

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
    },

    mounted() {
        const header = {headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`}};

        axios.get(`http://localhost:8080/api/users/info/${localStorage.getItem('username')}`, header)
        .then(response => this.friendships = response.data.friendships)
        .catch(error => this.$toasted.error('Error while loading data about friends.', {duration:5000}));
    }
}
</script>

<style scoped>

</style>

