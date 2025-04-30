package com.bit.mini.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/recipe")  // 📌 기본 경로 설정
public class RecipeController {
    
    @Inject
    @Qualifier("myRecipeCommand")
    private Command myRecipeCommand;
    
    @Inject
    @Qualifier("RecipeInfoCommand")
    private Command recipeInfoCommand;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String myRecipe(HttpServletRequest request, Model model) {
        System.out.println("myRecipe()");
        
        model.addAttribute("request", request);
        myRecipeCommand.execute(model);
        
        return "MyRecipe/recipe";
    }

    @RequestMapping(value = "/loading2", method = RequestMethod.GET)
    public String showLoadingPage() {
        return "MyRecipe/loading2"; // views/MyRecipe/loading2.jsp 로 연결
    }

    @RequestMapping(value = "/recipeInfo")
    public String recipeInfo(HttpServletRequest request, Model model) {
        model.addAttribute("request", request);
        recipeInfoCommand.execute(model);
        return "MyRecipe/getRecipeInfo";
    }
}
