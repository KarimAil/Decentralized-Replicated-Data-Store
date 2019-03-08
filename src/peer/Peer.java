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
public class Peer extends Thread {
	/**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
	
	
	private static ArrayList<String> resalt = new ArrayList<>();
    private static fakeDataGenerator Gen = new fakeDataGenerator();
    public static String message= "";
    

    public static ServerThread serverThread ;
    public static String setupvalues;
    
   
    
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
    
    public static void updateListenToPeer( ServerThread serverThread , String port , boolean f) throws Exception {
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
        Gen.start();
   
    }
    
    
    public static void ss() throws Exception {
    	
             if(new sqlConnection().uptodate(getIP()+":"+setupvalues) == 1){
                 updateListenToPeer( serverThread , setupvalues, false );
             }
             //System.out.println("*************");
        
    	 new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				Peer.message = Gen.copydata();
				System.out.println(Peer.message);
				 try {
					communicate(serverThread, Peer.message);
				} catch (java.net.UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}) .start();
    	
    }
    
    
    public static void communicate( ServerThread serverThread, String message) throws java.net.UnknownHostException {
    	 String username = getPCname();
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
