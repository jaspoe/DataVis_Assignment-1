import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
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
		//TODO: add 2 Text fields,  and 1 ok button
		String[] dropDownArray = new String[reader.dataGenDropdown().size()];
		for(int i = 0; i < reader.dataGenDropdown().size(); i++){
			dropDownArray[i] = reader.dataGenDropdown().get(i);
		}
		jPanel3.add(new JComboBox(dropDownArray));
		//TODO: check the input fields for correct inputs have to be int!
		JFormattedTextField textfield = new JFormattedTextField();
		
		jPanel3.add(new JButton(new AbstractAction("Create Plot"){
			@Override
			public void actionPerformed(ActionEvent e){
//				String[] plot3UserData = getPlot3UserData();
//				jPanel3.remove(4);
//				plot3 = createPlot3(plot3UserData[0], Integer.parseInt(plot3UserData[1]), Integer.parseInt(plot3UserData[2]));
//				jPanel1.updateUI();
			}
		}));
		
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
				"Plot 1", 						//chart title
				"Zurich Areas", 				//domain axis label x-axis
				"Number of Births", 			//range axis label y-axis
				createDatasetPlot1(gender), 			//data
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
			//			@Override
			//			public Dimension getPreferredSize(){
			//				return new Dimension(480, 240);
			//			}
		};
	}

	private ChartPanel createPlot2() {
		JFreeChart chart2 = ChartFactory.createPieChart("Plot 1", createDatasetPlot2());

		return new ChartPanel(chart2) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(480, 240);
			}
		};
	}

	private ChartPanel createPlot3(String area, int from, int to){
		JFreeChart chart3 = ChartFactory.createStackedBarChart(
				"Plot3", 							//chart title
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

		return new ChartPanel(chart3){
			//			@Override
			//			public Dimension getPreferredSize(){
			//				return new Dimension(480, 240);
			//			}
		};
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TabChart().display();
			}
		});
	}
}