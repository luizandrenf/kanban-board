package org.example.domain.enterprise.entities;

import jakarta.persistence.*;
import org.example.domain.enterprise.enums.Priority;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;
    protected String description;
    protected LocalDate limitDate;
    protected Boolean finished;

    @Enumerated(EnumType.STRING)
    protected Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    public Task(){}

    public Task(String title, String description, LocalDate limitDate, Boolean finished, Priority priority, User user) {
        this.title = title;
        this.description = description;
        this.limitDate = limitDate;
        this.finished = finished;
        this.priority = priority;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
