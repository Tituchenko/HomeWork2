package org.example.server;

public interface LoggerView {
    public boolean hasHistory(String username);
    public String getHistory(String username);
    public void addText(String username,String msg);
}
