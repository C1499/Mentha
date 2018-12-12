package com.mika.mentha.db;

import org.litepal.crud.DataSupport;

public class Text extends DataSupport {

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    private String content;
    private String creationTime;
    private Users users;

}
