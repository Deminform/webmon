package com.webmonitor.webmon.controllers;

import com.webmonitor.webmon.models.Website;
import com.webmonitor.webmon.services.AuthenticationService;
import com.webmonitor.webmon.services.WebsiteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class WebsiteController {
    private final WebsiteService websiteService;
    private final AuthenticationService authenticationService;

    @GetMapping("/website")
    public String websites(@RequestParam(name = "domain", required = false) String domain, Model model, HttpServletRequest request, @ModelAttribute("message") String message, @ModelAttribute("errormessage") String message2) {
        model.addAttribute("websites", websiteService.lisOftWebsites(domain, request));
        model.addAttribute("message", message);
        model.addAttribute("errormessage", message2);


        return "website";
    }

    @GetMapping("/website/{domain}")
    public String websiteInfo(@PathVariable String domain, Model model, HttpServletRequest request) {
        Website website = websiteService.getWebsiteByDomain(domain, request);
        model.addAttribute("websites", websiteService.getAllWebsite(request));
        model.addAttribute("website", website);


        return "website-info";
    }

    @PostMapping("/website/remove/{domain}")
    public String removeWebsite(@PathVariable String domain, HttpServletRequest request) {
        websiteService.removeWebsite(domain, request);

        return "redirect:/website";
    }


    @PostMapping("/website/create")
    public String addWebsite(Website website, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        log.info(" CHECK Websitelist -------------------------- " + authenticationService.getUserFromCookie(request).getWebsiteList().contains(website));

        try {
            if (getWebsite(website.getDomain(), request).getUser().getId() == authenticationService.getUserFromCookie(request).getId()) {
                redirectAttributes.addFlashAttribute("message", "The domain already exists");
                redirectAttributes.addFlashAttribute("errormessage", "failed");
            } else {
                buildWebsite(request, website);
                redirectAttributes.addFlashAttribute("message", "Site has been successfully added");
                redirectAttributes.addFlashAttribute("errormessage", "success");
                return "redirect:/website";
            }
        } catch (RuntimeException exception) {
            log.info("User not found" + exception);
            buildWebsite(request, website);
            redirectAttributes.addFlashAttribute("message", "Site has been successfully added");
            redirectAttributes.addFlashAttribute("errormessage", "success");
            return "redirect:/website";
        }

        return "redirect:/website";
    }

    @PostMapping("/website/update/{domain}")
    public String updateWebsite(@PathVariable String domain, HttpServletRequest request) throws IOException {

        buildWebsite(request, getWebsite(domain, request));

        return "redirect:/website/{domain}";
    }

    private Website getWebsite(String domain, HttpServletRequest request) {
        return websiteService.getWebsiteByDomain(domain, request);
    }


    @PostMapping("/website/update-local/{domain}")
    public String updateWebsites(@PathVariable String domain, Model model, HttpServletRequest request) throws IOException {
        Website website = websiteService.getWebsiteByDomain(domain, request);
        buildWebsite(request, website);

        return "redirect:/website";
    }

    public String checkUrl(String domain) {
        if (domain.startsWith("https://")) return domain.replace("https://", "");
        else if (domain.startsWith("http://")) return domain.replace("http://", "");
        return domain.trim();
    }

    public void buildWebsite(HttpServletRequest request, Website website) throws IOException {

        website.setDomain(checkUrl(website.getDomain()));

        String status = websiteService.checkOnline(website.getDomain());

        if (!status.equals("online")) {
            website.setStatus("unavailable");
            website.setIp("No data");
            website.setLocation("No data");
            website.setDelayResponse("No data");
            website.setLoadTime("No data");
            website.setLastUpdate(websiteService.getCurrentDateTimeAsString());
            website.setScreenshot(websiteService.imageToBase64());
            websiteService.saveWebsite(request, website);
        } else {

            website.setStatus(status);
            String[] loadTimeAndScreenshot = websiteService.checkLoadTimeAndScreenshot(website.getDomain());
            String[] ipAndLocation = websiteService.checkIp(website.getDomain());

            website.setIp(ipAndLocation[0]);
            website.setLocation(ipAndLocation[1]);
            website.setDelayResponse(websiteService.checkDelay(website.getDomain()));
            website.setLoadTime(loadTimeAndScreenshot[0]);
            website.setScreenshot(loadTimeAndScreenshot[1]);
            website.setLastUpdate(websiteService.getCurrentDateTimeAsString());
            websiteService.saveWebsite(request, website);
        }

    }


}
