package life.bhw.conmmunity.controller;

import life.bhw.conmmunity.dto.AccessTokenDTO;
import life.bhw.conmmunity.dto.GitHubUser;
import life.bhw.conmmunity.entity.User;
import life.bhw.conmmunity.provider.GitHubProvider;
import life.bhw.conmmunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")//去配置文件properties里面找
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken=gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GitHubUser gitHubUser=gitHubProvider.getUser(accessToken);
        if(gitHubUser!=null){
            User user=new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccount_id(String.valueOf(gitHubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userService.insertUser(user);
            //登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            //登录失败重新登录
            return "redirect:/";
        }
    }


}
