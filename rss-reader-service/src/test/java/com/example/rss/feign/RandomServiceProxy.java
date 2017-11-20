package com.example.rss.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.rss.resources.DetailsNewsResource;

@FeignClient(name = "RandomServiceProxy", url = "http://localhost:8080/")
public interface RandomServiceProxy {

    @RequestMapping(value = "/v1/dnesbg/random", method = RequestMethod.GET)
    public DetailsNewsResource randomNews();

}
