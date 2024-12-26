package kr.teammangers.dev.member.application.facade;

import kr.teammangers.dev.member.application.service.CommentService;
import kr.teammangers.dev.member.dto.request.CreateCommentReq;
import kr.teammangers.dev.member.dto.response.CreateCommentRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentApiFacade {

    private final CommentService commentService;

    public List<CreateCommentRes> createComment(List<CreateCommentReq> req) {
        List<Long> commentIdList = req.stream()
                .map(commentReq -> commentService.save(commentReq.memberId(), commentReq.content()))
                .toList();
        return commentIdList.stream()
                .map(commentId -> CreateCommentRes.builder().createdCommentId(commentId).build())
                .toList();
    }

}
