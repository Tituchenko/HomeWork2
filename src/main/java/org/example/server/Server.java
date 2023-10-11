package org.example.server;

import org.example.client.Client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server {
    private boolean isStart;
    private List<Client> clients;
    private static final String MSG_ALLREADY_CLIENT="Клиент уже подключен";
    private static final String HAS_INPUT="В чат вошел ";
    private static final String HAS_EXIT="Чат покинул ";
    private static final String NO_CLIENT="Такого клиента нет";
    private ServerView serverView;
    private LoggerView logger;
    public Server(ServerView serverView){
        clients=new ArrayList<>();
        this.serverView=serverView;


    }
    public void setLogger(LoggerView logger){
        this.logger=logger;
    }

    public boolean addClient(Client client){
        if (!isStart) return false;
        if (clients.contains(client) || checkClientName(client.getUserName())) {
            System.out.println(MSG_ALLREADY_CLIENT);
            return false;
        }
        addText(HAS_INPUT+ client.getUserName());
        clients.add(client);
        if (logger.hasHistory(client.getUserName())){
            client.addText(logger.getHistory(client.getUserName()));
        }
        return true;
    }
    public boolean removeFromServer(Client client){
        if (!isStart) return false;
        if (!clients.contains(client)){
            System.out.println(NO_CLIENT);
            return false;
        }

        clients.remove(client);
        addText(HAS_EXIT+ client.getUserName());
        return true;
    }
    private boolean checkClientName (String name){
        for (Client client:clients) {
            if (client.getUserName().equals(name)) return true;

        }
        return false;

    }

    public boolean addText(String m){
        if (!isStart) return false;
        serverView.showMessage(m);
        for (Client client:clients) {
            client.addText(m);
            logger.addText(client.getUserName(),m);
        }
        return true;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
    public void stopServer(){
        Iterator<Client> iteratorC=clients.iterator();
        while (clients.size()>0) {
            clients.get(0).disconnectFromServerByServer();
        }
        isStart=false;
    }

    public boolean getIsStart() {
        return isStart;
    }
}
