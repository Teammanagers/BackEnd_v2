package kr.teammangers.dev.s3.application.service;

import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.entity.MemberImg;
import kr.teammangers.dev.s3.domain.repository.S3Repository;
import kr.teammangers.dev.s3.domain.repository.MemberImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.s3.mapper.MemberImgMapper.MEMBER_IMG_MAPPER;

@Service
@RequiredArgsConstructor
public class MemberImgService {

    private final MemberImgRepository memberImgRepository;
    private final MemberRepository memberRepository;
    private final S3Repository s3Repository;

    public Long save(Long memberId, Long s3FileInfoId) {
        Member member = memberRepository.getReferenceById(memberId);
        S3FileInfo s3FileInfo = s3Repository.getReferenceById(s3FileInfoId);
        return memberImgRepository.save(MEMBER_IMG_MAPPER.toEntity(member, s3FileInfo)).getId();
    }

    public String findFilePahtByMemberId(Long memberId) {
        return memberImgRepository.findByMember_Id(memberId)
                .map(MemberImg::getS3FileInfo)
                .map(S3FileInfo::getFilePath)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_IMG_NOT_FOUND));
    }

    public void delete(Long memberId) {
        memberImgRepository.deleteByMember_Id(memberId);
    }

}
