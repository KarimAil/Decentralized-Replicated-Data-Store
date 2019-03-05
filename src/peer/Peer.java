

//github copy [Decentralized-Replicated-Data-Store]
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peer;

import java.io.BufferedReader;
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
	
	
	
	private String ip ;
	private final int portNum = 8000;
	protected static ArrayList <String> peersIPs = new ArrayList<>();

	
    public static void main(String[] args)throws Exception{	
    	
    	Peer peer = new Peer();
    	peer.getIP();
    	peer.setPeersIPs();
   
        //new Peer().updateListenToPeer(bufferedReader , setupvalues[0] , serverThread);
    }
    
    
    
    
    public String getIP() throws UnknownHostException, SocketException { 
		String ii ;
    	try(final DatagramSocket socket = new DatagramSocket()){
			  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			  ii=this.ip = socket.getLocalAddress().getHostAddress();
			}
		return ii;
	}
	
	
	public void setPeersIPs() {
		
		if(!peersIPs.contains(this.ip)) {
			peersIPs.add(this.ip);
		}
		
		/*for(int i=0 ; i<peersIPs.size() ; i++) {
			System.out.println(peersIPs.get(i));
		}*/
	}

    
    public void updateListenToPeer(BufferedReader bufferedReader , String username , ServerThread serverThread) throws Exception{
        System.out.println("Enter (space separeted) hostname:portnumber ");
        System.out.println("Peers to receive messages from (s to skip):");
        String input = bufferedReader.readLine();
        String[] inputValues = input.split(" ");
        if(!input.equals("s"))
            for (int i = 0; i < inputValues.length; i++) {
                String[] address = inputValues[i].split(":");
                Socket socket = null;
                try {
                    socket = new Socket(address[0] , Integer.valueOf(address[1]));
                    new PeerThread(socket).start();
                } catch (Exception e) {
                    if (socket != null) socket.close();
                    else System.out.println("Invalid input. skipping to next step.");
                }
            }
        communicate(bufferedReader , username , serverThread);
    }
    
    
    
    public void communicate(BufferedReader bufferedReader , String username , ServerThread serverThread){
        try {
            System.out.println("you can now communicate (e to Exit , c to Change)");
            boolean flag = true;
            while (flag) {                
                String message = bufferedReader.readLine();
                if(message.equals("e")){
                    flag = false;
                    break;
                }else if (message.equals("c")){
                    updateListenToPeer(bufferedReader , username , serverThread);
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
