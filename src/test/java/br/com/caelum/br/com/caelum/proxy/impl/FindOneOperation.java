package br.com.caelum.br.com.caelum.proxy.impl;

import javax.persistence.EntityManager;

public class FindOneOperation<T,ID> {

    private final Class<T> entityClass;
    private final EntityManager manager;

    public FindOneOperation(Class<T> entityClass, EntityManager manager) {
        this.entityClass = entityClass;
        this.manager = manager;
    }

    public T findOne(ID id){
        return  manager.find(entityClass, id);
    }
}
