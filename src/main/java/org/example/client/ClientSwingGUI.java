package org.example.client;

import org.example.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientSwingGUI extends JFrame implements ClientView{
    private static final String TITLE="Chat client";
    private static final String BTN_SEND="send";
    private static final String BTN_LOGIN="login";
    private static final String BTN_DISC="disconnect";
    private static final String MSG_SRV_CONNECT="Соединение установлено";
    private static final String MSG_SRV_DISCONNECT="Соединение разорвано";
    private static final String MSG_SRV_OFFLINE="Не удалось соединиться с сервером";
    private static final int WIDTH=397;
    private static final int HEIGHT=304;
    private static final String DEF_IP="127.0.0.1";
    private static final String DEF_PORT="8189";

    private JTextField ipTF,portTF,loginTF,passTF,messageTF;
    private JButton loginBtn,sendBtn,dscBtn;
    private JTextArea textTA;
    private Client client;

    public ClientSwingGUI(Server server,String defLogin,String defPass){
        this.client=new Client(this,server);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createTopPanel(defLogin,defPass),BorderLayout.NORTH);
        textTA=new JTextArea();
        textTA.setEditable(false);
        add(textTA,BorderLayout.CENTER);
        setVisible(true);
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {connectToServer();}});
        dscBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {disconnectFromServer();}});
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {sendMessage();}});

        messageTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    sendMessage();
                }
            }
        });
    }
    private void connectToServer() {
        if (client.connectToServer()){
            inverseLoginButton();
            System.out.println(MSG_SRV_CONNECT);
        } else{
            System.out.println(MSG_SRV_OFFLINE);
        }
    }

    public void disconnectFromServer() {
                client.disconnectFromServer();
                inverseLoginButton();
    }
    private String addLogin(String m){
        return loginTF.getText()+":"+m;
    }
    private void sendMessage(){
        if (!messageTF.getText().equals("")) {
            if (client.sendMessage(addLogin(messageTF.getText()))) {
                messageTF.setText("");
            } else {
                System.out.println(MSG_SRV_OFFLINE);
            }
        }
    }
    public String getUserName(){
        return loginTF.getText();
    }

    public void showMessage(String m) {
        if (!m.equals("")) {
            if (!textTA.getText().equals("")) textTA.setText(textTA.getText() + "\n");
            textTA.setText(textTA.getText() + m);
        }
    }
    private JPanel createTopPanel(String defLogin,String defPass){
        JPanel panTop=new JPanel(new GridLayout(2,3));
        ipTF=new JTextField(DEF_IP);
        portTF=new JTextField(DEF_PORT);
        loginTF=new JTextField(defLogin);
        passTF=new JPasswordField(defPass);
        loginBtn=new JButton(BTN_LOGIN);
        dscBtn=new JButton(BTN_DISC);
        dscBtn.setVisible(false);
        panTop.add(ipTF);
        panTop.add(portTF);
        panTop.add(dscBtn);
        panTop.add(loginTF);
        panTop.add(passTF);
        panTop.add(loginBtn);
        return panTop;
    }
    private JPanel createBottomPanel(){
        JPanel panBottom = new JPanel();
        panBottom.setLayout(new BoxLayout(panBottom, BoxLayout.X_AXIS));
        messageTF = new JTextField(20);
        sendBtn = new JButton(BTN_SEND);
        panBottom.add(messageTF);
        panBottom.add(sendBtn);
        return panBottom;
    }

    private void inverseLoginButton(){
        loginBtn.setVisible(!loginBtn.isVisible());
        dscBtn.setVisible(!dscBtn.isVisible());
        ipTF.setEditable(!ipTF.isEditable());
        loginTF.setEditable(!loginTF.isEditable());
        portTF.setEditable(!portTF.isEditable());
        passTF.setEditable(!passTF.isEditable());
    }
}
