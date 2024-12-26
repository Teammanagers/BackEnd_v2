package kr.teammangers.dev.member.presentation;

import kr.teammangers.dev.global.common.response.ApiRes;
import kr.teammangers.dev.member.application.facade.CommentApiFacade;
import kr.teammangers.dev.member.dto.request.CreateCommentReq;
import kr.teammangers.dev.member.dto.response.CreateCommentRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/comment")
public class CommentController {

    private final CommentApiFacade commentApiFacade;

    @PostMapping
    public ApiRes<List<CreateCommentRes>> createComment(
            @RequestBody final List<CreateCommentReq> req
    ) {
        List<CreateCommentRes> result = commentApiFacade.createComment(req);
        return ApiRes.onSuccess(result);
    }

}
