package kr.teammangers.dev.feedback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.teammangers.dev.member.dto.MemberDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FeedbackDto {

    private Long id;
    private String content;
    private Long parentId;
    private MemberInfo author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Setter
    @Builder.Default
    private List<FeedbackDto> children = new ArrayList<>();

    @Getter
    @Builder
    public static class MemberInfo {
        private Long id;
        private String name;
        private String belong;
    }

    public static MemberInfo from(MemberDto memberDto) {
        return MemberInfo.builder()
                .id(memberDto.id())
                .name(memberDto.name())
                .belong(memberDto.belong())
                .build();
    }
}
