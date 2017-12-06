package br.com.caelum.br.com.caelum.proxy.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FindAllOperation<T> {
    private final Class<T> entityClass;
    private final EntityManager manager;

    public FindAllOperation(Class<T> entityClass, EntityManager manager) {
        this.entityClass = entityClass;
        this.manager = manager;
    }

    public List<T> findAll() {

        return manager.createQuery("select t from Studenty t").getResultList();
    }
}
