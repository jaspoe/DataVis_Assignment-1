import java.awt.BorderLayout;
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
		jtp.add("Plot 1", createPlot1());
		jtp.add("Plot 2", createPlot2());
		f.add(jtp, BorderLayout.CENTER);
//		        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		        p.add(new JButton(new AbstractAction("Add") {
//		            @Override
//		            public void actionPerformed(ActionEvent e) {
//		                jtp.add(String.valueOf(++n), createPlot1());
//		                jtp.setSelectedIndex(n - 1);
//		            }
//		        }));
//		        f.add(p, BorderLayout.SOUTH);

		JPanel test = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JCheckBox maleCheckbox = new JCheckBox("Male");
		JCheckBox femaleCheckbox = new JCheckBox("Female");
		test.add(createPlot1());
		test.add(maleCheckbox);
		test.add(femaleCheckbox);
		
		jtp.addTab("test", test);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private CategoryDataset createDatasetPlot1(){
		CSVReader reader = new CSVReader();
		ArrayList<Plot1DataObject> data = reader.dataGenPlot1();
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		for(int i = 0; i < data.size(); i++){
			//add male births'
			result.addValue(data.get(i).counterMale, "Male", data.get(i).area);
			//add female births'
			result.addValue(data.get(i).counterFemale, "Female", data.get(i).area);
		}
		return result;
	}
	
	private PieDataset createDatasetPlot2(){
		CSVReader reader = new CSVReader();
		int[] data = reader.dataGenPlot2();
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Male", data[0]);
		result.setValue("Female", data[1]);
		return result;
	}

	private ChartPanel createPlot1(){
		JFreeChart chart1 = ChartFactory.createStackedBarChart(
				"Plot 1", 						//chart title
				"Zurich Areas", 				//domain axis label x-axis
				"Number of Births", 			//range axis label y-axis
				createDatasetPlot1(), 			//data
				PlotOrientation.VERTICAL,		//plot orientation
				true,							//legend
				true,							//tooltips
				false							//urls
				);
		
//		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
//        KeyToGroupMap map = new KeyToGroupMap("G1");
//        map.mapKeyToGroup("Female", "G1");
//        map.mapKeyToGroup("Male", "G1");
//        renderer.setSeriesToGroupMap(map); 
		
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