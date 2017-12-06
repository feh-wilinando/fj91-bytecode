package br.com.caelum.fj91.bytecode;

import br.com.caelum.fj91.bytecode.operations.Fibonacci;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class FiboTest {



    @Parameters(name = "{index}: fib({0})={1}")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][] { {1,1}, {2,1}, {3,2} });
    }

    @Parameter
    public Integer position;


    @Parameter(1)
    public Integer result;


    @Test
    public void deveRetornar1ParaAPrimeiraPosiçãoDaSequencia(){

        Fibonacci fibonacci = new Fibonacci();

        Integer value = fibonacci.getPosition(position);

        assertThat(value, is(result));
    }


}
