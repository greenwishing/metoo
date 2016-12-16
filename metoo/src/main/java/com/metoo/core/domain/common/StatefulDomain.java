package com.metoo.core.domain.common;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/16
 */
@MappedSuperclass
public abstract class StatefulDomain extends Domain {

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    protected Status status = Status.ACTIVATED;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void toggleStatus() {
        this.status = status.getToggle();
    }
}
