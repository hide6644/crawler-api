package crawlerapi.repository;

public class SearchParameter {

    public static final int OR_PREDICATE = 1;

    public static final int KEY = 2;

    public static final int OPERATION = 3;

    public static final int VALUE = 5;

    public static final int PREFIX = 4;

    public static final int SUFFIX = 6;

    /**
     * プライベート・コンストラクタ.
     */
    private SearchParameter() {
    }
}
