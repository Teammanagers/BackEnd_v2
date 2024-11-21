package kr.teammangers.dev.memo.application.impl;

import kr.teammangers.dev.memo.application.MemoCrudService;
import kr.teammangers.dev.memo.application.MemoService;
import kr.teammangers.dev.memo.dto.MemoDto;
import kr.teammangers.dev.memo.dto.req.CreateMemoReq;
import kr.teammangers.dev.memo.dto.res.CreateMemoRes;
import kr.teammangers.dev.memo.dto.res.GetMemoRes;
import kr.teammangers.dev.tag.application.MemoTagService;
import kr.teammangers.dev.tag.application.TagService;
import kr.teammangers.dev.tag.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.teammangers.dev.memo.mapper.MemoResMapper.MEMO_RES_MAPPER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoCrudServiceImpl implements MemoCrudService {

    private final MemoService memoService;
    private final TagService tagService;
    private final MemoTagService memoTagService;

    @Override
    @Transactional
    public CreateMemoRes createMemo(Long teamId, CreateMemoReq req) {
        MemoDto memoDto = memoService.save(teamId, req);

        req.memoTagList().forEach(tagName -> {
            TagDto tagDto = tagService.findDtoOrSave(tagName);
            memoTagService.save(memoDto.id(), tagDto.id());
        });

        return MEMO_RES_MAPPER.toCreate(memoDto);
    }

    @Override
    public List<GetMemoRes> getMemoList(Long teamId) {
        List<MemoDto> memoDtoList = memoService.findAllDtoById(teamId);
        return memoDtoList.stream()
                .map(memoDto -> {
                    List<TagDto> tagDtoList = memoTagService.findAllTagDtoByMemoId(memoDto.id());
                    return MEMO_RES_MAPPER.toGet(memoDto, tagDtoList);
                }).toList();
    }

}
