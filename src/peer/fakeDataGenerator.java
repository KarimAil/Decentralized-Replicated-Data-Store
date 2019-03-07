package peer;

import java.io.*;

public class fakeDataGenerator extends Thread
{
    public static String [] messages = new String[33];
    @Override
    public void run()
    {
        QueueingModule q = new QueueingModule();
        generateMessage();
        while(true)
        {
            try
            {
                Thread.sleep((int)(Math.random() * 5555));
                String name = messages[ (int)(Math.random() * 31) ];
                System.out.println(name);
                q.add(name);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static void generateMessage()
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
        for (int i=0;i<33;i++)
            System.out.println(messages[i]);

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
    public static void main(String[] args)
    {
        fakeDataGenerator g = new fakeDataGenerator();
        g.start();
    }
}