package kim.sihwan.trip_reviewer.dto.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg){
        super(msg);
    }
}
