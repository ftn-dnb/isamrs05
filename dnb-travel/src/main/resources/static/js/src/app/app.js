const startPage = { template: '<start-page></start-page>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: startPage },
    ]
});

let app = new Vue({
    router,
    el: '#dnb-travel'
});