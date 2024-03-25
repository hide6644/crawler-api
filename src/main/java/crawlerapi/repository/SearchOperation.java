package crawlerapi.repository;

/**
 * 検索操作.
 */
public enum SearchOperation {

    /** 等しい */
    EQUALITY,

    /** 等しくない */
    NEGATION,

    /** より大きい */
    GREATER_THAN,

    /** より小さい */
    LESS_THAN,

    /** 曖昧 */
    LIKE,

    /** 前方一致 */
    STARTS_WITH,

    /** 後方一致 */
    ENDS_WITH,

    /** 部分一致 */
    CONTAINS;

    /** 検索操作のセット */
    public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", "<", "~" };

    /** OR述語 */
    public static final String OR_PREDICATE_FLAG = "'";

    /** ゼロ以上の正規表現 */
    public static final String ZERO_OR_MORE_REGEX = "*";

    /**
     * 検索操作を取得する.
     *
     * @param input
     *            検索操作の文字列
     * @return 検索操作の型
     */
    public static final SearchOperation getSimpleOperation(final char input) {
        return switch (input) {
        case ':' -> EQUALITY;
        case '!' -> NEGATION;
        case '>' -> GREATER_THAN;
        case '<' -> LESS_THAN;
        case '~' -> LIKE;
        default -> null;
        };
    }
}
