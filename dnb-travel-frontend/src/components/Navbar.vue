<template>
	<nav>
		<v-toolbar>
			<v-toolbar-side-icon class="grey--text" @click="drawer = !drawer">
			</v-toolbar-side-icon>

			<v-toolbar-title class="text-uppercase grey--text">
				<span>DNB</span>
				<span class="font-weight-light">TRAVEL</span>
			</v-toolbar-title>

			<v-spacer></v-spacer>

			<div v-if="this.role == null">
				<v-btn :to ="{name: 'Login'}">
	                     <v-icon left>lock_open</v-icon>
	                	 <span>Login</span>
	            </v-btn>
				<v-btn :to ="{name: 'Registration'}">
	                     <v-icon left>face</v-icon>
	                     <span>Register</span>
            	</v-btn>
            	<v-menu name="aa">
            		aa
            	</v-menu>
            </div>
            
            <div v-else-if="this.role != null">
            <router-link :to="{ name: 'UserSettings'}">User</router-link>
            &nbsp;
            <router-link :to="{name:'Logout'}">SignOut</router-link>
            </div>
		</v-toolbar>

		<v-navigation-drawer v-model="drawer" app class="secondary" >
			<v-list  >
				<v-list-tile v-for="item in lista" :key="item.text" router :to="item.route">
					<v-list-tile-action>
					    <v-icon dark>{{item.icon}}</v-icon> 
					</v-list-tile-action>
					<v-list-tile-content>
						<v-list-tile-title class="white--text">{{item.text}}</v-list-tile-title>
					</v-list-tile-content>
				</v-list-tile>
			</v-list>
		</v-navigation-drawer>

	</nav>
</template>

<script>
import {bus} from '../main';
import Login from './Login.vue';

export default {
	name: 'Navbar',

	props: {},
	components: {
		Login,
	},

	data() {
		return {
			role: null,
			drawer: false,
			lista: null,
			airlineAdminDrawer: [
				{ icon: 'flight_takeoff', text: 'Flights', route: '/flights' },
				{ icon: 'airplanemode_active', text: 'Add flight', route: '/add-flight' },
				{ icon: 'work_outline', text: 'Company settings', route: '/airline-settings' },
				{ icon: 'perm_identity', text: 'Profile settings', route: '/user-settings' },
			],

			rentACarAdminDrawer: [
			{icon: 'work_outline', text: 'Company settings', route: '/rent-a-car-settings'},
			{icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
			],

			userDrawer:[
			{ icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
			],
			
		}
	},
	created(){
		bus.$on('roleChanged', (data) =>{
			this.role = localStorage.getItem('role');
			if(data === 'ROLE_RAC_ADMIN'){
				this.lista = this.rentACarAdminDrawer;
				this.role = 'asd';
			}
			else if(data === 'ROLE_AIRLINE_ADMIN'){
				this.lista = this.airlineAdminDrawer;
				this.role = 'asd';
			}
			else if(data ==='ROLE_SYSTEM_ADMIN'){
				//dodati
			}
			else if(data === 'ROLE_HOTEL_ADMIN'){
				//dodati
			}
			else if(data === 'ROLE_USER'){
				//dodati
			}
			else{
				//default
			}

		});
		bus.$on('logout', (data) =>{
			if(data ==='logout'){
				this.role = null;
				this.lista = null;
			}
		})
	},
	methods: {
		register(){
			this.$router.push('Registration');
		}
	},

	mounted() {
		
	}
}	
</script>

<style>
</style>