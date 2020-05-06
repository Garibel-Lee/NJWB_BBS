package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Dele;
import njwb.lcqjoyce.bbs.mapper.DeleMapper;
import njwb.lcqjoyce.bbs.service.impl.DeleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class DeleServiceImpl implements DeleService{

    @Resource
    private DeleMapper deleMapper;

    @Override
    public int deleteByPrimaryKey(Long deletionId) {
        return deleMapper.deleteByPrimaryKey(deletionId);
    }

    @Override
    public int insert(Dele record) {
        return deleMapper.insert(record);
    }

    @Override
    public int insertSelective(Dele record) {
        return deleMapper.insertSelective(record);
    }

    @Override
    public Dele selectByPrimaryKey(Long deletionId) {
        return deleMapper.selectByPrimaryKey(deletionId);
    }

    @Override
    public int updateByPrimaryKeySelective(Dele record) {
        return deleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Dele record) {
        return deleMapper.updateByPrimaryKey(record);
    }

}
