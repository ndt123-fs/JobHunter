package vn.hoidanit.jobhunter.domain.response.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor


@AllArgsConstructor

public class ResUploadFileDTO {
    private String fileName;
    private Instant uploadedAt;
}
