package com.xiagang.bean;

public class User {
    private int id;
    private String name;
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnonymousName() {
        StringBuilder anonymous = null;
        if(name != null) {
            anonymous = new StringBuilder();
            anonymous.append(name.charAt(0));
            for(int i=0; i < name.length(); i++) {
                anonymous.append('*');
            }
        } else {
            System.exit(1);
        }
        return anonymous.toString();
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
    }

}

