package com.transport.transit.admin.external;

import com.transport.transit.admin.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "CUSTOMER-SERVER", url = "http://localhost:8082/")
public interface UserClient {

    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable("id") Long id);
}
