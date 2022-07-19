import io.jsonwebtoken.Claims;
import security.JWTDemo;

public class Main {

    public static void main(String[] args) {
//        System.out.println("*******************************");
//        System.out.println("*******************************");
//        System.out.println("*******************************");
//        System.out.println("*******************************");
//        System.out.println("Docker Deploy Is Working!!!!!!!");
//        System.out.println("*******************************");
//        System.out.println("*******************************");
//        System.out.println("*******************************");
//        System.out.println("*******************************");

        String jwt = JWTDemo.createJWT("test_id", "joerg", "test",-1);
        System.out.println(jwt);
        Claims decoder = JWTDemo.decodeJWT(jwt);
        System.out.println(decoder);
    }
}
