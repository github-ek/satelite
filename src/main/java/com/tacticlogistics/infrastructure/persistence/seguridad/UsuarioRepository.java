package com.tacticlogistics.infrastructure.persistence.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.seguridad.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByUsuarioIgnoringCase(String usuario);
}
