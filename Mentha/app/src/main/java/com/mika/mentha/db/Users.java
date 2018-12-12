package com.mika.mentha.db;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Users extends DataSupport {
    private int id;
    private String username;
    private String passwd;
    private List<Text> textList = new ArrayList<Text>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public List<Text> getTextList() {
        return textList;
    }

    public void setTextList(List<Text> textList) {
        this.textList = textList;
    }
}
