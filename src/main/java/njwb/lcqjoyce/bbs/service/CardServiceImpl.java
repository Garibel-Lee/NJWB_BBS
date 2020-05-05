package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Card;
import njwb.lcqjoyce.bbs.mapper.CardMapper;
import njwb.lcqjoyce.bbs.service.impl.CardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class CardServiceImpl implements CardService{

    @Resource
    private CardMapper cardMapper;

    @Override
    public int deleteByPrimaryKey(Long cardId) {
        return cardMapper.deleteByPrimaryKey(cardId);
    }

    @Override
    public int insert(Card record) {
        return cardMapper.insert(record);
    }

    @Override
    public int insertSelective(Card record) {
        return cardMapper.insertSelective(record);
    }

    @Override
    public Card selectByPrimaryKey(Long cardId) {
        return cardMapper.selectByPrimaryKey(cardId);
    }

    @Override
    public int updateByPrimaryKeySelective(Card record) {
        return cardMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Card record) {
        return cardMapper.updateByPrimaryKey(record);
    }

    @Override
    public Card selectByUseId(Long userId) {
        return cardMapper.selectByUseId(userId);
    }

}
