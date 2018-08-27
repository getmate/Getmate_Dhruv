package com.getmate.getmate_final.Class;

/**
 * Created by Dhruv on 6/26/2018.
 */


public class CurrentUserData {
    public Person getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }

    Person currentUser = new Person();

}
