package per.kc.tomcat.httpserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    /**
     * WEB_ROOT是用来存放HTML和其他网页文件的目录
     * 在这里，固定为工作目录下的webroot目录
     * 所谓的工作目录，是指在文件系统中，Java命令被调用时所在的位置
     */
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    // 关闭
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // 收到关闭命令
    private boolean shutdown = false;

    public static void start(String[] args) {
        System.out.println(WEB_ROOT);
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket =  new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 循环等待请求
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // 创建并解析请求
                Request request = new Request(input);
                request.parse();

                // 创建回复对象
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // 关闭socket
                socket.close();

                // 检查是否是关闭命令
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
