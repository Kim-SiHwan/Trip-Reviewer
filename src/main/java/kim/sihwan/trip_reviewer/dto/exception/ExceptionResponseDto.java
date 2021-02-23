package kim.sihwan.trip_reviewer.dto.exception;

import lombok.Getter;

@Getter
public class ExceptionResponseDto {
    private boolean success;
    private String message;
    private String code;
    public ExceptionResponseDto(boolean success,String message,String code){
        this.success=success;
        this.message=message;
        this.code=code;
    }
}
