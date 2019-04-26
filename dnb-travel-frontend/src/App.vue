<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import StartPage from './components/StartPage.vue';
import UserSettings from './components/UserSettings.vue';
import HotelSettings from './components/HotelSettings.vue';
import HotelRegistration from './components/HotelRegistration.vue';

export default {
    name: 'App',
    components: {
      StartPage,
      UserSettings,
      HotelSettings
    },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
