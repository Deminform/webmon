package com.webmonitor.webmon.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webmonitor.webmon.models.Website;
import com.webmonitor.webmon.repositories.WebsiteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WebsiteService {

    private final WebsiteRepository websiteRepository;
    private final AuthenticationService service;


    public List<Website> lisOftWebsites(String domain, HttpServletRequest request) {

        if (domain != null) {
            Website website = websiteRepository.findByDomainAndUser(domain, service.getUserFromCookie(request)).orElseThrow(() -> new RuntimeException("Website not found"));
            if (website != null) {
                log.info("++ websiteRepository one in List" + website);
                return new ArrayList<>(List.of(website));
            }
        }

        List<Website> websites = websiteRepository.findAllByUser(service.getUserFromCookie(request));
        Collections.reverse(websites);
        log.info("++ websiteRepository " + domain);
        return websites;
    }

    public void removeWebsite(String domain, HttpServletRequest request) {
        log.info("++ removeWebsite domain " + domain);
        service.getUserFromCookie(request).getWebsiteList().remove(websiteRepository.findByDomainAndUser(domain, service.getUserFromCookie(request)).orElseThrow(() -> new RuntimeException("User not found")));
        websiteRepository.deleteById(getWebsiteByDomain(domain, request).getId());
    }

    public Website getWebsiteById(Long id) {
        log.info("++ getWebsiteById " + id);
        return websiteRepository.findById(id).orElse(null);
    }

    public Website getWebsiteByDomain(String domain, HttpServletRequest request) {
        log.info("++ getWebsiteByDomain " + domain);
        return websiteRepository.findByDomainAndUser(domain, service.getUserFromCookie(request)).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Website> getAllWebsite(HttpServletRequest request) {
        log.info("++ getAllWebsite ");
        return websiteRepository.findAllByUser(service.getUserFromCookie(request));
    }

    public void saveWebsite(HttpServletRequest request, Website website) {
        website.setUser(service.getUserFromCookie(request));
        log.info("++ saveWebsite " + website.getDomain());
        websiteRepository.save(website);
    }


    public String checkOnline(String domain) {
        log.info("++ CheckOnline " + domain);

        try {
            InetAddress inetAddress = InetAddress.getByName(domain);
            if (inetAddress.isReachable(5000)) {
                return "online";
            } else {
                // retry 2 more times with 1s delay
                for (int i = 0; i < 2; i++) {
                    Thread.sleep(1000); // wait for 1 second
                    inetAddress = InetAddress.getByName(domain);
                    if (inetAddress.isReachable(5000)) {
                        return "online";
                    }
                }
                return "unavailable";
            }
        } catch (IOException | InterruptedException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String[] checkIp(String domain) {
        String ipAddress;
        String continentCode;
        String countryName;

        try {
            // ???????????????? IP-?????????? ???? ?????????????????? ??????????
            InetAddress inetAddress = InetAddress.getByName(domain);
            ipAddress = inetAddress.getHostAddress();

            // ???????????????? ???????????? ?? ?????????????????? ???? IP-???????????? ?? ?????????????? ipapi.co
            String url = "https://ipapi.co/" + ipAddress + "/json/";
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode node = new ObjectMapper().readTree(response.body());
            continentCode = node.get("continent_code").asText();
            countryName = node.get("country_name").asText();

        } catch (IOException | InterruptedException e) {
            // ???????? ??????-???? ?????????? ???? ??????, ???????????????????? null
            return null;
        }
        log.info("++ checkIp " + ipAddress + " " + countryName + " " + continentCode);
        return new String[]{ipAddress, countryName + " / " + continentCode};
    }

    public String checkDelay(String domain) {

        try {
            URL url = new URL("https://" + domain);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            long startTime = System.currentTimeMillis();
            connection.connect();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.info("++ checkDelay " + duration);
            return duration + " ms";
        } catch (IOException e) {
            return "Error when getting site response rate: " + e.getMessage();
        }
    }

    public String[] checkLoadTimeAndScreenshot(String domain) throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9515");
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\justForTest\\webmonapp\\src\\main\\resources\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https:/" + domain);

        Dimension dimension = new Dimension(1299, 810);
        driver.manage().window().setSize(dimension);

        long loadEndTime = (Long) ((JavascriptExecutor) driver).executeScript(
                "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart)");

        String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        String compressedScreenshot = compressBase64Image(screenshot); // ?????????????? ?????????????????????? ?? 3 ????????

        double loadTime = loadEndTime / 1000.0;

        driver.quit();
        log.info("++ checkLoadTimeAndScreenshot " + loadTime);
        return new String[]{String.format("%.2f", loadTime) + " s", compressedScreenshot};
    }


    private static String compressBase64Image(String base64Image) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(base64Image);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        int newWidth = image.getWidth() / 3;
        int newHeight = image.getHeight() / 3;
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", outputStream);

        byte[] compressedBytes = outputStream.toByteArray();
        String compressedBase64Image = Base64.getEncoder().encodeToString(compressedBytes);

        log.info("++ compressBase64Image ");
        return compressedBase64Image;
    }

    public String imageToBase64() {
        String base64Image = "";
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/images/404.jpg"));
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("++ imageToBase64 ");
        return base64Image;
    }

    public String getCurrentDateTimeAsString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("++ getCurrentDateTimeAsString " + formatter);
        return now.format(formatter);
    }


}