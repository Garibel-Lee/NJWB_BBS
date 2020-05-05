package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Card;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardMapper {
    int deleteByPrimaryKey(Long cardId);

    int insert(Card record);

    int insertSelective(Card record);

    Card selectByPrimaryKey(Long cardId);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);

    Card selectByUseId(Long userId);
}