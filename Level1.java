import java.util.*;

public class Level1 {
    public static void main(String[] args) {
        HashMap<String, Item> itemList = new HashMap<>();

        // Adding items to the itemList dictionary
        itemList.put("Sunglasses", new Item(3, 4));
        itemList.put("Hydration pack", new Item(4, 7));
        itemList.put("Energy gels", new Item(4, 8));
        itemList.put("Towel", new Item(4, 8));
        itemList.put("Water bottle", new Item(3, 1));

        // Accessing items in the itemList dictionary
        // Item sunglasses = itemList.get("Sunglasses");
        // System.out.println("Sunglasses - Weight: " + sunglasses.getWeight() + ",
        // Value: " + sunglasses.getValue());

        // Getting the backpack capacity
        int backpackCapacity = 10;
        System.out.println(
                "Backpack Capacity: " + backpackCapacity + " Maximum Value: " + maxValue(itemList, backpackCapacity));

    }

    public static String entryToString(Map.Entry<String, Item> entry) {
        String key = entry.getKey();
        Item item = entry.getValue();
        return key + " - Weight: " + item.getWeight() + ", Value: " + item.getValue();
    }

    public static String maxValue(HashMap<String, Item> itemsHashMap, int capacity) {
        // Create a list from the entries of the itemList
        List<HashMap.Entry<String, Item>> itemList = new ArrayList<>(itemsHashMap.entrySet());
        // System.out.println(itemList.toString()); // scaffolding
        // Sort the items by value/weight ratio
        Comparator<HashMap.Entry<String, Item>> valueComparator = Comparator
                .comparing(entry -> entry.getValue().getValuePerWeight());
        itemList.sort(valueComparator);

        int backpackValue = 0;

        // while there are still items in the list and the backpack isn't full
        while (!itemList.isEmpty() && capacity > 0) {

            Map.Entry<String, Item> entry = itemList.get(itemList.size() - 1);
            Item item = entry.getValue(); // Access the Item object from the Map.Entry //get the item with the highest
                                          // value/weight ratio

            // System.out.println(entryToString(entry)); // scaffolding
            if (item.getWeight() <= capacity) { // if the item fits in the backpack, add it to the backpack
                // System.out.println(entryToString(entry) + " fits in the backpack");
                capacity -= item.getWeight();
                backpackValue += item.getValue();
                itemList.remove(itemList.size() - 1);

            }
            // if the item doesn't fit in the backpack, remove it from the list
            else {
                itemList.remove(itemList.size() - 1);
            }
        }
        return String.valueOf(backpackValue);
    }
}

class Item {
    private int weight;
    private int value;
    private double valuePerWeight;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
        this.valuePerWeight = (double) (value / weight);
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public double getValuePerWeight() {
        return valuePerWeight;
    }
}
