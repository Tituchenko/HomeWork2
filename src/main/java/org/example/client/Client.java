package org.example.client;

import org.example.server.Server;

public class Client {

    private String login;
    private ClientView clientView;
    private Server server;
    private boolean connected;

    public Client(ClientView clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }
    public boolean connectToServer(){
        login= clientView.getUserName();
        if (server.addClient(this)){
            connected=true;
            return true;
        }
        return false;
    }
    public void disconnectFromServer(){
        if (connected) {
            if (server.removeFromServer(this)) {
                connected = false;
                //clientView.disconnectFromServer();
            }
        }

    }
    public void disconnectFromServerByServer(){
        disconnectFromServer();
        clientView.disconnectFromServer();
    }
    public String getUserName(){
        return clientView.getUserName();
    }
    public boolean sendMessage(String m){
        if (!connected) return false;
        return server.addText(m);
    }
    public void addText(String m){
        clientView.showMessage(m);
    }

}
