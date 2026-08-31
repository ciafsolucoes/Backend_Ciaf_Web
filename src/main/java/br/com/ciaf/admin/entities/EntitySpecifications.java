package br.com.ciaf.admin.entities;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EntitySpecifications {

    public static <T extends MyEmitenteId> Specification<T> hasEmitenteId(Long emitente_id) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get("emitente_id"), emitente_id);
        };
    }
}
