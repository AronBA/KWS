package Response;

public class GetResponse extends HttpResponse{



    public GetResponse(String version, HttpStatus httpStatus,ContentType contentType, String body) {
        super(version, httpStatus, body, contentType);
    }
    public String build(){
        return getVersion() + " " +getHttpStatus().getCode() + " " + getHttpStatus().getMessage() + "\r\n" + "Content-Type: " + getContentType().getValue() + "\r\n\r\n" + getBody();
    }

}
