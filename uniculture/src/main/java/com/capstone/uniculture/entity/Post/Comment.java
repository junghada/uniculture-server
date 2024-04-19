package com.capstone.uniculture.entity.Post;

import com.capstone.uniculture.entity.BaseEntity;
import com.capstone.uniculture.entity.Member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@DynamicInsert @NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ColumnDefault("FALSE")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    @BatchSize(size = 10)
    private Member member; //멤버쪽에서는 댓글을 알필요 없음, 게시물만 알고있으면됨. 따라서 단방향 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent; // 대댓글일때의 부모 댓글, 없으면 댓글

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @BatchSize(size = 10)
    private List<Comment> children = new ArrayList<>(); // 자식 댓글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(Member member, Post post, String content) {
        this.member = member;
        this.post = post;
        this.content = content;
    }

}
