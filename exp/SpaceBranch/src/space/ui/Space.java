package space.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import space.ui.status.StatusPane;
import space.ui.map.MapPane;
import space.ui.planets.PlanetsPane;
import space.ui.fleets.FleetsPane;
import space.ui.research.ResearchPane;

public class Space extends JFrame {
	private static final long serialVersionUID = 8872158514385315895L;
	
	private final JTabbedPane rootPane;
	
	private final StatusPane statusPane;
	private final MapPane mapPane;
	private final PlanetsPane planetsPane;
	private final FleetsPane fleetsPane;
	private final ResearchPane researchPane;
	private final JPanel diplomacyPane;
	
	private Space(){
		rootPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		
		rootPane.addTab("Overview", statusPane = new StatusPane());
		rootPane.addTab("Galaxy", mapPane = new MapPane());
		rootPane.addTab("Planets", planetsPane = new PlanetsPane());
		rootPane.addTab("Fleets", fleetsPane = new FleetsPane());
		rootPane.addTab("R&D", researchPane = new ResearchPane());
		rootPane.addTab("Diplomacy", diplomacyPane = new JPanel());
	}
	
	private void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(rootPane);
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new Space()).init();
	}

}
