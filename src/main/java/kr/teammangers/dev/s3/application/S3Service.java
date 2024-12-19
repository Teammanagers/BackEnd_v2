package kr.teammangers.dev.s3.application;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Template;
import kr.teammangers.dev.global.common.payload.exception.GeneralException;
import kr.teammangers.dev.s3.domain.S3FileInfo;
import kr.teammangers.dev.s3.dto.FileNameParts;
import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import kr.teammangers.dev.s3.repository.S3Repository;
import kr.teammangers.dev.s3.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

import static kr.teammangers.dev.global.common.payload.code.dto.enums.ErrorStatus.S3_NOT_FOUND_FROM_BUCKET;
import static kr.teammangers.dev.s3.mapper.S3Mapper.S3_MAPPER;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Operations s3Operations;
    private final S3Template s3Template;
    private final S3Repository s3Repository;

    public S3FileInfoDto uploadFile(MultipartFile file, String rootPath) {
        String originalFilename = file.getOriginalFilename();
        String timeStamp = S3Util.generateTimeStamp();
        String physicalFileName = timeStamp + S3Util.generateUniqueFileName();
        String filePath = rootPath + File.separator + physicalFileName;

        try (InputStream inputStream = file.getInputStream()) {
            s3Operations.upload(bucketName, filePath, inputStream,
                    ObjectMetadata.builder().contentType(file.getContentType()).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileNameParts nameParts = S3Util.getFileNameParts(originalFilename);
        S3FileInfo s3FileInfo = insert(S3FileInfo.builder()
                .originalFileName(nameParts.fileNm())
                .physicalFileName(physicalFileName)
                .filePath(filePath)
                .fileNameExtension(nameParts.ext())
                .fileSize(file.getSize())
                .build());

        return S3_MAPPER.toDto(s3FileInfo);
    }

    public String generateUrl(String filePath) {
        boolean doesObjectExist = s3Operations.objectExists(bucketName, filePath);
        if (!doesObjectExist) throw new GeneralException(S3_NOT_FOUND_FROM_BUCKET);

        return s3Template.createSignedGetURL(bucketName, filePath, Duration.ofHours(1L)).toString();
    }

    private S3FileInfo insert(final S3FileInfo s3FileInfo) {
        return s3Repository.save(s3FileInfo);
    }

}
