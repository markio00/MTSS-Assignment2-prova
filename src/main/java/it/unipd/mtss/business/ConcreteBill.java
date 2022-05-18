////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import it.unipd.mtss.model.User;
import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;

import java.util.List;

public class ConcreteBill implements Bill{
    
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
    throws BillException {
        
        max30Products(itemsOrdered);
        double total = getTotalPrice(itemsOrdered);

        return total - 
        getDiscount5Processors(itemsOrdered) - 
        getDiscount10Mouse(itemsOrdered) -
        getDiscountSameMouseKeyboards(itemsOrdered) -
        get100Discount(total);
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

    public void max30Products(List<EItem> itemsOrdered) throws BillException{
        // Act
        int count = itemsOrdered.size();
        // Assert
        if (count > 30){
            throw new BillException("Mannaggia");
        }
        
    }
    
}
