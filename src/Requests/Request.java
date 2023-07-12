package Requests;

import java.io.PrintWriter;

public class Request {
    private final String type;
    private final String uri;

    private final String version;
    public Request(String request){
        String[] requestArray = request.split(" ");
        this.type = requestArray[0];
        this.uri = requestArray[1];
        this.version = requestArray[2];
    }
    public String getUri() {
        return uri;
    }
    public String getVersion() {
        return version;
    }
    public String getType() {
        return type;
    }
}
