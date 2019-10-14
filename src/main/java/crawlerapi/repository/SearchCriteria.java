package crawlerapi.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor()
@Setter
@Getter
public class SearchCriteria {

    private final String key;

    private final SearchOperation operation;

    private final Object value;

    private boolean orPredicate;

    public SearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
