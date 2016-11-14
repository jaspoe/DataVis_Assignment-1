//import java.awt.Color;
//import java.awt.GradientPaint;
//import java.awt.Paint;
//import java.sql.ResultSetMetaData;
//import java.util.ArrayList;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.LegendItem;
//import org.jfree.chart.LegendItemCollection;
//import org.jfree.chart.axis.CategoryLabelPositions;
//import org.jfree.chart.axis.SubCategoryAxis;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
//import org.jfree.data.KeyToGroupMap;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.GradientPaintTransformType;
//import org.jfree.ui.RefineryUtilities;
//import org.jfree.ui.StandardGradientPaintTransformer;
//
//
//public class Plot1 extends ApplicationFrame {
//	private static final long serialVersionUID = 1L;
//	CSVReader reader = new CSVReader();
//	
//	
//    public Plot1(final String title) {
//        super(title);
//        final CategoryDataset dataset = createDataset();
//        final JFreeChart chart = createChart(dataset);
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(590, 350));
//        setContentPane(chartPanel);
//    }
//    
//    /**
//     * Creates a sample dataset.
//     * 
//     * @return A sample dataset.
//     */
//    private CategoryDataset createDataset() {
//        DefaultCategoryDataset result = new DefaultCategoryDataset();
//        //result.addValue(10.1, "Product 3 (Middle East)", "Mar 04");
//        ArrayList<Plot1DataObject> data = reader.dataGenPlot1();
//        for(int i = 0; i < data.size(); i++){
//        	//add male births'
//        	result.addValue(data.get(i).counterMale, "Male", data.get(i).area);
//        	//add female births'
//        	result.addValue(data.get(i).counterFemale, "Female", data.get(i).area);
//        	//System.out.println("Area: " + data.get(i).area
//        	//					+ " Male: " + data.get(i).counterMale 
//        	//					+ " Female: " + data.get(i).counterFemale);
//        }
//        
//        return result;
//    }
//    
//    /**
//     * Creates a sample chart.
//     * 
//     * @param dataset  the dataset for the chart.
//     * 
//     * @return A sample chart.
//     */
//    private JFreeChart createChart(final CategoryDataset dataset) {
//
//        final JFreeChart chart = ChartFactory.createStackedBarChart(
//            "Plot 1",  					 // chart title
//            "Category",	                 // domain axis label
//            "Number of births",          // range axis label
//            dataset,                     // data
//            PlotOrientation.VERTICAL,    // the plot orientation
//            true,                        // legend
//            true,                        // tooltips
//            false                        // urls
//        );
//        
//        GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
//        KeyToGroupMap map = new KeyToGroupMap("G1");
//        map.mapKeyToGroup("Female", "G1");
//        map.mapKeyToGroup("Male", "G1");
//        renderer.setSeriesToGroupMap(map); 
//        
//        
//        
//        SubCategoryAxis domainAxis = new SubCategoryAxis("Zurich areas");
//        domainAxis.setCategoryMargin(0.05);
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
////        domainAxis.addSubCategory("Product 1");
////        domainAxis.addSubCategory("Product 2");
////        domainAxis.addSubCategory("Product 3");
//        
//        CategoryPlot plot = (CategoryPlot) chart.getPlot();
//        plot.setDomainAxis(domainAxis);
//        //plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
//        plot.setRenderer(renderer);
//        plot.setFixedLegendItems(createLegendItems());
//        return chart;
//        
//    }
//
//    /**
//     * Creates the legend items for the chart.  In this case, we set them manually because we
//     * only want legend items for a subset of the data series.
//     * 
//     * @return The legend items.
//     */
//    private LegendItemCollection createLegendItems() {
//        LegendItemCollection result = new LegendItemCollection();
//
//        LegendItem item1 = new LegendItem("Male", new Color(0xCC,0x00,0x00));
//        LegendItem item2 = new LegendItem ("Female", new Color(0x00, 0x66, 0xFF));
//        result.add(item1);
//        result.add(item2);
//        return result;
//    }
//
//}