# DNB Travel - Tim 05
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=ftn-dnb_isamrs05)](https://sonarcloud.io/dashboard?id=ftn-dnb_isamrs05)

Projekat iz predmeta internet softverske arhitekture i metodologije razvoja softvera.

Članovi tima:
* Boris Šuličenko SW 4/2016
* David Stanojević SW 41/2016
* Nemanja Miković SW 68/2016

# Uputstvo za pokretanje projekta

## Neophodni programi
Da bi se aplikacija pokrenula potrebno je instalirati sledeće programe:
* [MySQL](https://dev.mysql.com/downloads/) baza podataka
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows) razvojno okruzenje za pokretanje SpringBoot aplikacije
* [Node.js](https://nodejs.org/en/) za instalaciju VueJS framework-a

Klonirati repozitorijum:
```
git clone https://github.com/ftn-dnb/isamrs05
cd isamrs05
```


### Pokretanje frontend aplikacije
Prvo treba instalirati VueJS framework korišćenjem sledeće komande:
```
npm install -g @vue/cli
```

Premestiti se u folder frontend aplikacije i instalirati dodatne plugine:
```
cd dnb-travel-frontend
npm install
```

Pokrenuti aplikaciju (biće pokrenuta na portu 8000):
```
npm run serve
```


### Pokretanje backend aplikacije
Importovati SpringBoot projekat u IntelliJ IDEA koji se nalazi u folderu ```dnb-travel``` repozitorijuma.

Odabrati opciju ```Import project from external model``` i selektovati ```Maven```.

Sačekati da Maven skine sve biblioteke. U fajlu ```src/main/resources/application.properties``` podesiti parametre za bazu podataka. 
Parametri koji treba da se nameste:
```
spring.datasource.url = jdbc:mysql://localhost:3306/isamrs?useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username = root
spring.datasource.password = root
```
