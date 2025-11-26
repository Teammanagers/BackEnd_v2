package kr.teammangers.dev.memo.application.service;

import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.memo.domain.entity.Folder;
import kr.teammangers.dev.memo.domain.entity.Memo;
import kr.teammangers.dev.memo.domain.repository.FolderRepository;
import kr.teammangers.dev.memo.domain.repository.MemoRepository;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.request.CreateMemoReq;
import kr.teammangers.dev.memo.dto.request.UpdateMemoReq;
import kr.teammangers.dev.member.domain.entity.Member;
import kr.teammangers.dev.member.domain.repository.MemberRepository;
import kr.teammangers.dev.team.domain.entity.Team;
import kr.teammangers.dev.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static kr.teammangers.dev.global.error.code.ErrorStatus.*;
import static kr.teammangers.dev.global.error.code.ErrorStatus.MEMO_NOT_FOUND;
import static kr.teammangers.dev.global.error.code.ErrorStatus.MEMO_NO_AUTHORITY;
import static kr.teammangers.dev.memo.mapper.MemoMapper.MEMO_MAPPER;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final FolderRepository folderRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public MemoDto save(Long folderId, Long teamId, CreateMemoReq req) {
        return MEMO_MAPPER.toDto(insert(folderId, teamId, req));
    }

    public MemoDto findDtoById(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new GeneralException(MEMO_NOT_FOUND));
        return enrichWithMemberInfo(MEMO_MAPPER.toDto(memo));
    }

    public List<MemoDto> findAllDtoByFolderId(Long folderId, Boolean isFixed) {
        List<Memo> memos = memoRepository.findAllByOptions(folderId, isFixed);
        return enrichMemosWithMemberInfo(memos);
    }

    public List<MemoDto> findAllDtoByFixed(Long teamId) {
        List<Memo> memos = memoRepository.findAllByMemoListByFixed(teamId);
        return enrichMemosWithMemberInfo(memos);
    }

    public MemoDto update(Long memoId, UpdateMemoReq req) {
        Memo memo = findById(memoId);
        memo.update(req);
        return MEMO_MAPPER.toDto(memo);
    }

    public MemoDto updateMemoFolder(Long memoId, Long newFolderId) {
        Memo memo = findById(memoId);
        Folder newFolder = folderRepository.findById(newFolderId)
                .orElseThrow(() -> new GeneralException(FOLDER_NOT_FOUND));
        memo.moveFolder(newFolder);
        return MEMO_MAPPER.toDto(memo);
    }

    public void deleteById(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    public void validateMemoAdmin(Long memoId, Long memberId) {
        if (!Objects.equals(findById(memoId).getCreatedBy(), memberId)) {
            throw new GeneralException(MEMO_NO_AUTHORITY);
        }
    }

    public MemoDto updateFixStatus(Long memoId) {
        Memo memo = findById(memoId);
        memo.updateFixStatus();
        return MEMO_MAPPER.toDto(memo);
    }

    public List<MemoDto> findAllDtoByMemberId(Long memberId, Long teamId) {
        List<Memo> memos = memoRepository.findAllByMemberIdAndTeamId(memberId, teamId);
        return enrichMemosWithMemberInfo(memos);
    }

    private List<MemoDto> enrichMemosWithMemberInfo(List<Memo> memos) {
        if (memos.isEmpty()) {
            return List.of();
        }

        // 모든 메모의 작성자/수정자 ID 수집
        List<Long> memberIds = memos.stream()
                .flatMap(memo -> List.of(memo.getCreatedBy(), memo.getUpdatedBy()).stream())
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // 한 번에 모든 멤버 정보 조회
        Map<Long, String> memberNameMap = memberRepository.findAllByIdIn(memberIds).stream()
                .collect(Collectors.toMap(Member::getId, Member::getName));

        // MemoDto 생성 시 작성자 정보 포함
        return memos.stream()
                .map(memo -> {
                    MemoDto baseDto = MEMO_MAPPER.toDto(memo);
                    return MemoDto.builder()
                            .id(baseDto.id())
                            .title(baseDto.title())
                            .content(baseDto.content())
                            .isFixed(baseDto.isFixed())
                            .folderId(baseDto.folderId())
                            .teamId(baseDto.teamId())
                            .createdAt(baseDto.createdAt())
                            .createdBy(baseDto.createdBy())
                            .createdByName(memberNameMap.get(memo.getCreatedBy()))
                            .updatedAt(baseDto.updatedAt())
                            .updatedBy(baseDto.updatedBy())
                            .updatedByName(memberNameMap.get(memo.getUpdatedBy()))
                            .useYn(baseDto.useYn())
                            .build();
                }).toList();
    }

    private MemoDto enrichWithMemberInfo(MemoDto memoDto) {
        String createdByName = memoDto.createdBy() != null
                ? memberRepository.findById(memoDto.createdBy())
                .map(Member::getName)
                .orElse(null)
                : null;

        String updatedByName = memoDto.updatedBy() != null
                ? memberRepository.findById(memoDto.updatedBy())
                .map(Member::getName)
                .orElse(null)
                : null;

        return MemoDto.builder()
                .id(memoDto.id())
                .title(memoDto.title())
                .content(memoDto.content())
                .isFixed(memoDto.isFixed())
                .folderId(memoDto.folderId())
                .teamId(memoDto.teamId())
                .createdAt(memoDto.createdAt())
                .createdBy(memoDto.createdBy())
                .createdByName(createdByName)
                .updatedAt(memoDto.updatedAt())
                .updatedBy(memoDto.updatedBy())
                .updatedByName(updatedByName)
                .useYn(memoDto.useYn())
                .build();
    }

    private Memo insert(Long folderId, Long teamId, CreateMemoReq req) {
        Folder folder = folderRepository.getReferenceById(folderId);
        Team team = teamRepository.getReferenceById(teamId);
        return memoRepository.save(MEMO_MAPPER.toEntity(req, folder, team));
    }

    private Memo findById(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new GeneralException(MEMO_NOT_FOUND));
    }

}
