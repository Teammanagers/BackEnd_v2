package kr.teammangers.dev.member.application.service;

import kr.teammangers.dev.member.domain.entity.Comment;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.CommentRepository;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.member.dto.CommentDto;
import kr.teammangers.dev.member.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public CommentDto save(Long memberId, String content) {
        Member member = memberRepository.getReferenceById(memberId);
        Comment comment = commentRepository.save(CommentMapper.COMMENT_MAPPER.toEntity(member, content));
        return CommentMapper.COMMENT_MAPPER.toDto(comment);
    }
}
