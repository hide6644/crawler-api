package crawlerapi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import crawlerapi.entity.Novel;

public class NovelSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public NovelSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public final NovelSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final NovelSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));

        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }

            params.add(new SearchCriteria(orPredicate, key, op, value));
        }

        return this;
    }

    public Specification<Novel> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<Novel> result = new NovelSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new NovelSpecification(params.get(i)))
                    : Specification.where(result).and(new NovelSpecification(params.get(i)));
        }

        return result;
    }
}
