package com.mutualfund.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
//Entity class mapped to 'users' table in database
// Represents user data stored in the system
// Uses JPA annotations for ORM mapping
@Entity
@Table(name = "educational_content")
public class EducationalContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String keyTakeaways;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    private Integer views = 0;

    private LocalDate createdDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    public enum Status {
        PUBLISHED, DRAFT
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getKeyTakeaways() { return keyTakeaways; }
    public void setKeyTakeaways(String keyTakeaways) { this.keyTakeaways = keyTakeaways; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Integer getViews() { return views; }
    public void setViews(Integer views) { this.views = views; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
}