package it.unipd.mtss.business;

import java.time.LocalDate;
import java.util.Vector;

import org.junit.Before;

import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;

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
        this.list.add(new EItem(ItemType.Mouse, "crappy", 10));
        this.list.add(new EItem(ItemType.Keyboard, "Ozone Stike Pro", 100));

    }
    
    
}
