package com.tacticlogistics.domain.model.common.valueobjects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.geo.Ciudad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class Direccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciudad", nullable = false)
    @NotNull
    private Ciudad ciudad;

    @Column(nullable = false, length = 150)
    @NotNull
    private String direccion;

    @Column(nullable = false, length = 150)
    @NotNull
    private String indicacionesDireccion;
}
