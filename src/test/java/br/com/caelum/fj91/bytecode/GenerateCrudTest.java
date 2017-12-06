package br.com.caelum.fj91.bytecode;

import br.com.caelum.fj91.bytecode.models.Studenty;
import br.com.caelum.fj91.bytecode.repositories.StudentRepository;
import br.com.caelum.proxy.ProxyCrudInvocationHandler;
import com.sun.jmx.mbeanserver.Repository;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.instrument.ClassDefinition;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.caelum.fj91.bytecode.models.vo.Address.Buider.number;
import static net.bytebuddy.matcher.ElementMatchers.anyOf;
import static net.bytebuddy.matcher.ElementMatchers.isPackagePrivate;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerateCrudTest {


    private ByteBuddy byteBuddy;
    private Studenty fernando;
    private AtomicLong counter;
    private Map<Long, Studenty> database;
    @Before
    public void setup(){
        byteBuddy = new ByteBuddy();


        fernando = new Studenty(1L, "Fernando", number(42)
                                                            .ofStreet("Baker")
                                                                .zipCode("123123")
                                                                    .build());

        counter = new AtomicLong();
        database = new HashMap<>();
    }


    @Test
    public void proxyDinamico() throws IllegalAccessException, InstantiationException {

        EntityManager entityManager = getMockedEntityManager();


        StudentRepositoryDefinition definition = getDefinition();


        Class<?> loaded = byteBuddy
                .subclass(Object.class)
                .implement(definition.getBaseClass())
                .method(anyOf(definition.getMethods()))
                .intercept(InvocationHandlerAdapter.of(new ProxyCrudInvocationHandler<>(definition.getGenericEntityClass(), definition.getGenericIdClass(), entityManager)))
                .make()
                .load(this.getClass().getClassLoader())
                .getLoaded();

        StudentRepository repository = (StudentRepository) loaded.newInstance();

        Studenty save = repository.save(fernando);
        System.out.println("SAVED::::::>" + save);

        Studenty one = repository.findOne(1L);
        System.out.println("ONE:::::>" + one);

        repository.findAll().forEach(System.out::println);


    }

    //Auxiliares para o Teste

    private EntityManager getMockedEntityManager() {
        EntityManager entityManager = mock(EntityManager.class);

        doAnswer(invocationOnMock -> {
            Object in = invocationOnMock.getArguments()[0];

            Studenty studenty = (Studenty) in;

            database.put(counter.incrementAndGet(), studenty);
            return Void.TYPE;
        }).when(entityManager).persist(anyObject());


        when(entityManager.createQuery(anyString())).thenAnswer(invocationOnMock ->
            new Query() {
                @Override
                public List getResultList() {
                    return new ArrayList(database.values());
                }

                @Override
                public Object getSingleResult() {
                    return null;
                }

                @Override
                public int executeUpdate() {
                    return 0;
                }

                @Override
                public Query setMaxResults(int maxResult) {
                    return null;
                }

                @Override
                public int getMaxResults() {
                    return 0;
                }

                @Override
                public Query setFirstResult(int startPosition) {
                    return null;
                }

                @Override
                public int getFirstResult() {
                    return 0;
                }

                @Override
                public Query setHint(String hintName, Object value) {
                    return null;
                }

                @Override
                public Map<String, Object> getHints() {
                    return null;
                }

                @Override
                public <T> Query setParameter(Parameter<T> param, T value) {
                    return null;
                }

                @Override
                public Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
                    return null;
                }

                @Override
                public Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
                    return null;
                }

                @Override
                public Query setParameter(String name, Object value) {
                    return null;
                }

                @Override
                public Query setParameter(String name, Calendar value, TemporalType temporalType) {
                    return null;
                }

                @Override
                public Query setParameter(String name, Date value, TemporalType temporalType) {
                    return null;
                }

                @Override
                public Query setParameter(int position, Object value) {
                    return null;
                }

                @Override
                public Query setParameter(int position, Calendar value, TemporalType temporalType) {
                    return null;
                }

                @Override
                public Query setParameter(int position, Date value, TemporalType temporalType) {
                    return null;
                }

                @Override
                public Set<Parameter<?>> getParameters() {
                    return null;
                }

                @Override
                public Parameter<?> getParameter(String name) {
                    return null;
                }

                @Override
                public <T> Parameter<T> getParameter(String name, Class<T> type) {
                    return null;
                }

                @Override
                public Parameter<?> getParameter(int position) {
                    return null;
                }

                @Override
                public <T> Parameter<T> getParameter(int position, Class<T> type) {
                    return null;
                }

                @Override
                public boolean isBound(Parameter<?> param) {
                    return false;
                }

                @Override
                public <T> T getParameterValue(Parameter<T> param) {
                    return null;
                }

                @Override
                public Object getParameterValue(String name) {
                    return null;
                }

                @Override
                public Object getParameterValue(int position) {
                    return null;
                }

                @Override
                public Query setFlushMode(FlushModeType flushMode) {
                    return null;
                }

                @Override
                public FlushModeType getFlushMode() {
                    return null;
                }

                @Override
                public Query setLockMode(LockModeType lockMode) {
                    return null;
                }

                @Override
                public LockModeType getLockMode() {
                    return null;
                }

                @Override
                public <T> T unwrap(Class<T> cls) {
                    return null;
                }
            });

        when(entityManager.find(anyObject(), anyObject())).thenAnswer(invocationOnMock -> {

            Object in = invocationOnMock.getArguments()[1];

            if (in.getClass().isAssignableFrom(Long.class)) {
                Long id = (Long) in;

                return database.get(id);
            }

            throw new IllegalArgumentException();
        });
        return entityManager;
    }

    StudentRepositoryDefinition getDefinition() {
        return new StudentRepositoryDefinition(StudentRepository.class);
    }

    private class StudentRepositoryDefinition {
        private final Class<StudentRepository> clazz;
        private final ParameterizedType parameterizedType;

        StudentRepositoryDefinition(Class<StudentRepository> clazz) {
            this.clazz = clazz;
            Type type = clazz.getGenericInterfaces()[0];

            parameterizedType = (ParameterizedType) type;
        }

        Class<?> getBaseClass() {
            return clazz;
        }

        Method[] getMethods() {
            return clazz.getMethods();
        }

        Class<?> getGenericEntityClass() {
            return (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }

        Class<?> getGenericIdClass() {
            return (Class<?>) parameterizedType.getActualTypeArguments()[1];
        }
    }
}
