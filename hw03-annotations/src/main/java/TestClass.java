import annotations.After;
import annotations.Before;
import annotations.Test;

public class TestClass {
    @Before
    public void before() {
        System.out.println("Before-0");
        //  throw new RuntimeException("hi im before exception");
    }

    @Test
    public void test() {
        System.out.println("Test-0");
    }

    @Test
    public void test1() {
        System.out.println("Test-1");
    }

    @Test
    public void test4() {
        throw new UnsupportedOperationException("this method not worked");
    }

    @Test
    public void test2() {
        throw new IllegalCallerException("its exception");
    }

    public void test3() {
        System.out.println("И не должен работать");
    }

    @After
    public void after() {
        System.out.println("After-0");
        //   throw new RuntimeException("hi im after exception");
    }

    @Test
    public void test1000() {
        System.out.println("123123");
    }
}
