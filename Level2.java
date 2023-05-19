import java.io.*;
import java.util.*;

public class Level2 {
    public static void main(String[] args) {
        String filePath = "/2.txt";
        HashMap<String, Item> itemList = readItemsFromFile(filePath);

        // Testing accessing items in the itemList dictionary
        Item sunglasses = itemList.get("Sunglasses");
        System.out.println("Sunglasses - Weight: " + sunglasses.getWeight() + ", Value: " + sunglasses.getValue());

        // Getting the backpack capacity
        int backpackCapacity = getBackpackCapacityFromFile(filePath);
        System.out.println("Backpack Capacity: " + backpackCapacity);
    }

    public static String entryToString(Map.Entry<String, Item> entry) {
        String key = entry.getKey();
        Item item = entry.getValue();
        return key + " - Weight: " + item.getWeight() + ", Value: " + item.getValue();
    }

    public static HashMap<String, Item> readItemsFromFile(String filePath) {
        HashMap<String, Item> itemList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean readingItems = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Items:")) {
                    readingItems = true;
                } else if (line.startsWith("Backpack capacity:")) {
                    readingItems = false;
                } else if (readingItems) {
                    String[] parts = line.split(":");
                    String itemName = parts[0].trim();
                    String[] itemProperties =
                    parts[1].replaceAll("[{}\"]", "").split(",");
                    int weight = Integer.parseInt(itemProperties[0].trim().split(":")[1]);
                    int value = Integer.parseInt(itemProperties[1].trim().split(":")[1]);
                    itemList.put(itemName, new Item(weight, value));
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    public static int getBackpackCapacityFromFile(String filePath) {
        int backpackCapacity = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Backpack capacity:")) {
                backpackCapacity = Integer.parseInt(line.split(":")[1].trim());
                break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backpackCapacity;
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

