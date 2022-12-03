package crawlerapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小説の章の付随情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "novel_chapter_info")
public class NovelChapterInfo extends BaseObject implements Serializable {

    /** 最終確認日時 */
    @Column(name = "checked_date")
    private LocalDateTime checkedDate;

    /** 最終更新日時 */
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    /** 未読フラグ */
    @Column
    private boolean unread;

    /** 既読日時 */
    @Column(name = "read_date")
    private LocalDateTime readDate;

    /** 小説の章 */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "novel_chapter_id")
    private NovelChapter novelChapter;
}
