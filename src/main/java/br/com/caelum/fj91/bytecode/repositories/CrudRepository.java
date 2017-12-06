package br.com.caelum.fj91.bytecode.repositories;

import java.util.List;

public interface CrudRepository<T, ID> {

    T findOne(ID id);
    List<T> findAll();
    T save(T entity);

}
