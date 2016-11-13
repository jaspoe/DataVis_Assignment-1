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

	public ArrayList<Plot1DataObject> dataGenPlot1(){
		String csvFile = "D:/eclipse/workspace/DataVis_Diagrams/src/res/bevgeburtenjahrgeschlquartstz.csv";
		String line = "";
		String cvsSplitBy = ",";
		int counterArea = -1;
		int counterMale = 0;
		int counterFemale = 0;
		final String YEAR = "2015";
		String area = "";
		
		ArrayList<Plot1DataObject> returnData = new ArrayList<Plot1DataObject>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);

				//select all Births' in year 2015
				if(data[0].equals(YEAR)){
					//check if a new area has to be added to arrayList
					if(!data[6].equals(area)){
						counterArea++;
						area = data[6];
						//add all the male births'
						if(data[2].equals("\"M\"")){
							counterMale = Integer.parseInt(data[7]);
							counterFemale = 0;
						}
						//add all female births'
						else{
							counterMale = 0;
							counterFemale = Integer.parseInt(data[7]);
						}
						Plot1DataObject addArea = new Plot1DataObject(area, counterMale, counterFemale);
						returnData.add(addArea);

					}
					
				//add births' to already existing areas
					else{
						//add all male births'
						if(data[2].equals("\"M\"")){
							counterMale = Integer.parseInt(data[7]);
							returnData.get(counterArea).counterMale = returnData.get(counterArea).counterMale + counterMale;
						}
						//add all female births'
						else{
							counterFemale = Integer.parseInt(data[7]);
							returnData.get(counterArea).counterFemale = returnData.get(counterArea).counterFemale + counterFemale;
						}
//						System.out.println("area: " + returnData.get(counterArea).area + " male: " + returnData.get(counterArea).counterMale
//								+ " female: " + returnData.get(counterArea).counterFemale);
					}
				}

		}} catch (IOException e) {
			e.printStackTrace();
		}

		return returnData;
	}
}