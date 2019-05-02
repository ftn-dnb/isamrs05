import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import VueKonva from 'vue-konva'
import Toasted from 'vue-toasted';
import DatetimePicker from 'vuetify-datetime-picker'
import 'vuetify-datetime-picker/src/stylus/main.styl'

Vue.config.productionTip = false
Vue.use(VueKonva)
Vue.use(Toasted)
Vue.use(DatetimePicker)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
