package kr.teammangers.dev.s3.application;

import kr.teammangers.dev.s3.dto.S3FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    S3FileInfoDto uploadFile(MultipartFile file, String rootPath);

    String generateUrl(String filePath);
}
