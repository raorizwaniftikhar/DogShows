package ca.sheridancollege.khushi.web.controller;

import java.util.ArrayList;
import java.util.List;

import ca.sheridancollege.khushi.bean.Dog;
import ca.sheridancollege.khushi.service.DogService;
import ca.sheridancollege.khushi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.khushi.bean.Breed;
import ca.sheridancollege.khushi.bean.User;

@Controller
public class DogController {

    @Autowired
    private DogService dogService;

    @Autowired
    private UserService userService;

    // SHOW Dogs
    @RequestMapping("/")
    public String fetchDogsList(Model model) {
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        User user = userService.findByEmail(currentUserName);
        List<Dog> dogs = new ArrayList<>();
        if (user != null && user.getId() > 0 && !authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            dogs = dogService.findAllDog(user.getId());
            model.addAttribute("user", user);
        } else {
            dogs = dogService.findAllDog(0);
        }
        model.addAttribute("dogs", dogs);
        return "index";

    }

    // CREATE NEW Dog PAGE
    @RequestMapping("/createDog")
    public String createDog(Model module) {
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
                List<User> users = userService.findAll();
                module.addAttribute("users", users);
            } else {
                currentUserName = authentication.getName();
                User user = userService.findByEmail(currentUserName);
                module.addAttribute("user", user);
            }
        }
        module.addAttribute("breeds", dogService.findAllBreed());
        return "addDog";
    }

    // CREATE THE ACTUAL Dog POST
    @RequestMapping(value = "/addDog", method = RequestMethod.POST)
    public String addDog(@ModelAttribute("dog") Dog dog) {
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().toString().contains("ROLE_USER")) {
                currentUserName = authentication.getName();
                User user = userService.findByEmail(currentUserName);
                dog.setUserId(user);
            }
        }
        dogService.addUpdateDog(dog);
        return "redirect:/";
    }
    //Update Dog info Request
    @RequestMapping(value = "/updateDog", method = RequestMethod.GET)
    public String updateSurvey(@RequestParam(value = "id") int id, Model model) {
        Dog dog = dogService.findDogById(id);
        model.addAttribute("dog", dog);
        model.addAttribute("breeds", dogService.findAllBreed());
        return "updateDog";
    }

    //Delete Dog Record
    @RequestMapping(value = "/deleteDog", method = RequestMethod.GET)
    public String addSurvey(@RequestParam(value = "id") int id, Model model) {
        dogService.deleteDogById(id);
        return "redirect:/";
    }

}
