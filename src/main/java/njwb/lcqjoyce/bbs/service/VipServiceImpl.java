package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Vip;
import njwb.lcqjoyce.bbs.mapper.VipMapper;
import njwb.lcqjoyce.bbs.service.impl.VipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class VipServiceImpl implements VipService{

    @Resource
    private VipMapper vipMapper;

    @Override
    public int deleteByPrimaryKey(Long vipId) {
        return vipMapper.deleteByPrimaryKey(vipId);
    }

    @Override
    public int insert(Vip record) {
        return vipMapper.insert(record);
    }

    @Override
    public int insertSelective(Vip record) {
        return vipMapper.insertSelective(record);
    }

    @Override
    public Vip selectByPrimaryKey(Long vipId) {
        return vipMapper.selectByPrimaryKey(vipId);
    }

    @Override
    public int updateByPrimaryKeySelective(Vip record) {
        return vipMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Vip record) {
        return vipMapper.updateByPrimaryKey(record);
    }

    @Override
    public Vip selectByUserId(Long userId) {

        if(    vipMapper.selectByVipUseid(userId).size()==0){
            return null ;
        }else {
            return vipMapper.selectByVipUseid(userId).get(0);
        }

    }

}
