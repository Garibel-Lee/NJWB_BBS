package njwb.lcqjoyce.bbs.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import njwb.lcqjoyce.bbs.entity.Collect;
import njwb.lcqjoyce.bbs.mapper.CollectMapper;
import njwb.lcqjoyce.bbs.service.impl.CollectService;
@Service
public class CollectServiceImpl implements CollectService{

    @Resource
    private CollectMapper collectMapper;

    @Override
    public int deleteByPrimaryKey(Long collectId) {
        return collectMapper.deleteByPrimaryKey(collectId);
    }

    @Override
    public int insert(Collect record) {
        return collectMapper.insert(record);
    }

    @Override
    public int insertSelective(Collect record) {
        return collectMapper.insertSelective(record);
    }

    @Override
    public Collect selectByPrimaryKey(Long collectId) {
        return collectMapper.selectByPrimaryKey(collectId);
    }

    @Override
    public int updateByPrimaryKeySelective(Collect record) {
        return collectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Collect record) {
        return collectMapper.updateByPrimaryKey(record);
    }

}
