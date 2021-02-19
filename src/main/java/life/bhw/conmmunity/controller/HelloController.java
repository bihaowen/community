package life.bhw.conmmunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String getHello(Model model, @RequestParam(name = "name") String name){
        model.addAttribute("name",name);
        return "index";
    }
}
