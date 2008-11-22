package space.ui.designer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.model.Stats;

import space.display.DisplayStats;
import space.model.Resource;
import space.model.component.IComponent;
import space.model.component.IComponentType;
import space.model.tech.Technology;
import space.ui.designer.LibraryPanel.ComponentNode;

public class InfoPanel<I> 
		extends JPanel 
		implements TreeSelectionListener, IComponentTransferer<I> 
{
	private static final long serialVersionUID = 7574923042502794728L;
	
	private final Vector<String> exclusionList;
	private final Stack<JLabel[]> recycle;
	
	private BufferedImage bi;
	private ImageIcon icon;
	
	private final JLabel name;
	private final JLabel costPro;
	private final JLabel costMin1;
	private final JLabel costMin2;
	private final JLabel costMin3;
	private final JLabel costMin4;
	private final JLabel costMin5;
	private final JTextArea description;
	private final HashMap<String,JLabel[]> displayList;
	
	private final SpringLayout layout;
	
	private IComponent<I> component;
	private int maxSecondary = 0;
	
	public InfoPanel(){
		super();
		
		this.setPreferredSize(new Dimension(100,320));
		bi = new BufferedImage(
				ComponentTile.TILE_SIZE.width,
				ComponentTile.TILE_SIZE.height,
				BufferedImage.TYPE_4BYTE_ABGR
		);
		icon = new ImageIcon(bi);
		
		exclusionList = new Vector<String>();
		exclusionList.add("cost" + Resource.PRODUCTION.shortName());
		exclusionList.add("cost" + Resource.MINERAL1.shortName());
		exclusionList.add("cost" + Resource.MINERAL2.shortName());
		exclusionList.add("cost" + Resource.MINERAL3.shortName());
		exclusionList.add("cost" + Resource.MINERAL4.shortName());
		exclusionList.add("cost" + Resource.MINERAL5.shortName());
		exclusionList.add(DisplayStats.STAT_ROTATION);
		exclusionList.add(DisplayStats.STAT_X_POS);
		exclusionList.add(DisplayStats.STAT_Y_POS);
		exclusionList.add(Technology.TECH1.getName());
		exclusionList.add(Technology.TECH2.getName());
		exclusionList.add(Technology.TECH3.getName());
		exclusionList.add(Technology.TECH4.getName());
		exclusionList.add(Technology.TECH5.getName());
		exclusionList.add(Technology.TECH6.getName());
		exclusionList.add(Technology.TECH7.getName());
		exclusionList.add(Technology.TECH8.getName());
		
		recycle = new Stack<JLabel[]>();
		
		name = new JLabel();
		name.setIcon(icon);
		JSeparator topRule = new JSeparator();
		JLabel proName = new JLabel(Resource.PRODUCTION.shortName() + ": ");
			proName.setForeground(Resource.PRODUCTION.uiColor().darker());
			costPro = new JLabel("0.0");
			costPro.setForeground(Resource.PRODUCTION.uiColor().darker());
		JLabel min1Name = new JLabel(Resource.MINERAL1.shortName() + ": ");
			min1Name.setForeground(Resource.MINERAL1.uiColor().darker());
			costMin1 = new JLabel("0.0");
			costMin1.setForeground(Resource.MINERAL1.uiColor().darker());
		JLabel min2Name = new JLabel(Resource.MINERAL2.shortName() + ": ");
			min2Name.setForeground(Resource.MINERAL2.uiColor().darker());
			costMin2 = new JLabel("0.0");
			costMin2.setForeground(Resource.MINERAL2.uiColor().darker());
		JLabel min3Name = new JLabel(Resource.MINERAL3.shortName() + ": ");
			min3Name.setForeground(Resource.MINERAL3.uiColor().darker());
			costMin3 = new JLabel("0.0");
			costMin3.setForeground(Resource.MINERAL3.uiColor().darker());
		JLabel min4Name = new JLabel(Resource.MINERAL4.shortName() + ": ");
			min4Name.setForeground(Resource.MINERAL4.uiColor().darker());
			costMin4 = new JLabel("0.0");
			costMin4.setForeground(Resource.MINERAL4.uiColor().darker());
		JLabel min5Name = new JLabel(Resource.MINERAL5.shortName() + ": ");
			min5Name.setForeground(Resource.MINERAL5.uiColor().darker());
			costMin5 = new JLabel("0.0");
			costMin5.setForeground(Resource.MINERAL5.uiColor().darker());
		JSeparator bottomRule = new JSeparator();
		description = new JTextArea("none");
		description.setEditable(false);
		displayList = new HashMap<String,JLabel[]>();
		
		layout = new SpringLayout();
		this.setLayout(layout);
		
		this.add(name);
		this.add(topRule);
		this.add(proName);this.add(costPro);
		this.add(min1Name);this.add(costMin1);
		this.add(min2Name);this.add(costMin2);
		this.add(min3Name);this.add(costMin3);
		this.add(min4Name);this.add(costMin4);
		this.add(min5Name);this.add(costMin5);
		this.add(bottomRule);
		this.add(description);
		
		//Icon
		//Name
		//-Rule-
		//Production Cost:	#
		//Mineral1 Cost:	#
		//Mineral2 Cost:	#
		//Mineral3 Cost:	#
		//Mineral4 Cost:	#
		//Mineral5 Cost:	#
		//-Rule-
		//Description
		//Stats[]:			#
		
		layout.putConstraint(SpringLayout.WEST, name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, topRule, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, topRule, 5, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.EAST, topRule, -5, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.WEST, proName, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, proName, 5, SpringLayout.SOUTH, topRule);
		layout.putConstraint(SpringLayout.EAST, costPro, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costPro, 0, SpringLayout.NORTH, proName);
		
		layout.putConstraint(SpringLayout.WEST, min1Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min1Name, 5, SpringLayout.SOUTH, proName);
		layout.putConstraint(SpringLayout.EAST, costMin1, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin1, 0, SpringLayout.NORTH, min1Name);
		
		layout.putConstraint(SpringLayout.WEST, min2Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min2Name, 5, SpringLayout.SOUTH, min1Name);
		layout.putConstraint(SpringLayout.EAST, costMin2, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin2, 0, SpringLayout.NORTH, min2Name);
		
		layout.putConstraint(SpringLayout.WEST, min3Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min3Name, 5, SpringLayout.SOUTH, min2Name);
		layout.putConstraint(SpringLayout.EAST, costMin3, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin3, 0, SpringLayout.NORTH, min3Name);
		
		layout.putConstraint(SpringLayout.WEST, min4Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min4Name, 5, SpringLayout.SOUTH, min3Name);
		layout.putConstraint(SpringLayout.EAST, costMin4, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin4, 0, SpringLayout.NORTH, min4Name);
		
		layout.putConstraint(SpringLayout.WEST, min5Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min5Name, 5, SpringLayout.SOUTH, min4Name);
		layout.putConstraint(SpringLayout.EAST, costMin5, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin5, 0, SpringLayout.NORTH, min5Name);
		
		layout.putConstraint(SpringLayout.WEST, bottomRule, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, bottomRule, 5, SpringLayout.SOUTH, min5Name);
		layout.putConstraint(SpringLayout.EAST, bottomRule, -5, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.WEST, description, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH,description,5,SpringLayout.SOUTH,bottomRule);
		layout.putConstraint(SpringLayout.EAST, description, -5, SpringLayout.EAST, this);
	}
	
	private void select(IComponent<I> component){
		this.component = component;
		
		for(String key : displayList.keySet()){
			JLabel[] labels = displayList.remove(key);
			
			for(JLabel label : labels){
				this.remove(label);
				layout.removeLayoutComponent(label);
			}
			
			recycle.push(labels);
		}
		
		if(component != null){
			name.setText(component.getName());
			description.setText(component.getDescription());
			
			costPro.setText(component.getCost(Resource.PRODUCTION) + ".0");
			costMin1.setText(component.getCost(Resource.MINERAL1) + ".0");
			costMin2.setText(component.getCost(Resource.MINERAL2) + ".0");
			costMin3.setText(component.getCost(Resource.MINERAL3) + ".0");
			costMin4.setText(component.getCost(Resource.MINERAL4) + ".0");
			costMin5.setText(component.getCost(Resource.MINERAL5) + ".0");
			
			Stats<String> stats  = (Stats<String>)component.getStats().clone();
			Set<String> keys = stats.keySet();
			
			if(component.getSecondaryTypes().size() > maxSecondary){
				for(int i = maxSecondary; i <= component.getSecondaryTypes().size(); i++){
					String pre = "Secondary" + i + ":";
					
					exclusionList.add(pre + "cost" + Resource.PRODUCTION.shortName());
					exclusionList.add(pre + "cost" + Resource.MINERAL1.shortName());
					exclusionList.add(pre + "cost" + Resource.MINERAL2.shortName());
					exclusionList.add(pre + "cost" + Resource.MINERAL3.shortName());
					exclusionList.add(pre + "cost" + Resource.MINERAL4.shortName());
					exclusionList.add(pre + "cost" + Resource.MINERAL5.shortName());
					exclusionList.add(pre + DisplayStats.STAT_ROTATION);
					exclusionList.add(pre + DisplayStats.STAT_X_POS);
					exclusionList.add(pre + DisplayStats.STAT_Y_POS);
					exclusionList.add(pre + Technology.TECH1.getName());
					exclusionList.add(pre + Technology.TECH2.getName());
					exclusionList.add(pre + Technology.TECH3.getName());
					exclusionList.add(pre + Technology.TECH4.getName());
					exclusionList.add(pre + Technology.TECH5.getName());
					exclusionList.add(pre + Technology.TECH6.getName());
					exclusionList.add(pre + Technology.TECH7.getName());
					exclusionList.add(pre + Technology.TECH8.getName());
					exclusionList.add(pre + IComponentType.STAT_MASS);
					exclusionList.add(pre + IComponentType.STAT_HITPOINTS);
					exclusionList.add(pre + IComponentType.STAT_SIGNATURE);
				}
				
				maxSecondary=component.getSecondaryTypes().size();
			}
			keys.removeAll(exclusionList);
			
			String previous = null;
			for(String key : keys){
				JLabel[] labels = !recycle.isEmpty() ? recycle.pop():new JLabel[]{new JLabel(),new JLabel()};
				
				if(key.startsWith("Secondary")){
					labels[0].setText("+" + key.split(":")[1] + ":");
				}else{
					labels[0].setText(key + ":");
				}
				int val = component.value(key).intValue();
				labels[1].setText(val + ".0");
				
				this.add(labels[0]);
				this.add(labels[1]);
				
				if(previous == null){
					layout.putConstraint(
							SpringLayout.WEST, labels[0], 5, SpringLayout.WEST, this
					);
					layout.putConstraint(
							SpringLayout.NORTH, 
							labels[0], 
							5, 
							SpringLayout.SOUTH, 
							description
					);
					layout.putConstraint(
							SpringLayout.EAST, labels[1], -5, SpringLayout.EAST, this
					);
					layout.putConstraint(
							SpringLayout.NORTH, labels[1], 0, SpringLayout.NORTH, labels[0]
					);
				}else{
					layout.putConstraint(
							SpringLayout.WEST, labels[0], 5, SpringLayout.WEST, this
					);
					layout.putConstraint(
							SpringLayout.NORTH, 
							labels[0], 
							5, 
							SpringLayout.SOUTH, 
							displayList.get(previous)[0]
					);
					layout.putConstraint(
							SpringLayout.EAST, labels[1], -5, SpringLayout.EAST, this
					);
					layout.putConstraint(
							SpringLayout.NORTH, labels[1], 0, SpringLayout.NORTH, labels[0]
					);
				}
				
				previous = key;
				displayList.put(key, labels);
			}
		}else{
			costPro.setText("0.0");
			costMin1.setText("0.0");
			costMin2.setText("0.0");
			costMin3.setText("0.0");
			costMin4.setText("0.0");
			costMin5.setText("0.0");
		}
		
		Graphics2D g2 = bi.createGraphics();
		g2.clearRect(0, 0, ComponentTile.TILE_SIZE.width, ComponentTile.TILE_SIZE.height);
		component.getDisplayFactory().display(component, g2);
		g2.finalize();
		g2.dispose();
		
		layout.layoutContainer(this);
		updateUI();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		if(arg0.getPath().getLastPathComponent() instanceof ComponentNode){
			ComponentNode n = (ComponentNode)arg0.getPath().getLastPathComponent();
			select((IComponent)n.getUserObject());
		}
	}

	@Override
	public IComponent<I> getSelectedComponent() {
		return component;
	}

	@Override
	public boolean isFinite() {
		// TODO: This will need to be sorted eventually.
		return false;
	}

	@Override
	public void removeComponent(IComponent<I> component) {
		// TODO: This will need to be sorted too.
		select(null);
	}

	@Override
	public void setSelectedComponent(IComponent<I> component) {
		select(component);
	}
}
