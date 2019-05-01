import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import VueKonva from 'vue-konva'
import Toasted from 'vue-toasted';

Vue.config.productionTip = false
Vue.use(VueKonva)
Vue.use(Toasted)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
