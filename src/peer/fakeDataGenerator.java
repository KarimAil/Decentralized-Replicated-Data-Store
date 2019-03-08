package peer;

import java.io.*;


public class fakeDataGenerator  extends Thread{

	
	public static String [] messages = new String[33];
	private QueueingModule queue = new QueueingModule();
	private int counter = 0;
	Peer p ;
	private String message;
	
	public fakeDataGenerator() {
		// TODO Auto-generated constructor stub
	
	}
	
    @Override
    public void run()
    {
    	
        generateMessage();
        while(true)
        {
            try
            {
            	message =  messages[ /*(int)(Math.random() * 31) */ counter] ; //GETTING RANDOM DATA TO STORE IN THE QUEUE
            	
                

           		queue.getFromDataGen(message);counter++; 
           		try {
					Peer.ss();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
           		
           		if(counter == 31 ) { queue.start();queue.join(); counter=0;}
                
                System.out.println("---------------  "+counter);
                
                Thread.sleep((int)(Math.random() * 2000));
               
            }
            catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public String copydata () {
		
    	
    	return message;
    	
    }
    
    
    private void generateMessage()
    {
        try {
            String name = takeInput();
            String temp="";
            int t=0;
            for(int i=0;i<name.length();i++)
            {
                if(name.charAt(i)!='.')
                {
                    temp+=name.charAt(i);
                }
                else
                {
                    messages[t++]=temp+".";
                    
                    temp="";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
    private static String takeInput() throws Exception  // Take input from file
    {
        File file = new File("fake.txt");
        BufferedReader READ = new BufferedReader(new FileReader(file));
        int q=READ.read();
        String data = new String("");
        while(q != -1)
        {
            data+=(char)q;
            q=READ.read();
        }

        return data;
    }
    
    public String send() {
		return "asd";
    	
    }
   
	/*public static void main(String[] args) throws Exception {	
		// TODO Auto-generated method stub
		fakeDataGenerator g = new fakeDataGenerator();
        g.start();
}*/
	
}

