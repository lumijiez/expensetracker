package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Department", schema = "HumanResources")
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DepartmentID")
    private Short departmentId;

    @Column(name = "GroupName", nullable = false, length = 50)
    private String groupName;

    @Column(name = "ModifiedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    public Short getDepartmentId() {
        return departmentId;
    }
    public String getGroupName() {
        return groupName;
    }
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public String getName() {
        return name;
    }
}
