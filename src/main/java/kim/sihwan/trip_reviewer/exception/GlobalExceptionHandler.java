package kim.sihwan.trip_reviewer.exception;

import kim.sihwan.trip_reviewer.dto.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ExceptionResponseDto> usernameNotFoundException(UsernameNotFoundException e){
        log.info("UsernameNotFoundException!" +" "+e);
        return new ResponseEntity<>(new ExceptionResponseDto(false,"해당 유저가 존재하지 않습니다.","400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    protected ResponseEntity<ExceptionResponseDto> unAuthorizedException(HttpClientErrorException.Unauthorized e){
        System.out.println("에러에러에러에러");
        System.out.println(e);
        return new ResponseEntity<>(new ExceptionResponseDto(false,"401에러임","401"),HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ExceptionResponseDto> userNotFoundException(UserNotFoundException e){
        System.out.println("낫파운드");
        System.out.println(e);
        return new ResponseEntity<>(new ExceptionResponseDto(false,e.getMessage(),"400"),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ExceptionResponseDto> badCredentialsException(BadCredentialsException e){
        System.out.println("로그인에러");
        System.out.println(e);
        return new ResponseEntity<>(new ExceptionResponseDto(false,"아이디 혹은 비밀번호를 확인해주세요.","400"),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AreaNotFoundException.class)
    protected ResponseEntity<ExceptionResponseDto> areaNotFoundException(AreaNotFoundException e){
        System.out.println("지역에러");
        System.out.println(e);
        return new ResponseEntity<>(new ExceptionResponseDto(false,e.getMessage(),"400"),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameDuplicatedException.class)
    protected ResponseEntity<ExceptionResponseDto> usernameDuplicatedException(UsernameDuplicatedException e){
        System.out.println("회원가입 에러");
        System.out.println(e);
        return new ResponseEntity<>(new ExceptionResponseDto(false,"이미 존재하는 아이디입니다.","400"),HttpStatus.BAD_REQUEST);
    }




}
