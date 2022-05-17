package it.unipd.mtss.business;

import it.unipd.mtss.model.User;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;

import java.util.Vector;
import java.util.Date;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ConcreteBillTest {

    private ConcreteBill bill;
    private User user;
    private Vector<EItem> list;

    @Before
    public void testDataSetup() {
        // Arrange
        this.bill = new ConcreteBill();

        this.user = new User("user1", "Mario", "Rossi", new Date(0));

        this.list = new Vector<EItem>();
        this.list.add(new EItem(ItemType.Processor, "R7 5700X", 300.00));
        this.list.add(new EItem(ItemType.Processor, "R5 5600X", 200.00));
        this.list.add(new EItem(ItemType.Processor, "R7 5700X", 300.00));
        this.list.add(new EItem(ItemType.Processor, "R5 5600X", 200.00));
        this.list.add(new EItem(ItemType.Processor, "R7 5700X", 300.00));
        
        this.list.add(new EItem(ItemType.Mouse, "G502", 45.99));
    }

    @Test
    public void testGetOrderPrice() {
        // Act
        double price = bill.getOrderPrice(list, user);

        // Assert
        assertEquals(price, 1245.99, 0);
    }

    @Test
    public void testGetTotalPrice() {
        // Act
        double tot = bill.getTotalPrice(list);

        // Assert
        assertEquals(tot, 1345.99, 0);
    }

    @Test
    public void testGetDiscount5Processors() {
        // Act
        double discount = bill.getDiscount5Processors(list);

        // Assert
        assertEquals(discount, 100, 0);
    }
}
