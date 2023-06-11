package MyHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapDriver {

	public static void main(String[] args) {
	    MyHashMap<String, String> myHashMap = new MyHashMap<String, String>();
	    myHashMap.put("A", "B");
	    myHashMap.put("E", "F");
	    myHashMap.put("H", "P");
	    myHashMap.put("P", "2");
	    myHashMap.put("1",  "G");
	    myHashMap.put("2", "6");
	    myHashMap.put("3", "2");
	    myHashMap.put("4", "4");
	    myHashMap.put("1",  "H");
	    
	    ArrayList<String> keys= myHashMap.keySet();
	    
	    for(String s:keys)
	    {
	    	System.out.println(s);
	    }
	    
	 
	 
	  
	    myHashMap.remove("2");
	    System.out.println(myHashMap);
	    
	    System.out.println(myHashMap.getSize());
	    System.out.println(myHashMap.get("1"));
	    System.out.println(myHashMap.containsKey("2"));
	    System.out.println(myHashMap.containsKey("3"));
	    
	    HashMap<String,Integer> map = new HashMap<>();
	    
	    map.put("Huhu",1);
	    map.put("Hehe",1);
	    map.put("Hoho",1);
	    
	    System.out.println(map.keySet());
	    ArrayList<String> list = new ArrayList<>();
	    list.add("huhu");
	    
	    System.out.println(list);
		  
	}
}
