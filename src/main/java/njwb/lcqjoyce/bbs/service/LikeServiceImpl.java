package njwb.lcqjoyce.bbs.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import njwb.lcqjoyce.bbs.mapper.LikeMapper;
import njwb.lcqjoyce.bbs.entity.Like;
import njwb.lcqjoyce.bbs.service.impl.LikeService;
@Service
public class LikeServiceImpl implements LikeService{

    @Resource
    private LikeMapper likeMapper;

    @Override
    public int deleteByPrimaryKey(Long likeId) {
        return likeMapper.deleteByPrimaryKey(likeId);
    }

    @Override
    public int insert(Like record) {
        return likeMapper.insert(record);
    }

    @Override
    public int insertSelective(Like record) {
        return likeMapper.insertSelective(record);
    }

    @Override
    public Like selectByPrimaryKey(Long likeId) {
        return likeMapper.selectByPrimaryKey(likeId);
    }

    @Override
    public int updateByPrimaryKeySelective(Like record) {
        return likeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Like record) {
        return likeMapper.updateByPrimaryKey(record);
    }

}
