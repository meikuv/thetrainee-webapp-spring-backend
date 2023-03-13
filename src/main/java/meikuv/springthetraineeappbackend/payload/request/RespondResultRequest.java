package meikuv.springthetraineeappbackend.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespondResultRequest {

    private String username;
    private Long respondId;
    private String resultFlag;
    private String resultMessage;
}
