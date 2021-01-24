package discount;


import java.math.BigDecimal;
import java.util.ArrayList;

import cart.ShoppingCartItem;

public interface DiscountInterface {
    BigDecimal discount(ArrayList<ShoppingCartItem> items);
}
