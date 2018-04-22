package com.example.rss.web.assemblers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.rss.entity.CrawlerInfo;
import com.example.rss.web.resources.CrawlerInfoResource;

@Component
public class CrawlerInfoAssembler extends ResourceAssemblerSupport<CrawlerInfo, CrawlerInfoResource> {

    public CrawlerInfoAssembler() {
        super(CrawlerInfo.class, CrawlerInfoResource.class);
    }


    @Override
    public CrawlerInfoResource toResource(CrawlerInfo entity) {
        if (entity == null) {
            return null;
        }
        CrawlerInfoResource contentResource = new CrawlerInfoResource();
        contentResource.setCrawledSiteName(entity.getCrawledSiteName());
        contentResource.setFirstCrawledNews(entity.getFirstCrawledNews());
        contentResource.setLastCrawledNews(entity.getLastCrawledNews());
        contentResource.setFirstUpdatedNews(entity.getFirstUpdatedNews());
        contentResource.setLastUpdatedNews(entity.getLastUpdatedNews());
        contentResource.setLastRead(entity.getLastRead());
        contentResource.setLastStart(entity.getInitialDate());

        return contentResource;
    }

    @Override
    public List<CrawlerInfoResource> toResources(Iterable<? extends CrawlerInfo> entities) {
        List<CrawlerInfoResource> resources = new LinkedList<>();
        for (CrawlerInfo content : entities) {
            resources.add(toResource(content));
        }
        return resources;
    }
}
