import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class PIGBANK {

	public static void minimizePiggyBank(int[] W, int[] V, int M)
	{
		int wLen = W.length;
		int temp1, temp;
		int[] K = new int[M+1];
		K[0] = 0;


		for(int w=1;w<=M;w++)
		{
			temp = Integer.MAX_VALUE;

			for(int i=1;i<wLen;i++)
			{
				temp1 = 0;
				if(w >= W[i])
				{
					if(K[w - W[i]] > 0 || w == W[i])
					{
						temp1 = V[i] + K[w-W[i]];
					}
				}

				if(temp1 < temp && temp1 != 0)
				{
					temp = temp1;
				}
			}

			if(temp==Integer.MAX_VALUE)
			{
				K[w] = 0;
			}
			else
			{
				K[w] = temp;
			}
		}

		if(K[M] == 0)
		{
			System.out.println("This is impossible.");
		}
		else
		{
			System.out.println("The minimum amount of money in the piggy-bank is "+K[M]+".");
		}
	}

	public static void main(String[] args)
	{
		int T=0;
		int E=0, F=0;
		int N=0;
		int[] V, W;
		String[] inp;
		int M;

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		try
		{
			T = Integer.parseInt(br.readLine());
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

		while(T-- > 0)
		{
			try
			{
				inp  = br.readLine().split(" ");
				E = Integer.parseInt(inp[0]);
				F = Integer.parseInt(inp[1]);

				N = Integer.parseInt(br.readLine());
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}

			V = new int[N+1];
			W = new int[N+1];

			for(int i=1;i<=N;i++)
			{
				try
				{
					inp  = br.readLine().split(" ");
					V[i] = Integer.parseInt(inp[0]);
					W[i] = Integer.parseInt(inp[1]);
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}

			M = F - E;

			minimizePiggyBank(W, V, M);
		}
	}
}