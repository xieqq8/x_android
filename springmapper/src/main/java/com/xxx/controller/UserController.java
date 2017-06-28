package com.xxx.controller;


import com.xxx.entity.User;
import com.xxx.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
//@Api("userController相关api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userlst")
    public User finduserbyid() {
        User people = userService.findUserByID("1");
        return people;
    }

    @ApiOperation("添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", dataType = "String", required = true, value = "用户的姓名", defaultValue = "zhaojigang"),
            @ApiImplicitParam(paramType = "query", name = "address", dataType = "String", required = true, value = "用户的地址", defaultValue = "address")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public boolean addUser(@RequestParam("username") String username,
                           @RequestParam("address") String password) {
        return userService.addUser(username, password);
    }
}