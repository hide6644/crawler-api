package crawlerapi.config;

import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.stereotype.Component;

/**
 * アナライザー等を定義するクラス.
 */
@Component("customLuceneAnalysisConfigurer")
public class CustomLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        context.analyzer("japanese").instance(new JapaneseAnalyzer());
    }
}
