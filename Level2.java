import java.io.*;
import java.util.*;
// import java.lang.*; 

public class Level2 {
    public static void main(String[] args) {
        String filePath = "./2.txt";
        HashMap<String, Item> itemList = readItemsFromFile(filePath);
        
        // Getting the backpack capacity
        int capacity = getBackpackCapacityFromFile(filePath);
        System.out.println("Backpack Capacity: " + capacity + " Maximum Value: " + maxValue(itemList, capacity));
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
        // System.out.println(itemList.toString()); // scaffolding

        // Sort the items by value/weight ratio
        Comparator<Map.Entry<String, Item>> valueComparator = Comparator.comparing(
            (Map.Entry<String, Item> entry) -> entry.getValue().getValuePerWeight()).thenComparing(entry -> entry.getValue().getWeight());  //sort by value/weight ratio, then by weight
        itemList.sort(valueComparator);

        int backpackValue = 0;

        // while there are still items in the list and the backpack isn't full
        while (!itemList.isEmpty() && capacity > 0) {

            Map.Entry<String, Item> entry = itemList.get(itemList.size() - 1);
            Item item = entry.getValue(); // Access the Item object from the Map.Entry //get the item with the highest
                                          // value/weight ratio

            // System.out.println(entryToString(entry)); // scaffolding
            if (item.getWeight() <= capacity) { // if the item fits in the backpack, add it to the backpack
                // System.out.println(entryToString(entry) + " fits in the backpack");   //scaffolding
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

// class Item {
//     private int weight;
//     private int value;
//     private double valuePerWeight;

//     public Item(int weight, int value) {
//         this.weight = weight;
//         this.value = value;
//         this.valuePerWeight = (double) (value / weight);
//     }

//     public int getWeight() {
//         return weight;
//     }

//     public int getValue() {
//         return value;
//     }

//     public double getValuePerWeight() {
//         return valuePerWeight;
//     }
// }

