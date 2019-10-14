package crawlerapi.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import crawlerapi.entity.Novel;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
public class NovelServiceTests {

    @Autowired
    private NovelService novelService;

    @Test
    public void searchTest() {
        Stream<Novel> novels = novelService.search("title:t1,writername~*w1*");
        System.out.println(novels.count());
    }
}
