package GUI;

import Interface.ClientInterface;
import chat.ChatClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;

/**
 *
 * @author Christian
 */
public class ChatGUI extends javax.swing.JFrame implements Runnable, Observer {

    
    
    DefaultListModel MODEL = new DefaultListModel();

    DefaultListModel DLM;

    ChatClient clientInter = new ChatClient();

    static boolean conn = false;

    /**
     * Creates new form ChatGUI
     */
    public ChatGUI() {
        clientInter.addObserver(this);
        initComponents();
        
        chatList.setModel(MODEL);
        
        setLocationRelativeTo(null);
        // MSGArea.setText("Disconnected");
//        if (conn == false) {
//            connect.setEnabled(true);
//            disconnect.setEnabled(false);
//            send.setEnabled(false);
//        } else {
//            connect.setEnabled(false);
//            disconnect.setEnabled(true);
//            send.setEnabled(true);
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        MSGField = new javax.swing.JTextField();
        myName = new javax.swing.JLabel();
        sendto = new javax.swing.JLabel();
        connect = new javax.swing.JButton();
        disconnect = new javax.swing.JButton();
        send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jScrollPane2.setViewportView(userList);

        MSGField.setForeground(new java.awt.Color(153, 153, 153));
        MSGField.setText("Write your message here");
        MSGField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MSGFieldMouseClicked(evt);
            }
        });

        myName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        sendto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sendto.setText("All");

        connect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        connect.setText("Connect");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });

        disconnect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        disconnect.setText("Disconnect");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });

        send.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(chatList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(myName, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addGap(400, 400, 400))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MSGField))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(connect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(myName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendto, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MSGField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(send, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(connect, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(disconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
        Connect con = new Connect(clientInter);
        con.setVisible(true);
        MODEL.clear();
    }//GEN-LAST:event_connectActionPerformed

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        message();
        MSGField.setText("");
    }//GEN-LAST:event_sendActionPerformed

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed

        clientInter.disconnect();
        MODEL.clear();
        MODEL.addElement((String) "Disconnected!");

    }//GEN-LAST:event_disconnectActionPerformed

    private void MSGFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSGFieldMouseClicked
        MSGField.setText("");
    }//GEN-LAST:event_MSGFieldMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
       clientInter.disconnect();
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ChatGUI().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField MSGField;
    private javax.swing.JList chatList;
    private javax.swing.JButton connect;
    private javax.swing.JButton disconnect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel myName;
    private javax.swing.JButton send;
    private javax.swing.JLabel sendto;
    private javax.swing.JList userList;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {

        this.setVisible(true);
    }

    // Silas
    public void updateUsers(Object arg) {
        String args = (String) arg;
        String[] comma;

        //Create listModel
        DLM = new DefaultListModel();

        //Set JList to listModel
        
        userList.setModel(DLM);
        //Fill listModel
        if (args.contains(",")) {
            comma = args.split(",");

            for (int i = 1; i <= comma.length; i++) {
                DLM.addElement(comma[i - 1]);
                System.out.println("DLM.addElement is: "+comma[i-1]);
//                
            }
        } else {
            DLM.addElement(args);
        }
        

    }

    public void updateMsgArea(Object arg) {
        String args = (String) arg;
        System.out.println("UpdateMsArea(): " + args);

        String[] hash;

        hash = args.split("#");
//        
//        chatList.add(hash[1]+"said: ");
//        chatList.add(hash[2]);

        chatList.setModel(MODEL);
//        for (int i = 0; i < chatList.size(); i++) {

//        MODEL.addElement(hash[1]);
        MODEL.addElement(hash[2]);

//        }
    }
    
    public void message() {

        List stuff = userList.getSelectedValuesList();
        List<String> users = new ArrayList();
        
        

        for (Object user : stuff) {

            users.add(user.toString());
        }
        String msg = MSGField.getText();
        
        

        clientInter.sendMsg(users, msg);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update() CALLED - from GUI.");
        String FKJUNIT = (String) arg;
        String sub = FKJUNIT.substring(0, 4);
        System.out.println("substring: " + sub);
        myName.setText("Welcome "+clientInter.afs+"!");

        if (sub.equalsIgnoreCase("MSG#")) {
            updateMsgArea(arg);
            System.out.println("if(sub.equalsIgnoreCase(MSG#); is TRUE.");
        } else {
            updateUsers(arg);
            System.out.println("ELSE IS TRUE.");
        }
    }
}
