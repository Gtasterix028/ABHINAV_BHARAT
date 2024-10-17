package com.spring.jwt.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.Interfaces.IOtp;
import com.spring.jwt.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Value("${fast2sms.api.key}")
    private String apiKey;
    @Autowired
    private IOtp iOtp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OTPController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/sendByNumber")
    public ResponseEntity<?> sendByNumber(@RequestParam String number) {
        String OTP = this.generateOTP();
        ResponseEntity<?> response = send_SMS(number,OTP);
        HttpStatusCode h = response.getStatusCode();
        String statusCodeString = String.valueOf(h.value());


//        System.out.println(statusCodeString.equals("200"));
        if (statusCodeString.equals("200")){


            Object object = iOtp.save(number,OTP);

            return ResponseEntity.status(HttpStatus.OK).body(new Response("success",object,false));
        }else return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("unsuccess","Otp Not Send :: By Server",true));
    }
    public String generateOTP() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9000) + 1000); // generates a 4-digit string between 1000 and 9999
    }
    @GetMapping("/verifiedOtp")
    public ResponseEntity<?> sendByNumber(@RequestParam String number,@RequestParam String otp) {
        try {
            Boolean object = iOtp.getVerifiedByNumberOtp(number,otp);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("success",object,false));

        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Unsuccess",e.getMessage(),true));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Unsuccess",e.getMessage(),true));
        }
    }

    public ResponseEntity<?> send_SMS(@RequestParam String number, @RequestParam String otp) {
        String url = "https://www.fast2sms.com/dev/bulkV2";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", apiKey);

        String jsonBody = String.format("{\"route\":\"otp\",\"variables_values\":\"%s\",\"numbers\":\"%s\"}", otp, number);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
// Create a response map to return as JSON
        Map<String, Object> responseBody = new HashMap<>();
        try {
            // Parse the JSON string in the "body" field
            String body = response.getBody(); // This is the string you provided
            JsonNode jsonBodyNode = objectMapper.readTree(body);

            // Extracting values from the parsed JSON
            responseBody.put("requestId", jsonBodyNode.get("request_id").asText());
            responseBody.put("message", jsonBodyNode.get("message").isArray() ? jsonBodyNode.get("message").get(0).asText() : "");
            responseBody.put("return", jsonBodyNode.get("return").asBoolean());
            responseBody.put("statusCode", response.getStatusCodeValue());
        } catch (Exception e) {
            // Handle the exception
            responseBody.put("error", "Failed to parse JSON response");
            responseBody.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    @PostMapping("/send_VOICE")
    public ResponseEntity<?> send_VOICE(@RequestParam String number, @RequestParam String otp) {
        String url = "https://www.fast2sms.com/dev/voice";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", apiKey);

        String jsonBody = String.format("{\"route\":\"otp\",\"variables_values\":\"%s\",\"numbers\":\"%s\"}", otp, number);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        // Create a response map to return as JSON
        Map<String, Object> responseBody = new HashMap<>();
        try {
            // Parse the JSON string in the "body" field
            String body = response.getBody(); // This is the string you provided
            JsonNode jsonBodyNode = objectMapper.readTree(body);

            // Extracting values from the parsed JSON
            responseBody.put("requestId", jsonBodyNode.get("request_id").asText());
            responseBody.put("message", jsonBodyNode.get("message").isArray() ? jsonBodyNode.get("message").get(0).asText() : "");
            responseBody.put("return", jsonBodyNode.get("return").asBoolean());
            responseBody.put("statusCode", response.getStatusCodeValue());
        } catch (Exception e) {
            // Handle the exception
            responseBody.put("error", "Failed to parse JSON response");
            responseBody.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}