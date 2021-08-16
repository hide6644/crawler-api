package crawlerapi.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小説の章の情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "novel_chapter")
@Indexed
public class NovelChapter extends BaseObject implements Serializable {

    /** URL */
    @Column(nullable = false, length = 64)
    private String url;

    /** タイトル */
    @EqualsAndHashCode.Exclude
    @Column(length = 100)
    @FullTextField
    @KeywordField(name = "titleSort", sortable = Sortable.YES)
    private String title;

    /** 本文 */
    @EqualsAndHashCode.Exclude
    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @FullTextField
    @KeywordField(name = "bodySort", sortable = Sortable.YES)
    private String body;

    /** 小説の章の付随情報 */
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "novelChapter", cascade = CascadeType.ALL)
    private NovelChapterInfo novelChapterInfo;

    /** 小説の章の更新履歴セット */
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "novelChapter", cascade = CascadeType.ALL)
    private Set<NovelChapterHistory> novelChapterHistories = new HashSet<>();

    /** 小説 */
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_id")
    private Novel novel;

    public void addNovelChapterHistory(NovelChapterHistory novelChapterHistory) {
        getNovelChapterHistories().add(novelChapterHistory);
    }
}
