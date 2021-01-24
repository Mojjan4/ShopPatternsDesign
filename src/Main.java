import discount.*;

import cart.Product;
import cart.ShoppingCartItem;
import cart.ShoppingCart;

import command.CommandControl;

public class Main {

    public static void main(String[] args) {
        CommandControl manager = new CommandControl();
        ShoppingCart shoppingCart = new ShoppingCart(manager);

        ShoppingCartItem item1 = new ShoppingCartItem(new Product("Bröd"), 32, 4, manager);
        ShoppingCartItem item2 = new ShoppingCartItem(new Product("Broccoli"), 25, 2, manager);
        ShoppingCartItem item3 = new ShoppingCartItem(new Product("Mjölk"), 20, 3, manager);
        ShoppingCartItem item4 = new ShoppingCartItem(new Product("Räkor"),79,1, manager);
        ShoppingCartItem item5 = new ShoppingCartItem(new Product("Lax"),199,1, manager);

        // Adding item to the cart.
        shoppingCart.addCartItem(item1);
        shoppingCart.addCartItem(item2);
        shoppingCart.addCartItem(item3);
        shoppingCart.addCartItem(item4);
        shoppingCart.addCartItem(item5);

        // Discounts available
        shoppingCart.addDiscount(new CheapestForFree());
        shoppingCart.addDiscount(new ThreeForPriceOfTwo());
        shoppingCart.addDiscount(new DiscountOver500());

        // Remove and add items.
        shoppingCart.undo();
        shoppingCart.undo();
        shoppingCart.redo();

        // Displays the receipt
        System.out.println(shoppingCart.receipt());
    }
}
