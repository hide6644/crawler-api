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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 小説の章の情報
 */
@Entity
@Table(name = "novel_chapter")
@JsonIdentityInfo(property = "_id", generator = ObjectIdGenerators.UUIDGenerator.class)
@Setter
@Getter
@EqualsAndHashCode(of = { "url" }, callSuper = false)
public class NovelChapter extends BaseObject implements Serializable {

    /** URL */
    @Column(nullable = false, length = 64)
    private String url;

    /** タイトル */
    @Column(length = 100)
    private String title;

    /** 本文 */
    @Column
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String body;

    /** 小説の章の付随情報 */
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "novelChapter", cascade = CascadeType.ALL)
    private NovelChapterInfo novelChapterInfo;

    /** 小説の章の更新履歴セット */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "novelChapter", cascade = CascadeType.ALL)
    private Set<NovelChapterHistory> novelChapterHistories = new HashSet<>();

    /** 小説 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "novel_id")
    private Novel novel;

    public void addNovelChapterHistory(NovelChapterHistory novelChapterHistory) {
        novelChapterHistories.add(novelChapterHistory);
    }
}
