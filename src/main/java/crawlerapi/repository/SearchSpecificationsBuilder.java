package crawlerapi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

/**
 * 検索仕様を生成するクラス.
 */
public class SearchSpecificationsBuilder<T> {

    /** 検索条件の一覧 */
    private final List<SearchCriteria> params;

    /**
     * デフォルト・コンストラクタ.
     */
    public SearchSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    /**
     * 検索仕様を生成するクラスをインスタンス化する.
     *
     * @param key
     *            キー
     * @param operation
     *            操作
     * @param value
     *            値
     * @param prefix
     *            接頭辞
     * @param suffix
     *            接尾辞
     * @return 検索仕様を生成するクラス
     */
    public final SearchSpecificationsBuilder<T> with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    /**
     * 検索仕様を生成するクラスをインスタンス化する.
     *
     * @param orPredicate
     *            OR述語 true:OR、false:AND
     * @param key
     *            キー
     * @param operation
     *            操作
     * @param value
     *            値
     * @param prefix
     *            接頭辞
     * @param suffix
     *            接尾辞
     * @return 検索仕様を生成するクラス
     */
    public final SearchSpecificationsBuilder<T> with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
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

    /**
     * 検索仕様を生成する.
     */
    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<T> result = new SearchSpecification<T>(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new SearchSpecification<T>(params.get(i)))
                    : Specification.where(result).and(new SearchSpecification<T>(params.get(i)));
        }

        return result;
    }
}
