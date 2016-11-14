import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


/**
 * @see http://stackoverflow.com/a/15715096/230513
 * @see http://stackoverflow.com/a/11949899/230513
 */
public class TabChart {

	private static final int N = 128;
	private static final Random random = new Random();
	private int n = 1;
	CSVReader reader = new CSVReader();

	//variables for Plot3 User Selection
	String plot3SelectedArea = "\"Rathaus\"";
	int plot3YearFrom = 1993;
	int plot3YearTo = 2015;

	public String getPlot3SelectedArea() {
		return plot3SelectedArea;
	}

	public void setPlot3SelectedArea(String plot3SelectedArea) {
		this.plot3SelectedArea = plot3SelectedArea;
	}

	public int getPlot3YearFrom() {
		return plot3YearFrom;
	}

	public void setPlot3YearFrom(int plot3YearFrom) {
		this.plot3YearFrom = plot3YearFrom;
	}

	public int getPlot3YearTo() {
		return plot3YearTo;
	}

	public void setPlot3YearTo(int plot3YearTo) {
		this.plot3YearTo = plot3YearTo;
	}


	// setters and getters for varialbe Plot3 User Selection

	private void display() {
		JFrame f = new JFrame("TabChart");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JTabbedPane jtp = new JTabbedPane();

		JPanel jPanel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		ChartPanel plot1 = createPlot1(0);
		jPanel1.add(new JButton(new AbstractAction("Female"){
			@Override
			public void actionPerformed(ActionEvent e){
				jPanel1.remove(jPanel1.getComponent(3));
				jPanel1.add(createPlot1(2));
				jPanel1.updateUI();
			}
		}));
		jPanel1.add(new JButton(new AbstractAction("Male"){
			@Override
			public void actionPerformed(ActionEvent e){
				jPanel1.remove(jPanel1.getComponent(3));
				jPanel1.add(createPlot1(1));
				jPanel1.updateUI();
			}
		}));
		jPanel1.add(new JButton(new AbstractAction("Both"){
			@Override
			public void actionPerformed(ActionEvent e){
				jPanel1.remove(jPanel1.getComponent(3));
				jPanel1.add(createPlot1(0));
				jPanel1.updateUI();
			}
		}));

		jPanel1.add(plot1);	
		jtp.addTab("Plot 1", jPanel1);


		jtp.add("Plot 2", createPlot2());
		f.add(jtp, BorderLayout.CENTER);

		JPanel jPanel3 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		ChartPanel plot3 = createPlot3("\"Rathaus\"", 1993, 2015);

		/** create Dropdown Menu
		 * 	populate Menu
		 * 	create Action Listener
		 * 	add to pane
		 */
		String[] dropDownArray = new String[reader.dataGenDropdown().size()];
		for(int i = 0; i < reader.dataGenDropdown().size(); i++){
			dropDownArray[i] = reader.dataGenDropdown().get(i);
		}
		JComboBox comboBox = new JComboBox(dropDownArray);
		ActionListener comboBoxListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				jPanel3.remove(3);
				setPlot3SelectedArea((String) comboBox.getSelectedItem());
				jPanel3.add(createPlot3(getPlot3SelectedArea(), getPlot3YearFrom(), getPlot3YearTo()));
				jPanel3.updateUI();
				//System.out.println("If you have not selected any year range, then the range of 1993 to 2015 will be automatically selected.");
			}
		};
		comboBox.addActionListener(comboBoxListener);
		jPanel3.add(comboBox);

		/** create From  And To TextBox
		 * 	create Action Listener
		 * 	add to pane
		 */
		JTextField yearToTextField = new JTextField();
		yearToTextField.setToolTipText("End Year of Data Range");
		yearToTextField.setColumns(5);
		
		ActionListener yearToListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int year = Integer.parseInt(yearToTextField.getText());
				jPanel3.remove(3);
				setPlot3YearTo(year);
				jPanel3.add(createPlot3(getPlot3SelectedArea(), getPlot3YearFrom(), getPlot3YearTo()));
				jPanel3.updateUI();
			}
		};
		yearToTextField.addActionListener(yearToListener);

		/**This code updates the text field at every digit enter. takes way to long to calculate and leads to errors*/
		/*yearToTextField.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent e){
				updatePlot();
			}
			public void removeUpdate(DocumentEvent e){
				updatePlot();
			}
			public void insertUpdate(DocumentEvent e){
				updatePlot();
			}
			public void updatePlot(){
				int year = Integer.parseInt(yearToTextField.getText());
				jPanel3.remove(3);
				setPlot3YearTo(year);
				jPanel3.add(createPlot3(getPlot3SelectedArea(), getPlot3YearFrom(), getPlot3YearTo()));
				jPanel3.updateUI();
				//System.out.println("If you have not selected a second year, then the From-year will be automatically set to 1993" );
			}
		});*/
		jPanel3.add(yearToTextField);

		JTextField yearFromTextField = new JTextField();
		yearFromTextField.setToolTipText("Start Year of Data Range");
		yearFromTextField.setColumns(5);
		ActionListener yearFromListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int year = Integer.parseInt(yearFromTextField.getText());
				jPanel3.remove(3);
				setPlot3YearFrom(year);
				jPanel3.add(createPlot3(getPlot3SelectedArea(), getPlot3YearFrom(), getPlot3YearTo()));
				jPanel3.updateUI();
			}
		};
		yearFromTextField.addActionListener(yearFromListener);

		/**This code updates the text field at every digit enter. takes way to long to calculate and leads to errors*/

		/*yearFromTextField.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				updatePlot();
			}
			public void removeUpdate(DocumentEvent e){
				updatePlot();
			}
			public void insertUpdate(DocumentEvent e){
				updatePlot();
			}
			public void updatePlot(){
				int year = Integer.parseInt(yearFromTextField.getText());
				jPanel3.remove(3);
				setPlot3YearFrom(year);
				jPanel3.add(createPlot3(getPlot3SelectedArea(), getPlot3YearFrom(), getPlot3YearTo()));
				jPanel3.updateUI();
				//System.out.println("If you have not selected a second year, then the To-year will be automatically set to 2015" );
			}
		});*/
		jPanel3.add(yearFromTextField);

		jPanel3.add(plot3);
		jtp.add("Plot 3", jPanel3);

		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}


	private CategoryDataset createDatasetPlot1(int gender){
		CSVReader reader = new CSVReader();
		ArrayList<ChartDataObject> data = reader.dataGenPlot1();
		DefaultCategoryDataset result = new DefaultCategoryDataset();

		if(gender == 0){
			for(int i = 0; i < data.size(); i++){
				//add male births'
				result.addValue(data.get(i).counterMale, "Male", data.get(i).area);
				//				System.out.println("Area: " + data.get(i).area
				//	        						+ " Male: " + data.get(i).counterMale 
				//	        						+ " Female: " + data.get(i).counterFemale);
				//add female births'
				result.addValue(data.get(i).counterFemale, "Female", data.get(i).area);
			}
			return result;
		}
		else if(gender ==1){
			for(int i = 0; i<data.size(); i++){
				result.addValue(data.get(i).counterMale, "Male", data.get(i).area);
			}
			return result;
		}
		else{
			for(int i = 0; i<data.size(); i++){
				result.addValue(data.get(i).counterFemale, "Female", data.get(i).area);
			}
			return result;
		}


	}

	private PieDataset createDatasetPlot2(){
		CSVReader reader = new CSVReader();
		int[] data = reader.dataGenPlot2();
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Male", data[0]);
		result.setValue("Female", data[1]);
		return result;
	}

	private CategoryDataset createDatasetPlot3(String area, int from, int to){
		CSVReader reader = new CSVReader();
		ArrayList<ChartDataObject> data = reader.dataGenPlot3(area, from, to);
		DefaultCategoryDataset result = new DefaultCategoryDataset();

		for(int i = 0; i < data.size(); i++){
			String birthyear = Integer.toString(data.get(i).birthyear);
			result.addValue(data.get(i).counterMale, "Male", birthyear);
			result.addValue(data.get(i).counterFemale, "Female", birthyear);
		}
		return result;
	}

	private ChartPanel createPlot1(int gender){
		JFreeChart chart1 = ChartFactory.createStackedBarChart(
				"Plot 1 - Total number of births per area in 2015", 						//chart title
				"Zurich Areas", 				//domain axis label x-axis
				"Number of Births", 			//range axis label y-axis
				createDatasetPlot1(gender), 	//data
				PlotOrientation.VERTICAL,		//plot orientation
				true,							//legend
				true,							//tooltips
				false							//urls
				);

		SubCategoryAxis domainAxis = new SubCategoryAxis("Zurich Area");
		domainAxis.setCategoryMargin(0.3);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		CategoryPlot plot = (CategoryPlot) chart1.getPlot();
		plot.setDomainAxis(domainAxis);

		return new ChartPanel(chart1){

		};
	}

	private ChartPanel createPlot2() {
		JFreeChart chart2 = ChartFactory.createPieChart("Plot 2 - Total number of births in Zurich in 2015", createDatasetPlot2());

		return new ChartPanel(chart2) {

		};
	}

	private ChartPanel createPlot3(String area, int from, int to){
		if(from <1993 || from > 2015){
			from = 1993;
			System.out.println("The 'from'-number was not valid. Please enter a year between 1993 and 2015. The year has been set to 1993");
		}
		if(to < 1993 || to > 2015){
			to = 2015;
			System.out.println("The 'to'-number was not valid. Please enter a year between 1993 and 2015. The year has been set to 2015");
		}
		if(from > to){
			int n;
			n = from;
			from = to;
			to = n;
			System.out.println("The first entered year has to be lower than the second one. Your chosen years have been reversed.");
		}
		
		JFreeChart chart3 = ChartFactory.createStackedBarChart(
				"Plot 3 - Total number of births per area in a time interval", 							//chart title
				"Year of Births",					//domain axis label, x-axis
				"Number of Births",					//range axis label, y-axis
				createDatasetPlot3(area, from, to), //data
				PlotOrientation.VERTICAL,			//plot orientation
				true,								//legend
				true,								//tooltips
				false								//URLS
				);

		SubCategoryAxis domainAxis = new SubCategoryAxis("Year of Births");
		domainAxis.setCategoryMargin(0.3);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		CategoryPlot plot = (CategoryPlot) chart3.getPlot();
		plot.setDomainAxis(domainAxis);

		//create Median Line
		double plot3Median = calculateMedian(area, from, to);
		ValueMarker median = new ValueMarker(plot3Median);
		median.setPaint(Color.cyan);
		plot.addRangeMarker(median);

		//create Standard Deviation Line
		double plot3StdDev = calculateStdDev(area, from, to);
		ValueMarker stdDev = new ValueMarker(plot3StdDev);
		stdDev.setPaint(Color.GREEN);
		plot.addRangeMarker(stdDev);

		//create  and set Legend
		plot.setFixedLegendItems(createLegendItems());
		return new ChartPanel(chart3){
		};
	}

	private LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();
		LegendItem item1 = new LegendItem("Male", Color.RED);
		LegendItem item2 = new LegendItem ("Female", Color.BLUE);
		LegendItem item3 = new LegendItem ("Median", Color.CYAN);
		LegendItem item4 = new LegendItem ("Standard Deviation", Color.GREEN);
		result.add(item1);
		result.add(item2);
		result.add(item3);
		result.add(item4);
		return result;
	}

	private double calculateStdDev(String area, int from, int to){
		double stdDev = 0;
		double mean = calculateMedian(area, from, to);
		double x = 0;
		double y = 0;
		double sum = 0;
		int size = reader.dataGenPlot3(area, from, to).size();

		for(int i = 0; i< size; i++){
			x = reader.dataGenPlot3(area, from, to).get(i).counterFemale
					+ reader.dataGenPlot3(area, from, to).get(i).counterMale;
			y = x - mean;
			y = Math.abs(y);
			y = Math.pow(y, 2);
			sum = sum + y;
		}
		stdDev = sum/reader.dataGenPlot3(area, from, to).size();
		stdDev = Math.sqrt(stdDev);
		return stdDev;
	}

	private double calculateMedian(String area, int from, int to){
		double median = 0;
		int counter = 0;
		int size = reader.dataGenPlot3(area, from, to).size();

		for(int i = 0; i < size; i++){
			counter = counter + reader.dataGenPlot3(area, from, to).get(i).counterFemale
					+ reader.dataGenPlot3(area, from, to).get(i).counterMale;
		}
		if(size == 0){
			size = 1;
		}
		median = counter / size;


		return median;
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new TabChart().display();
			}
		});
	}
}