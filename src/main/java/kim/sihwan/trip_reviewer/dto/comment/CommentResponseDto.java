package kim.sihwan.trip_reviewer.dto.comment;

import kim.sihwan.trip_reviewer.domain.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;//닉네임으로?
    private String createDate;

    public CommentResponseDto (Comment comment){
        id= comment.getId();
        content= comment.getContent();
        //쿼리
        username= comment.getUsername();
        createDate= comment.getCreateDate().toString();

    }
}
