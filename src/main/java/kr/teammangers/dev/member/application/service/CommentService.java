package kr.teammangers.dev.member.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.domain.entity.Comment;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.CommentRepository;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.member.dto.CommentDto;
import kr.teammangers.dev.member.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.teammangers.dev.global.error.code.ErrorStatus.COMMENT_NOT_FOUND;
import static kr.teammangers.dev.member.constant.CommentConstant.COMMENT_SIZE;
import static kr.teammangers.dev.member.mapper.CommentMapper.COMMENT_MAPPER;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public CommentDto save(Long memberId, String content) {
        Member member = memberRepository.getReferenceById(memberId);
        Comment comment = commentRepository.save(COMMENT_MAPPER.toEntity(member, content));
        return COMMENT_MAPPER.toDto(comment);
    }

    public List<CommentDto> findAllDtoByMemberId(Long memberId) {
        return commentRepository.findAllByRecent(memberId, PageRequest.of(0, COMMENT_SIZE)).stream()
                .map(COMMENT_MAPPER::toDto)
                .toList();
    }

    public CommentDto updateStatus(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(COMMENT_NOT_FOUND));
        return COMMENT_MAPPER.toDto(comment);
    }

}
