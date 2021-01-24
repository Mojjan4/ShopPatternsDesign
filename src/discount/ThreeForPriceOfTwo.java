package discount;

import cart.ShoppingCartItem;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ThreeForPriceOfTwo implements DiscountInterface {
    @Override
    public BigDecimal discount(ArrayList<ShoppingCartItem> items) {
        var sum = BigDecimal.ZERO;
        int quantity = 0;
        BigDecimal cheapest = items.get(0).itemCost();

        for (var item: items) {
            for (int i = 0; i < item.quantity(); i++) {
                quantity++;
                sum = item.itemCost().add(sum);

                if (item.itemCost().intValue() < cheapest.intValue()) {
                    cheapest = item.itemCost();
                }
            }
        }
        if (quantity > 2) {
            sum = sum.subtract(cheapest);
        }
        System.out.println(sum);
        return sum;
    }
}
