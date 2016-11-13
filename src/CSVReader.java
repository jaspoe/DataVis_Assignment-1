import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {


	public int[] dataGenPlot2(){
		String csvFile = "D:/eclipse/workspace/DataVis_Diagrams/src/res/bevgeburtenjahrgeschlquartstz.csv";
		String line = "";
		String cvsSplitBy = ",";
		int counterFemale = 0;
		int counterMale = 0;
		int[] counter = new int[2];

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);
				//count all females born in 2015
				if(data[0].equals("2015")&&data[2].equals("\"W\"")){
					counterFemale = counterFemale + Integer.parseInt(data[7]);
				//count all males born in 2015
				}else if(data[0].equals("2015")&&data[2].equals("\"M\"")){
					counterMale = counterMale + Integer.parseInt(data[7]);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//return the male and female counters
		counter[0] = counterMale;
		System.out.println("CSV plot 2 male: " + counter[0]);
		counter[1] = counterFemale;
		System.out.println("CSV plot 2 female: " + counter[1]);
		return counter;
	}

//	public ArrayList<Plot1DataObject> dataGenPlot1(){
//		String csvFile = "D:/eclipse/workspace/DataVis_Diagrams/src/res/bevgeburtenjahrgeschlquartstz.csv";
//		String line = "";
//		String cvsSplitBy = ",";
//		int counterArea = 0;
//		final String YEAR = "2015";
//		String area = "";
//		
//		ArrayList<Plot1DataObject> returnData = new ArrayList<Plot1DataObject>();
//
//		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//
//			while ((line = br.readLine()) != null) {
//
//				// use comma as separator
//				String[] data = line.split(cvsSplitBy);
//
//				
//				if(data[0].equals(YEAR)){
//					//create new Area and increase Birth counter (male or female)
//					if(!data[7].equals(area)){
//						area = data[7];
//						counterArea = counterArea +1;
//						Plot2DataObject newArea = new Plot2DataObject(area, 0, 0);
//						returnData.add(newArea);
//						//increase male counter
//						if(data[1].equals("1")){
//							returnData.get(counterArea).counterMale++;
//						}
//						//increase female counter
//						else{
//							returnData.get(counterArea).counterFemale++;
//						}
//						
//					}
//					// increase Birth counter (male or female)
//					else{
//					
//						//increase male counter
//						if(data[1].equals("1")){
//							returnData.get(counterArea).counterMale++;
//						}
//						//increase female counter
//						else{
//							returnData.get(counterArea).counterFemale++;
//					}
//				}
//			}
//				System.out.println("Area: " +returnData.get(counterArea).area);
//				System.out.print(" Male: " + returnData.get(counterArea).counterMale);
//				System.out.print(" Female: " + returnData.get(counterArea).counterFemale);
//		}} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return returnData;
//	}
}