Vue.component('airline-settings', {
    data: function() {
        return {
            airline: {
                id: null,
                name: null,
                address: null,
                description: null,
            },
        };
    },

    template:
    `
    <div>
        <h3>Podešavanja profila aviokompanije {{ airline.name }}</h3>

        <table>
            <tr>
                <td>Naziv:</td>
                <td><input type="text" v-model="airline.name" /></td>
            </tr>
            <tr>
                <td>Adresa:</td>
                <td><input type="text" v-model="airline.address" /></td>
            </tr>
            <tr>
                <td>Promotivni opis</td>
                <td><input type="textarea" v-model="airline.description" /></td>
            </tr>
            <tr>
                <th colspan="2"><input type="button" value="Izmeni profil" v-on:click="editProfile()" /></th>
            </tr>
        </table>
    </div>
    `,

    methods: {
        editProfile: function() {

            axios.put('api/airlines/' + this.airline.id, this.airline)
            .then(response => {
                if (response.data === '') {
                    alert('Doslo je do greske prilikom izmene profila aviokompanije');
                    return;
                }

                alert('Profil aviokompanije je uspesno izmenjen');
            });
        },
    },

    mounted() {
        // @TODO: pokupiti podatke odgovarajuce aviokompanije na osnovu podataka
        // njenog administratora
        // Zasad se uzima predefinisana vrednost iz baze
        axios.get("api/airlines/3").then(response => this.airline = response.data);
    }
});