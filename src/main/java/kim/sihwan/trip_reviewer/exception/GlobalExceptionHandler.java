package kim.sihwan.trip_reviewer.exception;

import kim.sihwan.trip_reviewer.dto.exception.*;
import kim.sihwan.trip_reviewer.exception.cumtomException.*;
import kim.sihwan.trip_reviewer.exception.cumtomException.AreaNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.FileSizeOverException;
import kim.sihwan.trip_reviewer.exception.cumtomException.UserNotFoundException;
import kim.sihwan.trip_reviewer.exception.cumtomException.UsernameDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> userNotFoundException(UserNotFoundException e){
        log.info("UserNotFoundException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.INVALID_LOGIN_INFO.getCode(), ErrorCode.INVALID_LOGIN_INFO.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponseDto> badCredentialsException(BadCredentialsException e){
        log.info("BadCredentialsException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.INVALID_LOGIN_INFO.getCode(), ErrorCode.INVALID_LOGIN_INFO.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AreaNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> areaNotFoundException(AreaNotFoundException e){
        log.info("AreaNotFoundException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NON_LOGIN.getCode(), ErrorCode.NON_LOGIN.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> reviewNotFoundException(ReviewNotFoundException e){
        log.info("ReviewNotFoundException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NON_LOGIN.getCode(), ErrorCode.NON_LOGIN.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> commentNotFoundException(CommentNotFoundException e){
        log.info("CommentNotFoundException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NON_LOGIN.getCode(), ErrorCode.NON_LOGIN.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameDuplicatedException.class)
    protected ResponseEntity<ErrorResponseDto> usernameDuplicatedException(UsernameDuplicatedException e){
        log.info("UsernameDuplicatedException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.DUPLICATED_USERNAME.getCode(),ErrorCode.DUPLICATED_USERNAME.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileSizeOverException.class)
    protected ResponseEntity<ErrorResponseDto> fileSizeLimitExceededException(FileSizeOverException e){
        log.info("FileSizeOverException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.FILE_SIZE_OVER.getCode(), ErrorCode.FILE_SIZE_OVER.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponseDto> ff(MultipartException e){
        return new ResponseEntity<>(errorResponseDto(ErrorCode.FILE_SIZE_OVER.getCode(), ErrorCode.FILE_SIZE_OVER.getDescription()),HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e){
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.info("MethodArgumentNotValidException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NOT_NULL.getCode(), message),HttpStatus.BAD_REQUEST);

    }
    
    @ExceptionHandler(ExpiredTokenException.class)
    protected ResponseEntity<ErrorResponseDto> expiredTokenException(ExpiredTokenException e){
        log.info("tokenExpired"+e);
        return new ResponseEntity<>(errorResponseDto(ErrorCode.EXPIRED_TOKEN.getCode(), ErrorCode.EXPIRED_TOKEN.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponseDto> bindException(BindException e){
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.info("BindException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NOT_NULL.getCode(), message),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DifferentUsernameException.class)
    protected ResponseEntity<ErrorResponseDto> differentUsernameException(DifferentUsernameException e){
        log.info("e");
        return new ResponseEntity<>(errorResponseDto(ErrorCode.DIFFERENT_USER_ACCESS.getCode(),ErrorCode.DIFFERENT_USER_ACCESS.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeletedReviewException.class)
    protected ResponseEntity<ErrorResponseDto> deletedReviewException (DeletedReviewException e){
        log.info("DeletedReviewException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.DELETED_REVIEW.getCode() , ErrorCode.DELETED_REVIEW.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    protected ResponseEntity<ErrorResponseDto> forbiddenAccessException (ForbiddenAccessException e){
        log.info("DeletedReviewException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.FORBIDDEN_ACCESS.getCode() , ErrorCode.FORBIDDEN_ACCESS.getDescription()),HttpStatus.BAD_REQUEST);
    }
    private ErrorResponseDto errorResponseDto(int code, String message){
        return new ErrorResponseDto(false,code,message);

    }

}
