package crawlerapi.config;

import jakarta.persistence.EntityManager;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.log4j.Log4j2;

/**
 * アプリ起動時にインデックスを作成するクラス.
 */
@Configuration
@Log4j2
public class HibernateSearchIndexBuild implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Started Initializing Indexes");
        SearchSession searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer().idFetchSize(150).batchSizeToLoadObjects(25)
                .threadsToLoadObjects(12);

        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            log.warn("Failed to load data from database");
            Thread.currentThread().interrupt();
        }

        log.info("Completed Indexing");
    }
}
