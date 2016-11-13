import org.jfree.ui.RefineryUtilities;

public class Main {

	public static void main(String[] args) {
		CSVReader reader = new CSVReader();
		//Create Plot 1
		final Plot1 plot1 = new Plot1("Plot1");
        plot1.pack();
        RefineryUtilities.centerFrameOnScreen(plot1);
        plot1.setVisible(true);

		
		
		//Create Plot 2
		Plot2 plot2 = new Plot2("Plot 2", "Plot 2");
        plot2.pack();
        plot2.setVisible(true);
        
        
        //DEMOCODE
//        final StackedBarChartDemo4 demo = new StackedBarChartDemo4("Stacked Bar Chart Demo 4");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
        

//        final AreaChartDemo demo = new AreaChartDemo("Area Chart Demo");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);

    }
}