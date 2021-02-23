package kim.sihwan.trip_reviewer.dto.exception;

public class FileSizeLimitExceededException extends RuntimeException{
    public FileSizeLimitExceededException(String msg){
        super(msg);
    }
}
