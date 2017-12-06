package br.com.caelum.br.com.caelum.proxy.impl;

import javax.persistence.EntityManager;

public class SaveCrudOparation<T> {

    private final EntityManager manager;

    public SaveCrudOparation(EntityManager manager) {
        this.manager = manager;
    }

    public void save(T entity){
        manager.persist(entity);
    }
}
