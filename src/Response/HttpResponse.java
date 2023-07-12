package Response;

public abstract class  HttpResponse {

    private String version;
    private HttpStatus httpStatus;
    private String body;

    HttpResponse(String version, HttpStatus httpStatus, String body){
        this.httpStatus = httpStatus;
        this.version = version;
        this.body = body;
    }

    public String getVersion() {
        return version;
    }

    public String getBody() {
        return body;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
