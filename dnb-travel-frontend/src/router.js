import Vue from 'vue'
import Router from 'vue-router'
import App from './App.vue'
import AdminRentACar from './components/AdminRentACar.vue'
import StartPage from './components/StartPage.vue'
import AdminHotel from './components/AdminHotel.vue'
import AdminAirline from './components/AdminAirline.vue'
import AdminSystem from './components/AdminSystem.vue'
import axios from 'axios';  
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

  ]
})
