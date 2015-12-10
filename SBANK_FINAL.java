import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;


public class SBANK_FINAL {
	public static void main(String[] args) {
		int t,n;
		String account_number;
		int count;

		BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
		Map<String, Integer> tree_map = new TreeMap<String, Integer>();

		try {
			t = Integer.parseInt(bis.readLine());

			while(t > 0) {
				n = Integer.parseInt(bis.readLine());
				tree_map.clear();
				for(int i=0;i<n;i++) {
					account_number = bis.readLine();
					if(tree_map.containsKey(account_number)) {
						count = tree_map.get(account_number);
						count++;
						tree_map.put(account_number, count);
					} else {
						tree_map.put(account_number, 1);
					}	
				}

				for(Map.Entry<String,Integer> entry : tree_map.entrySet()) {
					String key = entry.getKey();
					Integer value = entry.getValue();

					System.out.println(key + value);
				}
				if(t > 1) {
					System.out.println();
				}
				t = t - 1;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
