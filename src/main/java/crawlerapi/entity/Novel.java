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

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Normalizer;
import org.hibernate.search.annotations.NormalizerDef;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.TokenFilterDef;

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
@Analyzer(impl = JapaneseAnalyzer.class)
@NormalizerDef(name = "novelSort", filters = @TokenFilterDef(factory = LowerCaseFilterFactory.class))
@XmlRootElement
public class Novel extends BaseObject implements Serializable {

    /** URL */
    @Column(nullable = false, length = 64)
    private String url;

    /** タイトル */
    @EqualsAndHashCode.Exclude
    @Column(length = 100)
    @Field
    @Field(name = "titleSort", normalizer = @Normalizer(definition = "novelSort"))
    @SortableField(forField = "titleSort")
    private String title;

    /** 作者名 */
    @EqualsAndHashCode.Exclude
    @Column(length = 100)
    @Field
    @Field(name = "writernameSort", normalizer = @Normalizer(definition = "novelSort"))
    @SortableField(forField = "writernameSort")
    private String writername;

    /** 解説 */
    @EqualsAndHashCode.Exclude
    @Column
    @Field
    @Field(name = "descriptionSort", normalizer = @Normalizer(definition = "novelSort"))
    @SortableField(forField = "descriptionSort")
    private String description;

    /** 本文 */
    @EqualsAndHashCode.Exclude
    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Field
    @Field(name = "bodySort", normalizer = @Normalizer(definition = "novelSort"))
    @SortableField(forField = "bodySort")
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
    @IndexedEmbedded
    private List<NovelChapter> novelChapters = new ArrayList<>();

    public void addNovelHistory(NovelHistory novelHistory) {
        getNovelHistories().add(novelHistory);
    }

    public void addNovelChapter(NovelChapter novel) {
        getNovelChapters().add(novel);
    }
}
