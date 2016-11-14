
public class ChartDataObject {
	String area;
	int counterMale;
	int counterFemale;
	int birthyear;
	
	//DataStructure for Plot 3
	ChartDataObject(String area, int counterMale, int counterFemale, int birthyear){
		this.area = area;
		this.counterMale = counterMale;
		this.counterFemale = counterFemale;
		this.birthyear = birthyear;
	}
	
	//DataStructure for Plot 1
	ChartDataObject(String area, int counterMale, int counterFemale){
		this.area = area;
		this.counterMale = counterMale;
		this.counterFemale = counterFemale;
	}
}
