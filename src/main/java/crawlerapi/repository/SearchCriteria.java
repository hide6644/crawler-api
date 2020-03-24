package crawlerapi.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 検索条件を保持するクラス.
 */
@RequiredArgsConstructor()
@Setter
@Getter
public class SearchCriteria {

    /** キー */
    private final String key;

    /** 操作 */
    private final SearchOperation operation;

    /** 値 */
    private final Object value;

    /** OR述語 true:OR、false:AND */
    private boolean orPredicate;

    /**
     * コンストラクタ.
     *
     * @param orPredicate
     *            OR述語 true:OR、false:AND
     * @param key
     *            キー
     * @param operation
     *            操作
     * @param value
     *            値
     */
    public SearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
