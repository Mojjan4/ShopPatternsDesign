package cart;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import command.CommandInterFace;
import command.CommandControl;
import discount.DiscountInterface;

public class ShoppingCart {
     final ArrayList<ShoppingCartItem> items = new ArrayList<>();
     final ArrayList<DiscountInterface> discounts = new ArrayList<>();
     final CommandControl command;
    BigDecimal bestDiscount = BigDecimal.ZERO;

    public ShoppingCart(CommandControl command) {
        this.command = command;
    }

    public void addCartItem(ShoppingCartItem item) {
        items.add(item);

        CommandInterFace undoCommand = new CommandInterFace() {
            @Override
            public void execute() {
                items.remove(item);
            }

            @Override
            public void redo() {
                items.add(item);
            }
        };
        command.addToUndo(undoCommand);
    }

    public void removeCartItem(ShoppingCartItem item) {
        items.remove(item);
        CommandInterFace addCommand = new CommandInterFace() {
            @Override
            public void execute() {
                items.add(item);
            }

            @Override
            public void redo() {
                items.remove(item);
            }
        };
        command.addToUndo(addCommand);
    }

    public ArrayList<ShoppingCartItem> getItems() {
        return items;
    }

    public BigDecimal calculatePrice() {
        var sum = BigDecimal.ZERO;
        bestDiscount = BigDecimal.ZERO;

        for (var item : items) {
            sum = item.itemCost().multiply(BigDecimal.valueOf(item.quantity())).add(sum);
        }
        for (var discount : discounts) {
            var totalDiscount = discount.discount(items);
            if (sum.subtract(totalDiscount).intValue() > bestDiscount.intValue()) {
                bestDiscount = sum.subtract(totalDiscount);
            }
        }
        return sum.subtract(bestDiscount);
    }

    public void addDiscount(DiscountInterface discount) {
        discounts.add(discount);
    }

    public String receipt() {
        String line = "--------------------------------\n";
        StringBuilder sb = new StringBuilder();
        sb.append(line);
        var list = items.stream()
                .sorted(Comparator.comparing(item -> item.product().name()))
                .collect(Collectors.toList());
        for (var each : list) {
            sb.append(String.format("%-24s % 7.2f \nquantity: %d%n\n", each.product().name(), each.itemCost(), each.quantity()));
        }
        sb.append(line);
        sb.append(String.format("%24s% 8.2f", "TOTAL:", calculatePrice()));
        sb.append(String.format("\n%24s% 8.2f", "DISCOUNT:", bestDiscount.multiply(BigDecimal.valueOf(-1))));
        return sb.toString();
    }

    public void undo() {
        int index = command.getUndoList().size() - 1;
        command.getUndoList().get(index).execute();
        command.getRedoList().push(command.getUndoList().peek());
        command.getUndoList().pop();
    }

    public void redo() {
        if (command.getRedoList().size() > 0) {
            command.getRedoList().get(0).redo();
        }
    }
}
