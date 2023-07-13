import Requests.Request;
import Response.ContentType;
import Response.GetResponse;
import Response.HttpStatus;
import Response.PostResponse;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Objects;

public class Server {
    private ServerSocket server;

    private final File contentFolder = new File("www");

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


    /**
     * Checks if there is a valid index file in the www folder
     */
    private boolean setIndex(){
        File[] files = contentFolder.listFiles();

        for (File file : files){
            if (file.getName().equals("index.html")){
               this.index = file;
               return true;
            }
        }
        return false;
    }


    private void handleGetRequest(Request request,PrintWriter out) throws IOException {
        String requestedURI = request.getUri();

        GetResponse response;

        if (Objects.equals(requestedURI, "/") || Objects.equals(requestedURI, "/index.html")){
            String indexbody = Files.readString(index.toPath());

            response = new GetResponse("HTTP/1.1", HttpStatus.OK, ContentType.TEXT_HTML,indexbody);


        } else {
            File file = new File(contentFolder + requestedURI);
            if (file.exists() && file.isFile()){
                String body = Files.readString(file.toPath());
                response = new GetResponse("HTTP/1.1", HttpStatus.OK, ContentType.TEXT_HTML,body);
            }
            else {
                response = new GetResponse("HTTP/1.1",HttpStatus.NOT_FOUND, ContentType.TEXT_HTML,"<h1> 404 file not found</h1>");
            }
        }




        out.append(response.build());
        out.flush();

    }

    private void handlePostRequest(Request request, BufferedReader in, PrintWriter out) throws IOException {
        StringBuilder requestBody = new StringBuilder();

            int contentLength = 0;
            String contentLengthHeader = "Content-Length:";
            String headerLine;
            while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
                if (headerLine.startsWith(contentLengthHeader)) {
                    contentLength = Integer.parseInt(headerLine.substring(contentLengthHeader.length()).trim());
                }
            }
            for (int i = 0; i < contentLength; i++) {
                requestBody.append((char) in.read());
            }

            PostResponse response = new PostResponse("HTTP/1.1",HttpStatus.ACCEPTED,ContentType.TEXT_HTML,"<h1> hi "+ requestBody +"</h1>");



            out.append(response.build());
            out.flush();

        }


    public void run() throws IOException {
        System.out.println("Server has started and listens to port " + server.getLocalPort());

        while(true) {
            Socket client = server.accept();


            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String request = in.readLine();

            if (request != null){
                System.out.println(request + " from " + client.getLocalAddress());
                Request r = new Request(request);



                switch (r.getType()) {
                    case "GET" -> handleGetRequest(r, out);
                    case "POST" -> handlePostRequest(r,in,out);
                    default -> throw new RuntimeException("Unsupported Request");
                }
            }
            client.close();
        }
    }
}
