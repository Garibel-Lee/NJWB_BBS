package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Right;
import njwb.lcqjoyce.bbs.mapper.RightMapper;
import njwb.lcqjoyce.bbs.service.impl.RightService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class RightServiceImpl implements RightService{

    @Resource
    private RightMapper rightMapper;

    @Override
    public int deleteByPrimaryKey(Integer rightId) {
        return rightMapper.deleteByPrimaryKey(rightId);
    }

    @Override
    public int insert(Right record) {
        return rightMapper.insert(record);
    }

    @Override
    public int insertSelective(Right record) {
        return rightMapper.insertSelective(record);
    }

    @Override
    public Right selectByPrimaryKey(Integer rightId) {
        return rightMapper.selectByPrimaryKey(rightId);
    }

    @Override
    public int updateByPrimaryKeySelective(Right record) {
        return rightMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Right record) {
        return rightMapper.updateByPrimaryKey(record);
    }

    @Override
    public Right selectByUserId(Long userId) {

        return rightMapper.selectAllByRightUserid(userId).get(0);
    }

}
