package kim.sihwan.trip_reviewer.exception.cumtomException;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg){
        super(msg);
    }
}
