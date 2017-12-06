package br.com.caelum.br.com.caelum.proxy.impl;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JPACrudCordinate<T, ID> {


    private final Class<T> entityClass;
    private final Class<ID> idClass;
    private final EntityManager manager;

    public JPACrudCordinate(Class<T> entityClass, Class<ID> idClass, EntityManager manager) {
        this.entityClass = entityClass;
        this.idClass = idClass;
        this.manager = manager;
    }

    public Object invoke(Method method, Object[] args) {
        String name = method.getName();

        if ("save".equals(name)){


            Object in = args[0];

            if (in.getClass().isAssignableFrom(entityClass)){

                T entity = (T) in;

                new SaveCrudOparation<T>(manager).save(entity);

                return entity;
            }

            throw new IllegalArgumentException("Deu Ruim!");
        }


        if ("findOne".equals(name)){
            Object in = args[0];

            if (in.getClass().isAssignableFrom(idClass)) {

                ID id = (ID) in;

                return new FindOneOperation<T, ID>(entityClass,manager).findOne(id);
            }

            throw new IllegalArgumentException("Deu Ruim!");
        }


        return new FindAllOperation<T>(entityClass,manager).findAll();
    }
}
