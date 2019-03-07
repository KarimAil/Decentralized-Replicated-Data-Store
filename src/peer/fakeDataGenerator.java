package peer;

import java.io.*;

public class fakeDataGenerator  extends Thread{


    public static String [] messages = new String[33];
    QueueingModule queue = new QueueingModule();
    int counter = 0;

    @Override
    public void run()
    {

        generateMessage();
        while(true)
        {
            try
            {
                Thread.sleep((int)(Math.random() * 5000));

                queue.getFromDataGen( messages[ (int)(Math.random() * 31) ]);counter++; //GETTING RANDOM DATA TO STORE IN THE QUEUE

                /*
                 *
                 *
                 *SENDING TO THE NETWORK
                 *
                 *
                 */

                if(counter == 31 ) { queue.start();queue.join(); counter=0;}

                System.out.println("---------------  "+counter);

            }
            catch (InterruptedException | IOException e) {
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
        /*for (int i=0;i<32;i++) {
           System.out.println(messages[i]);

        }*/
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

