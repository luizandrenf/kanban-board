package org.example.domain.enterprise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import org.example.domain.enterprise.enums.Priority;

import java.time.LocalDate;

@Entity
@Table(name = "personal_tasks")
@PrimaryKeyJoinColumn(name="id")
public class PersonalTask extends Task {

    private String place;

    public PersonalTask(){}

    public PersonalTask(String title, String description, LocalDate limitDate, Boolean finished, Priority priority, String place, User user) {
        super(title, description, limitDate, finished, priority, user);
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
