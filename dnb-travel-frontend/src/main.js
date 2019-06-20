import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import VueKonva from 'vue-konva'
import Toasted from 'vue-toasted';
import Datetime from 'vue-datetime'
import 'vue-datetime/dist/vue-datetime.css'
import VueGoogleCharts from 'vue-google-charts'
import * as VueGoogleMaps from 'vue2-google-maps'
import VuetifyGoogleAutocomplete from 'vuetify-google-autocomplete';
 
Vue.use(VuetifyGoogleAutocomplete, {
  apiKey: 'AIzaSyBA0eblab9krEoWhf7MB1-5dzlQeEhq08Q',
});

Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyBA0eblab9krEoWhf7MB1-5dzlQeEhq08Q',
    libraries: 'places',
  },
});

Vue.config.productionTip = false
Vue.use(VueKonva)
Vue.use(Toasted)
Vue.use(Datetime)
Vue.use(VueGoogleCharts)

export const bus = new Vue();

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
