package com.example.myexplist;

import androidx.room.ColumnInfo;


public class searchResult {

    @ColumnInfo(name = "isContact")
    private String isContact;

    @ColumnInfo(name = "type")
    private String type;

    public void setIsContact(String isContact) {
        this.isContact = isContact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsContact() {
        return isContact;
    }

    public String getType() {
        return type;
    }

}