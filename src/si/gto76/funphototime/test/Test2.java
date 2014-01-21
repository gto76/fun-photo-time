package si.gto76.funphototime.test;

public class Test2 {

	public static void main(String[] args) {
		Integer[] iii = {9,3,4};//new Integer[3];
		Pair<Integer, Integer[]> p = new Pair<Integer, Integer[]>(8,iii);
		System.out.println(p.key + " " + p.value);
	}

}
	
class Pair<K, V> {
    K key;
    V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
  
}