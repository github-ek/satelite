package com.tacticlogistics.application.dto.ordenes;

public class ReferenciaDto<T> {

    private T id;
    private String codigo;
    private String nombre;

    // ---------------------------------------------------------------------------------------------------------
    public ReferenciaDto() {
        this(null, "", "");
    }

    public ReferenciaDto(T aId, String aCodigo, String aNombre) {
        super();
        setId(aId);
        setCodigo(aCodigo);
        setNombre(aNombre);
    }

    // ---------------------------------------------------------------------------------------------------------
    public T getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setId(T aId) {
        id = aId;
    }

    public void setCodigo(String aCodigo) {
        if (aCodigo == null) {
            throw new NullPointerException();
        }
        codigo = aCodigo;
    }

    public void setNombre(String aNombre) {
        if (aNombre == null) {
            throw new NullPointerException();
        }
        nombre = aNombre;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReferenciaDto<?> other = (ReferenciaDto<?>) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Referencia [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (codigo != null) {
            builder.append("codigo=").append(codigo).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre);
        }
        builder.append("]");
        return builder.toString();
    }
}