package peer;

import java.io.*;


public class fakeDataGenerator  extends Thread{

	
	public static String [] messages = new String[33];
	private QueueingModule queue;
	private int counter = 0;
	Peer p ;
	private static String message;
	
	public fakeDataGenerator(QueueingModule queue2) {
		queue = queue2;
	}
	
    @Override
    public void run()
    {
        generateMessage();
        while(true)
        {
            try
            {
            	message =  messages[ (int)(Math.random() * 31)  ] ; //GETTING RANDOM DATA TO STORE IN THE QUEUE
            	
           		 queue.addToQueue(message);
           		try {
					Peer.ss(message); 
				} catch (Exception e) {
					
					e.printStackTrace();
				}
           		
           		while(queue.getStatus() == false ) { Thread.sleep((int)(Math.random() * 10000));}
                                
                Thread.sleep((int)(Math.random() * 5000));
               
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
}

