package com.sparnix.URL_SHORTENER.web.controllers;

import com.sparnix.URL_SHORTENER.ApplicationProperties;
import com.sparnix.URL_SHORTENER.domain.model.CreateShortUrlCmd;
import com.sparnix.URL_SHORTENER.domain.model.ShortUrlDto;
import com.sparnix.URL_SHORTENER.domain.services.ShortUrlService;
import com.sparnix.URL_SHORTENER.web.dtos.CreateShortUrlForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;

    public HomeController(ShortUrlService shortUrlService, ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("createShortUrlForm",new CreateShortUrlForm(""));
        return "index";
    }

    @PostMapping("/short-urls")
    public String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model){
            if(bindingResult.hasErrors()){
                List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls();
                model.addAttribute("shortUrls", shortUrls);
                model.addAttribute("baseUrl", properties.baseUrl());
                return "index";
            }

            try {
                CreateShortUrlCmd cmd = new CreateShortUrlCmd(form.originalUrl());
                var shortUrlDto = shortUrlService.createShortUrl(cmd);
                redirectAttributes.addFlashAttribute("successMessage","Short URL Created Successfully "
                + properties.baseUrl() +"/s/"+shortUrlDto.shortKey());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage","Failed to create Short URL");
        }
            return "redirect:/";
    }

}
