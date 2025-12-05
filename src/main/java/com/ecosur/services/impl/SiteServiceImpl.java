package com.ecosur.services.impl;

import com.ecosur.entities.Site;
import com.ecosur.exception.ResourceNotFoundException;
import com.ecosur.repositories.SiteRepository;
import com.ecosur.services.SiteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Site getSiteById(Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Site non trouvé pour l'id : " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Site createSite(Site site) {
        // Si tu veux faire des contrôles métier, tu pourras les ajouter ici.
        return siteRepository.save(site);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Site updateSite(Long id, Site site) {
        Site existing = getSiteById(id);
        existing.setNom(site.getNom());
        existing.setAdresse(site.getAdresse());
        return siteRepository.save(existing);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteSite(Long id) {
        Site site = getSiteById(id);
        siteRepository.delete(site);
    }
}
