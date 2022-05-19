////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;

public class ConcreteBill implements Bill{
    
    static final int presents = 10;
    static Vector<User> luckyUsers = new Vector<User>(10);
    
    public double getOrderPrice(final List<EItem> itemsOrdered, 
    final User user, 
    final LocalTime date) throws BillException {
        
        max30Products(itemsOrdered);
        
        if(lottery(user, date)){
            return 0;
        }
        
        final double total = getTotalPrice(itemsOrdered);
        
        return total - 
        getDiscount5Processors(itemsOrdered) - 
        getDiscount10Mouse(itemsOrdered) -
        getDiscountSameMouseKeyboards(itemsOrdered) -
        get100Discount(total) +
        getCommission2(total);
    }
    
    private boolean lottery(final User user, final LocalTime currentTime) {
        
        final int time = Integer.parseInt(DateTimeFormatter.ofPattern("HHmm").
        format(currentTime));
        
        if(time >= 1800 && time <= 1900){
            
            if(!luckyUsers.contains(user) && presents > luckyUsers.size() &&
            user.getBirthDate().compareTo(LocalDate.now().minusYears(18)) > 0){
                
                final Random rand = new Random();
                final int randomino = rand.nextInt(10); //0-9
                //10%
                if(randomino == 0){
                    luckyUsers.add(user);
                    return true;
                }
            }
        }   
        else{
            luckyUsers.clear();
            return false;
        }      
        return false;
        
    }
    
    private double getTotalPrice(final List<EItem> itemsOrdered) {
        
        double tot = 0;
        
        for(final EItem item : itemsOrdered) {
            tot += item.getPrice();
        }
        
        return tot;
    }
    
    private double getDiscount5Processors(final List<EItem> itemsOrdered) {
        int processorsFound = 0;
        double minPrice = Double.MAX_VALUE;
        
        for(final EItem item : itemsOrdered){
            if(item.getItemType() == ItemType.Processor){
                minPrice = Math.min(minPrice, item.getPrice());
                ++processorsFound;
            }
        }
        
        if(processorsFound >= 5){
            return minPrice/2;
        }
        
        return 0;
    }
    
    private double getDiscount10Mouse(final List<EItem> itemsOrdered) {
        int mouseFound = 0;
        double minPrice = Double.MAX_VALUE;
        
        for(final EItem item : itemsOrdered){
            if(item.getItemType() == ItemType.Mouse){
                minPrice = Math.min(minPrice, item.getPrice());
                ++mouseFound;
            }
        }
        
        if(mouseFound > 10){
            return minPrice;
        }
        
        return 0;
    }
    
    private double get100Discount(final double totalPrice)  {
        if(totalPrice > 1000) {
            return totalPrice/10;
        }
        
        return 0;
    }
    
    private double getDiscountSameMouseKeyboards
    (final List<EItem> itemsOrdered) {
        
        int matchFound = 0;
        double minPrice = Double.MAX_VALUE;
        
        for(final EItem item : itemsOrdered){
            if(item.getItemType() == ItemType.Mouse){
                minPrice = Math.min(minPrice, item.getPrice());
                ++matchFound;
            }
            if(item.getItemType() == ItemType.Keyboard){
                minPrice = Math.min(minPrice, item.getPrice());
                --matchFound;
            }
        }
        
        if(matchFound == 0 && !itemsOrdered.isEmpty()){
            return minPrice/2;
        }
        
        return 0;
    }
    
    private double getCommission2(final double totalPrice) {
        if(totalPrice < 10) {
            return 2.0;
        }
        
        return 0;
    }
    private void max30Products(final List<EItem> itemsOrdered) 
    throws BillException{
        // Act
        final int count = itemsOrdered.size();
        // Assert
        
        if (count > 30){
            throw new BillException("Mannaggia");
        }
        
    }
    
    
}
