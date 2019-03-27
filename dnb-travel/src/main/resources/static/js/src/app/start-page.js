Vue.component('start-page', {

    data: function() {
        return {

        }
    },

    template:
    `
    <div>
        <h1>Hello from Vue.js</h1>

        <!--- @TODO: obrisati komponente ispod, zasad stoje ovde samo za testiranje --->
        <user-settings></user-settings>
        <airline-settings></airline-settings>

    </div>
    `,

    methods: {

    },

    mounted() {

    }
});