package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Card;
public interface CardService{


    int deleteByPrimaryKey(Long cardId);

    int insert(Card record);

    int insertSelective(Card record);

    Card selectByPrimaryKey(Long cardId);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);

    Card selectByUseId(Long userId);
}
