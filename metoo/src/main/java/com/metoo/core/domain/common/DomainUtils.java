package com.metoo.core.domain.common;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/16
 */
public class DomainUtils {

    @SuppressWarnings("unchecked")
    public static void toggleStatus(JpaRepository statefulRepository, Long id) {
        StatefulDomain statefulDomain = (StatefulDomain) statefulRepository.findOne(id);
        if (statefulDomain != null) {
            statefulDomain.toggleStatus();
            statefulRepository.save(statefulDomain);
        }
    }
}
