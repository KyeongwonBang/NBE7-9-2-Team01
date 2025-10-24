package org.example.povi.domain.community.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.povi.domain.user.entity.User;
import org.example.povi.global.entity.BaseEntity;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "community_posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AttributeOverride(
        name = "id",
        column = @Column(name = "post_id")
)
public class CommunityPost extends BaseEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "title", nullable = false, length = 255)
        private String title;

        @Column(name = "content", nullable = false, columnDefinition = "TEXT")
        private String content;

        @Enumerated(EnumType.STRING)
        @Column(name = "emoticon", nullable = false)
        private CommunityEmoticon emoticon;

        @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
        private Set<PostLike> likes = new HashSet<>();

        @Column(name = "like_count", nullable = false)
        private int likeCount = 0; // 기본값을 0으로 설정

        @OneToMany(
                mappedBy = "communityPost",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        private List<Comment> comments = new ArrayList<>();

        @Formula("(SELECT COUNT(1) FROM comments c WHERE c.post_id = post_id)")
        private int commentCount;

        @CreatedDate
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @LastModifiedDate
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CommunityImage> images = new ArrayList<>();


        @Builder
        public CommunityPost(User user, String title, String content, CommunityEmoticon emoticon) {
            this.user = user;
            this.title = title;
            this.content = content;
            this.emoticon = emoticon;
        }

        public void updatePost(String title, String content) {
            this.title = title;
            this.content = content;

        }

        public void addLike() {
                this.likeCount++;
        }
        public void removeLike() {
                if (this.likeCount > 0) {
                    this.likeCount--;
                }
        }
}
