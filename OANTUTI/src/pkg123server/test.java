/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123server;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg123client.User;
import pkg123client.UserResult;

/**
 *
 * @author Tran
 */
public class test {
    public static void main(String[] args) {
        ServerDAO dao = new ServerDAO();
        float i = 30;
        float i2 =-20;
        User us = new User();
        us.setUsername("a");
      //  System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48   
        //dao.addHistory("hongcong",i, "hongcong2",i2);
//        System.out.println(dao.checkUserName(us));
        UserResult ur = new UserResult();
        ur.setUsername("a");
        UserResult t = new UserResult();
        t = dao.getUserResult(ur);
        System.out.println("size = "+t.getListResult().size());
    }
}











/*
if (message instanceof String) {
                       data = message.toString().split(":");
                       for (String token : data) {
                           ta_chat.append(token + "\n");
                       }
                       if (data[0].equals("Login")) {
                           //ServerDAO sDAO = new ServerDAO();
                           userAdd(data[1]);
                           client.writeObject("Login:true");
                           client.flush();
                       }
                       if (data[0].equals("Connect")) {

                           for (int i = 0; i < users.size(); i++) {
                               if (data[2].equals(users.get(i))) {
                                   vt = i;
                               }
                           }
                           if (vt != -1) {
                               ObjectOutputStream writer = (ObjectOutputStream) clientOutputStreams.get(vt);
                               writer.writeObject("Connect:" + users.get(vt) + ":" + data[1]);
                           } else {
                               client.writeObject("Connect:false");
                           }

                       }
                       if (data[0].equals("Play")) {
                           if (data[1].equals("accept")) {

                               if (data[2].equals("playing") == false) {
                                   int vt1 = -1;
                                   int vt2 = -1;
                                   ObjectOutputStream writer1 = null;
                                   ObjectOutputStream writer2 = null;
                                   for (int i = 0; i < users.size(); i++) {
                                       if (data[2].equals(users.get(i))) {
                                           vt1 = i;
                                       }
                                   }

                                   for (int i = 0; i < users.size(); i++) {
                                       if (data[3].equals(users.get(i))) {
                                           vt2 = i;
                                       }
                                   }
                                   if (vt1 != -1 && vt2 != -1) {
                                       System.out.println("_________________");

                                       writer1 = (ObjectOutputStream) clientOutputStreams.get(vt1);
                                       writer2 = (ObjectOutputStream) clientOutputStreams.get(vt2);
                                       System.out.println(writer1);
                                       System.out.println(writer2);
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
                                       writer1.writeObject("Play:start:" + data[2] + ":" + data[3] + ":" + rdRoom);
                                       writer2.writeObject("Play:start:" + data[3] + ":" + data[2] + ":" + rdRoom);

                                   }
                               }
                               if (data[2].equals("playing") == true) {
                                   int vt1 = -1;
                                   int vt2 = -1;
                                   ObjectOutputStream writer1 = null;
                                   ObjectOutputStream writer2 = null;
                                   int j = -1;
                                   for (int i = 0; i < room.size(); i++) {
                                       if (data[6].equals(room.get(i).name)) {
                                           j = i;
                                       }
                                   }
                                   if (j != -1) { // NẾu vẫn tồn tại cái phòng ý
                                       if (room.get(j).user1.equals("") == true && room.get(j).user2.equals("") == true) {
                                           room.get(j).user1 = data[3];
                                           room.get(j).user1P = data[4];
                                           ta_chat.append("CUCUCUCUCUCUC");

                                       }
                                       if (room.get(j).user2.equals("") == true && data[3].equals(room.get(j).user1) == false) {
                                           room.get(j).user2 = data[3];
                                           room.get(j).user2P = data[4];
                                           ta_chat.append("PMPMPMPMPMPMMPMPPM");

                                       }
                                       if (data[3].equals(room.get(j).user1) == true && room.get(j).user1P.equals("") == true) {
                                           room.get(j).user1P = data[4];
                                           ta_chat.append("ZXZXZXZXZXZXZXZ");
                                       }
                                       if (data[3].equals(room.get(j).user2) == true && room.get(j).user2P.equals("") == true) {
                                           room.get(j).user2P = data[4];
                                           ta_chat.append("KOKOKOKOKOKOKOKOKOOK");
                                       }
                                       if (room.get(j).user1.equals("") == false && room.get(j).user2.equals("") == false && room.get(j).user1P.equals("") == false && room.get(j).user2P.equals("") == false) {
                                           room.get(j).XetKetQua();
                                           ta_chat.append(room.get(j).result1);
                                           ta_chat.append(room.get(j).result2);
                                           for (int i = 0; i < users.size(); i++) {
                                               if (room.get(j).user1.equals(users.get(i))) {
                                                   ta_chat.append(room.get(j).user1);
                                                   ta_chat.append(users.get(i));
                                                   System.out.println("________________________");
                                                   vt1 = i;
                                                   System.out.println("vt1 = " + vt1);
                                               }
                                           }
                                           for (int i = 0; i < users.size(); i++) {
                                               if (room.get(j).user2.equals(users.get(i))) {
                                                   vt2 = i;
                                                   ta_chat.append(room.get(j).user2);
                                                   ta_chat.append(users.get(i));
                                                   System.out.println("vt2 = " + vt2);
                                               }
                                           }
                                           writer1 = (ObjectOutputStream) clientOutputStreams.get(vt1);
                                           writer2 = (ObjectOutputStream) clientOutputStreams.get(vt2);
                                           System.out.println(writer1);
                                           System.out.println(writer2);
                                           System.out.println(room.get(j).user1P);
                                           System.out.println(room.get(j).user2P);
                                           room.get(j).user1P = "";
                                           room.get(j).user2P = "";
                                           System.out.println(room.get(j).user1P);
                                           System.out.println(room.get(j).user2P);
                                           writer1.writeObject("HDT:" + room.get(j).result1);
                                           writer2.writeObject("HDT:" + room.get(j).result2);

                                       }
                                   }

                               }
                           }

                       }
                   }
*/












/*
if (stream instanceof String) {
                        data = stream.toString().split(":");
                        if (data[0].equals("Login")) {
                            if (data[1].equals("true")) {
                                ClientLoginView.clSM.showMess("Login succesfully");
                            }

                        }
                        if (data[0].equals("Connect")) {
                            if (data[1].equals("false")) {
                                ClientLoginView.clSM.showMess("Ko co nguoi nay\n");
                            } else {
                                ClientLoginView.clSM.showMess("Co nguoi vua thach dau ban " + data[1] + " : " + data[2].toUpperCase() + "\n");
                                AcceptChal aC = new AcceptChal(reader, writer, data[1], data[2]);
                                aC.setVisible(true);

                            }
                        }
                        if (data[0].equals("HDT")) {
                            if (data[1].equals("win")) {
                                ClientLoginView.clSM.showMess("BAN DA CHIEN THANG \n");
                            }
                            if (data[1].equals("lose")) {
                                ClientLoginView.clSM.showMess("BAN DA THUA CUOC \n");

                            }
                            if (data[1].equals("draw")) {
                                ClientLoginView.clSM.showMess("HAI BEN HOA \n");
                            }
                        }
                        if (data[0].equals("Play")) {
                            PlayingView pV = new PlayingView(reader, writer, data[2], data[3], data[4]);
                            if (data[1].equals("start")) {
                                pV.setVisible(true);
                            }

                        }
                        if (data[0].equals("MVL")) {
                            ClientLoginView.clSM.showMess("MA VAN LOC");
                        }
                }
*/