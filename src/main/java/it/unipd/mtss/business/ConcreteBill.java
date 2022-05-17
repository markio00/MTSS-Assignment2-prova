////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import it.unipd.mtss.model.User;
import it.unipd.mtss.model.EItem;

import java.util.List;

public class ConcreteBill implements Bill{
    
    public double getOrderPrice(List<EItem> itemsOrdered, User user) {

        double totale = 0;

        for(EItem item : itemsOrdered) {
            totale += item.getPrice();
        }

        return totale;
    }

    

}
