package dong.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Created by xzd on 2017/11/28.
 * @Description
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/test")
    public void test (){
        System.out.println("进来了");
        System.out.println("啦啦啦啦啦");
    }

}
