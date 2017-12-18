package cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import tsp.algorithm.Algorithm;
import tsp.algorithm.neighborhood.InsertNeighborhoodGenerator;
import tsp.algorithm.neighborhood.InvertNeighborhoodGenerator;
import tsp.algorithm.neighborhood.NeighborhoodGenerator;
import tsp.algorithm.neighborhood.SwapNeighborhoodGenerator;
import tsp.algorithm.path.Path;
import tsp.algorithm.tabu.TabuList;
import tsp.algorithm.tabu.TabuListDisabled;
import tsp.algorithm.tabu.TabuListImpl;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.algorithm.thread.DiversificationChecker;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class CommandLineInterface {

	private boolean terminated = false;

	private Scanner scannerSysIn = new Scanner(System.in);

	private NeighborhoodGenerator currentNeighborhoodGenerator = new SwapNeighborhoodGenerator();
	private AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
	private DiversificationChecker diversificationChecker = null;
	private TabuList currentTabuList = new TabuListImpl(10);

	private Instance loadedInstance = null;

	private List<NeighborhoodGenerator> availableNeighborhoodGenerators = new ArrayList<NeighborhoodGenerator>();
	private List<TabuList> availableTabuLists = new ArrayList<TabuList>();

	public CommandLineInterface() {
		// add neighborhood generators
		availableNeighborhoodGenerators.add(new SwapNeighborhoodGenerator());
		availableNeighborhoodGenerators.add(new InsertNeighborhoodGenerator());
		availableNeighborhoodGenerators.add(new InvertNeighborhoodGenerator());

		// add tabu lists
		availableTabuLists.add(new TabuListImpl(5));
		availableTabuLists.add(new TabuListDisabled());
	}

	public void enter() {

		while (!terminated) {
			clearScreen();

			showCurrentConfiguration();
			showMainMenuOptions();
		}
	}

	private void showMainMenuOptions() {
		System.out.println("============================");
		System.out.println("Opcje: ");

		System.out.println("1. Wczytaj instancje z pliku");
		System.out.println("2. WprowadŸ kryterium stopu");
		System.out.println("3. W³¹cz/wy³¹cz dywersyfikacje");
		System.out.println("4. W³¹cz/wy³¹cz listê tabu");
		System.out.println("5. Wybierz s¹siedztwo");
		System.out.println("6. Uruchom algorytm");
		System.out.println("7. WyjdŸ");

		System.out.println("Twój wybór: ");

		int pickedOne = -1;
		do {
			try {
				pickedOne = Integer.parseInt(scannerSysIn.nextLine());
			} catch (Exception e) {
				pickedOne = 0;
			}
		} while (pickedOne < 1 || pickedOne > 7);

		if (pickedOne == 1) {
			processInstanceLoad();
		} else if (pickedOne == 2) {
			processStopCriteriumConfig();
		} else if (pickedOne == 3) {
			processDiversificationConfig();
		} else if (pickedOne == 4) {
			processTabuListConfig();
		} else if (pickedOne == 5) {

			processNeighborhoodGeneratorPick();
		} else if (pickedOne == 6) {
			processAlgorithmRun();
		} else if (pickedOne == 7) {
			terminated = true;
		}

	}

	private void processTabuListConfig() {
		clearScreen();
		for (int i = 0; i < availableTabuLists.size(); i++) {
			System.out.println(i + ") " + availableTabuLists.get(i));
		}

		System.out.println("Twój wybór: ");

		int pickedOne = -1;
		do {
			pickedOne = scannerSysIn.nextInt();
		} while (pickedOne < 0 || pickedOne >= availableTabuLists.size());

		if (availableTabuLists.get(pickedOne) instanceof TabuListImpl) {
			System.out.println("WprowadŸ kadencjê: ");

			int enteredCadency = -1;
			do {
				try {
					enteredCadency = Integer.parseInt(scannerSysIn.nextLine());
				} catch (Exception e) {
					enteredCadency = 0;
				}
			} while (enteredCadency < 1);

			currentTabuList = new TabuListImpl(enteredCadency);
		} else {
			currentTabuList = new TabuListDisabled();
		}
	}

	private void processAlgorithmRun() {
		if (loadedInstance != null) {

			currentTabuList.clear();
			Algorithm algorithm = new Algorithm(currentTabuList, currentNeighborhoodGenerator);

			if (diversificationChecker != null) {

				DiversificationChecker newDiversificationChecker = new DiversificationChecker(
						diversificationChecker.getDiversificationIntervalMs());
				algorithm.setDiversificationChecker(newDiversificationChecker);
			}

			AlgorithmTerminator newAlgorithmTerminator = new AlgorithmTerminator();
			newAlgorithmTerminator.setTimeLimitMs(algorithmTerminator.getTimeLimitMs());

			newAlgorithmTerminator.setAlgorithm(algorithm);
			algorithm.setAlgorithmTerminator(newAlgorithmTerminator);

			BestDistanceSampler bestDistanceSampler = new BestDistanceSampler(algorithm);
			algorithm.setBestDistanceSampler(bestDistanceSampler);

			XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
			XYSeries series = new XYSeries("Random");
			xySeriesCollection.addSeries(series);

			JFreeChart jfreechart = ChartFactory.createXYLineChart("D³ugoœæ najlepszej znalezionej trasy od czasu",
					"Czas[s]", "D³ugoœæ", (XYDataset) xySeriesCollection, PlotOrientation.VERTICAL, true, true, false);

			ChartFrame cf = new ChartFrame("D³ugoœæ najlepszej znalezionej trasy po czasie", jfreechart);
			cf.setSize(800, 600);
			cf.show();
			bestDistanceSampler.setPlotSerie(series);

			Path bestPath = algorithm.execute(loadedInstance);

			System.out.println(bestPath);
			System.out.println("D³ugoœæ œcie¿ki: " + loadedInstance.calculateTotalDistance(bestPath));

			processPressToContinue();
		} else {
			System.out.println("Najpierw wczytaj instancjê.");
			processPressToContinue();
		}

	}

	private void processPressToContinue() {
		System.out.println("Wciœnij enter aby kontynuowaæ...");
		scannerSysIn.nextLine();
	}

	private void processInstanceLoad() {
		clearScreen();
		System.out.println("Nazwa pliku (œcie¿ka wzglêdna): ");

		String enteredFilename = scannerSysIn.nextLine();

		InstanceFileReader instanceFileReader = new InstanceFileReader();
		try {
			loadedInstance = instanceFileReader.read(enteredFilename);
		} catch (Exception e) {
			System.out.println("Instancja nie mog³a zostaæ wczytana.");
			processPressToContinue();
			return;
		}

		System.out.println("Instancja wczytana pomyœlnie");
		processPressToContinue();
	}

	private void processStopCriteriumConfig() {
		clearScreen();

		System.out.println("WprowadŸ limit czasowy wykonywania algorytmu (w sekundach): ");

		long enteredTimeSeconds = -1;
		do {
			try {
				enteredTimeSeconds = Long.parseLong(scannerSysIn.nextLine());
			} catch (Exception e) {
				enteredTimeSeconds = 0;
			}
		} while (enteredTimeSeconds <= 0);

		algorithmTerminator.setTimeLimitMs(enteredTimeSeconds * 1000);
	}

	private void processDiversificationConfig() {
		if (diversificationChecker == null) {
			clearScreen();
			System.out
					.println("WprowadŸ czas, po którym nast¹pi dywersyfikacja w przypadku braku poprawy wyniku (s): ");

			int enteredTime = -1;
			do {
				try {
					enteredTime = Integer.parseInt(scannerSysIn.nextLine());
				} catch (Exception e) {
					enteredTime = 0;
				}
			} while (enteredTime < 1);

			diversificationChecker = new DiversificationChecker(enteredTime * 1000);
		} else {
			diversificationChecker = null;
		}
	}

	private void processNeighborhoodGeneratorPick() {
		clearScreen();
		for (int i = 0; i < availableNeighborhoodGenerators.size(); i++) {
			System.out.println(i + ") " + availableNeighborhoodGenerators.get(i));
		}

		System.out.println("Twój wybór: ");

		int pickedOne = -1;
		do {
			pickedOne = scannerSysIn.nextInt();
		} while (pickedOne < 0 || pickedOne >= availableNeighborhoodGenerators.size());

		currentNeighborhoodGenerator = availableNeighborhoodGenerators.get(pickedOne);
	}

	private void showCurrentConfiguration() {
		System.out.println("Instancja:" + ((loadedInstance == null) ? "Brak" : loadedInstance.getName()));
		System.out.println("");
		System.out.println("S¹siedztwo: " + currentNeighborhoodGenerator);
		System.out.println("Lista tabu: " + currentTabuList);
		System.out.println("Dywersyfikacja: " + ((diversificationChecker == null) ? "Brak" : diversificationChecker));
		System.out.println("Zatrzymaj po: " + algorithmTerminator.getTimeLimitMs() / 1000 + "s");
	}

	private void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}
}
