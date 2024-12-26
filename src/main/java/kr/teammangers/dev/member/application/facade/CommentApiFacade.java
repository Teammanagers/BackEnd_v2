package kr.teammangers.dev.member.application.facade;

import kr.teammangers.dev.member.application.service.CommentService;
import kr.teammangers.dev.member.dto.CommentDto;
import kr.teammangers.dev.member.dto.request.CreateCommentReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentApiFacade {

    private final CommentService commentService;

    public List<CommentDto> createComment(List<CreateCommentReq> req) {
        return req.stream()
                .map(commentReq -> commentService.save(commentReq.memberId(), commentReq.content()))
                .toList();
    }

}
