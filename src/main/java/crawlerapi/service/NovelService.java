package crawlerapi.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import crawlerapi.entity.Novel;
import crawlerapi.repository.NovelRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;

    public Stream<Novel> findAll() {
        return novelRepository.findAll().stream();
    }

    public Optional<Novel> findById(Long id) {
        return novelRepository.findById(id);
    }
}
