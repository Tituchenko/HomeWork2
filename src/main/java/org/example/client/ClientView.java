package org.example.client;

public interface ClientView {
    void showMessage(String text);
    String getUserName();
    void disconnectFromServer();
}
