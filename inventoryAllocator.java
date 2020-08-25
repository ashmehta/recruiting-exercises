package inventoryAllocator1;

import java.util.*;

//Create the warehouse with them name varible and the item variable
class Warehouse{
  String warehouseName;
  Map<String, Integer> itemCount;
  public Warehouse(String warehouseName, Map<String, Integer> itemCount){
      this.warehouseName = warehouseName;
      this.itemCount = itemCount;
  }
}

public class inventoryAllocator {
  public static List<Warehouse> alloc(Map<String, Integer> orders, List<Warehouse> warehouse){
      List<Warehouse> ship = new ArrayList<>();
      if(orders == null || orders.size() == 0 || warehouse == null || warehouse.size() == 0){
          return ship;
      }

      //Iterate through each warehouse to check the amount of inventory is present in that warehouse
      for(Warehouse whouse: warehouse){
          //Checks to see if more items are required to fulfill the order if not then break
          if(orders.size() == 0){
              break;
          }
          Map<String, Integer> inventoryInWarehouse = whouse.itemCount;
          String warehouseName = whouse.warehouseName;
          HashMap<String, Integer> itemsInWarehouse = new HashMap<>();
          for(Map.Entry<String, Integer> i: orders.entrySet()){
              Integer inventoryAmount = inventoryInWarehouse.get(i.getKey());
              if(inventoryAmount != null && inventoryAmount > 0){
                  if(i.getValue() > inventoryAmount){
                      itemsInWarehouse.put(i.getKey(), inventoryAmount);
                  }else{
                      itemsInWarehouse.put(i.getKey(), i.getValue());
                  }
              }
          }
          if(itemsInWarehouse.size() > 0){
              ship.add(new Warehouse(warehouseName, itemsInWarehouse));
              for(Map.Entry<String, Integer> i: itemsInWarehouse.entrySet()){
                  int amount = orders.get(i.getKey());
                  if(amount == i.getValue()){
                      orders.remove(i.getKey());
                  }else{
                      orders.put(i.getKey(), amount - i.getValue());
                  }
              }
          }
      }
      return ship;

  }
  //Some tests to check the code above
  
  //Happy Case, Exact Inventory Match!*
  private static void happyCase() {
	  HashMap<String, Integer> newOrder = new HashMap<>();
	  newOrder.put("apple", 1);
	  HashMap<String, Integer> warehouse = new HashMap<>();
	  warehouse.put("apple", 1);
	  ArrayList<Warehouse> whouse = new ArrayList<>();
	  whouse.add(new Warehouse("owd", warehouse));
	  List<Warehouse> happyCase = alloc(newOrder, whouse);
	  test(happyCase);
  }
  
  //Not enough inventory -> no allocations!
  private static void notEnoughInventory() {
	  HashMap<String, Integer> newOrder = new HashMap<>();
	  newOrder.put("apple", 1);
	  HashMap<String, Integer> warehouse = new HashMap<>();
	  warehouse.put("apple", 0);
	  ArrayList<Warehouse> whouse = new ArrayList<>();
	  whouse.add(new Warehouse("owd", warehouse));
	  List<Warehouse> notEnoughInventory = alloc(newOrder, whouse);
	  test(notEnoughInventory);
	  
  }
  
  //Should split an item across warehouses if that is the only way to completely ship an item:
  private static void split() {
	  HashMap<String, Integer> newOrder = new HashMap<>();
	  newOrder.put("apple", 10);
	  HashMap<String, Integer> warehouse = new HashMap<>();
	  warehouse.put("apple", 5);
	  HashMap<String, Integer> warehouse2 = new HashMap<>();
	  warehouse2.put("apple", 5);
	  ArrayList<Warehouse> whouse = new ArrayList<>();
	  whouse.add(new Warehouse("owd", warehouse));
	  whouse.add(new Warehouse("dw", warehouse2));
	  List<Warehouse> split = alloc(newOrder, whouse);
	  test(split);
  }
  
  //Prints in the required format
  private static void test(List<Warehouse> testCase) {
	  if(testCase == null || testCase.size() == 0) {
		  System.out.println("[]");
	  }else {
		  StringBuilder result = new StringBuilder();
		  result.append("[");
		  for(Warehouse whouse: testCase) {
			  result.append("{ " + whouse.warehouseName+": ");
			  for(Map.Entry<String, Integer> inventory: whouse.itemCount.entrySet()) {
				  result.append("{ "+inventory.getKey()+": "+inventory.getValue()+ " }, ");
			  }
			  result.deleteCharAt(result.length() - 1);
			  result.deleteCharAt(result.length() - 1);
			  result.append(" }, ");
		  }
		  result.deleteCharAt(result.length() - 1);
		  result.deleteCharAt(result.length() - 1);
		  result.append("]");
		  System.out.println(result);
		  
	  }
	
  }
  
  public static void main(String [] args) {
	  //Prints the result of happyCase to the console
	  System.out.println("Happy Case");
	  System.out.println("Expected output of this code is: ");
	  System.out.println("[{ owd: { apple: 1 } }]");
	  System.out.println("Our Program gave: ");
	  happyCase();
	  
	  //Prints the result of notEnoughInventory to the console
	  System.out.println("Happy Case");
	  System.out.println("Expected output of this code is: ");
	  System.out.println("[]");
	  System.out.println("Our Program gave: ");
	  notEnoughInventory();
	  
	  //Prints the result of split to the console
	  System.out.println("Happy Case");
	  System.out.println("Expected output of this code is: ");
	  System.out.println("[{ dm: { apple: 5 }}, { owd: { apple: 5 } }]");
	  System.out.println("Our Program gave: ");
	  split();
  }
}



