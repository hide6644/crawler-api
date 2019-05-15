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

import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 小説の情報
 */
@Entity
@Table(name = "novel")
@Indexed
@Analyzer(impl = JapaneseAnalyzer.class)
@XmlRootElement
@Setter
@Getter
@EqualsAndHashCode(of = { "url" }, callSuper = false)
public class Novel extends BaseObject implements Serializable {

    /** URL */
    @Column(nullable = false, length = 64)
    private String url;

    /** タイトル */
    @Column(length = 100)
    @Field
    private String title;

    /** 作者名 */
    @Column(length = 100)
    @Field
    private String writername;

    /** 解説 */
    @Column
    @Field
    private String description;

    /** 本文 */
    @Column
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Field
    private String body;

    /** 削除フラグ */
    @Column
    private boolean deleted;

    /** 小説の付随情報 */
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "novel", cascade = CascadeType.ALL)
    @IndexedEmbedded
    private NovelInfo novelInfo;

    /** 小説の更新履歴セット */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "novel", cascade = CascadeType.ALL)
    private Set<NovelHistory> novelHistories = new HashSet<>();

    /** 小説の章リスト */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "novel", cascade = CascadeType.ALL)
    @IndexedEmbedded
    private List<NovelChapter> novelChapters = new ArrayList<>();

    public void addNovelHistory(NovelHistory novelHistory) {
        novelHistories.add(novelHistory);
    }

    public void addNovelChapter(NovelChapter novel) {
        novelChapters.add(novel);
    }
}
