
public class ChartDataObject {
	String area;
	int counterMale;
	int counterFemale;
	int birthyear;
	
	ChartDataObject(String area, int counterMale, int counterFemale, int birthyear){
		this.area = area;
		this.counterMale = counterMale;
		this.counterFemale = counterFemale;
		this.birthyear = birthyear;
	}
	
	ChartDataObject(String area, int counterMale, int counterFemale){
		this.area = area;
		this.counterMale = counterMale;
		this.counterFemale = counterFemale;
	}
}
