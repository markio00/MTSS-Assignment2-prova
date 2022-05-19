package it.unipd.mtss.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;

public class ConcreteBillTest {
    
    private ConcreteBill bill;
    private User user;
    private Vector<EItem> list;
    
    
    @Test
    public void testEmptyOrder() {
        this.bill = new ConcreteBill();

        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        this.list = new Vector<EItem>();

        try {

            double price = bill.getOrderPrice(list, user, LocalTime.of(1, 0, 0));
    
            assertEquals(2, price, 0);

        } catch(BillException ex) {
            ex.printStackTrace();
            fail("Carrello superiore a 3 elementi");
        }
    }


    @Test
    public void testTotalPriceSumWithoutCaveats() {
        this.bill = new ConcreteBill();

        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        this.list = new Vector<EItem>();

        this.list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 130.00));
        this.list.add(new EItem(ItemType.Processor, "Athlon", 45.99));

        try {

            double price = bill.getOrderPrice(list, user, LocalTime.of(1, 0, 0));
    
            assertEquals(175.99, price, 0);

        } catch(BillException ex) {
            ex.printStackTrace();
            fail("Carrello superiore a 3 elementi");
        }
    }


    @Test
    public void giftCheapestMouseIfMoreThan10() {
        this.bill = new ConcreteBill();

        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        this.list = new Vector<EItem>();

        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
        this.list.add(new EItem(ItemType.Mouse, "G502", 8.00));
        this.list.add(new EItem(ItemType.Processor, "Elder SKU", 5.00));

        try {

            double price = bill.getOrderPrice(list, user, LocalTime.of(1, 0, 0));
    
            assertEquals(103.00, price, 0);

            // do not gift if less os equal than 10 mouses in order
            this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
            price = bill.getOrderPrice(list, user, LocalTime.of(1, 0, 0));

            assertEquals(105.00, price, 0);

        } catch(BillException ex) {
            ex.printStackTrace();
            fail("Carrello superiore a 3 elementi");
        }
    }

}
