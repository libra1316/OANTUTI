/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123server;
import pkg123client.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg123client.UserResult;

/**
 *
 * @author Tran
 */
public class ServerDAO {
    Connection con;
    String dbName="useraccountstuff" ,username = "root",password = "123456";
   
    public ServerDAO( ) {
       
        getDBConnection(dbName, username, password);
    }
    
    public void getDBConnection(String dbName, String username, String password){
        String dbURL = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass =  "com.mysql.jdbc.Driver";
        try{
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbURL, username, password);
            //ServerCtr.svv.showMess("Connected to DB successfully");
            System.out.println("Connected to DB successfully");
        }catch(Exception e){
            //ServerCtr.svv.showMess("Could not connect to DB!");
            System.out.println("Could not connect to DB!");
        }
    }
    public int checkUser(User us){
        String query = "SELECT * FROM logininfo where name = '"+us.getUsername()+"' AND pass = '"+us.getPassword()+"'";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                if(rs.getInt(5)==0)return 1;
                else return 2;
            }
        }catch(Exception e){
            // ServerCtr.svv.showMess("Could not query");
            System.out.println("cnq");
        }
        return 0;
    }
    public User getUserLogin(User us){
        String query = "SELECT * FROM logininfo WHERE name = '"+us.getUsername()+"'AND pass = '"+us.getPassword()+"'";
        User user = null;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                user = new  User(rs.getInt(3), rs.getString(1), rs.getString(2), rs.getFloat(4), rs.getInt(5));
               
            }
        }
        catch(Exception e){
        }
        ArrayList<User> temp = new ArrayList<>();
        query = "SELECT * FROM logininfo WHERE trangthai = 1"; //get userb online
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                User usertemp = new User(rs.getInt(3),rs.getString(1),rs.getString(2),rs.getFloat(4),rs.getInt(5));
                temp.add(usertemp);
            }
            user.setListUserOnline(temp);
        }catch(Exception e){
            System.out.println("Could not query users are online!");
        }
        return user;
        
    }
    
    public User getUserOnline(){
        ArrayList<User> temp = new ArrayList<>();
        User user = new User();
        String query = "SELECT * FROM logininfo WHERE trangthai = 1 OR trangthai = 2"; //get userb online
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                User usertemp = new User(rs.getInt(3),rs.getString(1),rs.getString(2),rs.getFloat(4),rs.getInt(5));
                temp.add(usertemp);
            }
            user.setListUserOnline(temp);
        }catch(Exception e){
            System.out.println("Could not query users are online!");
        }
        return user;
    }
    
    public User getAllUser(){
        ArrayList<User> temp = new ArrayList<>();
        User user = new User();
        String query = "SELECT * FROM logininfo WHERE 1"; //get userb online
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                User usertemp = new User(rs.getInt(3),rs.getString(1),rs.getString(2),rs.getFloat(4),rs.getInt(5));
                temp.add(usertemp);
            }
            user.setListUserOnline(temp);
        }catch(Exception e){
            System.out.println("Could not query users are online!");
        }
        return user;
    }
    public void changeState(User us, String Do){
         String query = "SELECT id FROM logininfo where name = '"+us.getUsername()+"'";
         int id = 0;
         try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
              id = rs.getInt(1);
            }
        }catch(Exception e){
             System.out.println("Could not query");  
        }
        if(Do.equals("login")){
            String t =  new Integer(id).toString();
            query = "UPDATE `logininfo` SET `trangthai` = '1' WHERE `logininfo`.`id` = " + t ;
            try{
            Statement st = con.createStatement();
            st.execute(query);
            
            }catch(Exception e){
                System.out.println("Could not change state");  
            }
        }
        if(Do.equals("logout")){
            String t =  new Integer(id).toString();
            query = "UPDATE `logininfo` SET `trangthai` = '0' WHERE `logininfo`.`id` = " + t ;
            try{
            Statement st = con.createStatement();
            st.execute(query);
            
            }catch(Exception e){
                System.out.println("Could not change state");  
            }
        }
        if(Do.equals("playing")){
            String t =  new Integer(id).toString();
            query = "UPDATE `logininfo` SET `trangthai` = '2' WHERE `logininfo`.`id` = " + t ;
            try{
            Statement st = con.createStatement();
            st.execute(query);
            
            }catch(Exception e){
                System.out.println("Could not change state");  
            }
        }
    }
    public boolean checkUserName(User us){
        String query = "SELECT * FROM logininfo where name = '"+us.getUsername()+"'";
       // String query = "SELECT * FROM logininfo where name ='"+us.getUsername()+"' AND pass = '"+ us.getPassword()+"'";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println("Could not query");
            
        }
        return false;
    }
    public void addUser(User us){
        String query = "SELECT MAX(id) FROM logininfo ";
        int id = 0;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
               id = rs.getInt(1)+1;
            }
        }catch(Exception e){
            System.out.println("Could not query");
            
        }
        User model = new User(id, us.getUsername(), us.getPassword(), 0,0);
        query = "INSERT INTO `logininfo` (`name`, `pass`, `id`, `score`, `trangthai`) VALUES (?,?,?,?,?);";
         try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, model.getUsername());
            ps.setString(2, model.getPassword());
            ps.setInt(3, model.getID());
            ps.setFloat(4, model.getScore());
            ps.setInt(5, 0);
            
            try{
                ps.executeUpdate();
            }catch(Exception  e){
            }
            }catch(Exception e){
                e.printStackTrace();
                
            }    
    }
    public void addHistory(String username,Float yourScore,String yourOpName,Float yourOpScore){
        String query = "SELECT MAX(id) FROM playershistory ";
        int id = 0;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
               id = rs.getInt(1)+1;
            }
        }catch(Exception e){
            System.out.println("Could not query");
            
        }
        int userid = 0;
        
        query = "SELECT id FROM logininfo WHERE name = '"+username+"'";
        
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
               userid = rs.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Could not query");
            
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date);
        
        query = "INSERT INTO `playershistory` (`id`, `userid`, `username`, `yourscore`,`youropname`, `youropscore`,`date`) VALUES (?,?,?,?,?,?,?);";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, userid);
            ps.setString(3, username);
            ps.setFloat(4, yourScore);
            ps.setString(5, yourOpName);
            ps.setFloat(6,yourOpScore);
            ps.setString(7, time);
            
            try{
                ps.executeUpdate();
            }catch(Exception  e){
            }
            }catch(Exception e){
                e.printStackTrace();
                
            }    
    }
    public void updateScore(String username,float newscore){
        String query = "SELECT score FROM logininfo WHERE name = '"+username+"'";
        float score = 0;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
               score = rs.getFloat(1);
            }
        }catch(Exception e){
            System.out.println("Could not query");
            
        }
        score = score + newscore;
        String scoreToString = new Float(score).toString();
        query = "UPDATE `logininfo` SET `score` = '"+scoreToString+"' WHERE `logininfo`.`name` = '"+username+"'";
        try{
            Statement st = con.createStatement();
            st.execute(query);

        } catch (Exception e) {
            System.out.println("Could not change state");
        }
    }
    public float getScore(String username){
       
        String query = "SELECT score FROM logininfo WHERE name = '"+username+"'";
        float score = 0;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
               score = rs.getFloat(1);
            }
        }catch(Exception e){
            System.out.println("Could not query");
            
        }
        return  score;
    }
    public UserResult getUserResult(UserResult ur){
        String query = "SELECT * FROM `playershistory` WHERE `playershistory`.`username` = '"+ur.getUsername()+"'";
        ArrayList<UserResult> temp = new ArrayList<UserResult>();
        UserResult urm = new UserResult();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
               UserResult urs = new UserResult(rs.getInt(1),rs.getInt(2), rs.getString(3),rs.getFloat(4),rs.getString(5),rs.getFloat(6),rs.getString(7));
               temp.add(urs);
            }
        }catch(Exception e){
            
        }
        urm.setListResult(temp);
        return urm;
    }
    public static void main(String[] args) {
        
    }
}
