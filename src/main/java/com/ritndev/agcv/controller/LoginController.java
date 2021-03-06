package com.ritndev.agcv.controller;


import com.ritndev.agcv.classes.Link;
import com.ritndev.agcv.pages.Page403;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author Ritn
 */
@Controller
public class LoginController {
   
    @Autowired private MessageSource messageSource;
    
//--------------------  Page de login  ---------------------------
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        
        Link login = new Link("Page de connexion", "login");
        String page = login.toString();
        String returnPage = login.getHref();
        
        model.addAttribute("pageName", page);
        model.addAttribute("index", new Link("Page principale","index"));
        
        return returnPage;
    }
    
    
//---------------   Page non accessible (403)  ---------------------------- 
    
    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String page403(Model model, Principal principal){
        Page403 page403 = new Page403(model, principal, messageSource);
        return page403.getPage();
    }    
    
    
}
