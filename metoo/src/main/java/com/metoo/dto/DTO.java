package com.metoo.dto;

import com.metoo.core.domain.common.Domain;

import java.io.Serializable;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/16
 */
public abstract class DTO implements Serializable {

    protected Long id;

    public DTO() {
    }

    public DTO(Domain domain) {
        this.id = domain.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
