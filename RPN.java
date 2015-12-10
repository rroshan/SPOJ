import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;


public class RPN {
	private static boolean isOperator(char c) {
		if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
			return true;
		}
		return false;
	}
	
	private static int getPriority(char o) {
		switch(o) {
			case '+':
				return 1;
			case '-':
				return 2;
			case '*':
				return 3;
			case '/':
				return 4;
			case '^':
				return 5;
			default:
				return 0;
		}
	}
	
	public static void getRPN(char[] exp) {
		Stack<Character> s = new Stack<Character>();
		int exp_len = exp.length;
		char[] output = new char[exp_len];
		int output_len = 0;
		for(int i=0;i<exp_len;i++) {
			if(exp[i] == '(') {
				//if ( then push into stack
				s.push(exp[i]);
			} else if(isOperator(exp[i])) {
				//if operator
				if(getPriority(exp[i]) < getPriority(s.peek())) {
					//if incoming operand has lower priority than the one existing
					output[output_len++] = s.pop();
					s.push(exp[i]);
				} else {
					//else just push
					s.push(exp[i]);
				}
			} else if(exp[i] == ')') {
				//if ) then pop till encounter (
				char c = s.pop();
				while(c != '(') {
					output[output_len++] = c;
					c = s.pop();
				}
				
			} else {
				//if Character
				output[output_len++] = exp[i];
			}
		}
		
		for(int j=0;j<output_len;j++) {
			System.out.print(output[j]);
		}
	}
	
	public static void main(String[] args) {
		int t;
		String exp;
		
		BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
		try {
			t = Integer.parseInt(bis.readLine());
			
			while(t > 0) {
				exp = bis.readLine();
				RPN.getRPN(exp.toCharArray());
				System.out.println();
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
