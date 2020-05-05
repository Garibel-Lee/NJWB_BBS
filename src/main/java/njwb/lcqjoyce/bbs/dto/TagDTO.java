package njwb.lcqjoyce.bbs.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by LCQJOYCE on 2020/5/1.
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
