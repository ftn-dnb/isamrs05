<template>
    <div>
        Logout
    </div>
</template>
<script>
import {bus} from '../main';
  export default {
    data (){
      return {
      }
    },
    created:function(){
      console.log('logout');
      bus.$emit('logout', 'logout');
      this.$router.push('StartPage');
    }
  }
</script>