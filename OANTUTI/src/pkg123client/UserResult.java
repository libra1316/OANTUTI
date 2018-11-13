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
public class UserResult implements Serializable{
    private int id;
    private int userid;
    private String username;
    private float userscore;
    private String rival;
    private float rivalscore;
    private String time;
    private ArrayList<UserResult> listResult = new ArrayList<UserResult>();
    public UserResult(int id, int userid, String username, float userscore, String rival, float rivalscore,String time) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.userscore = userscore;
        this.rival = rival;
        this.rivalscore = rivalscore;
        this.time = time;
    }

    public UserResult() {
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getUserscore() {
        return userscore;
    }

    public void setUserscore(float userscore) {
        this.userscore = userscore;
    }

    public String getRival() {
        return rival;
    }

    public void setRival(String rival) {
        this.rival = rival;
    }

    public ArrayList<UserResult> getListResult() {
        return listResult;
    }

    public void setListResult(ArrayList<UserResult> listResult) {
        this.listResult = listResult;
    }

    public float getRivalscore() {
        return rivalscore;
    }

    public void setRivalscore(float rivalscore) {
        this.rivalscore = rivalscore;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Object[] toObjects(){
        String t = new Float(this.userscore).toString();
        String t1 = new Float(this.rivalscore).toString();
        return new Object[] {this.username, t,this.rival,t1,this.time};
    }
}
