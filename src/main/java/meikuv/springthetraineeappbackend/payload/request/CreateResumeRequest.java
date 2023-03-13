package meikuv.springthetraineeappbackend.payload.request;

import lombok.*;
import meikuv.springthetraineeappbackend.resume.Resume;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateResumeRequest {

    private Resume resume;
}
