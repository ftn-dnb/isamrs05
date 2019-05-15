import Vue from 'vue'
import Router from 'vue-router'
import App from './App.vue'
import AdminRentACar from './components/AdminRentACar.vue'
import StartPage from './components/StartPage.vue'
import AdminHotel from './components/AdminHotel.vue'
import AdminAirline from './components/AdminAirline.vue'
import AdminSystem from './components/AdminSystem.vue'
import axios from 'axios';  
import FlightList from './components/FlightList.vue'
import FlightDetailedView from './components/FlightDetailedView.vue';
import AirlineList from './components/AirlineList.vue';
import AirlineDetailedView from './components/AirlineDetailedView.vue';
import AddFlight from './components/AddFlight.vue';
import AirlineSettings from './components/AirlineSettings.vue';
import LoggedInStartPage from './components/LoggedInStartPage.vue';
import UserSettings from './components/UserSettings.vue';
import RentACarCompanySettings from './components/RentACarCompanySettings.vue';
import UserRegistration from './components/UserRegistration.vue';
import Logout from './components/Logout.vue';
import Login from './components/Login.vue';
import CarSearch from './components/CarSearch.vue';
import Friends from './components/Friends.vue';
import AddHotel from './components/AddHotel';
import AddRentACar from './components/AddRentACar';
import AirlineStats from './components/AirlineStats.vue';
import AddCompanyDestination from './components/AddCompanyDestination.vue';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'StartPage',
      component: StartPage
    },
    {
        path: '/home',
        name: 'LoggedInStartPage',
        component: LoggedInStartPage
    },
    {
    	path:'/adminRentACar',
    	name: 'AdminRentACar',
    	component: AdminRentACar,
        beforeEnter:(to,from,next)=> {
            axios.get("http://localhost:8080/api/users/whoami",{ headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} }).then(response => 
                {
                    localStorage.setItem('role', response.data);
                    if(localStorage.getItem('role') === 'ROLE_RAC_ADMIN'){
                        next();
                    }
                    else{
                        alert('Page does not exist')
                        next("");
                    }
                
                });
            
        }
    },
    {
    	path:'/adminHotel',
    	name: 'AdminHotel',
    	component: AdminHotel,
        beforeEnter:(to,from,next)=> {
            axios.get("http://localhost:8080/api/users/whoami",{ headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} }).then(response => 
                {
                    localStorage.setItem('role', response.data);
                    if(localStorage.getItem('role') === 'ROLE_HOTEL_ADMIN'){
                        next();
                    }
                    else{
                        alert('Page does not exist')
                        next("");
                    }
                
                });
            
        }
    },
    {
    	path:'/adminAirline',
    	name: 'AdminAirline',
    	component: AdminAirline,
        beforeEnter:(to,from,next)=> {
            axios.get("http://localhost:8080/api/users/whoami",{ headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} }).then(response => 
                {
                    localStorage.setItem('role', response.data);
                    if(localStorage.getItem('role') === 'ROLE_AIRLINE_ADMIN'){
                        next();
                    }
                    else{
                        alert('Page does not exist')
                        next("");
                    }
                
                });
            
        }
    },
    {
    	path:'/adminSystem',
    	name: 'AdminSystem',
    	component: AdminSystem,
        beforeEnter:(to,from,next)=> {
            axios.get("http://localhost:8080/api/users/whoami",{ headers: {"Authorization" : `Bearer ${localStorage.getItem('user-token')}`} }).then(response => 
                {
                    localStorage.setItem('role', response.data);
                    if(localStorage.getItem('role') === 'ROLE_SYSTEM_ADMIN'){
                        next();
                    }
                    else{
                        alert('Page does not exist')
                        next("");
                    }
                
                });
            
        }
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
    },
    {
        path: '/add-flight',
        name: 'AddFlight',
        component: AddFlight
    },
    {
        path: '/add-hotel',
        name: 'AddHotel',
        component: AddHotel
    },
    {
        path: '/add-rent-a-car',
        name: 'AddRentACar',
        component: AddRentACar
    },
    {
        path: '/airline-settings',
        name: 'AirlineSettings',
        component: AirlineSettings
    },
    {
        path: '/user-settings',
        name: 'UserSettings',
        component: UserSettings
    },
    {
        path: '/rent-a-car-settings',
        name: 'RentACarCompanySettings',
        component: RentACarCompanySettings
    },
    {
        path: '/registration',
        name: 'Registration',
        component: UserRegistration
    },
    {
        path: '/logout',
        name: 'Logout',
        component: Logout
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/carSearch',
        name: 'CarSearch',
        component: CarSearch
    },
    {
        path: '/friends',
        name: 'Friends',
        component: Friends
    },
    {
        path: '/airline-statistics',
        name: 'AirlineStats',
        component: AirlineStats

    },
    {
        path: '/add-company-destination',
        name: 'AddCompanyDestination',
        component: AddCompanyDestination
    },
  ]
})
