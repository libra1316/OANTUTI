/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123server;

/**
 *
 * @author Tran
 */
public class Result {
    public String name;
    public String user1P = "";
    public String user2P = "";
    public String user1 = "";
    public String user2 ="";
    public String result1;
    public String result2;
    public float r1 = 0;
    public float r2 = 0;
    public Result(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser1P() {
        return user1P;
    }

    public void setUser1P(String user1P) {
        this.user1P = user1P;
    }

    public String getUser2P() {
        return user2P;
    }

    public void setUser2P(String user2P) {
        this.user2P = user2P;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getResult() {
        return result1;
    }

    public void setResult(String result) {
        this.result1 = result;
    }
    public void XetKetQua(){
        if(user1P.equals("keo") && user2P.equals("la")){
            result1 = "win";
            r1 +=3;
            result2 = "lose";
            r2 -=1;
        }
        if(user1P.equals("keo") && user2P.equals("bua")){
            result1 = "lose";
            r1 -=1;
            result2 = "win";
            r2 +=3;
        }
        if(user1P.equals("keo") && user2P.equals("keo")){
            result1 = "draw";
            r1 +=1;
            result2 = "draw";
            r2 +=1;
        }
        if(user1P.equals("la") && user2P.equals("bua")){
            result1 = "win";
            r1 +=3;
            result2 = "lose";
            r2 -=1;
        }
        if(user1P.equals("la") && user2P.equals("keo")){
            result1 = "lose";
            result2 = "win";
            r1 -=1;
            r2 +=3;
        }if(user1P.equals("la") && user2P.equals("la")){
            result1 = "draw";
            result2 = "draw";
            r1 +=1;
            r2 +=1;
        }if(user1P.equals("bua") && user2P.equals("keo")){
            result1 = "win";
            result2 = "lose";
            r1+=3;
            r2-=1;
        }if(user1P.equals("bua") && user2P.equals("la")){
            result1 = "lose";
            result2 = "win";
            r1 -=1;
            r2 +=3;
        }if(user1P.equals("bua") && user2P.equals("bua")){
            result1 = "draw";
            result2 = "draw";
            r1 +=1;
            r2 +=1;
        }
    }
    
}
