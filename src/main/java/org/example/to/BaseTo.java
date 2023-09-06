package org.example.to;

import io.swagger.annotations.ApiModelProperty;
import org.example.HasId;

public abstract class BaseTo implements HasId {
    @ApiModelProperty(hidden = true)
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
