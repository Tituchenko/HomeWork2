package org.example;

import org.example.client.ClientSwingGUI;
import org.example.server.Logger;
import org.example.server.ServerSwingGUI;

public class Main {
    public static void main(String[] args) {
        ServerSwingGUI serverSwingGUI=new ServerSwingGUI();
        serverSwingGUI.getServer().setLogger(new Logger());
        new ClientSwingGUI(serverSwingGUI.getServer(),"Ivan","1234");
        new ClientSwingGUI(serverSwingGUI.getServer(),"Petr","1234");
    }
}