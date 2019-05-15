<template>
	<nav>
		<v-toolbar>
			<v-toolbar-side-icon class="grey--text" @click="drawer = !drawer">
			</v-toolbar-side-icon>

			<v-toolbar-title class="text-uppercase grey--text">
				<span>DNB</span>
				<span class="font-weight-light">TRAVEL</span>
			</v-toolbar-title>
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

export default {
	name: 'Navbar',

	props: {},
	components: {},

	data() {
		return {
			role: null,
			drawer: false,
			lista: null,
			airlineAdminDrawer: [
				{ icon: 'flight_takeoff', text: 'Flights', route: '/flights' },
				{ icon: 'airplanemode_active', text: 'Add flight', route: '/add-flight' },
				{ icon: 'assessment', text: 'Statistics', route: '/airline-statistics' },
				{ icon: 'work_outline', text: 'Company settings', route: '/airline-settings' },
				{ icon: 'perm_identity', text: 'Profile settings', route: '/user-settings' },
			],

			rentACarAdminDrawer: [
			{icon: 'work_outline', text: 'Company settings', route: '/rent-a-car-settings'},
			{icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
			],

			sysAdminDrawer: [
				{icon: 'perm_identity', text: 'Profile settings', route: '/user-settings'},
				{icon: 'add_circle', text: 'Add Admin', route: '/adminSystem'},
				{icon: 'add_circle', text: 'Add Hotel', route: '/add-hotel'}
			],
			
		}
	},
	created(){
		bus.$on('roleChanged', (data) =>{
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
				//dodati
			}
			else if(data === 'ROLE_USER'){
				//dodati
			}
			else{
				//default
			}

		})
	},
	methods: {

	},

	mounted() {
		
	}
}	
</script>

<style>
</style>