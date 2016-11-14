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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.SubCategoryAxis;
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

	private void display() {
		JFrame f = new JFrame("TabChart");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JTabbedPane jtp = new JTabbedPane();
		
		JPanel test = new JPanel(new FlowLayout(FlowLayout.LEADING));
		ChartPanel plot1 = createPlot1(0);
		test.add(new JButton(new AbstractAction("Female"){
			@Override
			public void actionPerformed(ActionEvent e){
				test.remove(test.getComponent(3));
				test.add(createPlot1(2));
				test.updateUI();
			}
		}));
		test.add(new JButton(new AbstractAction("Male"){
			@Override
			public void actionPerformed(ActionEvent e){
				test.remove(test.getComponent(3));
				test.add(createPlot1(1));
				test.updateUI();
			}
		}));
		test.add(new JButton(new AbstractAction("Both"){
			@Override
			public void actionPerformed(ActionEvent e){
				test.remove(test.getComponent(3));
				test.add(createPlot1(0));
				test.updateUI();
			}
		}));
		
		test.add(plot1);	
		jtp.addTab("Plot 1", test);
		
		
		jtp.add("Plot 2", createPlot2());
		f.add(jtp, BorderLayout.CENTER);
		
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private CategoryDataset createDatasetPlot1(int gender){
		CSVReader reader = new CSVReader();
		ArrayList<Plot1DataObject> data = reader.dataGenPlot1();
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		
		if(gender == 0){
			for(int i = 0; i < data.size(); i++){
				//add male births'
				result.addValue(data.get(i).counterMale, "Male", data.get(i).area);
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
			
		SubCategoryAxis domainAxis = new SubCategoryAxis("Zurich areas");
        domainAxis.setCategoryMargin(0.05);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		return new ChartPanel(chart1){
			@Override
			public Dimension getPreferredSize(){
				return new Dimension(480, 240);
			}
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

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TabChart().display();
			}
		});
	}
}