package com.vitara.location.controller;

import com.vitara.common.location.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public ApiResponse HomeController(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("hello everyone im location service of airline microservices");
        return apiResponse;
    }
}
