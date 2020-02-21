public class Complexity {
	
	//Example method for O(n)
	public static void method0(int n)
	{
		checkNumber(n,0);
		int counter = 0;
		for(int i =0 ; i<n;i++)
		{
			System.out.println("Operation:"+counter);
			counter ++;
			
		}
		System.out.println("The total number of “operations” performed : " + counter);
	}
	
	//Complexity O(n^2)
	public static void method1 (int n)
	{
		checkNumber(n,1);
		int counter = 0;
		for(int i=0;i<n;i++)
		{
			for(int j = 0; j<n;j++)
			{
				System.out.println("Operation:"+counter);
				counter ++;
			}
		}
		System.out.println("The total number of “operations” performed : " + counter);
	}
	
	//Complexity O(n^3)
	public static void method2 (int n)
	{
		checkNumber(n,2);
		int counter = 0;
		for(int i=0;i<n;i++)
		{
			for(int j = 0; j<n;j++)
			{
				for(int x=0; x<n;x++)
				{
					System.out.println("Operation:"+counter);
					counter ++;
					
				}
			}
		}
		System.out.println("The total number of “operations” performed : " + counter);
	}
	
	//Complexity O(log n)
	public static void method3 (int n)
	{	
		checkNumber(n,3);
		int counter = 0;
		for(int i =1; i<n;i=i*2)
		{
			System.out.println("Operation:"+counter);
			counter ++;
			
		}
		System.out.println("The number of “operations” performed : " + counter);
	}
	
	//Complexity O(n * log n)
	public static  void method4(int n)
	{
		checkNumber(n,4);
		int counter = 0;
		for(int i =1;i<=n;i++)
		{
			for(int j=1;j<n;j=j*2)
			{
				System.out.println("Operation:"+counter);
				counter ++;
			}
		}
		System.out.println("The total number of “operations” performed : " + counter);
		
	}
	
	//Complexity O(log log n)
	public static void method5(int n)
	{
		checkNumber(n,5);
		int counter = 0;
		for(double i = n;i>=4; i = Math.sqrt(i))
		{
			System.out.println("Operation:"+counter);
			counter ++;
		}
		System.out.println("The total number of “operations” performed : " + counter);
	}
	
	//Complexity O(2^n)
	static int counter1;
	public static int method6(int n)
	{	
		checkNumber(n,6);
		if(n == 0)
		{
			System.out.println("Operation: "+counter1);
			counter1 ++;
			return counter1;
		}
		else if(n == 1)
		{
			System.out.println("Opeartion: "+ counter1);
			counter1 ++;
			System.out.println("Opeartion: "+ counter1);
			counter1 ++;
			return counter1;
		}
		method6(n-1);
		method6(n-1);
		return counter1;
		
	}
	
	//check valid number
	public static void checkNumber(int n, int method)
	{
		if(n<0)
		{
			System.out.println("Please enter a valid number in method"+method);
			System.exit(-1);
		}
	}
	
	//resetting the value of static counter back to zero
	public static void reset()
	{
		counter1 = 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("======================== Example function =========================");
		Complexity.method0(5);
		System.out.println("============================= method1 =============================");
		Complexity.method1(3);
		System.out.println("============================= method2 =============================");
		Complexity.method2(2);
		System.out.println("============================= method3 =============================");
		Complexity.method3(10);
		System.out.println("============================= method4 =============================");
		Complexity.method4(10);
		System.out.println("============================= method5 =============================");
		Complexity.method5(100);
		System.out.println("============================= method6 =============================");
		System.out.println("The total number of “operations” performed : " + Complexity.method6(3));
		Complexity.reset();
		
	}


}
