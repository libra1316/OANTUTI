/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123client;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tran
 */
public class User implements Serializable{
    private int ID;
    private String username;
    private String password;
    private float score;
    private int state;
    private int action;
    private String mission;
    private ArrayList<User> listUserOnline;
    public User() {
     
    }
 

    public User(String username, String password, int action) {
        this.username = username;
        this.password = password;
        this.action = action;
    }
    

  

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public User(int ID, String username, String password, float score, int state) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.score = score;
        this.state = state;
    }

    public ArrayList<User> getListUserOnline() {
        return listUserOnline;
    }

    public void setListUserOnline(ArrayList<User> listUserOnline) {
        this.listUserOnline = listUserOnline;
    }
    
    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
    

  

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Object[] toObjects() {
        String t = new Float(this.score).toString();
        if(this.state == 1)
        return new Object[]{this.username,t,"online"};
        if(this.state == 2)
        return new Object[]{this.username,t,"busy"};
        return new Object[]{this.username,t,"offline"};
    }
    
}
