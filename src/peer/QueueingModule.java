package peer;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Queue;


public class QueueingModule extends Thread{
	
	 
	private static Queue<String> q = new ArrayDeque<>();
	
	 public QueueingModule(){}
	   
	 public void addToQueue(String Data)
    {
        q.add(Data);
        	
    }
	    
	 @Override
	 public void run() {
		 try {
			SaveToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	    
	    
	    public void SaveToFile() throws IOException {
	    	
	    	int size = q.size();
	    	PrintWriter pw = new PrintWriter(new FileOutputStream (new File("DB.txt"),true )); 
	    	for(int i = 0 ; i<size ; i++) {

		    	pw.append(q.poll()+"|");
		    }
	    	pw.close();
	    }

	    
	    public void print () {
	    	int size = q.size();
	    	for(int i =0 ; i< size; i++) {
	    		System.out.println(q.peek());
	    	}
	    }
	    
	    public boolean getStatus () {
	    	if(q.size() > 30) return false;
	    	
	    	else return true;
	    	
	    }
}
