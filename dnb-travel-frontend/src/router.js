import Vue from 'vue'
import Router from 'vue-router'
import App from './App.vue'
import AdminRentACar from './components/AdminRentACar.vue'
import StartPage from './components/StartPage.vue'
import AdminHotel from './components/AdminHotel.vue'
import AdminAirline from './components/AdminAirline.vue'
import AdminSystem from './components/AdminSystem.vue'
import FlightList from './components/FlightList.vue'
import FlightDetailedView from './components/FlightDetailedView.vue';
import AirlineList from './components/AirlineList.vue';
import AirlineDetailedView from './components/AirlineDetailedView.vue';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'StartPage',
      component: StartPage
    },
    {
    	path:'/adminRentACar',
    	name: 'AdminRentACar',
    	component: AdminRentACar
    },
    {
    	path:'/adminHotel',
    	name: 'AdminHotel',
    	component: AdminHotel
    },
    {
    	path:'/adminAirline',
    	name: 'AdminAirline',
    	component: AdminAirline
    },
    {
    	path:'/adminSystem',
    	name: 'AdminSystem',
    	component: AdminSystem
    },
    {
        path: '/flights',
        name: 'FlightList',
        component: FlightList
    },
    {
        path: '/flights/:flightId',
        props: true,
        name: 'FlightDetailedView',
        component: FlightDetailedView
    },
    {
        path: '/airlines',
        name: 'AirlineList',
        component: AirlineList
    },
    {
        path: '/airlines/:airlineId',
        props: true,
        name: 'AirlineDetailedView',
        component: AirlineDetailedView
    }
  ]
})
