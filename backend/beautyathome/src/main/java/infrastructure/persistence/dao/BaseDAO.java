package infrastructure.persistence.dao;


/**
 * Contrato genérico para operaciones CRUD básicas en memoria o base de datos.
 */
public interface BaseDAO<T, ID> {

    /**
     * Guarda o actualiza la entidad dada.
     *
     * @param entity entidad a persistir
     * @return entidad resultante (normalizada)
     */
    T save(T entity);

    /**
     * Recupera una entidad por su identificador.
     *
     * @param id clave primaria
     * @return entidad encontrada o {@code null}
     */
    T findById(ID id);

    /**
     * Elimina la entidad asociada a la clave proporcionada.
     *
     * @param id identificador a borrar
     */
    void delete(ID id);
}
