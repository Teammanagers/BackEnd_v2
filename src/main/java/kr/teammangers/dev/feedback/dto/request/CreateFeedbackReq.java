package kr.teammangers.dev.feedback.dto.request;

public record CreateFeedbackReq(
        String content,
        Long parentId
) {}