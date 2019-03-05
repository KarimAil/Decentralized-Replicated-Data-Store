

//github copy [Decentralized-Replicated-Data-Store]
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
	
	
	
	 /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
	private String ip ;
	private String hostname = "Anonymous ";
	private final int portNum = 8001;
	protected static ArrayList <String> peersIPs = new ArrayList<>();

	
    public static void main(String[] args)throws Exception{
      
    	Peer peer = new Peer();

    	ServerThread serverThread = new ServerThread(peer.portNum);
        serverThread.start();
    	    	peer.getIP_Name();
    	peer.setPeersIPs();
   
       peer.updateListenToPeer(peer.hostname,serverThread);
    }
    
   
    //GETTING THE IP OF THIS PEER
    public String getIP_Name() throws UnknownHostException { 
    	
		try
    	{
    	    InetAddress addr;
    	    addr = InetAddress.getLocalHost();
    	    this.hostname = addr.getHostName();
    	    this.ip=addr.getHostAddress();
    	}
    	catch (UnknownHostException ex)
    	{
    	    System.out.println("Hostname or IP can not be resolved");
    	}
    	
		return this.ip+"  "+this.hostname;
	}
   
	
	//SET PEER IP IN THE STATIC ARRAY 
	public void setPeersIPs() {
		
		if(!peersIPs.contains(this.ip)) {
			peersIPs.add(this.ip);
		}
		
		/*for(int i=0 ; i<peersIPs.size() ; i++) {
			System.out.println(peersIPs.get(i));
		}*/
	}
    
    public void updateListenToPeer(String Hostname ,ServerThread serverThread) throws UnknownHostException, IOException {
 
            for (int i = 0; i < peersIPs.size(); i++) {
                Socket socket = null;
                socket = new Socket(peersIPs.get(i), portNum);
                new PeerThread(socket).start();
            }
        communicate(Hostname,serverThread);
    }

    
    
    
    public void communicate(String username , ServerThread serverThread){
    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
        	
            System.out.println("you can now communicate (e to Exit , c to Change)");
            boolean flag = true;
            while (flag) {                
                String message = bufferedReader.readLine();
                if(message.equals("e")){
                    flag = false;
                    break;
                }else if (message.equals("c")){
                    updateListenToPeer( username , serverThread);
                }
                else{
                    StringWriter stringWriter = new StringWriter();
                    Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                                                   .add("username",username )
                                                   .add("message",message).build());
                    serverThread.sendMessage(stringWriter.toString());
                }
            }
            System.exit(0);
        } catch (Exception e) {
        
        }
    }
}
