package crawlerapi.service;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import crawlerapi.entity.Novel;

@SpringBootTest
@Transactional
@Rollback
public class NovelServiceTests {

    @Autowired
    private NovelService novelService;

    @Autowired
    private NovelIndexService novelIndexService;

    @Test
    public void searchTest() {
        Stream<Novel> novels = novelService.search("title:t1,writername~*w1*");
        System.out.println(novels.count());
    }

    @Test
    public void aggregateByTitleTest() {
        Map<String, Long> aggregateByKeywords = novelIndexService.aggregateByKeywords();
        System.out.println(aggregateByKeywords);
    }
}
