/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg123client;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Tab;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Tran
 */
public class ClientWaitingView extends javax.swing.JFrame {
    public static  User main;
    public static  User listUserOnline;
    ObjectInputStream reader;
    ObjectOutputStream writer;
    /**
     * Creates new form ClientWaitingView
     */
    public ClientWaitingView(User user,ObjectInputStream reader, ObjectOutputStream writer) {
        initComponents();
        this.listUserOnline = user;
        this.reader = reader;
        this.writer = writer;
        main = new User(listUserOnline.getID(), listUserOnline.getUsername(), listUserOnline.getPassword(), listUserOnline.getScore(), listUserOnline.getState());
       
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try{
                    
                    User man = new User(main.getUsername(), main.getPassword(), 2);
                    man.setAction(2);
                    man.setMission("2:signout");
                    writer.writeObject(man);
                    ClientLoginView.sock.close();
                } catch (Exception ex) {
                   showMess("Could not sign out");
                }
            }
        }
        );
        trangThaiBanDau();
    }
  
    
    public void trangThaiBanDau(){
        showMess("Welcome " + main.getUsername()+ " to our new game demo");
        
       
        DefaultTableModel listOnline = (DefaultTableModel)tbDanhSachNguoiChoi.getModel();
        
        jTextField1.setEditable(false);
        jTextField3.setEditable(false);
        jTextField2.setEditable(false);
        jTextField1.setText(main.getUsername());
        jTextField2.setText(new Float(main.getScore()).toString());
        if(main.getState() == 1)jTextField3.setText("Online");
        if(main.getState() == 2)jTextField3.setText("Busy");
        for(int i = 0; i <listUserOnline.getListUserOnline().size();i++){
            listOnline.addRow(listUserOnline.getListUserOnline().get(i).toObjects());
        }
    }
    public void setUserOnline(User listUserOnline){
        
        DefaultTableModel listOnline = (DefaultTableModel)tbDanhSachNguoiChoi.getModel();
        
        int count = listOnline.getRowCount();
        for(int i = count-1 ; i>=0 ; i--){
            listOnline.removeRow(i);
        }
        for(int i = 0; i <listUserOnline.getListUserOnline().size();i++){
            listOnline.addRow(listUserOnline.getListUserOnline().get(i).toObjects());
           
        }
    }
    public void showMess(String ms){
        JOptionPane.showMessageDialog(this, ms);
    }
    public User getMainUser(){
        return main;
    }
     public void updateStateAndScore(float score){
        jTextField3.setText("Online");
        String score1 = new Float(score).toString();
        jTextField2.setText(score1);
    }
     public void updateState(){
        jTextField3.setText("Busy");
       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tbDanhSachNguoiChoi = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tbDanhSachNguoiChoi.setRowHeight(40);
        tbDanhSachNguoiChoi.setBackground(new java.awt.Color(204, 204, 204));
        tbDanhSachNguoiChoi.setForeground(new java.awt.Color(0, 0, 153));
        tbDanhSachNguoiChoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Score", "Stat"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDanhSachNguoiChoi.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbDanhSachNguoiChoi.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbDanhSachNguoiChoi);
        if (tbDanhSachNguoiChoi.getColumnModel().getColumnCount() > 0) {
            tbDanhSachNguoiChoi.getColumnModel().getColumn(0).setResizable(false);
            tbDanhSachNguoiChoi.getColumnModel().getColumn(0).setPreferredWidth(150);
            tbDanhSachNguoiChoi.getColumnModel().getColumn(1).setResizable(false);
            tbDanhSachNguoiChoi.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbDanhSachNguoiChoi.getColumnModel().getColumn(2).setResizable(false);
            tbDanhSachNguoiChoi.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        jButton1.setText("Sign out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Defy");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("Score:");

        jLabel3.setText("Stat:");

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("INFO");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(48, 48, 48)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    User man = null;
    try{
        man = new User();
        man.setAction(3);
        writer.writeObject(man);
    }
    catch(Exception e){
        showMess("Could not refresh");
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        User man =null;
        int i = 0;
        try{
            man = new User(main.getUsername(), main.getPassword(), 2);
            man.setAction(2);
            man.setMission("2:signout");
            writer.writeObject(man);
            i = 1;
        }catch(Exception e){
            i = 0; 
            showMess("Could not sign out");
        }
        
        //ClientLoginView.clSM.showMess(man.getUsername()+"\n"+man.getPassword()+"\n"+man.getMission());
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int i = tbDanhSachNguoiChoi.getSelectedRow();
        String userWantToConnect = listUserOnline.getListUserOnline().get(i).getUsername();
        int output = JOptionPane.showConfirmDialog(this, "Do you want to invite " + userWantToConnect, "Confirm!", JOptionPane.YES_NO_OPTION);
        if (output == JOptionPane.YES_OPTION) {
            if (main.getUsername().equals(userWantToConnect)) {
                showMess("Could not invite yourself!");
            } else {
                try {
                    User man = new User();
                    man.setUsername(main.getUsername());
                    man.setAction(4);
                    man.setMission("4:connect:" + main.getUsername() + ":" + userWantToConnect);
                    writer.writeObject(man);
                } catch (IOException ex) {
                    showMess("Could not use this function");
                }
            }
        }
        if (output == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Chose another one");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        UserResult urs = new UserResult();
        urs.setUsername(jTextField1.getText());
        try{
        writer.writeObject(urs);
        }catch(Exception e){
            showMess("Could not view result");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable tbDanhSachNguoiChoi;
    // End of variables declaration//GEN-END:variables
}
