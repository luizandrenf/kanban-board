package org.example.domain.enterprise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import org.example.domain.enterprise.enums.Priority;

import java.time.LocalDate;

@Entity
@Table(name = "work_tasks")
@PrimaryKeyJoinColumn(name="id")
public class WorkTask extends Task {

    private String department;

    public WorkTask(){}

    public WorkTask(String department) {
        this.department = department;
    }

    public WorkTask(String title, String description, LocalDate limitDate, Boolean finished, Priority priority, String department, User user) {
        super(title, description, limitDate, finished, priority, user);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
