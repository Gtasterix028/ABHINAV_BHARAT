package com.spring.jwt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.spring.jwt.Interfaces.IOtp;

import java.util.Map;

import com.spring.jwt.dto.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {OTPController.class})
@ExtendWith(SpringExtension.class)
class OTPControllerDiffblueTest {
    @MockBean
    private IOtp iOtp;

    @Autowired
    private OTPController oTPController;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * Method under test: {@link OTPController#generateOTP()}
     */
    @Test
    public void testGenerateOTP() {
        // Act
        String otp = oTPController.generateOTP();

        // Assert
        assertEquals(4, otp.length()); // Ensure OTP is 4 digits
        assertTrue(otp.matches("\\d{4}")); // Ensure OTP consists of digits only
    }

    @Test
    public void testSendByNumber_Success() {
        // Arrange
        String number = "1234567890";
        String otp = "1234";
        when(oTPController.generateOTP()).thenReturn(otp);
        when(iOtp.save(number, otp)).thenReturn("otp send");

        // Simulate a successful SMS sending response
        ResponseEntity<Object> smsResponse = ResponseEntity.ok().build();
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(smsResponse);

        // Act
        ResponseEntity<?> responseEntity = oTPController.sendByNumber(number);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", ((Response) responseEntity.getBody()).getMessage());
        assertEquals("otp send", ((Response) responseEntity.getBody()).getObject());
        assertEquals(false, ((Response) responseEntity.getBody()).getHasError());
    }

    @Test
    public void testSendByNumber_Failure() {
        // Arrange
        String number = "1234567890";
        String otp = "1234";
        when(oTPController.generateOTP()).thenReturn(otp);

        // Simulate an SMS sending failure
        ResponseEntity<Object> smsResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(smsResponse);

        // Act
        ResponseEntity<?> responseEntity = oTPController.sendByNumber(number);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("unsuccess", ((Response) responseEntity.getBody()).getMessage());
        assertEquals("Otp Not Send :: By Server", ((Response) responseEntity.getBody()).getObject());
        assertEquals(true, ((Response) responseEntity.getBody()).getHasError());
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(2, ((Map<String, Object>) body).size());
        assertEquals("Failed to parse JSON response", ((Map<String, Object>) body).get("error"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS2() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        when(objectMapper.readTree(Mockito.<String>any()))
                .thenThrow(new RuntimeException("https://www.fast2sms.com/dev/bulkV2"));

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(2, ((Map<String, Object>) body).size());
        assertEquals("Failed to parse JSON response", ((Map<String, Object>) body).get("error"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS3() throws RestClientException {
        // Arrange
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(null);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(2, ((Map<String, Object>) body).size());
        assertEquals("Failed to parse JSON response", ((Map<String, Object>) body).get("error"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS4() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(jsonNode);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(jsonNode, atLeast(1)).get(Mockito.<String>any());
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(4, ((Map<String, Object>) body).size());
        assertEquals("", ((Map<String, Object>) body).get("message"));
        assertEquals("", ((Map<String, Object>) body).get("requestId"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("return"));
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS5() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(jsonNode);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(jsonNode, atLeast(1)).get(Mockito.<String>any());
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(4, ((Map<String, Object>) body).size());
        assertEquals("", ((Map<String, Object>) body).get("message"));
        assertEquals("null", ((Map<String, Object>) body).get("requestId"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("return"));
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS6() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.get(Mockito.<String>any())).thenReturn(BooleanNode.getFalse());
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(jsonNode);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(jsonNode, atLeast(1)).get(Mockito.<String>any());
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(4, ((Map<String, Object>) body).size());
        assertEquals("", ((Map<String, Object>) body).get("message"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("return"));
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        String expectedString = Boolean.FALSE.toString();
        assertEquals(expectedString, ((Map<String, Object>) body).get("requestId"));
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS7() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.get(anyInt())).thenReturn(MissingNode.getInstance());
        when(jsonNode.asText()).thenReturn("As Text");
        when(jsonNode.asBoolean()).thenReturn(true);
        when(jsonNode.isArray()).thenReturn(true);
        JsonNode jsonNode2 = mock(JsonNode.class);
        when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(jsonNode2);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(jsonNode).asBoolean();
        verify(jsonNode).asText();
        verify(jsonNode).get(eq(0));
        verify(jsonNode2, atLeast(1)).get(Mockito.<String>any());
        verify(jsonNode).isArray();
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(4, ((Map<String, Object>) body).size());
        assertEquals("", ((Map<String, Object>) body).get("message"));
        assertEquals("As Text", ((Map<String, Object>) body).get("requestId"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("return"));
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS8() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.get(anyInt())).thenThrow(new RuntimeException("https://www.fast2sms.com/dev/bulkV2"));
        when(jsonNode.asText()).thenReturn("As Text");
        when(jsonNode.isArray()).thenReturn(true);
        JsonNode jsonNode2 = mock(JsonNode.class);
        when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(jsonNode2);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(jsonNode).asText();
        verify(jsonNode).get(eq(0));
        verify(jsonNode2, atLeast(1)).get(Mockito.<String>any());
        verify(jsonNode).isArray();
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(3, ((Map<String, Object>) body).size());
        assertEquals("As Text", ((Map<String, Object>) body).get("requestId"));
        assertEquals("Failed to parse JSON response", ((Map<String, Object>) body).get("error"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS9() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.get(anyInt())).thenReturn(NullNode.getInstance());
        when(jsonNode.asText()).thenReturn("As Text");
        when(jsonNode.asBoolean()).thenReturn(true);
        when(jsonNode.isArray()).thenReturn(true);
        JsonNode jsonNode2 = mock(JsonNode.class);
        when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(jsonNode2);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(jsonNode).asBoolean();
        verify(jsonNode).asText();
        verify(jsonNode).get(eq(0));
        verify(jsonNode2, atLeast(1)).get(Mockito.<String>any());
        verify(jsonNode).isArray();
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(4, ((Map<String, Object>) body).size());
        assertEquals("As Text", ((Map<String, Object>) body).get("requestId"));
        assertEquals("null", ((Map<String, Object>) body).get("message"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("return"));
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#send_SMS(String, String)}
     */
    @Test
    void testSend_SMS10() throws JsonProcessingException, RestClientException {
        // Arrange
        HttpStatusCode status = HttpStatusCode.valueOf(200);
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(status));
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.asText()).thenThrow(new RuntimeException("https://www.fast2sms.com/dev/bulkV2"));
        JsonNode jsonNode2 = mock(JsonNode.class);
        when(jsonNode2.get(anyInt())).thenReturn(jsonNode);
        when(jsonNode2.asText()).thenReturn("As Text");
        when(jsonNode2.isArray()).thenReturn(true);
        JsonNode jsonNode3 = mock(JsonNode.class);
        when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(jsonNode3);

        // Act
        ResponseEntity<?> actualSend_SMSResult = oTPController.send_SMS("42", "Otp");

        // Assert
        verify(jsonNode2).asText();
        verify(jsonNode).asText();
        verify(jsonNode2).get(eq(0));
        verify(jsonNode3, atLeast(1)).get(Mockito.<String>any());
        verify(jsonNode2).isArray();
        verify(objectMapper).readTree((String) isNull());
        verify(restTemplate).exchange(eq("https://www.fast2sms.com/dev/bulkV2"), isA(HttpMethod.class),
                isA(HttpEntity.class), isA(Class.class), (Object[]) any());
        Object body = actualSend_SMSResult.getBody();
        assertTrue(body instanceof Map);
        HttpStatusCode statusCode = actualSend_SMSResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(3, ((Map<String, Object>) body).size());
        assertEquals("As Text", ((Map<String, Object>) body).get("requestId"));
        assertEquals("Failed to parse JSON response", ((Map<String, Object>) body).get("error"));
        assertEquals(200, actualSend_SMSResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(((Map<String, Object>) body).containsKey("statusCode"));
        assertTrue(actualSend_SMSResult.hasBody());
        assertTrue(actualSend_SMSResult.getHeaders().isEmpty());
        assertSame(status, statusCode);
    }

    /**
     * Method under test: {@link OTPController#sendByNumber(String)}
     */
    @Test
    void testSendByNumber() throws Exception {
        // Arrange
        when(iOtp.save(Mockito.<String>any(), Mockito.<String>any())).thenReturn("Save");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/sendByNumber")
                .param("number", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"message\":\"success\",\"object\":\"Save\",\"hasError\":false}"));
    }

    /**
     * Method under test: {@link OTPController#sendByNumber(String)}
     */
    @Test
    void testSendByNumber2() throws Exception {
        // Arrange
        when(iOtp.save(Mockito.<String>any(), Mockito.<String>any())).thenReturn("Save");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        when(objectMapper.readTree(Mockito.<String>any()))
                .thenThrow(new RuntimeException("https://www.fast2sms.com/dev/bulkV2"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/sendByNumber")
                .param("number", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"message\":\"success\",\"object\":\"Save\",\"hasError\":false}"));
    }

    /**
     * Method under test: {@link OTPController#sendByNumber(String)}
     */
    @Test
    void testSendByNumber3() throws Exception {
        // Arrange
        when(iOtp.save(Mockito.<String>any(), Mockito.<String>any())).thenReturn("Save");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(null);
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/sendByNumber")
                .param("number", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"message\":\"success\",\"object\":\"Save\",\"hasError\":false}"));
    }

    /**
     * Method under test: {@link OTPController#sendByNumber(String)}
     */
    @Test
    void testSendByNumber4() throws Exception {
        // Arrange
        when(iOtp.save(Mockito.<String>any(), Mockito.<String>any())).thenReturn("Save");
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/sendByNumber")
                .param("number", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"message\":\"success\",\"object\":\"Save\",\"hasError\":false}"));
    }

    /**
     * Method under test: {@link OTPController#sendByNumber(String, String)}
     */
    @Test
    void testSendByNumber5() throws Exception {
        // Arrange
        when(iOtp.getVerifiedByNumberOtp(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/otp/verifiedOtp")
                .param("number", "foo")
                .param("otp", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"message\":\"success\",\"object\":true,\"hasError\":false}"));
    }

    /**
     * Method under test: {@link OTPController#sendByNumber(String, String)}
     */
    @Test
    void testSendByNumber6() throws Exception {
        // Arrange
        when(iOtp.getVerifiedByNumberOtp(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new RuntimeException("success"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/otp/verifiedOtp")
                .param("number", "foo")
                .param("otp", "foo");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(oTPController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Unsuccess\",\"object\":\"success\",\"hasError\":true}"));
    }

    /**
     * Method under test: {@link OTPController#send_VOICE(String, String)}
     */
    @Test
    void testSend_VOICE() throws Exception {
        // Arrange
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/send_VOICE")
                .param("number", "foo")
                .param("otp", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"error\":\"Failed to parse JSON response\",\"statusCode\":500}"));
    }

    /**
     * Method under test: {@link OTPController#send_VOICE(String, String)}
     */
    @Test
    void testSend_VOICE2() throws Exception {
        // Arrange
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        when(objectMapper.readTree(Mockito.<String>any()))
                .thenThrow(new RuntimeException("https://www.fast2sms.com/dev/voice"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/send_VOICE")
                .param("number", "foo")
                .param("otp", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"error\":\"Failed to parse JSON response\",\"statusCode\":500}"));
    }

    /**
     * Method under test: {@link OTPController#send_VOICE(String, String)}
     */
    @Test
    void testSend_VOICE3() throws Exception {
        // Arrange
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(null);
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/send_VOICE")
                .param("number", "foo")
                .param("otp", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"error\":\"Failed to parse JSON response\",\"statusCode\":500}"));
    }

    /**
     * Method under test: {@link OTPController#send_VOICE(String, String)}
     */
    @Test
    void testSend_VOICE4() throws Exception {
        // Arrange
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/otp/send_VOICE")
                .param("number", "foo")
                .param("otp", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(oTPController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"error\":\"Failed to parse JSON response\",\"statusCode\":500}"));
    }
}
