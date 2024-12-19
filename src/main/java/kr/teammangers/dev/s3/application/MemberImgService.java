package kr.teammangers.dev.s3.application;

import kr.teammangers.dev.common.payload.code.dto.enums.ErrorStatus;
import kr.teammangers.dev.common.payload.exception.GeneralException;
import kr.teammangers.dev.member.domain.Member;
import kr.teammangers.dev.member.repository.MemberRepository;
import kr.teammangers.dev.s3.domain.S3FileInfo;
import kr.teammangers.dev.s3.domain.mapping.MemberImg;
import kr.teammangers.dev.s3.repository.S3Repository;
import kr.teammangers.dev.s3.repository.mapping.MemberImgRepository;
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
