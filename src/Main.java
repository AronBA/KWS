import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length != 1){
            System.out.println("usage: kek [port]");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            System.out.println("pls use a valid port number");
            return;

        }
        System.out.println("-------------------------------");
        System.out.println("          KekWebServer         ");
        System.out.println("-------------------------------");
        new Server(port).run();


    }
}