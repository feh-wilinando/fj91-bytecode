package br.com.caelum.fj91.bytecode;


import br.com.caelum.fj91.bytecode.models.Studenty;
import br.com.caelum.fj91.bytecode.models.vo.Address;
import br.com.caelum.fj91.bytecode.repositories.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static br.com.caelum.fj91.bytecode.models.vo.Address.Buider.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockTest {


    private StudentRepository repository;
    private Studenty studenty;


    @Before
    public void setup(){
        repository = mock(StudentRepository.class);

        studenty = mock(Studenty.class);

        LocalTime.of(13,23);
        LocalDate.of(2017,5,10);

        LocalDate xpto = DateBuilder.day(5)
                                    .month(Month.NOVEMBER)
                                    .year(2017)
                                    .build();



    }


    @Test
    public void deveRetornarUmaListaVazia(){


        Address address = number(42)
                .ofStreet("Baker")
                .zipCode("123123")
                .build();


//        anyString();
        when(studenty.getAddress()).thenReturn(address);

        when(studenty.getZipCode()).thenCallRealMethod();


        studenty.getZipCode();


        verify(studenty, times(3)).getAddress();

    }



    @Test
    public void test(){

        LocalTime time = LocalTime.of(13, 30);
        LocalTime time2 = LocalTime.of(13, 31);


        System.out.println(time.isAfter(time));
        System.out.println(time.isBefore(time));
        System.out.println(time.equals(time));

        assertFalse(time.isAfter(time));

    }


    static class DateBuilder {


        private int day;
        private int month;
        private int year;

        public static DayPart day(int day){
            return new DayPart(day);
        }

    }




    private static class YearPart {
        private final int year;
        private final int month;
        private final int day;

        private YearPart(int year, int month, int day) {

            this.year = year;
            this.month = month;
            this.day = day;
        }

        public LocalDate build() {
            return LocalDate.of(year, month, day);
        }
    }

    private static class MonthPart {

        private final int day;
        private final int month;

        private MonthPart(int day, int month) {
            this.day = day;
            this.month = month;
        }

        public YearPart year(int year){
            return new YearPart(year, month, day);
        }
    }

    private static class DayPart {
        private final int day;

        private DayPart(int day) {
            this.day = day;
        }

        public MonthPart month(int month) {
            return new MonthPart(day, month);
        }

        public MonthPart month(Month month){
            return new MonthPart(day, month.getValue());
        }
    }
}
