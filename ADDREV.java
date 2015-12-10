import java.util.Scanner;


public class ADDREV {
	public static void main(String[] args) {
		int n, a, b, c;
		String num1, num2, ans=null; 
		Scanner s = new Scanner(System.in);
		
		n = s.nextInt();
		
		while(n > 0) {
			num1 = s.next();
			num2 = s.next();
			//remove trailing zeros
			num1 = num1.replaceAll("0*$", "");
			num2 = num2.replaceAll("0*$", "");
			
			num1 = new StringBuilder(num1).reverse().toString();
			num2 = new StringBuilder(num2).reverse().toString();
			
			a = Integer.parseInt(num1);
			b = Integer.parseInt(num2);
			c = a + b;
			
			ans = Integer.toString(c);
			
			ans = new StringBuilder(ans).reverse().toString();
			
		    System.out.println(ans.replaceFirst("^0+(?!$)", ""));

			n = n - 1;
		}
		
		s.close();
	}
}
