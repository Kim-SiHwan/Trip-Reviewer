package kim.sihwan.trip_reviewer.exception;

import kim.sihwan.trip_reviewer.dto.exception.*;
import kim.sihwan.trip_reviewer.exception.cumtomException.AreaNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.FileSizeLimitExceededException;
import kim.sihwan.trip_reviewer.exception.cumtomException.UserNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.UsernameDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> userNotFoundException(UserNotFoundException e){
        System.out.println("낫파운드");
        System.out.println(e);
        return new ResponseEntity<>(errorResponseDto(false,ErrorCode.INVALID_LOGIN_INFO.getCode(), ErrorCode.INVALID_LOGIN_INFO.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponseDto> badCredentialsException(BadCredentialsException e){
        System.out.println("로그인에러");
        System.out.println(e);
        return new ResponseEntity<>(errorResponseDto(false,ErrorCode.INVALID_LOGIN_INFO.getCode(), ErrorCode.INVALID_LOGIN_INFO.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AreaNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> areaNotFoundException(AreaNotFoundException e){
        System.out.println("지역에러");
        System.out.println(e);
        return new ResponseEntity<>(errorResponseDto(false,ErrorCode.NON_LOGIN.getCode(), ErrorCode.NON_LOGIN.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameDuplicatedException.class)
    protected ResponseEntity<ErrorResponseDto> usernameDuplicatedException(UsernameDuplicatedException e){
        System.out.println("회원가입 에러");
        System.out.println(e);
        return new ResponseEntity<>(errorResponseDto(false,ErrorCode.DUPLICATED_USERNAME.getCode(),ErrorCode.DUPLICATED_USERNAME.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    protected ResponseEntity<ErrorResponseDto> fileSizeLimitExceededException(FileSizeLimitExceededException e){
        return new ResponseEntity<>(errorResponseDto(false,ErrorCode.FILE_SIZE_OVER.getCode(), ErrorCode.FILE_SIZE_OVER.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e){
        System.out.println("ValidException");
        System.out.println(e.getBindingResult());
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity(new ErrorResponseDto(false,ErrorCode.NOT_NULL.getCode(), message),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDto> bindException(BindException e){
        System.out.println("ValidException");
        System.out.println(e.getBindingResult());
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity(new ErrorResponseDto(false,ErrorCode.NOT_NULL.getCode(), message),HttpStatus.BAD_REQUEST);

    }
    private ErrorResponseDto errorResponseDto(boolean success, int code, String message){
        return new ErrorResponseDto(success,code,message);

    }

}
