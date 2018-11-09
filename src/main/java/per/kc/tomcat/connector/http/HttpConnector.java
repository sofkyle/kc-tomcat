package per.kc.tomcat.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {

    boolean stopped;
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!stopped) {
            // 接收从Server Socket传来的连接
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                continue;
            }

            // 将Socket推给HttpProcessor
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
