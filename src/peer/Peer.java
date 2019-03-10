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
import java.util.Scanner;

import javax.json.Json;

/**
 *
 * @author Karim
 */
public class Peer {
	/**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
	
	private static sqlConnection s;
	private static ArrayList<String> resalt = new ArrayList<>();
   
    private static String message= "";
    

    public static ServerThread serverThread ;
    public static String []setupvalues;
    public static fakeDataGenerator Gen ;
   
    public Peer(QueueingModule queue) {
    	Gen = new fakeDataGenerator(queue);
    }
    public void addinresalt(String string) {
		// TODO Auto-generated method stub
    	resalt.add(string);
	}
    
    
    public static String getIP() throws UnknownHostException, java.net.UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        return ip;
    }
    
    private static  String getPCname () throws java.net.UnknownHostException {
    	String hostname = "Anonymous";
    	
    	    InetAddress addr;
    	    addr = InetAddress.getLocalHost();
    	    hostname = addr.getHostName();
    	
    
    	return hostname;
    }
    
    public static  void updateListenToPeer( ServerThread serverThread ,String username, String port , boolean f) throws Exception {
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
        //System.out.println("you can now communicate (e to Exit)");
        
   
    }
    
    
    public void startCommunication() {
    	Gen.start();
    }
    
    public static void ss(String m) throws Exception {
    	
    	    if(new sqlConnection().uptodate(getIP()+":"+Peer.setupvalues[1]) == 1){
                 updateListenToPeer( serverThread , Peer.setupvalues[0],Peer.setupvalues[1], false );
             }
    	    message = m;// fakeDataGenerator.copydata();
    	    communicate(serverThread, message);
    }
    
    public static void communicate( ServerThread serverThread, String message ) throws java.net.UnknownHostException {
    	
    	String username = getPCname()+" - "+Peer.setupvalues[0];
        try {

            StringWriter stringWriter = new StringWriter();
            Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                    .add("username", username)
                    .add("message", message)
                    .build());
            serverThread.sendMessage(stringWriter.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    

	
}
