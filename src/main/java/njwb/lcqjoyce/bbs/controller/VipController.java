package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.ResultDTO;
import njwb.lcqjoyce.bbs.dto.UserDTO;
import njwb.lcqjoyce.bbs.entity.Right;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.entity.Vip;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.service.impl.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class VipController {

    @Autowired
    private UserService userService;

    @Autowired
    private RightService rightService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CardService cardService;
    @Autowired
    private VipService vipService;


    //充值会员
    @RequestMapping("/getVip")
    @ResponseBody
    @Transactional
    public Object getVip(HttpServletRequest request, String data) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("userDTO");
        if (ObjectUtils.isEmpty(data)) {
            return ResultDTO.errorOf(CustomizeErrorCode.INVALID_OPERATION);
        }
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (ObjectUtils.isEmpty(user.getRight())) {
            return ResultDTO.errorOf(CustomizeErrorCode.RIGATER_BROKEN);
        }
        if (ObjectUtils.isEmpty(user.getRole())) {
            return ResultDTO.errorOf(CustomizeErrorCode.RIGATER_BROKEN);
        }
        if (ObjectUtils.isEmpty(user.getCard())) {
            return ResultDTO.errorOf(CustomizeErrorCode.RIGATER_BROKEN);
        }
        if (user.getRole().getRoleId() == 3L) {
            return ResultDTO.errorOf(CustomizeErrorCode.GUANLIYUAN);
        }
        String[] tags = StringUtils.split(data, ",");
        Long times = Long.valueOf(tags[0]);
        Integer cost = Integer.valueOf(tags[1]);
        if (user.getUserBalances() <= cost) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOMONEY);
        } else {
            User update = new User();
            update.setUserBalances(user.getUserBalances() - cost);
            update.setUserId(user.getUserId());
            userService.updateByPrimaryKeySelective(update);

            Right newRight = user.getRight();
            newRight.setRightRoleid(2L);
            rightService.updateByPrimaryKeySelective(newRight);

            Vip vip = new Vip();
            vip.setVipUseid(user.getUserId());
            vip.setVipStarttime(System.currentTimeMillis());
            vip.setVipEndtime(times * 86400000 + System.currentTimeMillis());
            if (vipService.insert(vip) == 0) {
                return ResultDTO.errorOf(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
            } else {
                return ResultDTO.okOf(200, "充值成功");
            }
        }
    }


    //充值会员
    @RequestMapping("/goOnVip")
    @ResponseBody
    @Transactional
    public Object goOnVip(HttpServletRequest request, String data) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("userDTO");
        if (ObjectUtils.isEmpty(data)) {
            return ResultDTO.errorOf(CustomizeErrorCode.INVALID_OPERATION);
        }
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (ObjectUtils.isEmpty(user.getRight())) {
            return ResultDTO.errorOf(CustomizeErrorCode.RIGATER_BROKEN);
        }
        if (ObjectUtils.isEmpty(user.getRole())) {
            return ResultDTO.errorOf(CustomizeErrorCode.RIGATER_BROKEN);
        }
        if (ObjectUtils.isEmpty(user.getCard())) {
            return ResultDTO.errorOf(CustomizeErrorCode.RIGATER_BROKEN);
        }
        if (ObjectUtils.isEmpty(user.getVip())) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOVIP);
        }
        if (user.getRight().getRightRoleid() != 2) {
            return ResultDTO.errorOf(CustomizeErrorCode.ISVIP);
        }
        if (user.getRole().getRoleId() != 2) {
            return ResultDTO.errorOf(CustomizeErrorCode.ISVIP);
        }
        String[] tags = StringUtils.split(data, ",");
        Long times = Long.valueOf(tags[0]);
        Integer cost = Integer.valueOf(tags[1]);
        if (user.getUserBalances() <= cost) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOMONEY);
        } else {
            User update = new User();
            update.setUserBalances(user.getUserBalances() - cost);
            update.setUserId(user.getUserId());
            userService.updateByPrimaryKeySelective(update);
            Vip vip = user.getVip();
            vip.setVipEndtime(vip.getVipEndtime()+times * 86400000 + System.currentTimeMillis());
            if (vipService.updateByPrimaryKeySelective(vip) == 0) {
                return ResultDTO.errorOf(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
            } else {
                return ResultDTO.okOf(200, "续费成功");
            }
        }
    }


}
