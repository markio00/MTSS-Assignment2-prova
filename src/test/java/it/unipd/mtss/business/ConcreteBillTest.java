package it.unipd.mtss.business;

import it.unipd.mtss.model.User;
import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;

import java.util.Vector;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2010,04,19));

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
        double price = Double.MAX_VALUE;
        try {
            // Act
            price = bill.getOrderPrice(list, user);

            // Assert
            assertEquals(price, 1445.391, 0);
            price = bill.getOrderPrice(list, user, LocalTime.of(23, 12, 00));
            assertEquals(price, 1445.391, 0);
            price = bill.getOrderPrice(new Vector<EItem>(), user);
            assertEquals(2, price, 0);
            price = Double.MAX_VALUE;
            for(int i = 0; i < 100; ++i){
                price = Math.min(price, bill.getOrderPrice(list, user, LocalTime.of(18, 12, 00)));
            }
            assertEquals(0, price, 0);
        }catch (BillException e) {
            e.printStackTrace();
            fail("Carrello ciccione");
        }               
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
        assertEquals(173.099, discount, 0);

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

    @Test
    public void testGetCommission2() {
        // Act 
        double discountLT = bill.getCommission2(9);
        double discountEQ = bill.getCommission2(10);
        double discountGT = bill.getCommission2(11);

        // Assert
        assertEquals(2, discountLT, 0);
        assertEquals(0, discountEQ, 0);
        assertEquals(0, discountGT, 0);
    }

    @Test
    public void testMax30Products(){
        try {
            bill.max30Products(list);
        } catch (BillException e) {
            assertEquals(e.getMessage(), "Mannaggia");
        }
        
        try {
            bill.max30Products(new Vector<EItem>());
        } catch (BillException e) {
            assertEquals(e.getMessage(), "Mannaggia");
        }

        Vector<EItem> test = new Vector<EItem>();
        for (int i = 0; i < 50; ++i){
            test.add(new EItem(ItemType.Processor, "test", 1));
        }

        try {
            bill.max30Products(test);
        } catch (BillException e) {
            assertEquals(e.getMessage(), "Mannaggia");
        }
    }
    @Test
    public void testLottery(){
        boolean result = true;
        for(int i = 0; i<100; ++i){
            result = result && bill.lottery(user,LocalTime.of(17, 05, 00));
        }
        assertEquals(result, false);



        result = true;
        for(int i = 0; i<50; ++i){
            result = result && bill.lottery(new User("robo", "name", "surrname", 
                    LocalDate.of(1990, 01, 01)),LocalTime.of(17, 05, 00));
        }
        assertEquals(result, false);

                result = true;
        for(int i = 0; i<50; ++i){
            result = result && bill.lottery(new User("robo", "name", "surrname", 
                    LocalDate.of(1990, 01, 01)),LocalTime.of(18, 05, 00));
        }
        assertEquals(result, false);

        result = false;
        for(int i = 0; i<50; ++i){
            result = result || bill.lottery(new User("robo", "name", "surrname", 
                    LocalDate.of(2012, 01, 01)),LocalTime.of(18, 05, 00));
        }
        assertEquals(result, true);

        result = false;
        ConcreteBill.luckyUsers.clear();
        for(int i = 0; i<10; ++i){
            ConcreteBill.luckyUsers.add(user);
        }
        result = bill.lottery(new User("robo", "name", "surrname", 
                LocalDate.of(1990, 01, 01)),LocalTime.of(18, 05, 00));
        assertEquals(result, false);
    }
}
