package Assignment1;

import java.util.Arrays;

public class BinaryNumber {
	
	private int data [] ;			//Array to store binary number	
	private boolean overflow;		//variable to check carry while addition

	// Constructor to create array of 0's
	BinaryNumber(int length)       	
	{
		String str;
		data = new int[length];
		for(int i=0; i< data.length;i++)
		{
			data[i] = 0;
		}
		System.out.println("The array with the given length is : "+ Arrays.toString(data) +"  and the string value is : "+ toString());
	}
	
	//Constructor to convert binary string to array
	BinaryNumber(String str)		
	{
		int strLength = str.length();
		data = new int[strLength];
		for (int i =0;i<strLength;i++)
		{
			if(Character.getNumericValue(str.charAt(i) ) == 0  || Character.getNumericValue(str.charAt(i)) == 1 )
			{
				data[i]= Character.getNumericValue(str.charAt(i));
			}
			else
			{				
				System.out.println("Please insert valid string for conversion to binary.");
			}

		}
	}
	
	//Method to return length of binary array
	public int getLength()
	{
		return data.length;  
	}
	
	//Mehod to get binary digit at specified index
	public int getDigit(int index)
	{
		
		if(index < getLength() && index >=0)
		{
			return data[index];
		}
		else
		{
			System.out.println("Index "+ index + " is out of bound. Please insert valid index.");
			System.exit(-1);
			return -1;
		}
	}
	
	//Method to convert binary number to decimal
	public int toDecimal()
	{
		
		int pow = 0 ,powValue,mul, sumTotal = 0;
		for(int i =0 ; i<getLength();i++)
		{
			powValue = (int)Math.pow(2, pow);
			mul = data[i] * powValue;
			sumTotal = sumTotal + mul;
			pow = pow + 1;
		}
		return sumTotal;
	}
	
	//Method to shift right and insert 0's with the given amount
	public void shiftR(int amount)
	{
		if(amount > 0)
		{
			int length = getLength()+amount; // Length for new array with amount
			int count = 0;
			int [] temp;
			String str = "";
			temp = new int[length];			//Temporary array to store new array with shift
			for(int i =0; i<length;i++) 
			{
				if(count < amount)
				{
					temp[i] = 0;
					count ++;
				}	
				else
				{
					temp[i]=data[i-amount];
				}
				
			}
			for(int i=0;i<temp.length;i++)
			{
				str = str+ Integer.toString(temp[i]);
			}
			System.out.println("Array with the shift is : " + Arrays.toString(temp) +" and the string value is : "+ str);
		}
		else
		{
			System.out.println("Shift amount cannot be negative.");
			System.exit(-1);
		}
	}

	//Method to add binary numbers
	public void add(BinaryNumber aBinaryNumber)
	{
		
		int sum = 0,carry = 0,sumDecimal;
		int [] aData = new int[aBinaryNumber.getLength()];
		aData = aBinaryNumber.data;
		int flag = 0;
		String str;
		if(getLength() == aData.length)
		{
			for(int i = 0 ; i < getLength() ;i++)
			{
				sum = data[i] + aData[i] + carry;
				switch(sum)
				{
				case 0:
					data[i] = 0;
					carry = 0;
					break;
				case 1:
					data[i] = 1;
					carry = 0;
					break;
				case 2:
					data[i] = 0;
					carry = 1;
					break;
				case 3:
					data[i] = 1;
					carry = 1;
					break;
				}
				if(carry == 1)   //checking of carry and setting overflow
				{
					overflow = true;
				}
				else
				{
					overflow = false;
				}
				
			}
		}
		else
		{
			System.out.println("The binary strings are not same, so the adddition cannot be performed.");
			flag = 1;
		}
		if(overflow == true)  		// getting the decimal number of binary result
		{
			int pow = getLength();
			int carrySum = (int)Math.pow(2, pow);
			sumDecimal = toDecimal() + carrySum;
		}
		else
		{
			sumDecimal = toDecimal();
		}
		str = toString();
		if(flag == 0) 				//printing result if addition done successfully
		{
			System.out.println("The string value of the sum is : " + str + " and the decimal number is: " + sumDecimal);
		}
		
		clearOverflow();
	}
	
	//Converting binary array to string.
	public String toString()
	{
		String str="";
		if(overflow)
		{
			return "Overflow";
		}
		else
		{
			for(int i=0;i<data.length;i++)
			{
				str = str+ Integer.toString(data[i]);
			}
			return str;
		}
		
	}
	
	//Clearing the overflow/carry
	public void clearOverflow() {
		overflow = false;
	}
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinaryNumber b1 = new BinaryNumber(5);				
		BinaryNumber b2 = new BinaryNumber("10110");
		BinaryNumber b3 = new BinaryNumber("11101");
		System.out.println("The digit at the given index is: "+ b2.getDigit(0));
		System.out.println("The deciaml number for the given binary is: "+ b2.toDecimal());
		b2.shiftR(5);
		b2.add(b3);

	}

}
