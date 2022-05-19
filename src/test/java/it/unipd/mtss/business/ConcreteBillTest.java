package it.unipd.mtss.business;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

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
        
        // Set time of purchase out of range for random gifted order
        LocalTime time = LocalTime.of(1, 0, 0);
        
        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        this.list = new Vector<EItem>();
        
        try {
            
            double price = bill.getOrderPrice(list, user, time);
            
            assertEquals(2, price, 0);
            
        } catch(BillException ex) {
            ex.printStackTrace();
            fail("Carrello superiore a 30 elementi");
        }
    }
    
    
    @Test
    public void testTotalPriceSumWithoutCaveats() {
        this.bill = new ConcreteBill();
        
        // Set time of purchase out of range for random gifted order
        LocalTime time = LocalTime.of(1, 0, 0);
        
        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        this.list = new Vector<EItem>();
        
        this.list.add(new EItem(ItemType.Keyboard, "K70 MX Brown", 130.00));
        this.list.add(new EItem(ItemType.Processor, "Athlon", 45.99));
        
        try {
            
            double price = bill.getOrderPrice(list, user, time);
            
            assertEquals(175.99, price, 0);
            
        } catch(BillException ex) {
            ex.printStackTrace();
            fail("Carrello superiore a 30 elementi");
        }
    }
    
    @Test
    public void testDiscount5Processors(){
        
        try {
            bill = new ConcreteBill();
            user = new User("user1", "Mario", "Rossi", LocalDate.of(2000,04,19));
            list = new Vector<EItem>();
            // Set time of purchase out of range for random gifted order
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
    
    
    @Test
    public void giftCheapestMouseIfMoreThan10() {
        this.bill = new ConcreteBill();
        
        // Set time of purchase out of range for random gifted order
        LocalTime time = LocalTime.of(1, 0, 0);
        
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
            
            double price = bill.getOrderPrice(list, user, time);
            
            assertEquals(103.00, price, 0);
            
            // do not gift if less os equal than 10 mouses in order
            this.list.add(new EItem(ItemType.Mouse, "G502", 10.00));
            price = bill.getOrderPrice(list, user, time);
            
            assertEquals(105.00, price, 0);
            
        } catch(BillException ex) {
            ex.printStackTrace();
            fail("Carrello superiore a 30 elementi");
        }
    }
    
    @Test
    public void discount10percentIFTotalOver1000() {
        this.bill = new ConcreteBill();
        
        // Set time of purchase out of range for random gifted order
        LocalTime time = LocalTime.of(1, 0, 0);
        
        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        this.list = new Vector<EItem>();
        
        this.list.add(new EItem(ItemType.Processor, "R9 5950X", 500.00));
        this.list.add(new EItem(ItemType.Processor, "i9 12900K", 500.00));
        
        try {
            
            double price = bill.getOrderPrice(list, user, time);
            
            assertEquals(1000.00, price, 0);
            
            // chacking critical bound
            this.list.add(new EItem(ItemType.Mouse, "G502", 0.01));
            price = bill.getOrderPrice(list, user, time);
            
            assertEquals(900.009, price, 0);
            
        } catch(BillException ex) {
            ex.printStackTrace();
            fail("Carrello superiore a 30 elementi");
        }
        
    }
    
    
    @Test
    public void maxOrderSize30() {
        this.bill = new ConcreteBill();
        
        // Set time of purchase out of range for random gifted order
        LocalTime time = LocalTime.of(1, 0, 0);
        
        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
        this.list = new Vector<EItem>();
        
        this.list.add(new EItem(ItemType.Motherboard, "article1", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article2", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article3", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article4", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article5", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article6", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article7", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article8", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article9", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article10", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article11", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article12", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article13", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article14", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article15", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article16", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article17", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article18", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article19", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article20", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article21", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article22", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article23", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article24", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article25", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article26", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article27", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article28", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article29", 1.00));
        this.list.add(new EItem(ItemType.Motherboard, "article30", 1.00));
        
        try {
            
            double price = bill.getOrderPrice(list, user, time);
            
            assertEquals(30, price, 0);
            
            // chacking critical bound
            this.list.add(new EItem(ItemType.Motherboard, "article31", 1.00));
            price = bill.getOrderPrice(list, user, time);
            
            fail("Mode than 30 items are not allowed for one order");
            
        } catch(BillException ex) {
            ex.printStackTrace();
            assertTrue(true);
        }
    }
    
    @Test
    public void testDiscountSameMouseKeyboards(){
        try {
            this.bill = new ConcreteBill();
            
            // Set time of purchase out of range for random gifted order
            LocalTime time = LocalTime.of(1, 0, 0);
            
            this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
            this.list = new Vector<EItem>();
            double price;
            
            this.list.add(new EItem(ItemType.Mouse, "article1", 100.00));
            this.list.add(new EItem(ItemType.Keyboard, "article2", 100.00));
            
            price = bill.getOrderPrice(list, user, time);
            assertEquals(150, price, 0);
            
            this.list.add(new EItem(ItemType.Keyboard, "article2", 100.00));
            
            price = bill.getOrderPrice(list, user, time);
            assertEquals(300, price, 0);
            
        } catch (BillException e) {
            e.printStackTrace();
            fail("Carrello superiore a 30 elementi");
        }   
    }
    
    @Test
    public void testLottery() {
        try {
            this.bill = new ConcreteBill();
            LocalTime time = LocalTime.of(18, 15, 0);
            this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2020, 04, 19));
            this.list = new Vector<EItem>();
            double price = Double.MAX_VALUE; 
            this.list.add(new EItem(ItemType.Mouse, "article1", 100.00));
            
            //TEST LOTTERIA 
            for(int i = 0; i < 100; ++i){
                price = Math.min(bill.getOrderPrice(list, user, time), price);
            }
            assertEquals(0, price, 0);
            
            //TEST LISTA PIENA
            price = Double.MAX_VALUE;
            while(ConcreteBill.luckyUsers.size() < 10){
                ConcreteBill.luckyUsers.add(new User("user2", "Mario", "Rossi", LocalDate.of(2000, 04, 19)));
            }         
            for(int i = 0; i < 100; ++i){
                price = Math.min(bill.getOrderPrice(list, user, time), price);
            }
            assertEquals(100, price, 0);
            
            //TEST MAGGIORENNE
            this.user = new User("user2", "Mario", "Rossi", LocalDate.of(2000, 04, 19));
            price = Double.MAX_VALUE;
            for(int i = 0; i < 30; ++i){
                price = Math.min(bill.getOrderPrice(list, user, time), price);
            }
            assertEquals(100, price, 0);
            
            //TEST PRIMA DELLE 18
            time = LocalTime.of(16, 15, 0);
            price = Double.MAX_VALUE;
            ConcreteBill.luckyUsers.clear();
            for(int i = 0; i < 30; ++i){
                price = Math.min(bill.getOrderPrice(list, user, time), price);
            }
            assertEquals(100, price, 0);
            
            //TEST OLTRE LE 19
            time = LocalTime.of(23, 15, 0);
            price = Double.MAX_VALUE;
            ConcreteBill.luckyUsers.clear();
            for(int i = 0; i < 30; ++i){
                price = Math.min(bill.getOrderPrice(list, user, time), price);
            }
            assertEquals(100, price, 0);

        } catch (BillException e) {
            e.printStackTrace();
            fail("Carrello superiore a 30 elementi");
        }   
    }
}
