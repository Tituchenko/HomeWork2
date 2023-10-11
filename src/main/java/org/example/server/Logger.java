package org.example.server;
import java.io.*;

public class Logger implements LoggerView{
    public void addText(String username,String msg){
        String perenos="";
        String filePathString=createPath(username);
        File f = new File(filePathString);
        if(f.exists() && !f.isDirectory()){
            perenos="\n";
        }
        try(FileWriter writer = new FileWriter(filePathString, true))
        {
            writer.append(perenos);
            writer.append(msg);
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
//        }
    }
    public boolean hasHistory(String username){
        String filePathString=createPath(username);
        File f = new File(filePathString);
        if(f.exists() && !f.isDirectory()){
            return true;
        }
        return false;
    }
    public String getHistory(String username){
        String filePathString=createPath(username);
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePathString))) {
            String line;

            while((line = br.readLine()) != null) {
                if (!result.isEmpty()) result.append('\n');
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    private String createPath(String username){
        return username+".log";
    }
}
