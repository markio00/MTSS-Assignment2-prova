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
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Keyboard, "Ozone Stike Pro", 100));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "crappy", 20));
        this.list.add(new EItem(ItemType.Keyboard, "super crappy", 5));
    }

    @Test
    public void testGetOrderPrice() {
        // Act
        double price = bill.getOrderPrice(list, user);

        // Assert
        assertEquals(price, 1191.391, 0);

        price = bill.getOrderPrice(new Vector<EItem>(), user);
        assertEquals(0, price, 0);
    }

    @Test
    public void testGetTotalPrice() {
        // Act
        double tot = bill.getTotalPrice(list);

        // Assert
        assertEquals(tot, 1730.99, 0);

        tot = bill.getTotalPrice(new Vector<EItem>());
        assertEquals(0, tot, 0);
    }

    @Test
    public void testGetDiscount5Processors() {
        // Act
        double discount = bill.getDiscount5Processors(list);

        // Assert
        assertEquals(100, discount,  0);

        discount = bill.getDiscount5Processors(new Vector<EItem>());
        assertEquals(0, discount, 0);
    }

    @Test
    public void testGetDiscount10Mouse() {
        // Act
        double discount = bill.getDiscount10Mouse(list);

        // Assert
        assertEquals(10, discount,  0);

        discount = bill.getDiscount10Mouse(new Vector<EItem>());
        assertEquals(0, discount, 0);
    }

    @Test
    public void testGet1000Discount() {
        // Act
        double tot = bill.getTotalPrice(list);
        double discount = bill.get100Discount( tot);

        // Assert
        assertEquals(144.599, discount, 0);

        assertEquals(0, bill.get100Discount(0), 0);
    }
    
    @Test
    public void testGetDiscountSameMouseKeyboards() {
        // Act
        double discount = bill.getDiscountSameMouseKeyboards(list);

        // Assert
        assertEquals(2.5, discount,  0);

        discount = bill.getDiscountSameMouseKeyboards(new Vector<EItem>());
        assertEquals(0, discount, 0);

        Vector<EItem> v = new Vector<>(list);
        v.add(new EItem(ItemType.Keyboard, "crappy", 20));
        discount = bill.getDiscountSameMouseKeyboards(v);
        assertEquals(0, discount, 0);
    }
}
