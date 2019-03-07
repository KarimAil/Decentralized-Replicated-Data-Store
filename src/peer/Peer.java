//github copy [Decentralized-Replicated-Data-Store].....git 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.json.Json;

/**
 *
 * @author Karim
 */
public class Peer {
    private static ArrayList<String> resalt = new ArrayList<>();
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter user name and port number for this peer");
        String[] setupvalues = bufferedReader.readLine().split(" ");
        resalt.add(getIP()+":"+setupvalues[1]);
        ServerThread serverThread = new ServerThread(setupvalues[1]);
        serverThread.start();
        new Peer().updateListenToPeer(bufferedReader, setupvalues[0], serverThread , setupvalues[1] , true );
    }
    public static String getIP() throws UnknownHostException, java.net.UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        return ip;
    }

    public void updateListenToPeer(BufferedReader bufferedReader, String username, ServerThread serverThread , String port , boolean f) throws Exception {
        ArrayList<String> reslt = new sqlConnection().getIps(getIP()+":"+port , f);
        for(int i = 0 ; i < reslt.size(); i++) {
            if(resalt.contains(reslt.get(i))) continue;
            resalt.add(reslt.get(i));
            String[] address = reslt.get(i).split(":");
            Socket socket = null;
            try {
                socket = new Socket(address[0], Integer.valueOf(address[1]));
                new PeerThread(socket).start();
            } catch (Exception e) {
                if (socket != null) {
                    socket.close();
                }
            }
        }
        System.out.println("you can now communicate (e to Exit)");
        while (true) { 
            if(new sqlConnection().uptodate(getIP()+":"+port) == 1){
                updateListenToPeer(bufferedReader, username , serverThread , port, false );
            }
            String message = bufferedReader.readLine();
            if (message.equals("e")) {
                break;
            }
            communicate(bufferedReader, username, serverThread, message);
        }
        System.exit(0);
    }

    public void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread, String message) {
        try {

            StringWriter stringWriter = new StringWriter();
            Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                    .add("username", username)
                    .add("message", message).build());
            serverThread.sendMessage(stringWriter.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
