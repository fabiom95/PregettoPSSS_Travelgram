package com.psss.travelgram.model.entity;

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


    // costruttore
    public Traveler(){
        travelerRepo = new TravelerRepository();
        visitedCountries = new ArrayList<>();
        wishedCountries = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
        loadTraveler();
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


    // altre funzioni
    public void loadTraveler(){
        travelerRepo.loadTraveler(this);
    }

    public boolean isCountryVisited(String country){
        return visitedCountries.contains(country);
    }
    public boolean isCountryWished(String country){
        return wishedCountries.contains(country);
    }
    public boolean isUserFollower(String userID){
        return followers.contains(userID);
    }
    public boolean isUserFollowing(String userID){
        return following.contains(userID);
    }

    // notifica gli observer
    public void ready(){
        setChanged();
        notifyObservers();
    }


    // funzioni invocate da PlaceViewModel
    public void addVisitedCountry(String country){
        if(!isCountryVisited(country)) {
            //visitedCountries.add(country);
            travelerRepo.addVisitedCountry(country);
        }
    }

    public void addWishedCountry(String country){
        if(!isCountryWished(country)) {
            //wishedCountries.add(country);
            travelerRepo.addWishedCountry(country);
        }
    }

    public void removeVisitedCountry(String country){
        if(isCountryVisited(country)) {
            //visitedCountries.remove(country);
            travelerRepo.removeVisitedCountry(country);
        }
    }

    public void removeWishedCountry(String country){
        if(isCountryWished(country)) {
            //wishedCountries.remove(country);
            travelerRepo.removeWishedCountry(country);
        }
    }


    // funzioni invocate da SearchViewModel
    public void addFollower(String userID){
        if(!isUserFollower(userID)) {
            //followers.add(userID);
            travelerRepo.addFollower(userID);
        }
    }

    public void removeFollower(String userID){
        if(isUserFollower(userID)) {
            //followers.remove(userID);
            travelerRepo.removeFollower(userID);
        }
    }



}
