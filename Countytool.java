import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Countytool {
	public static void main(String [] county) throws FileNotFoundException {
		File file = new File("EIA-7A_Compiled.csv"); //CSV file with coal data
		Scanner scanner = new Scanner(file);
		String countyy = "\"Wise\""; //desired county
		String state = "\"Virginia\""; //specified state
		int countytotal = 0;
		int statetotal = 0;
		while (scanner.hasNextLine() ) {
			String data = scanner.nextLine();
			String[] values = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			values[16] = values[16].replaceAll(",", "");
			values[16] = values[16].replaceAll("^\"|\"$", "");
			if (isStringInt(values[16])) {
				int tons = Integer.parseInt(values[16]);
				if(values[5].equals(countyy)) {
					countytotal += (tons);
				}
				if (values[4].equals(state)) {
					statetotal += (tons);
				}
			}
		}
		scanner.close();
		int percent = countytotal*100/statetotal;
		System.out.println(countyy + " produced " + countytotal + " tons of coal since 1984" +" which is " + percent +  "% of the " + statetotal + " tons produced in " + state);
	}
	private static double parseInt(String string) {
		// TODO Auto-generated method stub
		return 0;
	}
	public static boolean isStringInt(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}
}
