package ca.sheridancollege.khushi.web.controller;

import ca.sheridancollege.khushi.bean.Breed;
import ca.sheridancollege.khushi.bean.CountResult;
import ca.sheridancollege.khushi.service.DogService;
import ca.sheridancollege.khushi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dog-shows")
public class DogShowsController {

    @Autowired
    private DogService dogService;

    @Autowired
    private UserService userService;

    @GetMapping("/result")
    public String dogShowsResult(Model model) {
        List<Breed> breeds = dogService.findAllBreed();
        List<CountResult> countResults = new ArrayList<>();
        if (breeds != null && breeds.size() > 0) {
            for (int breed = 0; breed < breeds.size(); breed++) {
                CountResult countResult = dogService.totleDogsByBreedId(breeds.get(breed).getId(), breeds.get(breed).getName());
                countResults.add(countResult);
            }
            model.addAttribute("countResults", countResults);
        }
        return "dogShows";
    }
}
