package com.psss.travelgram.model.entity;

import com.psss.travelgram.model.repository.AuthRepository;
import com.psss.travelgram.model.repository.TravelerRepository;

import java.util.ArrayList;
import java.util.Observable;


public class Traveler extends Observable {

    private String username;
    private String userID;
    private ArrayList<String> visitedCountries;
    private ArrayList<String> wishedCountries;
    private ArrayList<String> followers;
    private ArrayList<String> following;

    private TravelerRepository travelerRepo;
    private AuthRepository authRepo;


    // costruttore
    public Traveler(){
        travelerRepo = new TravelerRepository();
        authRepo = new AuthRepository();
        visitedCountries = new ArrayList<>();
        wishedCountries = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
    }


    // set e get
    public void setUsername(String username) {
        this.username = username;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setVisitedCountries(ArrayList<String> visitedCountries) { this.visitedCountries = visitedCountries;}
    public void setWishedCountries(ArrayList<String> wishedCountries) { this.wishedCountries = wishedCountries;}
    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }
    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    public String getUsername() {
        return username;
    }
    public String getUserID() {
        return userID;
    }
    public ArrayList<String> getVisitedCountries() {
        return visitedCountries;
    }
    public ArrayList<String> getWishedCountries() {
        return wishedCountries;
    }
    public ArrayList<String> getFollowers() {
        return followers;
    }
    public ArrayList<String> getFollowing() {
        return following;
    }


    // inserimento nuovo Traveler su Firestore
    public void createTraveler(String username){
        travelerRepo.createTraveler(username);
    }

    // caricamento Traveler da Firestore
    public void loadTraveler(){
        travelerRepo.loadTraveler(this);
    }

    // funzioni di utilità
    public boolean isCountryVisited(String country){
        return visitedCountries.contains(country);
    }
    public boolean isCountryWished(String country){
        return wishedCountries.contains(country);
    }
    public boolean isFollowedByCurrentUser(){ return followers.contains(travelerRepo.getCurrentUserID()); }


    // callback, chiamata da travelerRepo quando è arrivato il risultato della query
    // notifica gli observer
    public void callback(String s){
        setChanged();
        notifyObservers(s);
    }


    // funzioni per PlaceViewModel
    public void addVisitedCountry(String country){
        if(!isCountryVisited(country)) {
            travelerRepo.addVisitedCountry(country);
        }
    }

    public void addWishedCountry(String country){
        if(!isCountryWished(country)) {
            travelerRepo.addWishedCountry(country);
        }
    }

    public void removeVisitedCountry(String country){
        if(isCountryVisited(country)) {
            travelerRepo.removeVisitedCountry(country);
        }
    }

    public void removeWishedCountry(String country){
        if(isCountryWished(country)) {
            travelerRepo.removeWishedCountry(country);
        }
    }


    // funzioni per SearchViewModel
    public void follow(){
        travelerRepo.follow(userID);
    }

    public void unfollow(){
        travelerRepo.unfollow(userID);
    }


    // funzioni di autenticazione
    public void loginUser(String email, String password){
        authRepo.loginUser(email, password, this);
    }

    public void signupUser(String username, String email, String password){
        authRepo.signupUser(username, email, password, this);
    }


}
