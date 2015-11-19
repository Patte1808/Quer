import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Quer {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); 
		double time = (System.currentTimeMillis()-startTime)/1000.0; 
		System.out.println("start main");
		long values[] = calculateMultiplicativePersistence(Integer.parseInt(args[0]));
		
		for(int i = 0; i < values.length; i++) {
			System.out.println(i + 2 + " Schritte: " + values[i]);
		}
		
		time = (System.currentTimeMillis()-startTime)/1000.0; 
		
		System.out.println("Time needed: " + time);
		
		System.out.println("Finished");
 	}
	
	private static long querProdukt(long number) {
		long product = 1;
		
		while(number > 0) {
			product = product * (number % 10);
			number = number / 10;
		}
		
		return product;
	}
	
	private static int querProduktForK(long k) {
		int n = 0;
		
		while(k >= 10) {
			k = querProdukt(k);
			n++;
		}
		
		return n;
	}
	
	public static LinkedList<Long> getNextNumberAsDigitList(long number) {	
		LinkedList<Long> digits = convertLongValueToDigitList(number);
		int greatestDigitIndex = 0;
		long greatestDigitValue = 0;
		boolean youShallIncrement = false;
		
		digits.set(digits.size() - 1, digits.get(digits.size() - 1) + 1l);
		
		if(digits.get(digits.size() - 1) == 9l || digits.get(digits.size() - 1) == 10l) {
			//digits.set(digits.size() - 1, 0l);
			
			for(int i = digits.size() - 1; i >= 0; i--) {
				if(digits.get(i) == 10l) {
					//System.out.println("Setting " + digits.get(i) + " to 0");
					digits.set(i, 0l);
					//System.out.println(digits);
			
					if(i == 0) {
						digits.add(0, 1l);
						greatestDigitIndex = 0;
						greatestDigitValue = 1l;
					} else {
						digits.set(i - 1, digits.get(i - 1) + 1);
						
						if(digits.get(i - 1) > greatestDigitValue && digits.get(i - 1) < 10) {
							greatestDigitIndex = i - 1;
							greatestDigitValue = digits.get(i - 1);
						}
					}
					youShallIncrement = true;
				}
			}
			
			if(youShallIncrement) {
				
				for(int i = greatestDigitIndex; i < digits.size(); i++) {
					digits.set(i, greatestDigitValue);
				}

				youShallIncrement = false;
			}
		}
		
		return digits;
	}
	
	public static long convertDigitListToLongValue(LinkedList<Long> digits) {
		long longValue = digits.get(0);
		
		for(int i = 1; i < digits.size(); i++) {
			longValue *= 10;
			longValue += digits.get(i);
		}
			
		return longValue;
	}
	
	public static LinkedList<Long> convertLongValueToDigitList(long number) {	
		LinkedList<Long> digits = new LinkedList<Long>();
	
		while(number > 0) {
			digits.push(number % 10);
			number /= 10;
		}
		
		return digits;
	}
	
	private static long[] calculateMultiplicativePersistence(int steps) {
		long[] smallestProductsInSteps = new long[steps - 1];
		long i = 1l;
		
		while(true) {
			int stepsTakenForNumber = querProduktForK(i);
			
			if(stepsTakenForNumber > 1) {
				if(smallestProductsInSteps[stepsTakenForNumber - 2] == 0) {
					smallestProductsInSteps[stepsTakenForNumber - 2] = i;
					
					if(stepsTakenForNumber == steps) {
						break;
					}
				}
			}
			
			i = convertDigitListToLongValue(getNextNumberAsDigitList(i));
		}
		
		return smallestProductsInSteps;
	}

}
