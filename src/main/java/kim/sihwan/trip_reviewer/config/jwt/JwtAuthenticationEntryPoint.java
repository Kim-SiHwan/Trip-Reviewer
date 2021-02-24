package kim.sihwan.trip_reviewer.config.jwt;

import kim.sihwan.trip_reviewer.dto.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        ErrorCode errorCode;
        System.out.println(request.getAttribute("exception"));
        String exception="";
        try{
            exception = request.getAttribute("exception").toString();
        }catch (NullPointerException e){
            errorCode = ErrorCode.NON_LOGIN;
            response.sendError(errorCode.getCode(),errorCode.getDescription());
            return;
        }

        if(exception.equals("ExpiredTokenException")){
            errorCode = ErrorCode.EXPIRED_TOKEN;
            response.sendError(errorCode.getCode(),errorCode.getDescription());

            return;
        }
        if(exception.equals("InvalidTokenException")){
            errorCode = ErrorCode.INVALID_TOKEN;
            response.sendError(errorCode.getCode(),errorCode.getDescription());
            return;
        }
        if(exception.equals("UnsupportedTokenException")){
            errorCode = ErrorCode.INVALID_TOKEN;
            response.sendError(errorCode.getCode(),errorCode.getDescription());
            return;
        }
        if(exception.equals("IllegalArgumentTokenException")){
            errorCode = ErrorCode.INVALID_TOKEN;
            response.sendError(errorCode.getCode(),errorCode.getDescription());
            return;
        }


    }
}