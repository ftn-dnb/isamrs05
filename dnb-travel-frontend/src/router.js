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
import RACDetailedView from './components/RACDetailedView.vue';
import Friends from './components/Friends.vue';
import AddHotel from './components/AddHotel';
import AddRentACar from './components/AddRentACar';
import AirlineStats from './components/AirlineStats.vue';
import AddAirline from './components/AddAirline';
import AddCompanyDestination from './components/AddCompanyDestination.vue';
import FlightFastReservationForm from './components/FlightFastReservationForm.vue';
import FlightReservation from './components/FlightReservation.vue';
import AddCar from './components/AddCar';
import HotelPriceListSettings from './components/HotelPriceListSettings.vue';
import AddRoom from './components/AddRoom';
import HotelSearch from './components/HotelSearch';
import HotelDetailedView from './components/HotelDetailedView';
import AdminPasswordChange from './components/AdminPasswordChange';
import AddCarReservation from './components/AddCarReservation';
import AddPriceListRAC from './components/AddPriceListRAC';
import SetActivePriceListRAC from './components/SetActivePriceListRAC';
import HotelDetailedViewFast from './components/HotelDetailedViewFast';
import AddBranchOffice from './components/AddBranchOffice';
import SetMainBranchOffice from './components/SetMainBranchOffice';
import SearchRAC from './components/SearchRAC';
import HotelStats from './components/HotelStats';
import DeleteCar from './components/DeleteCar';
import EditCar from './components/EditCar';
import DeleteBranchOffice from './components/DeleteBranchOffice';
import EditBranchOffice from './components/EditBranchOffice';
import RACIncome from './components/RACIncome';
import RACReservationStats from './components/RACReservationStats';
import HotelSettings from './components/HotelSettings';
import CancelCarReservation from './components/CancelCarReservation';
import RACCarStats from './components/RACCarStats';
import RACFast from './components/RACFast';
import ListHotelReservations from './components/ListHotelReservations';
import ListFlightReservations from './components/ListFlightReservations';

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
        path: '/hotels/:hotelID',
        props: true,
        name: 'HotelDetailedView',
        component: HotelDetailedView
    },
    {
        path: '/hotels_fast/:hotelID',
        props: true,
        name: 'HotelDetailedViewFast',
        component: HotelDetailedViewFast
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
        path: '/add-airline',
        name: 'AddAirline',
        component: AddAirline
    },
    {
        path: '/add-room',
        name: 'AddRoom',
        component: AddRoom
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
        path: '/hotels',
        props: true,
        name: 'HotelSearch',
        component: HotelSearch
    },
    {
        path: '/hotel-price-list-settings',
        name: 'HotelPriceListSettings',
        component: HotelPriceListSettings
    },
    {
        path: '/hotel-statistics',
        name: 'HotelStats',
        component: HotelStats
    },
    {
        path: '/hotel-settings',
        name: 'HotelSettings',
        component: HotelSettings
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
    {
        path: '/fast-reservation-seats',
        name: 'FlightFastReservationForm',
        component: FlightFastReservationForm,
    },
    {
        path: '/flight-reservation',
        name: 'FlightReservation',
        props: true,
        component: FlightReservation
    },
    {
        path: '/add-car',
        name: 'AddCar',
        component: AddCar
    },

    {
        path:'/admin-password-change',
        name:'AdminPasswordChange',
        component: AdminPasswordChange

    },

    {
        path:'/add-car-reservation',
        name:'AddCarReservation',
        component: AddCarReservation
    },

    {
        path:'/add-price-list-rac',
        name:'AddPriceListRAC',
        component: AddPriceListRAC

    },

    {
        path:'/set-active-price-list-rac',
        name:'SetActivePriceListRAC',
        component: SetActivePriceListRAC
    },

    {
        path:'/add-branch-office',
        name:'AddBranchOffice',
        component: AddBranchOffice
    },

    {
        path:'/set-main-branch-office',
        name:'SetMainBranchOffice',
        component: SetMainBranchOffice
    },

    {
        path:'/search-rac',
        props: true,
        name:'SearchRAC',
        component: SearchRAC
    },

    {
        path: 'rac/:racID',
        props: true,
        name: 'RACDetailedView',
        component: RACDetailedView

    },

    {
        path:'/delete-car',
        name: 'DeleteCar',
        component: DeleteCar
    },

    {
        path: '/edit-car',
        name: 'EditCar',
        component: EditCar
    },
    {
        path: '/delete-branch-office',
        name: 'DeleteBranchOffice',
        component: DeleteBranchOffice
    },

    {
        path: '/edit-branch-office',
        name: 'EditBranchOffice',
        component: EditBranchOffice
    },

    {
        path:'/rac-income',
        name:'RACIncome',
        component: RACIncome
    },
    {
        path:'/rac-resetvation-stats',
        name:'RACReservationStats',
        component: RACReservationStats
    },

    {
        path:'/cancel-car',
        name:'CancelCarReservation',
        component: CancelCarReservation
    },
    {
        path:'/car-stats',
        name:'RACCarStats',
        component: RACCarStats
    },

    {
        path:'rac-fast/:racID',
        name:'RACFast',
        props: true,
        component: RACFast
    },

    {
        path:'/list-hotel-reservation',
        name: 'ListHotelReservations',
        component: ListHotelReservations
    },
    
    {
        path:'/list-fligt-reservations',
        name:'ListFlightReservations',
        component: ListFlightReservations

    },

  ]
})
