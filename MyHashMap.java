import java.util.ArrayList;

public class MyHashMap<K, V> {

	private static final int TABLESIZE = 5;
	private Entry<K, V>[] table;
	private int size;
	
	
	public MyHashMap() {
		table = new Entry[TABLESIZE];
		size=0;
	}

	
	
	private class Entry<K, V> {
		private K key;
		private V val;

		private Entry<K, V> next;

		Entry(K k, V v) {
			this.key = k;
			this.val = v;
			this.next = null;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.val;
		}

		public void setValue(V val) {
			this.val = val;
		}

		public String toString() {
			
			Entry<K, V> temp = this;
			StringBuilder sb = new StringBuilder();
			while (temp != null) {
				sb.append(temp.key + "->" + temp.val);
				
				if(temp.next!=null)
				{
					sb.append(", ");
				}
				
				temp = temp.next;
			}
			
			return sb.toString();
		}

	}

	
	
	public boolean isEmpty()
	{
		return this.size==0;
	}
	
	
	
	
	public int getSize()
	{
		return this.size;
	}
	
	
	

	public boolean containsKey(K key)
	{
		int hash = key.hashCode()%TABLESIZE;
		
		Entry<K,V> e = table[hash];
		
		
		while(e!=null)
		{
			if(e.key.equals(key))
				return true;
			
			e=e.next;
		}
		
		
		return false;
	}
	
	
	
	
	public void put(K key, V value) {
		int hash = key.hashCode() % TABLESIZE;

		Entry<K, V> e = table[hash];

		if (e == null) {
			table[hash] = new Entry<K, V>(key, value);
			size++;
			return;
		} else {
			while (e.next != null) {
				if (e.getKey().equals(key)) {
					e.setValue(value);
					return;
				}
				e = e.next;
			}

			if (e.getKey().equals(key)) {
				e.setValue(value);
				return;
			}

			e.next = new Entry<K, V>(key, value);
			size++;
		}
	}

	public V get(K key) {
		int hash = key.hashCode() % TABLESIZE;

		Entry<K, V> e = table[hash];

		if (e == null)
			return null;
		else {
			while (e != null) {
				if (e.getKey().equals(key)) {
					return e.getValue();
				}
				e = e.next;
			}
		}

		return null;
	}

	public Entry<K, V> remove(K key) {
		int hash = key.hashCode() % TABLESIZE;

		Entry<K, V> e = table[hash];

		if (e == null)
			return null;

		if (e.getKey().equals(key)) {
			table[hash] = e.next;
			e.next = null;
			size--;
			return e;
		}

		Entry<K, V> prev = e;
		e = e.next;

		while (e != null) {
			if (e.getKey().equals(key)) {
				prev.next = e.next;
				e.next = null;
				size--;
				return e;
			}

			prev = e;
			e = e.next;
		}

		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < TABLESIZE; i++) {
			if (table[i] != null) {
				sb.append(i + " " + table[i] + "\n");
			} else {
				sb.append(i + " " + "null" + "\n");
			}
		}

		return sb.toString();

	}
	
	public ArrayList<K> keySet()
	{
		ArrayList<K> keys = new ArrayList<>();
		
		for(int i=0;i<TABLESIZE;i++)
		{
			Entry<K,V> e = table[i];
			
			while(e!=null)
			{
				keys.add(e.getKey());
				e=e.next;
			}
		}
		
		return keys;
	}
}
