import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import VueKonva from 'vue-konva'
import Toasted from 'vue-toasted';
import Datetime from 'vue-datetime'
import 'vue-datetime/dist/vue-datetime.css'

Vue.config.productionTip = false
Vue.use(VueKonva)
Vue.use(Toasted)
Vue.use(Datetime)

export const bus = new Vue();

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
