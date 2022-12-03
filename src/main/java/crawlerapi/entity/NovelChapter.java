package crawlerapi.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
public class NovelChapter extends BaseObject implements Serializable {

    /** URL */
    @Column(nullable = false, length = 64)
    private String url;

    /** タイトル */
    @EqualsAndHashCode.Exclude
    @Column(length = 100)
    private String title;

    /** 本文 */
    @EqualsAndHashCode.Exclude
    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
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
