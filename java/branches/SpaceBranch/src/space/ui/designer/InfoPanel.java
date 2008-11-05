package space.ui.designer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.model.Stats;

import space.model.Resource;
import space.model.ships.IComponent;

public class InfoPanel<I> 
		extends JPanel 
		implements TreeSelectionListener, IComponentTransferer<I> 
{
	private static final long serialVersionUID = 7574923042502794728L;
	
	private final Vector<String> exclusionList;
	private final Stack<JLabel[]> recycle;
	
	private final JPanel icon;
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
	
	public InfoPanel(){
		super();
		
		exclusionList = new Vector<String>();
		exclusionList.add(Resource.PRODUCTION.name());
		exclusionList.add(Resource.MINERAL1.name());
		exclusionList.add(Resource.MINERAL2.name());
		exclusionList.add(Resource.MINERAL3.name());
		exclusionList.add(Resource.MINERAL4.name());
		exclusionList.add(Resource.MINERAL5.name());
		
		recycle = new Stack<JLabel[]>();
		
		icon = new JPanel();
		name = new JLabel();
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
		displayList = new HashMap<String,JLabel[]>();
		
		layout = new SpringLayout();
		this.setLayout(layout);
		
		this.add(icon);
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
		
		layout.putConstraint(SpringLayout.WEST, icon, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, icon, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.SOUTH, icon);
		
		layout.putConstraint(SpringLayout.WEST, topRule, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, topRule, 5, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.EAST, topRule, 5, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.WEST, proName, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, proName, 5, SpringLayout.SOUTH, topRule);
		layout.putConstraint(SpringLayout.EAST, costPro, 5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costPro, 0, SpringLayout.NORTH, proName);
		
		layout.putConstraint(SpringLayout.WEST, min1Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min1Name, 5, SpringLayout.SOUTH, proName);
		layout.putConstraint(SpringLayout.EAST, costMin1, 5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin1, 0, SpringLayout.NORTH, min1Name);
		
		layout.putConstraint(SpringLayout.WEST, min2Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min2Name, 5, SpringLayout.SOUTH, min1Name);
		layout.putConstraint(SpringLayout.EAST, costMin2, 5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin2, 0, SpringLayout.NORTH, min2Name);
		
		layout.putConstraint(SpringLayout.WEST, min3Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min3Name, 5, SpringLayout.SOUTH, min2Name);
		layout.putConstraint(SpringLayout.EAST, costMin3, 5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin3, 0, SpringLayout.NORTH, min3Name);
		
		layout.putConstraint(SpringLayout.WEST, min4Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min4Name, 5, SpringLayout.SOUTH, min3Name);
		layout.putConstraint(SpringLayout.EAST, costMin4, 5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin4, 0, SpringLayout.NORTH, min4Name);
		
		layout.putConstraint(SpringLayout.WEST, min5Name, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, min5Name, 5, SpringLayout.SOUTH, min4Name);
		layout.putConstraint(SpringLayout.EAST, costMin5, 5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, costMin5, 0, SpringLayout.NORTH, min5Name);
		
		layout.putConstraint(SpringLayout.WEST, bottomRule, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, bottomRule, 5, SpringLayout.SOUTH, min5Name);
		layout.putConstraint(SpringLayout.EAST, bottomRule, 5, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.WEST, description, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH,description,5,SpringLayout.SOUTH,bottomRule);
		layout.putConstraint(SpringLayout.EAST, description, 5, SpringLayout.EAST, this);
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
			
			costPro.setText(component.getCost(Resource.PRODUCTION) + ".0");
			costMin1.setText(component.getCost(Resource.MINERAL1) + ".0");
			costMin2.setText(component.getCost(Resource.MINERAL2) + ".0");
			costMin3.setText(component.getCost(Resource.MINERAL3) + ".0");
			costMin4.setText(component.getCost(Resource.MINERAL4) + ".0");
			costMin5.setText(component.getCost(Resource.MINERAL5) + ".0");
			
			Stats<String> stats  = (Stats<String>)component.getStats().clone();
			Set<String> keys = stats.keySet();
			keys.removeAll(exclusionList);
			
			String previous = null;
			for(String key : keys){
				JLabel[] labels = recycle.size() > 0 ? recycle.pop() : new JLabel[2];
				
				labels[0].setText(key + ":");
				labels[1].setText(component.value(key).toString());
				
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
							SpringLayout.EAST, labels[1], 5, SpringLayout.EAST, this
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
							SpringLayout.EAST, labels[1], 5, SpringLayout.EAST, this
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
		
		this.invalidate();
	}
	
	protected void paintChildren(Graphics g){
		super.paintChildren(g);
		
		if(component != null){
			icon.setSize(component.getDisplayFactory().getImagePalette().getTileSize(null));
			component.draw((Graphics2D)icon.getGraphics());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		if(arg0.getSource() instanceof IComponent){
			select((IComponent<I>)arg0);
		}else if(arg0.getPath().getLastPathComponent() instanceof IComponent){
			select((IComponent<I>)arg0.getPath().getLastPathComponent());
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