package peer;

import java.io.*;


public class fakeDataGenerator  extends Thread{

	
	public static String [] messages = new String[33];
	private QueueingModule queue = new QueueingModule();
	private int counter = 0;
	Peer p ;
	private static String message;
	
	public fakeDataGenerator() {}
	
    @Override
    public void run()
    {
    	boolean flag ,check;
        generateMessage();
        while(true)
        {
            try
            {
            	message =  messages[ (int)(Math.random() * 31)  ] ; //GETTING RANDOM DATA TO STORE IN THE QUEUE
            	
           		 queue.addToQueue(message); //counter++; 
           		 flag = queue.getStatus();
           	
           		try {
					Peer.ss(message); 
				} catch (Exception e) {
					
					e.printStackTrace();
				}
           		
           		if(flag == false ) { queue.start();queue.join(); flag = true; counter=0;}
                                
                Thread.sleep((int)(Math.random() * 20000));
               
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /*public static String copydata () {
		return message;
   }*/
    
    
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
}

