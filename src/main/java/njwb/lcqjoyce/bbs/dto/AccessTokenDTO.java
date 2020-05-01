package njwb.lcqjoyce.bbs.dto;

import lombok.Data;

/**
 * Created by LCQJOYCE on 2020.
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
