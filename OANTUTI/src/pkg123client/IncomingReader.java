/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123client;

import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author ATTT
 */
public class IncomingReader extends Thread {
    ObjectOutputStream writer;
    ObjectInputStream reader;
    PlayingView plV;
    ClientResult cR;

    public IncomingReader(ObjectOutputStream oos, ObjectInputStream ois) {
        this.writer = oos;
        this.reader = ois;
    }
     @Override
        public void run() 
        {
            String[] data;
            Object stream;

            try 
            {
                while ((stream = reader.readObject()) != null) 
                {
                    if(stream instanceof User){
                        User man = (User) stream;
                        data = man.getMission().split(":");
                        //ClientLoginView.clSM.showMess(man.getMission());
                        if(data[0].equals("2")){
                            if (data[1].equals("signoutsuccessfully")) {
                                ClientLoginView.clSM.showMess("Sign out successfully");
                                ClientLoginView.sock.close();
                                ClientLoginView.clWV.dispose();
                                ClientLoginView clv =new ClientLoginView();
                                clv.setVisible(true);
                            }
                        }
                        if(data[0].equals("3")){
                            if(data[1].equals("getuseronlinesuccessfully")){
                            //    ClientLoginView.clSM.showMess("Get user online successfully");
                                String t = new Integer(ClientWaitingView.listUserOnline.getListUserOnline().size()).toString();
                            //    ClientLoginView.clSM.showMess("size of user online before = "+ t);
                                ClientLoginView.clWV.setUserOnline(man);
                                ClientWaitingView.listUserOnline.setListUserOnline(man.getListUserOnline());
                            //    t = new Integer(ClientWaitingView.listUserOnline.getListUserOnline().size()).toString();
                            //    ClientLoginView.clSM.showMess("size of user online after = "+ t);
                            }
                        }
                        if(data[0].equals("4")){
                            if(data[1].equals("connect")){
                                if(data[2].equals("thisuserisnotonline")) { 
                                    ClientLoginView.clSM.showMess("This user is not online");
                                }
                                if(data[2].equals("refuse")){
                                    ClientLoginView.clSM.showMess("This user refuse your connection");
                                }
                                if(data[2].equals("accept")){
                                    ClientLoginView.clWV.updateState();
                                    plV = new PlayingView(reader, writer, data[3], data[4],data[5]);
                                    plV.setVisible(true);
                                }
                                if(data[2].equals("makesomecon")){
                                    int output = JOptionPane.showConfirmDialog(ClientLoginView.clWV,data[3] +" want to connect with you",ClientLoginView.clWV.main.getUsername(),JOptionPane.YES_NO_OPTION);
                                    if(output == JOptionPane.YES_OPTION){
                                       User man2 = new User();
                                       man2.setAction(4);
                                       man2.setMission("4:yesconnect:" + data[3]+":"+data[4]);
                                       writer.writeObject(man2);
                                    }
                                    if(output == JOptionPane.NO_OPTION){
                                        User man2 = new User();
                                        man2.setAction(4);
                                        man2.setMission("4:noconnect:" + data[3]+":"+data[4]);
                                        writer.writeObject(man2);
                                    }
                                    
                                //    ClientLoginView.clSM.showConfirmConnect(data[2],ClientLoginView.clWV.main.getUsername());
                                }
                            }
                        }
                        if(data[0].equals("5")){
                            if (data[1].equals("win")) {
                                ClientLoginView.clSM.showMess("YOU WIN\n");
                                plV.setText(3,data[2]);
                                plV.enableButton();
                            }
                            if (data[1].equals("lose")) {
                                ClientLoginView.clSM.showMess("YOU LOSE\n");
                                plV.setText(-1,data[2]);
                                plV.enableButton();
                            }
                            if (data[1].equals("draw")) {
                                ClientLoginView.clSM.showMess("BOTH DRAW\n");
                                plV.setText(1,data[2]);
                                plV.enableButton();
                            }
                        }
                        
                        if(data[0].equals("waiting")){
                            ClientLoginView.clSM.showMess("Wait the oppoment for few second\n");
                            plV.diasableButton();
                        }
                        if(data[0].equals("thisroomnotavailable")){
                            ClientLoginView.clSM.showMess("This room now is not available.\nYour OP has just escaped\nClick 'Back' to save your result ");
                        }
                        if(data[0].equals("newscore")){
                            ClientLoginView.clWV.updateStateAndScore(man.getScore());
                            plV.score = 0;
                        }
                       /* for(String token : data){
                            ClientLoginView.clSM.showMess(token);
                        }*/
                        /*if(data[0].equals("1")){
                            if(data[1].equals("loginsuccessfully")){
                                ClientLoginView.clSM.showMess("Login succesfully");
                            }
                            if(data[1].equals("loginfalse")){
                                 ClientLoginView.clSM.showMess("Invaild username or password!");
                            }
                             if(data[1].equals("thisaccisusing")){
                                 ClientLoginView.clSM.showMess("This account is activing");
                            }
                            
                            
                        }*/
                       // User man = (User)stream;
                        //ClientLoginView.clSM.showMess(man.getUsername()+"\n"+man.getPassword()+"\n"+man.getMission()+"\n"+man.getScore()+"\n"+man.getAction());
                        //ClientLoginView.clSM.showMess("Login succesfully chiu chiu");
                    }
                    if(stream instanceof UserResult){
                        cR = new ClientResult();
                        UserResult urs = (UserResult) stream;
                        cR.setReult(urs);
                        cR.setVisible(true);
                        String t = new Integer(urs.getListResult().size()).toString();
                        
                    }
            }
        } catch (Exception ex) {
        }
    }
       

}
