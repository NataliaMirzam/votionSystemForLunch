package org.example.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank(message = "Name should not be blank")
    @Size(min = 2, max = 128, message = "Name should be between 2 and 128 characters")
    @Column(name = "name", nullable = false)
    protected String name;

    public AbstractNamedEntity() {
    }

    public AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString()  {
        return super.toString() + '(' + name + ')';
    }
}