package com.gsy.base.web.controller.commonController;

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.web.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mrzsh on 2018/4/12.
 */
@RestController
@RequestMapping("/ajax/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @RequestMapping(value = "/allList",method = RequestMethod.GET)
    public ResultBean getAllList(@RequestParam int page ,
                                 @RequestParam  int count){
        return new ResultBean(schoolService.getAllListPage(page,count));
    }
    @RequestMapping(value = "/search/schoolname",method = RequestMethod.GET)
    public ResultBean search(  @RequestParam String name,
                                @RequestParam int page ,
                               @RequestParam  int count){
        return new ResultBean(schoolService.searchPage(name,page,count));
    }
}
