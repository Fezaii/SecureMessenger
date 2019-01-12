package com.example.fezai.securemessage.models;

public class Contact {

    private String username;
    private String phone;
    private String pubkey;
    private String privatekey;
    private String status;



    public Contact(String username, String phone, String pubkey, String privatekey, String status) {
        this.username = username;
        this.phone = phone;
        this.pubkey = pubkey;
        this.privatekey = privatekey;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Contact { "+ getUsername() +" , "+ getPhone() + "," + getPubkey() + "," + getPrivatekey() + "," + getStatus() +" }";
    }
}
