import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketClient {
    public <T> T send(T t, String host, int port){
        try {
            Socket socket = new Socket(host,port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(t);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            return (T) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        int port = 3210;
        String host = "127.0.0.1";
        ExecutorService threadPool = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    SocketClient client = new SocketClient();
                    String receive = client.send("hi", host, port);
                    System.out.println(receive);
                }
            });
        }
    }
}
