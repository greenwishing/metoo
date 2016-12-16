package com.metoo.dto;

import com.metoo.core.domain.common.StatefulDomain;
import com.metoo.core.domain.common.Status;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/16
 */
public abstract class StatefulDTO extends DTO {

    protected Status status = Status.ACTIVATED;

    public StatefulDTO() {
    }

    public StatefulDTO(StatefulDomain statefulDomain) {
        super(statefulDomain);
        this.status = statefulDomain.getStatus();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
