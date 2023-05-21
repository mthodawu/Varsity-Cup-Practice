import java.io.*;
import java.util.*;

public class Level3 {

    public static void main(String[] args) {
        String filePath = "./3.txt";
        HashMap<String, Item> itemList = readItemsFromFile(filePath);
        int capacity = getBackpackCapacityFromFile( filePath);
        //System.out.println("Items: " + itemList.toString());
        System.out.println("Backpack Capacity: " + capacity + " Maximum Value: " + maxValue(itemList, capacity));
        // Testing accessing items in the itemList dictionary
        // Item sunglasses = itemList.get("Sunglasses");
        // System.out.println("Sunglasses - Weight: " + sunglasses.getWeight() + ", Value: " + sunglasses.getValue());

        // Getting the backpack capacity
        // int backpackCapacity = getBackpackCapacityFromFile(filePath);
        // System.out.println("Backpack Capacity: " + backpackCapacity);
        
    }

    public static String entryToString(Map.Entry<String, Item> entry) {
        String key = entry.getKey();
        Item item = entry.getValue();
        return key + " - Weight: " + item.getWeight() + ", Value: " + item.getValue() + ", Value/Weight: "
                + item.getValuePerWeight();
    }

    public static HashMap<String, Item> readItemsFromFile(String filePath) {
        HashMap<String, Item> itemList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readingItems = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Items:")) {
                    line = line.substring(7);
                    //System.out.println("LINE>" + line);
                    readingItems = true;
                } else if (line.startsWith("Backpack capacity:")) {
                    readingItems = false;
                } if (readingItems) {
                    String[] parts = line.split("},");
                    //System.out.println("PARTS>" + Arrays.toString(parts));

                    for (String part : parts) {
                        String [] token = part.trim().replaceAll("[{\"]", "").split(":|,");       //result: TOKEN>["Runners tape",  "weight",  "3" ,"value" ,  "6"]
                        //System.out.println("TOKEN>" + Arrays.toString(token) + " LENGTH>" + token.length);
                        String itemName = token[0].trim();
                        int weight = Integer.parseInt(token[2].trim());
                        int value = Integer.parseInt(token[4].trim().replaceAll("[{}\"]", ""));
                        //System.out.println("ITEMNAME>" + itemName + " WEIGHT>" + weight + " VALUE>" + value);
                     
                        itemList.put(itemName, new Item(weight, value));
                    }
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

    public static String maxValue(HashMap<String, Item> itemsHashMap, int capacity) {
        // Create a list from the entries of the itemList
        List<HashMap.Entry<String, Item>> itemList = new ArrayList<>(itemsHashMap.entrySet());
       
        // // System.out.println(itemList.toString()); // scaffolding
        // Sort the items by value/weight ratio
        Comparator<Map.Entry<String, Item>> valueComparator = Comparator.comparing(
            (Map.Entry<String, Item> entry) ->  entry.getValue().getWeight()).thenComparing(entry -> entry.getValue().getreciprocalWeight()); //sort by weight, then by reciprocal value/weight ratio
        System.out.println("Before sorting: ");
        for (Map.Entry<String, Item> entrym : itemList ) {     //scaffold
            System.out.println(entryToString(entrym));
        }
        itemList.sort(valueComparator);
        System.out.println("\n+++++\nAfter sorting: ");
        for (Map.Entry<String, Item> entry : itemList) {     //scaffold
            System.out.println(entryToString(entry));
        }
        //return "0";
        int backpackValue = 0;

        // while there are still items in the list and the backpack isn't full
        while (!itemList.isEmpty() && capacity > 0) {

            Map.Entry<String, Item> entry = itemList.get(0);
            Item item = entry.getValue(); // Access the Item object from the Map.Entry //get the item with the highest
                                          // value/weight ratio

            // System.out.println(entryToString(entry)); // scaffolding
            if (item.getWeight() <= capacity) { // if the item fits in the backpack, add it to the backpack
                System.out.println(entryToString(entry) + " fits in the backpack");
                capacity -= item.getWeight();
                backpackValue += item.getValue();
                itemList.remove(0);

            }
            // if the item doesn't fit in the backpack, remove it from the list
            else {
                itemList.remove(0);
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
        this.valuePerWeight =  ((double) value / (double) weight);
    }

    public double getWeight() {
        return ( weight);
    }

    public int getValue() {
        return value;
    }
    
    public double getreciprocalWeight() {
        return (1.000 / (double) valuePerWeight);
    }

    public double getValuePerWeight() {
        return valuePerWeight;
    }
}
