import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
private ServerSocket server;
public Server(int port){
    try {
            server = new ServerSocket(port);
            server.setSoTimeout(100000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void run() throws IOException {
        System.out.println("Server has started");




        String index = Files.readString(Path.of("src/www/index.html"), StandardCharsets.UTF_8);

        while(true) {
            Socket client = server.accept();


            System.out.println("one mf connceted:" + client.getLocalAddress());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            PrintWriter out = new PrintWriter(outputStream);


            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("\r\n");
            out.println(index);
            out.flush();


            out.close();


        }
    }
}
