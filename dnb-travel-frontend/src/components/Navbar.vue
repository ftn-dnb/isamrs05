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
	            <v-btn :to ="{name: 'Logout'}">
	                     <v-icon left>input</v-icon>
	                     <span>Logout</span>
            	</v-btn>
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
				{ icon: 'map', text: 'Add destination', route: '/add-company-destination' },
				{ icon: 'fast_forward', text: 'Fast reservations', route: '/fast-reservation-seats' },
				{ icon: 'assessment', text: 'Statistics', route: '/airline-statistics' },
				{ icon: 'work_outline', text: 'Company settings', route: '/airline-settings' },
				{ icon: 'perm_identity', text: 'Profile settings', route: '/user-settings' },
			],

			rentACarAdminDrawer: [
			{icon: 'work_outline', text: 'Company settings', route: '/rent-a-car-settings'},
			{icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
			{icon: 'add_circle', text: 'Add new vehicle', route: '/add-car'},
			{icon: 'delete', text: 'Delete vehicle', route: '/delete-car'},
			{icon: 'edit', text: 'Edit vehicle', route: '/edit-car'},
			{icon: 'add_circle', text: 'Add new reservation', route: '/add-car-reservation'},
			{icon: 'add_circle', text: 'Add new price list', route: '/add-price-list-rac'},
			{icon: 'add_circle', text: 'Set active price list', route: '/set-active-price-list-rac'},
			{icon: 'add_circle', text: 'Add branch office', route: '/add-branch-office'},
			{icon: 'add_circle', text: 'Set main branch office', route: '/set-main-branch-office'},
			{icon: 'delete', text: 'Delete branch office', route: '/delete-branch-office'},
			{icon: 'edit', text: 'Edit branch office', route: '/edit-branch-office'},
			{icon: 'assessment', text: 'Company income', route: '/rac-income'},
			{icon: 'assessment', text: 'Reservation stats', route: '/rac-resetvation-stats'},
			{icon: 'assessment', text: 'Car stats & company rating', route: '/car-stats'},
			],

			userDrawer: [
			{ icon: 'flight_takeoff', text: 'Flights search', route: '/flights'},
			{ icon: 'directions_car', text: 'Car search', route: '/search-rac'},
			{ icon: 'hotel', text: 'Hotel Search', route: '/hotels'},
			{ icon: 'people', text:'Friends', route:'/friends'},
			{ icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
			{ icon: 'delete', text: 'Cancel car reservation', route: '/cancel-car'}
			],

			defaultUser: [
			{icon: 'flight_takeoff', text: 'Flights search', route:'/flights'},
			{icon: 'directions_car', text: 'Car search', route: '/search-rac'},
			{icon: 'hotel', text: 'Hotel Search', route: '/hotels'}

      		],

			sysAdminDrawer: [
				{icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
				{icon: 'add_circle', text: 'Add Admin', route: '/adminSystem'},
				{icon: 'add_circle', text: 'Add Hotel', route: '/add-hotel'},
				{icon: 'add_circle', text: 'Add Rent A Car Company', route: '/add-rent-a-car'},
				{icon: 'add_circle', text: 'Add Airline', route: '/add-airline'}
			],

			hotelAdminDrawer: [
				{icon: 'add_circle', text: 'Add Room', route: '/add-room'},
				{icon: 'work_outline', text: 'Price List Settings', route: '/hotel-price-list-settings'},
				{icon: 'assessment', text: 'Statistics', route: '/hotel-statistics'}
			],

		}
	},
	created(){
		bus.$on('roleChanged', (data) =>{
			this.role = localStorage.getItem('role');
			if(data === 'ROLE_RAC_ADMIN'){
				this.lista = this.rentACarAdminDrawer;
			}
			else if(data === 'ROLE_AIRLINE_ADMIN'){
				this.lista = this.airlineAdminDrawer;
			}
			else if(data ==='ROLE_SYSTEM_ADMIN'){
				this.lista = this.sysAdminDrawer;
			}
			else if(data === 'ROLE_HOTEL_ADMIN'){
				this.lista = this.hotelAdminDrawer;
			}
			else if(data === 'ROLE_USER'){
				this.lista = this.userDrawer;
			}
			else{
				this.lista = this.defaultUser;
			}

		});
		bus.$on('logout', (data) =>{
			if(data ==='logout'){
				localStorage.setItem('role', null);
				localStorage.setItem('username', null);
				localStorage.setItem('user-token', null);
				this.role = null;
				this.lista = this.defaultUser;
			}
		})
	},
	methods: {
		register(){
			this.$router.push('Registration');
		}
	},

	mounted() {
		this.role = localStorage.getItem('role');
		this.lista = this.defaultUser;
	}
}	
</script>

<style>
</style>