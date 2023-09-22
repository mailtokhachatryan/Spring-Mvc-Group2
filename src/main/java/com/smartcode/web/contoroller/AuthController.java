package com.smartcode.web.contoroller;

import com.smartcode.web.exception.ValidationException;
import com.smartcode.web.exception.VerificationException;
import com.smartcode.web.model.UserEntity;
import com.smartcode.web.service.user.UserService;
import com.smartcode.web.utils.encoder.AESManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping(path = "/")
    public ModelAndView start(@CookieValue(name = "rememberMe", required = false) Cookie rememberCookie,
                              HttpSession session,
                              HttpServletResponse response) {
        try {
            if (rememberCookie != null) {
                String encodedValue = rememberCookie.getValue();
                String decrypt = AESManager.decrypt(encodedValue);
                String email = decrypt.split(":")[0];
                String password = decrypt.split(":")[1];

                return login(email, password, response, session, "on");
            } else {
                return new ModelAndView("welcome");
            }
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("welcome");
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping(path = "/login")
    public ModelAndView login(@RequestParam String email,
                              @RequestParam String password,
                              HttpServletResponse response,
                              HttpSession session,
                              @RequestParam(required = false) String rememberMe) {
        try {
            userService.login(email, password);
            if (rememberMe.equalsIgnoreCase("on")) {
                Cookie cookie = new Cookie("rememberMe", AESManager.encrypt(email + ":" + password));
                cookie.setMaxAge(360000);
                response.addCookie(cookie);
            }
            UserEntity userByEmail = userService.getUserByEmail(email);
            session.setAttribute("email", email);
            session.setAttribute("id", userByEmail.getId());
            return new ModelAndView("home");
        } catch (VerificationException e) {
            ModelAndView modelAndView = new ModelAndView("verification");
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping(path = "/register")
    public String register(@RequestParam String name,
                           @RequestParam String lastName,
                           @RequestParam(required = false, name = "middleName") String middle,
                           @RequestParam Integer age,
                           @RequestParam String email,
                           @RequestParam String password) {

        UserEntity user = new UserEntity(name, lastName, middle, email, age, password, new BigDecimal(0));
        userService.register(user);

        return "verification";
    }


    @PostMapping(path = "/verify")
    public ModelAndView verify(@RequestParam String email,
                               @RequestParam String code) {

        try {
            userService.verify(email, code);
            return new ModelAndView("login");
        } catch (ValidationException exception) {
            ModelAndView modelAndView = new ModelAndView("verification");
            modelAndView.addObject("message", exception.getMessage());
            return modelAndView;
        }
    }


}
