package br.com.ciaf.admin.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MyEmitenteId {

    @Column(name = "emitente_id", nullable = false, updatable = false)
    private Long emitente_id;

    public Long getEmitente_id() {
        return emitente_id;
    }

    public void setEmitente_id(Long emitente_id) {
        this.emitente_id = emitente_id;
    }
}
