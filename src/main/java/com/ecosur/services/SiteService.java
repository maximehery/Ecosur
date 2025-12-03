package com.ecosur.services;

import com.ecosur.entities.Site;

import java.util.List;

public interface SiteService {

    List<Site> getAllSites();

    Site getSiteById(Long id);

    Site createSite(Site site);

    Site updateSite(Long id, Site site);

    void deleteSite(Long id);
}
