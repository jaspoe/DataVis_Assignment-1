import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
	String csvFile = "src/res/bevgeburtenjahrgeschlquartstz.csv";
	String line = "";
	String cvsSplitBy = ",";
	//create data for plot 1
	public ArrayList<ChartDataObject> dataGenPlot1(){
		int counterArea = -1;
		int counterMale = 0;
		int counterFemale = 0;
		final String YEAR = "2015";
		String area = "";

		ArrayList<ChartDataObject> returnData = new ArrayList<ChartDataObject>();

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
						ChartDataObject addArea = new ChartDataObject(area, counterMale, counterFemale);
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
	
	//create data for plot 2
	public int[] dataGenPlot2(){
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
		//System.out.println("CSV plot 2 male: " + counter[0]);
		counter[1] = counterFemale;
		//System.out.println("CSV plot 2 female: " + counter[1]);
		return counter;
	}
	
	//create data for plot 3
	public ArrayList<ChartDataObject> dataGenPlot3(String area, int from, int to){
		int counterYear = -1;
		int birthyear = 0;
		int counterMale = 0;
		int counterFemale = 0;		

		ArrayList<ChartDataObject> returnData = new ArrayList<ChartDataObject>();

		//start going through the data
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);
				
				if(data.length>1){
					//check if the area and birthyear are correct
					if(data[6].equals(area) && Integer.parseInt(data[0]) >= from && Integer.parseInt(data[0]) <= to){
						
						//check if new year has to be added
						if(Integer.parseInt(data[0]) != birthyear){
							counterYear++;
							birthyear = Integer.parseInt(data[0]);

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
							ChartDataObject addYear = new ChartDataObject(area, counterMale, counterFemale, birthyear);
							returnData.add(addYear);
						}

						else{
							//add all male births'
							if(data[2].equals("\"M\"")){
								counterMale = Integer.parseInt(data[7]);
								returnData.get(counterYear).counterMale = returnData.get(counterYear).counterMale + counterMale;
							}
							//add all female births'
							else{
								counterFemale = Integer.parseInt(data[7]);
								returnData.get(counterYear).counterFemale = returnData.get(counterYear).counterFemale + counterFemale;
							}
						}
					}
				}
				

			}} catch (IOException e) {
				e.printStackTrace();
			}

		return returnData;
	}
	
	//create data for dropdown menu
	public ArrayList<String> dataGenDropdown() {
		ArrayList<String> returnData = new ArrayList<String>();
		String areaName = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);
				
				/*there was some weird occurence where the very last parsed line was only containing 1 item...
				to prevent an outOfBoundException this if construct was put here*/
				if(data.length>1){
					if(!data[6].equals(areaName) && data[0].equals("2015")){
						areaName = data[6];
						returnData.add(data[6]);
						//System.out.println("dataGenDropdown areas: " + data[6]);	
					}
				}
			}} catch (IOException e) {
				e.printStackTrace();
			}

		return returnData;
		
	}
}
