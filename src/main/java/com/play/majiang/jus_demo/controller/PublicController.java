package com.play.majiang.jus_demo.controller;

import com.play.majiang.jus_demo.dto.QuestionDTO;
import com.play.majiang.jus_demo.mapper.QuestionMapper;
import com.play.majiang.jus_demo.model.Question;
import com.play.majiang.jus_demo.model.User;
import com.play.majiang.jus_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * created by wm on 2019/10/15
 */

@Controller
public class PublicController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
        public String edit(@PathVariable(name="id")Integer id,
                Model model){
            QuestionDTO question=questionService.getById(id);
            model.addAttribute("title",question.getTitle());
            model.addAttribute("description",question.getDescription());
            model.addAttribute("tag",question.getTag());
            model.addAttribute("id",question.getId());
            return "publish";
    }

     @GetMapping("/publish")
     public  String publish(){
        return "publish";
     }

     @PostMapping("/publish")
     public  String doPublish(
             @RequestParam(value = "title",required = false) String title,
             @RequestParam(value = "description",required = false) String description,
             @RequestParam(value = "tag",required = false) String tag,
             @RequestParam(value="id",required = false) Integer id,
             HttpServletRequest request,
             Model model) {
         model.addAttribute("title",title);
         model.addAttribute("description",description);
         model.addAttribute("tag",tag);

         if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
         }
         if(description==null||description==""){
             model.addAttribute("error","问题补充不能为空");
             return "publish";
         }
         if(tag==null||tag==""){
             model.addAttribute("error","标签不能为空");
             return "publish";
         }

         User user=(User) request.getSession().getAttribute("user");
         if (user==null){
             model.addAttribute("error","用户未登录");
             return "publish";
         }
         Question question=new Question();
         question.setTitle(title);
         question.setDescription(description);
         question.setTag(tag);
         question.setCreator(user.getId());
         question.setId(id);
         questionService.createOrUpdate(question);
         return "redirect:/";
     }
}
