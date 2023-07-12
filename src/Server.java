import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
private ServerSocket server;

private final File content = new File("src/www");

private File index;

public Server(int port){
    try {
            server = new ServerSocket(port);
            server.setSoTimeout(100000);
            if(!setIndex()){
                throw new Exception("No index found");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean setIndex(){
        File[] files = content.listFiles();

        for (File file : files){
            if (file.getName().equals("index.html")){
               this.index = file;
               return true;
            }
        }
        this.index = content;
        return false;
    }
    private void handleGetRequest(PrintWriter out) throws IOException {

        String index = Files.readString(this.index.toPath(), StandardCharsets.UTF_8);
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 200 OK\r\n");
        response.append("Content-Type: text/html\r\n");
        response.append("\r\n");
        response.append(index);
        out.println(response.toString());
        out.flush();
    }


    public void run() throws IOException {
        System.out.println("Server has started and listens to port " + server.getLocalPort());

        while(true) {

            Socket client = server.accept();
            System.out.println("One client connected: " + client.getLocalAddress());

            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String request = in.readLine();

            if (request != null){
                System.out.println(request +" from "+ client.getLocalAddress());
                String[] requestArray = request.split(" ");
                String method = requestArray[0];

                if (method.equals("GET")){
                    handleGetRequest(out);
                } else if (method.equals("POST")){
                   return;
                } else {
                    return;
                }

            }
        }
    }
}
