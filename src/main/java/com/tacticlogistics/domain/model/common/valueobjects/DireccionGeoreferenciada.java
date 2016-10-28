package com.tacticlogistics.domain.model.common.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DireccionGeoreferenciada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 150)
	@NotNull
	private String direccionEstandarizada;

	@Column(nullable = true, precision = 18, scale = 15)
	private BigDecimal cx;

	@Column(nullable = true, precision = 18, scale = 15)
	private BigDecimal cy;
}
