package com.bit.mini.MyPageCommand;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bit.mini.Controller.Command;
import com.bit.mini.dao.MyPageDao;
import com.bit.mini.dto.UserDto;

@Component("myInfoCommand")
public class MyInfoCommand implements Command {
    
    @Autowired
    private SqlSession sqlSession;

    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        HttpSession session = request.getSession();
        
        int user_id = (Integer) session.getAttribute("userId");
        
        MyPageDao dao = sqlSession.getMapper(MyPageDao.class);
        UserDto myInfo = dao.getMyInfo(user_id);
        
        // JSON -> List<String> 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> allergyList = null;
        try {
            allergyList = objectMapper.readValue(myInfo.getAllergies(), new TypeReference<List<String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("myInfo", myInfo);
        model.addAttribute("allergyList", allergyList); // JSP에서 사용 가능
    }
}
