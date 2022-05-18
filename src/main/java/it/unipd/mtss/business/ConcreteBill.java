////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import it.unipd.mtss.model.User;
import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ConcreteBill implements Bill{
    
    static final int presents = 10;
    static Vector<User> luckyUsers = new Vector<User>(10);

    public double getOrderPrice(List<EItem> itemsOrdered, User user, 
    LocalTime date) throws BillException {
        
        max30Products(itemsOrdered);

        if(lottery(user, date)){
            return 0;
        }
        
        double total = getTotalPrice(itemsOrdered);

        return total - 
        getDiscount5Processors(itemsOrdered) - 
        getDiscount10Mouse(itemsOrdered) -
        getDiscountSameMouseKeyboards(itemsOrdered) -
        get100Discount(total) +
        getCommission2(total);
    }

    public boolean lottery(User user, LocalTime currentTime) {

        int time = Integer.parseInt(DateTimeFormatter.ofPattern("HHmm").
        format(currentTime));
        
        if(time >= 1800 && time <= 1900){
                
            if(!luckyUsers.contains(user) && presents > luckyUsers.size() &&
            user.getBirthDate().compareTo(LocalDate.now().minusYears(18)) > 0){

                Random rand = new Random();
                int randomino = rand.nextInt(10); //0-9
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

    public double getTotalPrice(List<EItem> itemsOrdered) {

        double tot = 0;

        for(EItem item : itemsOrdered) {
            tot += item.getPrice();
        }

        return tot;
    }

    public double getDiscount5Processors(List<EItem> itemsOrdered) {
        int processorsFound = 0;
        double minPrice = Double.MAX_VALUE;

        for(EItem item : itemsOrdered){
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

    public double getDiscount10Mouse(List<EItem> itemsOrdered) {
        int mouseFound = 0;
        double minPrice = Double.MAX_VALUE;

        for(EItem item : itemsOrdered){
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

    public double get100Discount(double totalPrice)  {
        if(totalPrice > 1000) {
            return totalPrice/10;
        }

        return 0;
    }

    public double getDiscountSameMouseKeyboards(List<EItem> itemsOrdered) {
        
        int matchFound = 0;
        double minPrice = Double.MAX_VALUE;

        for(EItem item : itemsOrdered){
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

    public double getCommission2(double totalPrice) {
        if(totalPrice < 10) {
            return 2.0;
        }

        return 0;
    }
    public void max30Products(List<EItem> itemsOrdered) throws BillException{
        // Act
        int count = itemsOrdered.size();
        // Assert

        if (count > 30){
            throw new BillException("Mannaggia");
        }
        
    }
    

    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
    throws BillException {
        return getOrderPrice(itemsOrdered, user, LocalTime.of(15, 00, 00));
    }

}
