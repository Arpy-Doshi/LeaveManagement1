package com.brevitaz.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;

@EntityScan
public class LeavePolicyRule
{
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LeavePolicy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeavePolicyRule that = (LeavePolicyRule) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return description.equals(that.description);
    }


}
