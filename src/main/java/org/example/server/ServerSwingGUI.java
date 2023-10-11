package org.example.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ServerSwingGUI extends JFrame implements ServerView{
    private static final String TITLE="Chat server";
    private static final int WIDTH=397;
    private static final int HEIGHT=304;
    private static final String MSG_ALLREADY_START="Сервер уже запущен";
    private static final String MSG_ALLREADY_STOP="Сервер уже остановлен";
    private static final String MSG_START="Сервер запущен";
    private static final String MSG_STOP="Сервер остановлен";

    private static final String BTN_START="Start";
    private static final String BTN_STOP="Stop";
    private JTextArea textTA;
    private JButton startBtn,stopBtn;
    private Server server;

    public Server getServer() {
        return server;
    }

    public ServerSwingGUI(){
        this.server=new Server(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        add(createBottomPanel(), BorderLayout.SOUTH);
        textTA=new JTextArea();
        add(textTA,BorderLayout.CENTER);
        setVisible(true);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.getIsStart()) {
                    System.out.println(MSG_ALLREADY_START);
                    return;
                }
                server.setStart(true);
                System.out.println(MSG_START);
            }
        });
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!server.getIsStart()){
                    System.out.println(MSG_ALLREADY_STOP);
                    return;
                }
                server.stopServer();
                System.out.println(MSG_STOP);
            }
        });
    }
    private JPanel createBottomPanel(){
        JPanel panBottom = new JPanel(new GridLayout(1,2));
        startBtn = new JButton(BTN_START);
        stopBtn = new JButton(BTN_STOP);
        panBottom.add(startBtn);
        panBottom.add(stopBtn);
        return panBottom;
    }
    public void showMessage(String m){
        if (!m.equals("")){
            if (!textTA.getText().equals("")) textTA.setText(textTA.getText()+"\n");
            textTA.setText(textTA.getText()+m);
        }

    }
}
