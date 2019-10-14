package com.dream.city.base;

import com.dream.city.base.model.vo.UserVo;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.base.utils.RedisKeys;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author
 */
public class BaseController {


    @ModelAttribute
    public void getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(RedisKeys.CURRENT_USER + session.getId());
        UserVo userVo = new UserVo();
        if (attribute != null){
            userVo = DataUtils.toJavaObject(attribute, UserVo.class);
        }
        if (userVo != null){
            request.setAttribute("user",userVo);
        }
    }

}
