package crawlerapi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小説の情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "novel")
@Indexed
@XmlRootElement
public class Novel extends BaseObject implements Serializable {

    /** タイトルのフィールド名 */
    public static final String TITLE_FIELD_NAME = "title";

    /** 作者名のフィールド名 */
    public static final String WRITERNAME_FIELD_NAME = "writername";

    /** 解説のフィールド名 */
    public static final String DESCRIPTION_FIELD_NAME = "description";

    /** URL */
    @Column(nullable = false, length = 64)
    private String url;

    /** タイトル */
    @EqualsAndHashCode.Exclude
    @Column(length = 100)
    @FullTextField(analyzer = "japanese")
    @KeywordField(name = TITLE_FIELD_NAME + SORT_NAME, sortable = Sortable.YES)
    private String title;

    /** 作者名 */
    @EqualsAndHashCode.Exclude
    @Column(length = 100)
    @FullTextField(analyzer = "japanese")
    @KeywordField(name = WRITERNAME_FIELD_NAME + SORT_NAME, sortable = Sortable.YES)
    private String writername;

    /** 解説 */
    @EqualsAndHashCode.Exclude
    @Column
    @FullTextField(analyzer = "japanese")
    @KeywordField(name = DESCRIPTION_FIELD_NAME + SORT_NAME, sortable = Sortable.YES)
    private String description;

    /** 本文 */
    @EqualsAndHashCode.Exclude
    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String body;

    /** 削除フラグ */
    @EqualsAndHashCode.Exclude
    @Column
    private boolean deleted;

    /** 小説の付随情報 */
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "novel", cascade = CascadeType.ALL)
    @IndexedEmbedded
    private NovelInfo novelInfo;

    /** 小説の更新履歴セット */
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "novel", cascade = CascadeType.ALL)
    private Set<NovelHistory> novelHistories = new HashSet<>();

    /** 小説の章リスト */
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "novel", cascade = CascadeType.ALL)
    private List<NovelChapter> novelChapters = new ArrayList<>();

    public void addNovelHistory(NovelHistory novelHistory) {
        getNovelHistories().add(novelHistory);
    }

    public void addNovelChapter(NovelChapter novel) {
        getNovelChapters().add(novel);
    }
}
