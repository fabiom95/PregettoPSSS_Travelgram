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
    private TravelJournal TJ;
    private TravelerList TL;

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
        TJ = new TravelJournal();
        TL = null;
    }


    // get e set
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


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setVisitedCountries(ArrayList<String> visitedCountries) {
        this.visitedCountries = visitedCountries;
    }

    public void setWishedCountries(ArrayList<String> wishedCountries) {
        this.wishedCountries = wishedCountries;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }




    public void setTJ(TravelerList TL, String country){
        this.TL = TL;
        this.TJ.loadMemories(this, country, userID);
    }

    public int getMemoryCount(){
        if(TJ != null)
            return TJ.getMemoryCount();
        return 0;
    }

    // altre funzioni
    public void loadTraveler(){
        travelerRepo.loadTraveler(this);
    }

    public void createUser(String username){
        travelerRepo.createUser(username);
    }

    public boolean isCountryVisited(String country){
        return visitedCountries.contains(country);
    }
    public boolean isCountryWished(String country){
        return wishedCountries.contains(country);
    }
    public boolean isFollowedByCurrentUser(){
        return followers.contains(travelerRepo.getCurrentUserID());
    }

    public ArrayList<String> getImageLinks(){
        if(TJ != null)
            return TJ.getImageLinks();
        return null;
    }

    public ArrayList<String> getMemoryIDs(){
        if(TJ != null)
            return TJ.getMemoryIDs();
        return null;
    }

    // notifica gli observer
    public void ready(String s){
        if(TL != null && s.equals("TJ ready"))
            TL.ready();
        setChanged();
        notifyObservers(s);
    }


    // funzioni invocate da PlaceViewModel
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


    // funzioni invocate da SearchViewModel
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
