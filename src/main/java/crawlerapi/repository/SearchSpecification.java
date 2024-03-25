package crawlerapi.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;

/**
 * 検索仕様を保持するクラス.
 */
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    /** 検索条件 */
    private final SearchCriteria criteria;

    /**
     * {@inheritDoc}
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return switch (criteria.getOperation()) {
        case EQUALITY -> builder.equal(root.get(criteria.getKey()), criteria.getValue());
        case NEGATION -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
        case GREATER_THAN -> builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        case LESS_THAN -> builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        case LIKE -> builder.like(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        case STARTS_WITH -> builder.like(root.<String> get(criteria.getKey()), criteria.getValue() + "%");
        case ENDS_WITH -> builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
        case CONTAINS -> builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%");
        case null, default -> null;
        };
    }
}
