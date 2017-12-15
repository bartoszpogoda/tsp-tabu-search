package tsp;

import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.sun.org.apache.xpath.internal.operations.Div;

import tsp.algorithm.Algorithm;
import tsp.algorithm.BestDistanceHistory;
import tsp.algorithm.neighborhood.InsertNeighborhoodGenerator;
import tsp.algorithm.neighborhood.NeighborhoodGenerator;
import tsp.algorithm.neighborhood.SwapNeighborhoodGenerator;
import tsp.algorithm.tabu.TabuList;
import tsp.algorithm.tabu.TabuListDisabled;
import tsp.algorithm.tabu.TabuListImpl;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.algorithm.thread.DiversificationChecker;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
		InstanceFileReader instanceFileReader = new InstanceFileReader();
		Instance instance = instanceFileReader.read("input/eil101.tsp");

		AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
		algorithmTerminator.setTimeLimitMs(6000000);
		
		DiversificationChecker diversificationChecker = new DiversificationChecker(30000);
		
		
		TabuList tabuList = new TabuListImpl(101*3); //new TabuListDisabled();//
		
		//NeighborhoodGenerator neighborhoodGenerator = new SwapNeighborhoodGenerator();
		NeighborhoodGenerator neighborhoodGenerator = new InsertNeighborhoodGenerator();
		
		Algorithm algorithm = new Algorithm(tabuList, neighborhoodGenerator);
		algorithm.setAlgorithmTerminator(algorithmTerminator);
		algorithm.setDiversificationChecker(diversificationChecker);
		
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
	    XYSeries series = new XYSeries("Random");
	    xySeriesCollection.addSeries(series);
	    
	    JFreeChart jfreechart = ChartFactory.createXYLineChart(
	            "xd", "X", "Y", (XYDataset) xySeriesCollection,
	            PlotOrientation.VERTICAL, true, true, false);
	    
	   ChartFrame cf = new ChartFrame("xD", jfreechart);
	   cf.setSize(800, 600);
	   cf.show();
		
		BestDistanceSampler bestDistanceSampler = new BestDistanceSampler(new BestDistanceHistory(), algorithm);
		bestDistanceSampler.setPlotSerie(series);
		algorithm.setBestDistanceSampler(bestDistanceSampler);
		
		algorithm.execute(instance);

        
	   
	}

}
