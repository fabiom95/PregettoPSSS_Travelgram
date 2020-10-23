# Progetto PSSS: Travelgram

> credits go to Fabio Maresca, Pasquale Di Maio, Fabrizio Guillaro, Ivano Iodice;  this is an Italian team, so the ReadMe is built in two English sections and the Italian Introduction as well: 
> 1. English summary
> 2. Repository organization
> 3. Italian summary

<br />
<br />
<br />


## ENGLISH

### Introduction:

Travelgram allows you to store and share with friends your memories:
- interact with a ScratchMap where you can find in green the countries you have visited and in yellow those that you want to visit in future (marked as "wish").
- add and see your memories (images, texts, comments).

Look at your friends' experiences too:
- follow who you know and/or who you consider interesting, view their memories and reviews.
- manage your friendlist, login with facebook and follow automatically your facebook friends.

![alt text](https://github.com/fabiom95/ProgettoPSSS_Travelgram/blob/master/images/Immagine_interfaccia.png)



### How to insert a memory

1. Click on the “new Memory” button, where?
   - in the main interface (the ScratchMap);
   - first click on the “show cities” botton (on the main interface) and then click on a country of the map.
2. Add one or more images from your phone gallery.
3. Choose the city/country name; these two will be automatically chosen if you are in the 1.b scenario.
4. [Optional] Add more informations as place and date.
5. [Optional] Add a textual description of the memory. 
6. Click on “Share”.

![alt text](https://github.com/fabiom95/ProgettoPSSS_Travelgram/blob/master/images/UseCase_InserisciMemory.png)


<br />
<br />


### Repository Organization:

This section is widely descripted in the documentation, but is reported in the readme too in order to simplify the navigation in the repository.
The structure of the repository is:

.<br />
├── code<br />
│&nbsp; &nbsp; &nbsp; &nbsp;   ├── **apk_documentation** : contains the APK, documentation in pdf format, readme to guide the new users<br />
│&nbsp; &nbsp; &nbsp; &nbsp;   ├── .idea<br />
│&nbsp; &nbsp; &nbsp; &nbsp;   ├── **app**<br />
│&nbsp; &nbsp; &nbsp; &nbsp;   └── gradle/wrapper<br />
├── images : contains the pictures used for this readme<br />
├── README.md<br />
└── travelgram.vpp : is the Visual Paradigm file that contains UML diagrams<br />


Taking a look on the specific "app" section:

app<br />
└── src<br />
&nbsp; &nbsp; &nbsp; &nbsp;      ├── androidTest/java/com/psss/travelgram<br />
&nbsp; &nbsp; &nbsp; &nbsp;      ├── debug/res/values<br />
&nbsp; &nbsp; &nbsp; &nbsp;      ├── **main**<br />
&nbsp; &nbsp; &nbsp; &nbsp;      ├── release/res/values<br />
&nbsp; &nbsp; &nbsp; &nbsp;      └── test/java/com/psss/travelgram<br />


The main secontion contains:

main<br />
├── java/com/psss/travelgram<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ├── **model** : contains all the classes that implement entities and classes for communicate with server<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ├── **view** : contains classes necessary for the GUI<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; └── **viewmodel** : contains classes necessary to link model and view<br />
├── res : contains icons (folders "drawable" and "mipmap", folders as layout and menu (xml files) <br />
└── AndroidManifest.xml : contains important informations as package name and versions<br />

     
<br />
<br />
<br />

## ITALIAN

### Introduzione:

Con Travelgram puoi conservare i tuoi ricordi e condividerli con gli amici:
- interagisci con una mappa interattiva e visualizza i paesi visitati (verde) o che intendi visitare in futuro (giallo);
- aggiungi e visualizza le tue memories (immagini, testi, commenti).

Osserva le esperienze di altri travelers: 
- segui i tuoi amici o persone che reputi interessanti, visualizza le loro Memories e recensioni.
- gestisci la tua lista amici e segui automaticamente gli amici di facebook registrandoti all'app tramite il profilo facebook.

![alt text](https://github.com/fabiom95/ProgettoPSSS_Travelgram/blob/master/images/Immagine_interfaccia.png)



### Come inserire una memory:

1. Clicca sul bottone “new Memory”, dove?
   - nella schermata principale (la ScratchMap);
   - cliccando sul bottone “show cities” sulla schermata principale e selezionando una città sulla mappa.
2. Aggiungi una o più immagini dalla galleria del cellulare.
3. Seleziona i nomi della città e del paese; nel caso (1.b) i campi sono riempiti automaticamente in base alla città indicata.
4. [Opzionale] Aggiungi informazioni aggiuntive come luogo o data.
5. [Opzionale] Aggiungi una descrizione testuale del ricordo.
6. Clicca su “Share”.

![alt text](https://github.com/fabiom95/ProgettoPSSS_Travelgram/blob/master/images/UseCase_InserisciMemory.png)

