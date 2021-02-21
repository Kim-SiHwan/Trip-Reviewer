package kim.sihwan.trip_reviewer.config.jwt;

import io.jsonwebtoken.*;
import kim.sihwan.trip_reviewer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {


    private final MemberService memberService;

    public JwtTokenProvider (@Lazy MemberService memberService){
        this.memberService = memberService;
    }
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    long tokenValidityInSeconds;


    public String createToken(Authentication authentication){

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validity)
                .setIssuedAt(new Date(now))
                .compact();


    }
    public String createRefreshToken(Authentication authentication){

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds*20);


        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validity)
                .setIssuedAt(new Date(now))
                .compact();



    }
    public Authentication getAuthentication(String token){
        System.out.println("GETAUTHENTICATION ");
        System.out.println(token);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        UserDetails userDetails = memberService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
        } catch (ExpiredJwtException e) {
            System.out.println("토큰만료!");

        } catch (UnsupportedJwtException e) {
        } catch (IllegalArgumentException e) {
        }
        return false;
    }

}
