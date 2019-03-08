package peer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
	
	
	
public static void main(String[] args) throws Exception {
    	
       String port ;
	Peer peer = new Peer();
    	
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter user name and port number for this peer");
         
        Peer.setupvalues = bufferedReader.readLine();
         
         peer.addinresalt(peer.getIP()+":"+  Peer.setupvalues );
        
         //peer.setPort(port);
         Peer.serverThread = new ServerThread(  Peer.setupvalues );
        Peer.serverThread.start();
        
        Peer.updateListenToPeer( Peer.serverThread ,   Peer.setupvalues  , true );
    }
}
