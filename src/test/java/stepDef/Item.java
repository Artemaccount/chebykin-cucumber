package stepDef;

public class Item {
    String name;
    int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Наименование: " + name + "| Стоимость: " + price;
    }
}
