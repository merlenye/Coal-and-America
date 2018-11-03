import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CirclesCountry {
	static  ArrayList<String>finalanswer = new ArrayList<String>();
	static String helperstring = "";

	public static void main(String[] county) throws FileNotFoundException {
		File file = new File("EIA-7A_Compiled2.csv"); // CSV file with coal data
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a county in qutoes: ");
		String countyy = reader.next();
		System.out.println("Enter the State in qutoes: ");
		String state = reader.next();
		System.out.println("Enter the year you'd like to start at: ");
		int firstyear = reader.nextInt();
		System.out.println("Enter the year you'd like to end at: ");
		int lastyear = reader.nextInt();
		reader.close();
		 //"\"Wise\""; // desired county
		//String state = "\"Virginia\""; // specified state
		String[] makearrayone = makearray(firstyear,lastyear);
		dowork(file, countyy, state, makearrayone);
		System.out.print(finalanswer);
	}
	
	public static String[] makearray(int firstyear, int lastyear) {
		int[] range = IntStream.rangeClosed(firstyear, lastyear).toArray();
		String[] stringyears = new String[range.length];
		for (int y =0; y < stringyears.length; y++) {
			stringyears[y] = Integer.toString(range[y]);
		}
		return stringyears;
		
	}

	public static void dowork(File file, String countyy, String state, String[] years) throws FileNotFoundException {
		String nameoffile = (countyy.replaceAll("^\"+ \"+$", "") + " coal production from " + years[0] + "-" + years[years.length-1]);
		PrintStream o = new PrintStream(new File(nameoffile + ".txt"));
		PrintStream console = System.out;
		for (int i = 0; i < years.length; i++) {
			Scanner scanner = new Scanner(file);
			int countytotal = 0;
			int statetotal = 0;
			int percent = 0;
			double nationalportion = 0;
			int nationaltotal = 0;
			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				String[] values = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				values[14] = values[14].replaceAll(",", "");
				values[14] = values[14].replaceAll("^\"|\"$", "");
				values[1] = values[1].replaceAll("^\"|\"$", "");
				if (values[1].equals(years[i])) {
					if (isStringInt(values[14])) {
						nationaltotal += Integer.parseInt(values[14]);
						int tons = Integer.parseInt(values[14]);
						if (values[5].contains(countyy)) {
							countytotal += (tons);
						}
						if (values[4].equals(state)) {
							statetotal += (tons);
						}
					}
					if (statetotal > 0 && countytotal > 0) {
						 percent = countytotal * 100 / statetotal;
						
					}
					if (nationaltotal > 0 && statetotal > 0) {
						nationalportion = statetotal *1000/ nationaltotal;
					
						
					}
				}
			}
			System.setOut(o); 
	        System.out.println(countyy.replaceAll("^\"+ \"+$", "") + "produced " + countytotal + " tons of coal in " + years[i]
					+ " which was " + percent + "% of the " + statetotal + " tons produced in " + state.replaceAll("^\"+ \"+$", ""));
	        //System.out.println(state.replaceAll("^\"+ \"+$", "") + " produced " + nationalportion + " of the nation's coal in " + years[i]);
			scanner.close();
		}
		
	}

	private static double parseInt(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
