package com.example.rss.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.rss.web.resources.DetailsNewsResource;

@FeignClient(path = "/v1/dnesbg/", name = "rss-reader-service", url = "http://localhost:12345")
public interface RandomServiceProxy {

    @RequestMapping(value = "random", method = RequestMethod.GET)
    public DetailsNewsResource randomNews();

}
