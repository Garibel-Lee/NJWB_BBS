package njwb.lcqjoyce.bbs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Card implements Serializable {
    private Long cardId;

    private Long cardUserid;

    private Long cardNumber;

    private String cardPwd;

    private BigDecimal cardBalance;

    private static final long serialVersionUID = 1L;
}