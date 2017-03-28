package ar.com.clothes.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(ar.com.clothes.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Cliente.class.getName() + ".modelos", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Cliente.class.getName() + ".medidas", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Cliente.class.getName() + ".encargos", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Dominio.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Dominio.class.getName() + ".valorDominios", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Encargo.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Encargo.class.getName() + ".pagos", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Medida.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Modelo.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.Pago.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.ValorDominio.class.getName(), jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.ValorDominio.class.getName() + ".tipoEventos", jcacheConfiguration);
            cm.createCache(ar.com.clothes.domain.ValorDominio.class.getName() + ".estados", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
