package it.unipd.mtss.business.exception;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BillExceptionTest {
    @Test
    public void billExceptionTest() {
        assertEquals("Road to 100%",new BillException("Road to 100%").getMessage());
    }
}
