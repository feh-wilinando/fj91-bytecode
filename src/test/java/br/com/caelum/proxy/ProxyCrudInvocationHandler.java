package br.com.caelum.proxy;

import br.com.caelum.br.com.caelum.proxy.impl.JPACrudCordinate;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyCrudInvocationHandler<T, ID>  implements InvocationHandler {
    private final JPACrudCordinate<T, ID> cordinate;


    public ProxyCrudInvocationHandler(Class<T> entityClass, Class<ID> idClass, EntityManager manager) {
        cordinate = new JPACrudCordinate<>(entityClass, idClass, manager);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return cordinate.invoke(method, args);
    }
}
