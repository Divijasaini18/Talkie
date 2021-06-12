package com.example.talkie.models;

public class Users {

    public String profilePic, userName ,password, userId, lastMessage,about,email;

    public Users(String profilePic, String userName, String mail, String password, String userId, String lastMessage) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.email=mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }
    public Users() {}


    public Users(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getUserName() {
        return userName;
    }



    public String getPassword() {
        return password;
    }



    public String getLastMessage() {
        return lastMessage;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setAbout(String about) {
        this.about = about;
    }



    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
