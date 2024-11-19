package kr.teammangers.dev.s3.util;

import kr.teammangers.dev.s3.dto.FileNameParts;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class S3Util {

    public static String generateTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    public static String generateUniqueFileName() {
        Random random = new Random();
        String randomNum = Integer.toString(random.nextInt(Integer.MAX_VALUE));
        return randomNum + UUID.randomUUID();
    }

    public static FileNameParts getFileNameParts(String originalFileName) {
        if (originalFileName != null && originalFileName.contains(".")) {
            // 마지막 "."을 기준으로 파일 이름과 확장자를 분리
            int dotIndex = originalFileName.lastIndexOf(".");
            String name = originalFileName.substring(0, dotIndex);
            String extension = originalFileName.substring(dotIndex + 1);
            return FileNameParts.builder()
                    .fileNm(name)
                    .ext(extension)
                    .build();
        }

        // 확장자가 없는 경우, 전체를 이름으로 취급하고 확장자는 빈 문자열로 설정
        return FileNameParts.builder()
                .fileNm(originalFileName)
                .ext("")
                .build();
    }

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        return file;
    }
}
