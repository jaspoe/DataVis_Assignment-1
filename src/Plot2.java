import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;



public class Plot2 extends JFrame {

        private static final long serialVersionUID = 1L;
        CSVReader reader = new CSVReader();
        

        public Plot2(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        //get the data for the dataset
        int[] data = reader.dataGenPlot2();
        //System.out.println("Male: " + data[0] + " Female: " + data[1]);
        // This will create the dataset
        PieDataset dataset = createDataset(data);
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }

        private  PieDataset createDataset(int[] data) {
        	DefaultPieDataset result = new DefaultPieDataset();
              result.setValue("Male", data[0]);
              result.setValue("Female", data[1]);
    
            return result;
        }

        /**
         * Creates a chart
         */
        private JFreeChart createChart(PieDataset dataset, String title) {

            JFreeChart chart = ChartFactory.createPieChart3D(
                title,                  // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false
            );

            PiePlot3D plot = (PiePlot3D) chart.getPlot();
            plot.setStartAngle(290);
            plot.setDirection(Rotation.CLOCKWISE);
            plot.setForegroundAlpha(0.5f);
            return chart;

        }



}

