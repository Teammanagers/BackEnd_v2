package kr.teammangers.dev.member.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.member.application.facade.CommentApiFacade;
import kr.teammangers.dev.member.dto.CommentDto;
import kr.teammangers.dev.member.dto.request.CreateCommentReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/comment")
public class CommentController {

    private final CommentApiFacade commentApiFacade;

    @PostMapping
    public ApiRes<List<CommentDto>> createComment(
            @RequestBody final List<CreateCommentReq> req
    ) {
        List<CommentDto> result = commentApiFacade.createComment(req);
        return ApiRes.onSuccess(result);
    }

    @GetMapping
    public ApiRes<List<CommentDto>> getCommentList(
            @AuthenticationPrincipal final AuthInfo auth
    ) {
        List<CommentDto> result = commentApiFacade.getCommentList(auth.memberDto().id());
        return ApiRes.onSuccess(result);
    }

    @PatchMapping("/{commentId}")
    public ApiRes<CommentDto> updateCommentStatus(
            @PathVariable("commentId") final Long commentId
    ) {
        CommentDto result = commentApiFacade.updateCommentStatus(commentId);
        return ApiRes.onSuccess(result);
    }

}
