package com.popov.t04jh.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.popov.t04jh.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.popov.t04jh.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.popov.t04jh.domain.User.class.getName());
            createCache(cm, com.popov.t04jh.domain.Authority.class.getName());
            createCache(cm, com.popov.t04jh.domain.User.class.getName() + ".authorities");
            createCache(cm, com.popov.t04jh.domain.Groupe.class.getName());
            createCache(cm, com.popov.t04jh.domain.Groupe.class.getName() + ".nageurs");
            createCache(cm, com.popov.t04jh.domain.Groupe.class.getName() + ".ficheSeances");
            createCache(cm, com.popov.t04jh.domain.Groupe.class.getName() + ".programmations");
            createCache(cm, com.popov.t04jh.domain.Nageurs.class.getName());
            createCache(cm, com.popov.t04jh.domain.Nageurs.class.getName() + ".poidsTailles");
            createCache(cm, com.popov.t04jh.domain.Nageurs.class.getName() + ".testPerformances");
            createCache(cm, com.popov.t04jh.domain.Nageurs.class.getName() + ".documents");
            createCache(cm, com.popov.t04jh.domain.MesureAnthropo.class.getName());
            createCache(cm, com.popov.t04jh.domain.Document.class.getName());
            createCache(cm, com.popov.t04jh.domain.Document.class.getName() + ".exercices");
            createCache(cm, com.popov.t04jh.domain.TestPerformance.class.getName());
            createCache(cm, com.popov.t04jh.domain.TestNage.class.getName());
            createCache(cm, com.popov.t04jh.domain.TestAutre.class.getName());
            createCache(cm, com.popov.t04jh.domain.TestEtude.class.getName());
            createCache(cm, com.popov.t04jh.domain.TestPhysique.class.getName());
            createCache(cm, com.popov.t04jh.domain.FicheSeance.class.getName());
            createCache(cm, com.popov.t04jh.domain.FicheSeance.class.getName() + ".presences");
            createCache(cm, com.popov.t04jh.domain.FicheSeance.class.getName() + ".locations");
            createCache(cm, com.popov.t04jh.domain.Presence.class.getName());
            createCache(cm, com.popov.t04jh.domain.Presence.class.getName() + ".nageurs");
            createCache(cm, com.popov.t04jh.domain.Programmation.class.getName());
            createCache(cm, com.popov.t04jh.domain.Programmation.class.getName() + ".documents");
            createCache(cm, com.popov.t04jh.domain.Theme.class.getName());
            createCache(cm, com.popov.t04jh.domain.Theme.class.getName() + ".objectifs");
            createCache(cm, com.popov.t04jh.domain.Theme.class.getName() + ".ficheSeances");
            createCache(cm, com.popov.t04jh.domain.Theme.class.getName() + ".programmations");
            createCache(cm, com.popov.t04jh.domain.Objectif.class.getName());
            createCache(cm, com.popov.t04jh.domain.Objectif.class.getName() + ".exercices");
            createCache(cm, com.popov.t04jh.domain.Objectif.class.getName() + ".themes");
            createCache(cm, com.popov.t04jh.domain.Exercice.class.getName());
            createCache(cm, com.popov.t04jh.domain.Exercice.class.getName() + ".documents");
            createCache(cm, com.popov.t04jh.domain.Exercice.class.getName() + ".objectifs");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
