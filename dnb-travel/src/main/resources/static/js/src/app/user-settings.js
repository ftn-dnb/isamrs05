Vue.component('user-settings', {
    data: function() {
        return {
            user: {
                id: null,
                firstName: null,
                lastName: null,
            },
        };
    },

    template:
    `
    <div>
        <h3>Podešavanja profila</h3>

        <table>
            <tr>
                <td>Ime:</td>
                <td><input type="text" v-model="user.firstName" /></td>
            </tr>
            <tr>
                <td>Prezime:</td>
                <td><input type="text" v-model="user.lastName" /></td>
            </tr>
            <tr>
                <th colspan="2"><input type="button" value="Izmeni profil" v-on:click="editProfile()" /></th>
            </tr>
        </table>
    </div>
    `,

    methods: {
        editProfile: function() {

            axios.put('api/users/' + this.user.id, this.user)
            .then(response => {
                if (response.data === '') {
                    alert('Doslo je do greske prilikom izmene profila');
                    return;
                }

                alert('Profil je uspesno izmenjen');
            });
        },
    },

    mounted() {
        // @TODO: pokupiti podatke korisnika preko cookie-a za ulogovanog korisnika
        // Zasad uzima predefinisanog korisnika 1 iz baze
        axios.get("api/users/1").then(response => this.user = response.data);
    }
});