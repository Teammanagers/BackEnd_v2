package kr.teammangers.dev.feedback.presentation;

import kr.teammangers.dev.auth.infrastructure.security.AuthInfo;
import kr.teammangers.dev.feedback.application.FeedbackService;
import kr.teammangers.dev.feedback.dto.FeedbackDto;
import kr.teammangers.dev.feedback.dto.request.CreateFeedbackReq;
import kr.teammangers.dev.feedback.dto.request.UpdateFeedbackReq;
import kr.teammangers.dev.global.common.response.ApiRes; // ApiRes import
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/data/{dataId}/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ApiRes<List<FeedbackDto>> getFeedbacks(@PathVariable Long dataId) {
        List<FeedbackDto> feedbackTree = feedbackService.getFeedbackTree(dataId);
        return ApiRes.onSuccess(feedbackTree);
    }

    @PostMapping
    public ApiRes<Void> createFeedback(
            @PathVariable Long dataId,
            @AuthenticationPrincipal AuthInfo auth,
            @RequestBody CreateFeedbackReq req) {
        feedbackService.createFeedback(dataId, auth.memberDto().id(), req);
        return ApiRes.onSuccess(null);
    }


    @PutMapping("/{feedbackId}")
    public ApiRes<Void> updateFeedback(
            @PathVariable String dataId,
            @PathVariable Long feedbackId,
            @AuthenticationPrincipal AuthInfo auth,
            @RequestBody UpdateFeedbackReq req) {
        feedbackService.updateFeedback(feedbackId, auth.memberDto().id(), req);
        return ApiRes.onSuccess(null);
    }


    @DeleteMapping("/{feedbackId}")
    public ApiRes<Void> deleteFeedback(
            @PathVariable String dataId,
            @PathVariable Long feedbackId,
            @AuthenticationPrincipal AuthInfo auth) {
        feedbackService.deleteFeedback(feedbackId, auth.memberDto().id());
        return ApiRes.onSuccess(null);
    }
}
