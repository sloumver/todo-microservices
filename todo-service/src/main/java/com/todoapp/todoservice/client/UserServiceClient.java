package com.todoapp.todoservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    
    @GetMapping("/auth/validate")
    Boolean validateToken(@RequestParam("token") String token);
}