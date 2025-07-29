package kr.teammangers.dev.s3.application;

import kr.teammangers.dev.data.domain.entity.Data;
import kr.teammangers.dev.data.domain.repository.DataRepository;
import kr.teammangers.dev.global.error.code.ErrorStatus;
import kr.teammangers.dev.global.error.exception.GeneralException;
import kr.teammangers.dev.s3.domain.entity.DataFile;
import kr.teammangers.dev.s3.domain.entity.S3FileInfo;
import kr.teammangers.dev.s3.domain.repository.DataFileRepository;
import kr.teammangers.dev.s3.domain.repository.S3Repository;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.teammangers.dev.s3.mapper.S3Mapper.S3_MAPPER;

@Service
@RequiredArgsConstructor
public class DataFileService {

    private final S3Service s3Service;

    private final DataFileRepository dataFileRepository;
    private final DataRepository dataRepository;
    private final S3Repository s3Repository;

    public Long save(Long dataId, Long s3FileInfoId) {
        Data data = dataRepository.getReferenceById(dataId);
        S3FileInfo s3FileInfo = s3Repository.getReferenceById(s3FileInfoId);

        DataFile newDataFile = DataFile.builder()
                .data(data)
                .s3FileInfo(s3FileInfo).build();

        return dataFileRepository.save(newDataFile).getId();
    }

    public String findFilePathByDataId(Long dataId) {
        return dataFileRepository.findByDataId(dataId)
                .map(DataFile::getS3FileInfo)
                .map(S3FileInfo::getFilePath)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DATA_FILE_NOT_FOUND));
    }

    public S3FileInfoDto getDataFileInfo(Long dataId) {
        DataFile dataFile = dataFileRepository.findByDataId(dataId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DATA_FILE_NOT_FOUND));

        return S3_MAPPER.toDto(dataFile.getS3FileInfo());
    }

    public void deleteByDataId(Long dataId) {

        dataFileRepository.deleteByDataId(dataId);
    }





}
