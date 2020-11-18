package com.seasky.sytest.controller;

import com.seasky.sytest.bll.UserBLL;
import com.seasky.sytest.druid.CurDataSource;
import com.seasky.sytest.druid.DataSourceName;
import com.seasky.sytest.model.UserInfo;
import com.seasky.sytest.model.UserOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(value = "/user", tags = "用户管理Controller")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBLL userBLL;

    @Autowired
    DefaultWebSecurityManager defaultSecurityManager;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @GetMapping("/index")
    public String index(@RequestParam(name = "mobile")@ApiParam(value = "电话") String mobile) {
        String verificationCode = "7890";
        redisTemplate.boundValueOps(mobile).set(verificationCode);
        redisTemplate.expire(mobile, 60, TimeUnit.SECONDS);
        return verificationCode;
    }


    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Boolean login(@RequestParam(name = "userName")@ApiParam(value = "姓名") String userName,
                         @RequestParam(name = "passWord")@ApiParam(value = "密码") String passWord,
                         @RequestParam(name = "mobile")@ApiParam(value = "电话") String mobile,
                         @RequestParam(name = "vCode")@ApiParam(value = "验证码") String verificationCode) {
        String redistValue = redisTemplate.boundValueOps(mobile).get();
        if (redistValue == null) {
            return false;
        }
        if (!redistValue.equals(verificationCode)) {
            return false;
        }

        //将securityManager设置当前的运行环境中
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //从SecurityUtils里边创建一个subject
        Subject subject = SecurityUtils.getSubject();
        //在认证提交前准备token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }
        UserInfo user = (UserInfo) subject.getPrincipal();
        subject.getSession().setAttribute("userLogin", user);
        return true;
    }


    @ApiOperation(value = "创建用户")
    @PostMapping("/create")
    public Boolean createUser(@RequestBody @ApiParam(value = "用户信息") UserInfo userInfo) {
        return userBLL.createUser(userInfo);
    }

    @ApiOperation(value = "查询用户")
    @PostMapping("/query")
    @CurDataSource(source = DataSourceName.read)
    public List<UserOut> queryUser(@RequestBody @ApiParam(value = "用户查询条件") UserInfo userInfo) {
        return userBLL.queryUser(userInfo);
    }


    // @GetMapping("/query")
    // @CurDataSource(source = DataSourceName.read)
    // public List<UserOut> queryUser(@RequestBody UserInfo userInfo) {
    //     return userBll.queryUser(userInfo);
    // }


    // @PostMapping("/login")
    // public Boolean login(@RequestParam(name = "userName") String userName, @RequestParam(name = "passWord") String
    //         passWord) {
    //     return userBLL.login(userName, passWord);
    // }
}