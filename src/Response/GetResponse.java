package Response;

public class GetResponse extends HttpResponse{


    private ContentType contentType;
    public GetResponse(String version, HttpStatus httpStatus,ContentType contentType, String body) {
        super(version, httpStatus, body);
        this.contentType = contentType;
    }
    public String build(){
        return getVersion() + " " +getHttpStatus().getCode() + " " + getHttpStatus().getMessage() + "\r\n" + "Content-Type: " + getContentType().getValue() + "\r\n\r\n" + getBody();
    }
    public ContentType getContentType() {
        return contentType;
    }
}
