package kim.sihwan.trip_reviewer.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_NULL(1,"필수 값이 누락되었습니다."),
    DUPLICATED_USERNAME(2,"이미 존재하는 회원입니다."),
    INVALID_LOGIN_INFO(3,"아이디 혹은 비밀번호를 확인해주세요."),
    INVALID_TOKEN(4,"잘못된 토큰정보입니다. 다시 로그인을 진행해주세요."),
    EXPIRED_TOKEN(5,"만료된 토큰입니다. 다시 로그인을 진행해주세요."),
    NON_LOGIN(6,"로그인이 필요합니다."),
    FILE_SIZE_OVER(7,"사진당 1MB 미만의 사진만 가능합니다."),
    DELETED_REVIEW(8,"삭제된 게시글입니다."),
    FORBIDDEN_ACCESS(9,"접근할 수 없습니다"),
    DIFFERENT_USER_ACCESS(10,"작성자만 수정, 삭제 할 수 있습니다.");


    private int code;
    private String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
