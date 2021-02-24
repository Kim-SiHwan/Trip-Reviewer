package kim.sihwan.trip_reviewer.exception.cumtomException;

public class FileSizeLimitExceededException extends RuntimeException{
    public FileSizeLimitExceededException(String msg){
        super(msg);
    }
}
