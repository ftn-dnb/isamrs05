<template>
    <v-app>
        <v-content>
            <Navbar />

            <v-container class="mx-4 mb-4">
                <router-view />
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
import Navbar from './components/Navbar.vue';

export default {
  name: 'App',

  components: {
    Navbar,
  },

  data () {
    return {
    }
  }
}
</script>
