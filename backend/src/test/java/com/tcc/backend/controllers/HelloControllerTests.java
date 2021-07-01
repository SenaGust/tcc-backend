package com.tcc.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@WebMvcTest(HelloController.class)
class HelloControllerTests {

    @Test
    void ShouldReturnHello() {

        HelloController test = new HelloController();

         given(test.getHelloMessage()).willReturn("Hello");
    }

}
