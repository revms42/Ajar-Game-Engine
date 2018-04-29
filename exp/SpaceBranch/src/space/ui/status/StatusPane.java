package space.ui.status;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

public class StatusPane extends JPanel {
	private static final long serialVersionUID = -4985003163537423594L;

	private final JPanel topPane;
	
	private final JLabel empireIcon; private final JLabel empireName;
	private final JLabel empireYear;
	private final JLabel empirePlanets; private final JLabel planetCount;
	private final JLabel empirePopulation; private final JLabel populationCount;
	private final JLabel empireFleets; private final JLabel fleetsCount;
	
	private final MessagePane messagePane;
	
	public StatusPane(){
		topPane = new JPanel();
		topPane.setPreferredSize(new Dimension(200,200));
		topPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		
		SpringLayout topPaneLayout = new SpringLayout();
		topPane.setLayout(topPaneLayout);
		
		topPane.add(empireIcon = new JLabel("*Icon*"));
		topPane.add(empireName = new JLabel("*Name*"));
		
		topPane.add(empireYear = new JLabel("*Year*"));
		
		topPane.add(empirePlanets = new JLabel("Planets:"));
		topPane.add(planetCount = new JLabel("*Count*"));
		
		topPane.add(empirePopulation = new JLabel("Population:"));
		topPane.add(populationCount = new JLabel("*Count*"));
		
		topPane.add(empireFleets = new JLabel("Fleets:"));
		topPane.add(fleetsCount = new JLabel("*Count*"));
		
		layoutTopPane(topPaneLayout);
		
		this.setLayout(new BorderLayout());
		this.add(topPane,BorderLayout.NORTH);
		this.add(messagePane = new MessagePane(), BorderLayout.CENTER);
	}
	
	private void layoutTopPane(SpringLayout tpl){
		//The empire icon.
		tpl.putConstraint(
				SpringLayout.EAST, empireIcon, 
				-2,
				SpringLayout.HORIZONTAL_CENTER, topPane
		);
		tpl.putConstraint(
				SpringLayout.NORTH, empireIcon, 
				5,
				SpringLayout.NORTH, topPane
		);
		
		//The empire name, to the right of the icon.
		tpl.putConstraint(
				SpringLayout.WEST, empireName, 
				2,
				SpringLayout.HORIZONTAL_CENTER, topPane
		);
		tpl.putConstraint(
				SpringLayout.SOUTH, empireName, 
				0,
				SpringLayout.SOUTH, empireIcon
		);
		
		//The empire year, under the icon and name.
		tpl.putConstraint(
				SpringLayout.HORIZONTAL_CENTER, empireYear, 
				0,
				SpringLayout.HORIZONTAL_CENTER, topPane
		);
		tpl.putConstraint(
				SpringLayout.NORTH, empireYear, 
				5,
				SpringLayout.SOUTH, empireIcon
		);
		
		//The empire planets, lined up with icon under year.
		tpl.putConstraint(
				SpringLayout.EAST, empirePlanets, 
				0,
				SpringLayout.EAST, empireIcon
		);
		tpl.putConstraint(
				SpringLayout.NORTH, empirePlanets, 
				5,
				SpringLayout.SOUTH, empireYear
		);
		
		//The planet count, lined up with name under year.
		tpl.putConstraint(
				SpringLayout.WEST, planetCount, 
				0,
				SpringLayout.WEST, empireName
		);
		tpl.putConstraint(
				SpringLayout.NORTH, planetCount, 
				5,
				SpringLayout.SOUTH, empireYear
		);
		
		//The empire population, lined up with icon under empireCount.
		tpl.putConstraint(
				SpringLayout.EAST, empirePopulation, 
				0,
				SpringLayout.EAST, empireIcon
		);
		tpl.putConstraint(
				SpringLayout.NORTH, empirePopulation, 
				5,
				SpringLayout.SOUTH, empirePlanets
		);
		
		//The population count, lined up with name under planet count.
		tpl.putConstraint(
				SpringLayout.WEST, populationCount, 
				0,
				SpringLayout.WEST, empireName
		);
		tpl.putConstraint(
				SpringLayout.NORTH, populationCount, 
				5,
				SpringLayout.SOUTH, planetCount
		);
		
		//The empire fleets, lined up with icon under empirePopulation.
		tpl.putConstraint(
				SpringLayout.EAST, empireFleets, 
				0,
				SpringLayout.EAST, empireIcon
		);
		tpl.putConstraint(
				SpringLayout.NORTH, empireFleets, 
				5,
				SpringLayout.SOUTH, empirePopulation
		);
		
		//The fleet count, lined up with name under population count.
		tpl.putConstraint(
				SpringLayout.WEST, fleetsCount, 
				0,
				SpringLayout.WEST, empireName
		);
		tpl.putConstraint(
				SpringLayout.NORTH, fleetsCount, 
				5,
				SpringLayout.SOUTH, populationCount
		);
	}
}
