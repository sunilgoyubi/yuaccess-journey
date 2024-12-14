package com.yubi.yuaccessjourney.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "798f05a014a961e4bbbbb4fb20f2e8ff3b7740b3d06cd601199164fcbb9881bdca659dcc76a16955d4bc9ddc67e035acdfd043cacccf60562e3e10b93e7d7d7e";  // Use a stronger secret key in production

    // Token expiration time (e.g., 1 hour)
    private final long EXPIRATION_TIME = 3600000;

    /**
     * Method to generate a token.
     * @param email The email for which the token is being generated.
     * @return The generated JWT token.
     * @throws Exception If there's an error generating the token.
     */
    public String createToken(String email) throws Exception {
        // Set the JWT claims
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(email)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .build();

        // Create the signer using the secret key
        MACSigner signer = new MACSigner(SECRET_KEY);

        // Create the signed JWT
        SignedJWT signedJWT = new SignedJWT(
                new com.nimbusds.jose.JWSHeader(JWSAlgorithm.HS512),
                claims
        );

        // Sign the JWT
        signedJWT.sign(signer);

        // Return the serialized token
        return signedJWT.serialize();
    }

    /**
     * Method to validate the token.
     * @param token The JWT token to validate.
     * @return true if valid, false if invalid or expired.
     */
    public boolean validateToken(String token) {
        try {
            // Parse the signed JWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Create the verifier using the secret key
            MACVerifier verifier = new MACVerifier(SECRET_KEY);

            // Validate the token by verifying the signature
            return signedJWT.verify(verifier);  // Use MACVerifier here
        } catch (Exception e) {
            return false;  // Invalid token or expired
        }
    }

    /**
     * Method to extract the email from the token.
     * @param token The JWT token from which the email will be extracted.
     * @return The email address extracted from the token.
     * @throws ParseException If there's an error during extraction.
     */
    public String getEmailFromToken(String token) throws ParseException {
        // Parse the signed JWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Extract the claims
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        // Return the email (subject) from the claims
        return claims.getSubject();
    }

    /**
     * Method to extract the JWT token from the request header.
     * @param request The HTTP request.
     * @return The JWT token as a string.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Remove the "Bearer " prefix
        }
        return null;
    }
}
