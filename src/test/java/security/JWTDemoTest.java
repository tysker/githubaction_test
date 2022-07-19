package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JWTDemoTest {

    private static final Logger logger = LoggerFactory.getLogger(JWTDemoTest.class);

    /*
        Create a simple JWT, decode it, and assert the claims
     */
    @Test
    public void createAndDecodeJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JWTDemo.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        logger.info("jwt = \"" + jwt.toString() + "\"");

        Claims claims = JWTDemo.decodeJWT(jwt);

        logger.info("claims = " + claims.toString());

        assertEquals(jwtId, claims.getId());
        assertEquals(jwtIssuer, claims.getIssuer());
        assertEquals(jwtSubject, claims.getSubject());

    }

    /*
        Attempt to decode a bogus JWT and expect an exception
        expected = MalformedJwtException.class
     */
    @Test()
    public void decodeShouldFail() {

        String notAJwt = "This is not a JWT";

        MalformedJwtException thrown = Assertions
                .assertThrows(MalformedJwtException.class, () -> {
                    Claims claims = JWTDemo.decodeJWT(notAJwt);
                }, "MalformedJwtException error was expected");


        Assertions.assertEquals("JWT strings must contain exactly 2 period characters. Found: 0", thrown.getMessage());
    }

    /*
    Create a simple JWT, modify it, and try to decode it
    expected = SignatureException.class
 */
    @Test()
    public void createAndDecodeTamperedJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JWTDemo.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        logger.info("jwt = \"" + jwt.toString() + "\"");

        // tamper with the JWT

        StringBuilder tamperedJwt = new StringBuilder(jwt);
        tamperedJwt.setCharAt(22, 'I');

        logger.info("tamperedJwt = \"" + tamperedJwt.toString() + "\"");

        assertNotEquals(jwt, tamperedJwt);

        SignatureException thrown = Assertions
                .assertThrows(SignatureException.class, () -> {
                    JWTDemo.decodeJWT(tamperedJwt.toString());
                }, "SignatureException error was expected");

        Assertions.assertEquals("JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.", thrown.getMessage());
    }

}
