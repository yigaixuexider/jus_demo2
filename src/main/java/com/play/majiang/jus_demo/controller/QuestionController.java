package com.play.majiang.jus_demo.controller;

import com.play.majiang.jus_demo.dto.QuestionDTO;
import com.play.majiang.jus_demo.mapper.QuestionMapper;
import com.play.majiang.jus_demo.model.Question;
import com.play.majiang.jus_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * created by wm on 2019/10/24
 */

@Controller
public class QuestionController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model){
        QuestionDTO questionDTO= questionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }

}
