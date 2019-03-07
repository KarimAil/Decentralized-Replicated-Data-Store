package peer;


import java.util.ArrayDeque;
import java.util.Queue;

public class QueueingModule {

    private static Queue<String> q = new ArrayDeque<>();
    public static void add(String name)
    {
        q.add(name);
    }
    QueueingModule()
    {

    }
    public static void main(String[] args)
    {
        QueueingModule qq = new QueueingModule();
    }
}

