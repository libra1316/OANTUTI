/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123server;
import java.awt.event.WindowAdapter;
import pkg123client.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import pkg123client.UserResult;

/**
 *
 * @author Tran
 */
public class ServerView extends javax.swing.JFrame {
    ArrayList clientOutputStreams;
    ArrayList clientSCK;
    ArrayList<User> users;
    ArrayList<Result>room;   
    String dbName = "useraccount";
    String username = "root", password = "";
    ServerSocket serverSock ;
    
    /**
     * Creates new form serveroos
     */
    public class ClientHandler implements Runnable	
   {
       ObjectInputStream reader;
       Socket sock;
       ObjectOutputStream client;
       
       public ClientHandler(Socket clientSocket, ObjectOutputStream user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                reader = new ObjectInputStream(sock.getInputStream());
                
            }
            catch (Exception ex) 
            {
                ta_chat.append("Unexpected error... \n");
            }

       }

       @Override
       public void run() 
       {
            Object message;
          
            try 
            {
                
                while ((message = reader.readObject()) != null) 
                {   
                  
                        //User user = (User) message;
                        //client.writeObject("MVL");
                    if(message instanceof User)
                    {   
                        User man = (User) message;
                        switch(man.getAction()){
                            case 1:{
                                showMess(man.getUsername()+":"+man.getMission());
                                ServerDAO sDAO =new ServerDAO();
                                
                                if(sDAO.checkUser(man) == 0){
                                    User backToClient = new User();
                                    backToClient.setMission("1:loginfalse");
                                    client.writeObject(backToClient);
                                }
                                if(sDAO.checkUser(man) == 2){
                                    User backToClient = new User();
                                    backToClient.setMission("1:thisaccisusing");
                                    client.writeObject(backToClient);
                                }
                                if(sDAO.checkUser(man) == 1){
                                    sDAO.changeState(man,"login");
                                    man.setAction(0);
                                    users.add(man);
                                    
                                    
                                    showMess("*_______*");
                                    showMess("user name:"+man.getUsername());
                                    showMess("mission :"+man.getMission());
                                    showMess("pass word :"+man.getPassword());
                                    //showMess("online list :"+man.getListUserOnline().size());
                                    String t= new Integer(man.getAction()).toString();
                                    showMess("Action :" +t);
                                    t = new Integer(man.getState() ).toString();                                    
                                    showMess("state" +t);
                                    showMess("*_______*");
                                    
                                    User backToClient = sDAO.getUserLogin(man); 
                                    backToClient.setMission("1:loginsuccessfully");
                                    client.writeObject(backToClient);
                                 //   client.flush();
                                    
                                    
                                 
                                 
                                }
                                break;}
                            case 2:{
                                ServerDAO sDAO = new ServerDAO();
                                showMess(man.getUsername()+":"+man.getMission());
                                if(man.getUsername().equals("")){
                                    sock.close();
                                    client.close();
                                    clientOutputStreams.remove(client);
                                
                                }
                                if(man.getUsername().equals("")==false){
                                    sDAO.changeState(man, "logout");
                                    man.setAction(0);
                                    man.setMission(null);
                                    User backToClient = new User();
                                    backToClient.setMission("2:signoutsuccessfully");
                                    client.writeObject(backToClient);
                                    sock.close();
                                    
                                    showMess("*_______*");
                                    showMess("user name:"+man.getUsername());
                                    showMess("mission :"+man.getMission());
                                    showMess("pass word :"+man.getPassword());
                                    //showMess("online list :"+man.getListUserOnline().size());
                                    String t= new Integer(man.getAction()).toString();
                                    showMess("Action :" +t);
                                    t = new Integer(man.getState() ).toString();                                    
                                    showMess("state" +t);
                                    showMess("*_______*");
                                    int j = -1;
                                    for(int i = 0 ; i<users.size();i++){
                                        if(man.getUsername().equals(users.get(i).getUsername())){
                                            j = i;
                                        }
                                    }
                                    if(j != -1)
                                    users.remove(j);
                                    clientOutputStreams.remove(client);
                                }
                                break;
                            }
                            case 3:{
                                ServerDAO sDAO = new ServerDAO();
                                showMess(man.getUsername()+":"+man.getMission());
                                User backToClient = sDAO.getUserOnline();
                                backToClient.setMission("3:getuseronlinesuccessfully");
                                client.writeObject(backToClient);
                                break;
                            }
                            case 4:{
                                showMess(man.getUsername()+":"+man.getMission());
                                String[] data;
                                data = man.getMission().split(":");
                                if(data[1].equals("connect")){
                                    int j = -1;
                                    for(int i = 0 ; i < users.size();i++){
                                        if(data[3].equals(users.get(i).getUsername())){
                                            j = i;
                                        }
                                    }
                                    if(j!=-1){
                                        ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(j);
                                        User userBackToClientDefied = new User();
                                        userBackToClientDefied.setMission("4:connect:makesomecon:"+man.getUsername()+":"+data[3]);
                                        writer.writeObject(userBackToClientDefied);
                                        
                                        showMess("send requet to "+users.get(j).getUsername()+" successfully");
                                    }
                                    else{
                                        User userBackToClientDefy = new User();
                                        userBackToClientDefy.setMission("4:connect:thisuserisnotonline");
                                        client.writeObject(userBackToClientDefy);
                                        showMess("This user not online");
                                    }
                                }
                                if(data[1].equals("noconnect")){
                                    int j = -1;
                                    for(int i = 0 ; i < users.size();i++){
                                        if(data[2].equals(users.get(i).getUsername())){
                                            j = i;
                                        }
                                    }
                                    if(j!=-1){
                                        ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(j);
                                        User userBackToClientDefy = new User();
                                        userBackToClientDefy.setMission("4:connect:refuse");
                                        writer.writeObject(userBackToClientDefy);
                                        showMess("send responde to "+users.get(j).getUsername()+" successfully");
                                    }
                                }
                               if(data[1].equals("yesconnect")){
                                    int j = -1;//user thach dau
                                    int k = -1;//user bi thach dau
                                    for(int i = 0 ; i < users.size();i++){
                                        if(data[2].equals(users.get(i).getUsername())){
                                            j = i;
                                        }
                                    }
                                    for(int i = 0 ; i < users.size();i++){
                                        if(data[3].equals(users.get(i).getUsername())){
                                            k = i;
                                        }
                                    }
                                    if(j!=-1 && k!=-1){
                                        ServerDAO sDAO = new ServerDAO();
                                        sDAO.changeState(users.get(j), "playing");
                                        sDAO.changeState(users.get(k), "playing");
                                        ObjectOutputStream writer1 = (ObjectOutputStream) clientOutputStreams.get(j);//user thach dau
                                        ObjectOutputStream writer2 = (ObjectOutputStream) clientOutputStreams.get(k);//user bi thach dau
                                        User userBackToClientDefy = new User();
                                        User userBackToClientDified = new User();
                                        
                                        
                                        //_____Tao 1 phong moi cho 2 user nay
                                        Random rd = new Random();
                                        int checkRoom = 0;
                                        String rdRoom = "1";
                                        while (checkRoom == 0) {
                                            checkRoom = 1;
                                            int rdIntRoom = rd.nextInt(1000);
                                            rdRoom = new Integer(rdIntRoom).toString();
                                            for (int i = 0; i < room.size(); i++) {
                                                if (room.get(i).equals(rdRoom)) {
                                                    checkRoom = 0;
                                                }
                                            }
                                        }
                                        room.add(new Result(rdRoom));
                                        userBackToClientDefy.setMission("4:connect:accept:"+data[2]+":"+data[3]+":"+rdRoom);
                                        userBackToClientDified.setMission("4:connect:accept:"+data[3]+":"+data[2]+":"+rdRoom);
                                        writer1.writeObject(userBackToClientDefy);
                                        writer2.writeObject(userBackToClientDified);
                                        
                                        showMess("send responde to "+users.get(j).getUsername()+" successfully");
                                    }
                                }
                                break;
                            }
                            case 5:{
                                showMess(man.getUsername()+":"+man.getMission());
                                String[] data;
                                data = man.getMission().split(":");
                                int vt1 = -1;
                                int vt2 = -1;
                                ObjectOutputStream writer1 = null;
                                ObjectOutputStream writer2 = null;
                                int j = -1;
                                String t = new Integer(room.size()).toString();
                                showMess("size cua room = " +t);
                                for (int i = 0; i < room.size(); i++) {
                                    if (data[5].equals(room.get(i).name)) {
                                        j = i;
                                    }
                                }
                                if( j != -1){
                                    if (room.get(j).user1.equals("") == true && room.get(j).user2.equals("") == true) {
                                           room.get(j).user1 = data[2];
                                           room.get(j).user1P = data[3];
                                           User m = new User();
                                           m.setMission("waiting");
                                           client.writeObject(m);
                                    }
                                    if (room.get(j).user2.equals("") == true && data[2].equals(room.get(j).user1) == false) {
                                        room.get(j).user2 = data[2];
                                        room.get(j).user2P = data[3];
                                       
                                    }
                                    
                                    if (data[2].equals(room.get(j).user1) == true && room.get(j).user1P.equals("") == true) {
                                        room.get(j).user1P = data[3];
                                        User m = new User();
                                        m.setMission("waiting");
                                        client.writeObject(m);
                                    }
                                    if (data[2].equals(room.get(j).user2) == true && room.get(j).user2P.equals("") == true) {
                                        room.get(j).user2P = data[3];
                                        User m = new User();
                                        m.setMission("waiting");
                                        client.writeObject(m);
                                    }

                                    if (room.get(j).user1.equals("") == false && room.get(j).user2.equals("") == false && room.get(j).user1P.equals("") == false && room.get(j).user2P.equals("") == false) {
                                           room.get(j).XetKetQua();
                                           ta_chat.append(room.get(j).result1);
                                           ta_chat.append(room.get(j).result2);
                                           for (int i = 0; i < users.size(); i++) {
                                               if (room.get(j).user1.equals(users.get(i).getUsername())) {
                                                   vt1 = i;
                                               }
                                           }
                                           for (int i = 0; i < users.size(); i++) {
                                               if (room.get(j).user2.equals(users.get(i).getUsername())) {
                                                   vt2 = i;                                                  
                                               }
                                           }
                                           writer1 = (ObjectOutputStream) clientOutputStreams.get(vt1);
                                           writer2 = (ObjectOutputStream) clientOutputStreams.get(vt2);
                                           
                                           room.get(j).user1P = "";
                                           room.get(j).user2P = "";
                                           String scoreOP = new Float(room.get(j).r2).toString();
                                           User u1 = new User();
                                           u1.setMission("5:"+room.get(j).result1+":"+scoreOP);
                                           scoreOP = new Float(room.get(j).r1).toString();
                                           User u2= new User();
                                           u2.setMission("5:"+ room.get(j).result2+":"+scoreOP);
                                           writer1.writeObject(u1);
                                           writer2.writeObject(u2);
                                    }
                                }
                                if(j==-1){
                                    User backToClient = new User();
                                    backToClient.setMission("thisroomnotavailable");
                                    client.writeObject(backToClient);
                                }
                                break;
                            }
                            case 6:{
                                showMess(man.getUsername()+":"+man.getMission());
                                String[] data;
                                data = man.getMission().split(":");
                                if(data[1].equals("savethegame")){
                                    int j = -1;
                                    for(int i = 0;i < room.size();i++){
                                        if(room.get(i).getName().equals(data[2])){
                                            j = i;
                                        }
                                    }
                                    if(j!=-1){
                                        room.remove(j);
                                    }
                                    ServerDAO sDAO = new ServerDAO();
                                    float yourScore =  new Float(data[3]);
                                    float yourOpScore =  new Float(data[4]);
                                    sDAO.addHistory(man.getUsername(), yourScore, data[5], yourOpScore);
                                    sDAO.updateScore(man.getUsername(), yourScore);
                                    sDAO.changeState(man, "login");
                                    User backToClient = new User();
                                    backToClient.setScore(sDAO.getScore(man.getUsername()));
                                    backToClient.setMission("newscore");
                                    client.writeObject(backToClient);
                                }
                                break;
                            }
                            case 7:{
                                showMess(man.getUsername()+":"+man.getMission());
                                String t = new Integer(man.getAction()).toString();
                                showMess(t);
                                ServerDAO sDAO = new ServerDAO();
                                if(sDAO.checkUserName(man) == true){
                                    User backToClient = new User();
                                    backToClient.setMission("7:thisaccwasavail");
                                    client.writeObject(backToClient);
                                }
                                if(sDAO.checkUserName(man)==false){
                                    User backToClient = new User();
                                    sDAO.addUser(man);
                                    backToClient.setMission("7:signupsuccessfully");
                                    client.writeObject(backToClient);
                                }
                                break;
                            }
                                
                        }
                        
                    }
                    if(message instanceof UserResult)
                    {
                        UserResult urs = (UserResult) message;
                        ServerDAO sDAO = new ServerDAO();
                        UserResult backToClient = new UserResult();
                        backToClient = sDAO.getUserResult(urs);
                        backToClient.setUsername(urs.getUsername());
                        client.writeObject(backToClient);
                    }
               }
           } catch (Exception ex) {
               ta_chat.append("Lost a connection. \n");
               ex.printStackTrace();
               int i = clientOutputStreams.indexOf(client);
               clientOutputStreams.remove(client);
               try{
                  ServerDAO sDAO= new ServerDAO();
                  sDAO.changeState(users.get(i), "logout");
                  users.remove(i);
               }catch(Exception e){
                   
               }
               
           }
	} 
    }
    public ServerView() {
        initComponents();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(){
                try {
                    ServerDAO sDAO = new ServerDAO();
                    User u = sDAO.getAllUser();
                    for (int i = 0; i < u.getListUserOnline().size(); i++) {
                        sDAO.changeState(u.getListUserOnline().get(i), "logout");
                    }
                    serverSock.close();
                    showMess("Close server successfully");
                } catch (Exception e) {
                    showMess("Could not close server");
                }
            }
        });
    }
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            room  = new ArrayList<Result>();
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  
            try 
            {
                 serverSock = new ServerSocket(2223);
                ta_chat.append("Server started...\n");
                while (true) 
                {
				Socket clientSock = serverSock.accept();           
				ObjectOutputStream oos = new ObjectOutputStream(clientSock.getOutputStream());// khởi tạo 1 biến ghi data
				clientOutputStreams.add(oos);// thêm client vào mảng
                                String t = clientSock.getRemoteSocketAddress().toString();
                              //  clientSCK.add(t);
                                System.out.println(clientOutputStreams.size());
                                if(clientOutputStreams.get(0) instanceof ObjectOutputStream)System.out.println("true");
                                System.out.println(oos);
				Thread listener = new Thread(new ClientHandler(clientSock, oos));
				listener.start();
				ta_chat.append("******Got a connection :"+ clientSock.getRemoteSocketAddress() +". ******\n");
                }
            }
            catch (Exception ex)
            {
                ta_chat.append("Error making a connection. \n");
            }
        }
    }
    
    public static void showMess(String mes){
        ta_chat.append(mes +"\n");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("User Online");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("End");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Check room");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        for(int i =0 ; i<users.size();i++){
            showMess( "**** : " +users.get(i).getUsername());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         try{
             ServerDAO sDAO = new ServerDAO();
             User u = sDAO.getAllUser();
             for(int i = 0; i<u.getListUserOnline().size();i++){
                sDAO.changeState(u.getListUserOnline().get(i), "logout");
             }
             serverSock.close();
             showMess("Close server successfully");
         }catch(Exception e){
             showMess("Could not close server");
         }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         for(int i =0 ; i<room.size();i++){
            showMess( "**** : " +room.get(i).getName()+" user1 :"+room.get(i).getUser1()+" user2 :"+room.get(i).getUser2());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
 
    public static void main(String[] args) {
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea ta_chat;
    // End of variables declaration//GEN-END:variables
}
