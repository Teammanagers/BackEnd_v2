package kr.teammangers.dev.feedback.application;

import kr.teammangers.dev.data.domain.entity.Data;
import kr.teammangers.dev.data.domain.repository.DataRepository;
import kr.teammangers.dev.feedback.domain.entity.Feedback;
import kr.teammangers.dev.feedback.domain.repository.FeedbackRepository;
import kr.teammangers.dev.feedback.dto.FeedbackDto;
import kr.teammangers.dev.feedback.dto.request.CreateFeedbackReq;
import kr.teammangers.dev.feedback.dto.request.UpdateFeedbackReq;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.application.service.MemberService;
import kr.teammangers.dev.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.teammangers.dev.global.error.code.ErrorStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final DataRepository dataRepository;
    private final MemberService memberService;

    @Transactional
    public void createFeedback(Long dataId, Long memberId, CreateFeedbackReq req) {
        Data data = findDataById(dataId);

        Feedback feedback = Feedback.builder()
                .data(data)
                .memberId(memberId)
                .content(req.content())
                .parentId(req.parentId())
                .build();
        feedbackRepository.save(feedback);
    }

    @Transactional
    public void updateFeedback(Long feedbackId, Long memberId, UpdateFeedbackReq req) {
        Feedback feedback = findFeedbackById(feedbackId);
        if (!feedback.getMemberId().equals(memberId)) {
            throw new GeneralException(_FORBIDDEN);
        }
        feedback.updateContent(req.content());
    }

    @Transactional
    public void deleteFeedback(Long feedbackId, Long memberId) {
        Feedback feedback = findFeedbackById(feedbackId);
        if (!feedback.getMemberId().equals(memberId)) {
            throw new GeneralException(_FORBIDDEN);
        }
        feedbackRepository.delete(feedback);
    }

    public List<FeedbackDto> getFeedbackTree(Long dataId) {
        List<Feedback> allFeedbacks = feedbackRepository.findByDataIdOrderByCreatedAtAsc(dataId);

        List<Long> authorIds = allFeedbacks.stream()
                .map(Feedback::getMemberId)
                .distinct()
                .toList();
        Map<Long, MemberDto> authorsMap = memberService.findMembersByIds(authorIds).stream()
                .collect(Collectors.toMap(MemberDto::id, dto -> dto));

        Map<Long, FeedbackDto> feedbackDtoMap = new HashMap<>();
        List<FeedbackDto> rootFeedbacks = new ArrayList<>();

        allFeedbacks.forEach(feedback -> {
            MemberDto authorDto = authorsMap.get(feedback.getMemberId());
            FeedbackDto feedbackDto = FeedbackDto.builder()
                    .id(feedback.getId())
                    .content(feedback.getContent())
                    .parentId(feedback.getParentId())
                    .author(FeedbackDto.from(authorDto))
                    .createdAt(feedback.getCreatedAt())
                    .updatedAt(feedback.getUpdatedAt())
                    .build();
            feedbackDtoMap.put(feedback.getId(), feedbackDto);
        });

        feedbackDtoMap.values().forEach(feedbackDto -> {
            Long parentId = feedbackDto.getParentId();
            if (parentId != null) {
                FeedbackDto parentDto = feedbackDtoMap.get(parentId);
                if (parentDto != null) {
                    parentDto.getChildren().add(feedbackDto);
                }
            } else {
                rootFeedbacks.add(feedbackDto);
            }
        });
        return rootFeedbacks;
    }

    // DataCrudService에서 Data 삭제 시 호출될 메서드
    @Transactional
    public void deleteAllFeedbacksByDataId(Long dataId) {
        feedbackRepository.softDeleteAllByDataId(dataId);
    }

    private Data findDataById(Long dataId) {
        return dataRepository.findById(dataId)
                .orElseThrow(() -> new GeneralException(DATA_NOT_FOUND));
    }

    private Feedback findFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new GeneralException(FEEDBACK_NOT_FOUND));
    }
}
