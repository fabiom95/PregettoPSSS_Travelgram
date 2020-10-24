# Progetto PSSS: Travelgram

> credits go to Pasquale Di Maio, Fabrizio Guillaro, Ivano Iodice, Fabio Maresca;  this is an Italian team, so the ReadMe is built in two English sections and the Italian Introduction as well: 
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
├── **apk_and_documentation** : contains the APK, documentation, UML diagrams and a readme<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   ├── **Travelgram_Lyx** : LaTeX source for the .pdf documentation<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   ├── **README-video_e_consigli_per_primo_utilizzo.txt** : guide for the new user<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   ├── **Travelgram - Di Maio, Guillaro, Iodice, Maresca.pdf** : documentation<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   ├── **travelgram.apk** : APK<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   └── **travelgram.vpp** : is the Visual Paradigm file that contains UML diagrams<br />
├── **code/Travelgram** : contains the application code<br /> 
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   ├── **app/src/main/java/com/psss/travelgram** : contains the packages with the java classes<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   ├── **app/src/androidTest/java/com/psss/travelgram** : contains the test cases<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   ├── **app/src/main/res** : contains the resources<br />
│&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   └── **app/src/main/AndroidManifest.xml** : the app manifest<br />
├── images : contains the pictures used for this readme<br />
└── README.md<br />


     
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

