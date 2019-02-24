package per.kc.tomcat;

import per.kc.tomcat.servlet.HttpServer1;
import per.kc.tomcat.servlet.HttpServer2;

public class Main {
    // 一般网页访问：127.0.0.1:8080/index.html
    // Servlet访问：127.0.0.1:8080/servlet/PrimitiveServlet
    public static void main(String[] args) {
        HttpServer2.start(args);
    }
}
