package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;

public class ConcreteBillTest {
    
    private ConcreteBill bill;
    private User user;
    private Vector<EItem> list;
    
    
    
    @Test
    public void testDiscount5Processors(){
        try {
            bill = new ConcreteBill();
            user = new User("user1", "Mario", "Rossi", LocalDate.of(2000,04,19));
            list = new Vector<EItem>();
            LocalTime time = LocalTime.of(10, 0, 0);
            
            list.add(new EItem(ItemType.Processor, "R7 5700X", 100.00));
            list.add(new EItem(ItemType.Processor, "R7 5700X", 100.00));

            assertEquals(200, bill.getOrderPrice(list, user, time),0);

            list.add(new EItem(ItemType.Processor, "R7 5700X", 100.00));
            list.add(new EItem(ItemType.Processor, "R7 5700X", 100.00));
            list.add(new EItem(ItemType.Processor, "R7 5700X", 100.00));

            assertEquals(450, bill.getOrderPrice(list, user, time),0);

            list.add(new EItem(ItemType.Processor, "Celeron", 30.00));

            assertEquals(515, bill.getOrderPrice(list, user, time),0);

            list.add(new EItem(ItemType.Motherboard, "b310", 30.00));

            assertEquals(545, bill.getOrderPrice(list, user, time),0);


        } catch (BillException e) {
            e.printStackTrace();
            fail("Carrello superiore a 30 elementi");
        }
    }
}
