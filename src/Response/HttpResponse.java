package Response;

public abstract class  HttpResponse {

    private String version;
    private HttpStatus httpStatus;
    private String body;
    private ContentType contentType;

    HttpResponse(String version, HttpStatus httpStatus, String body,ContentType contentType){
        this.httpStatus = httpStatus;
        this.version = version;
        this.body = body;
        this.contentType = contentType;
    }


    public String getVersion() {
        return version;
    }

    public String getBody() {return body;}

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public abstract String build();

}
