package space.ui.designer;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;

public class DesignPanel<I> extends JPanel implements ICompPlacementEventListener {
	private static final long serialVersionUID = -7632875733043601841L;

	private final Vector<Vector<ComponentTile<I>>> grid;
	private final Dimension gridSize;
	private final SpringLayout manager;
	private final ComponentTransferHandler<I> transferer;
	
	public DesignPanel(ComponentTransferHandler<I> transferer){
		super();
		this.setPreferredSize(new Dimension(400,400));
		this.transferer = transferer;
		
		grid = new Vector<Vector<ComponentTile<I>>>();
		grid.add(new Vector<ComponentTile<I>>());
		grid.get(0).add(new ComponentTile<I>(transferer));
		
		gridSize = new Dimension(1,1);
		transferer.addPlacementEventListener(this);
		manager = new SpringLayout();
		this.setLayout(manager);
		updateGrid(null);
	}
	
	private boolean checkBoundries(ComponentTile<I> tile){
		boolean found1 = checkTopBoundry(tile);
		boolean found2 = checkBackBoundry(tile);
		boolean found3 = false;
		boolean found4 = false;
		
		if(!found1) found3 = checkBottomBoundry(tile);
		if(!found2) found4 = checkFrontBoundry(tile);
		
		if(found1){
			addRow(gridSize.height);
		}else if (found3){
			addRow(0);
		}
		
		if(found2){
			addColumn(gridSize.width);
		}else if (found4){
			addColumn(0);
		}
		
		
		if(gridSize.height <= 2){
			addRow(0);
		}
		
		if(gridSize.width <= 2){
			addColumn(0);
		}
		
		return found1 |  found2 | found3 | found4;
	}
	
	public Point findPosition(ComponentTile<I> tile){
		for(Vector<ComponentTile<I>> v : grid){
			for(ComponentTile<I> c : v){
				if(c == tile){
					return new Point(grid.indexOf(v),v.indexOf(c));
				}
			}
		}
		return null;
	}
	
	private boolean checkTopBoundry(ComponentTile<I> tile){
		for(Vector<ComponentTile<I>> v : grid){
			if(v.get(gridSize.height - 1) == tile){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkBottomBoundry(ComponentTile<I> tile){
		for(Vector<ComponentTile<I>> v : grid){
			if(v.get(0) == tile){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkFrontBoundry(ComponentTile<I> tile){
		for(ComponentTile<I> c : grid.get(0)){
			if(c == tile){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkBackBoundry(ComponentTile<I> tile){
		for(ComponentTile<I> c : grid.get(gridSize.width - 1)){
			if(c == tile){
				return true;
			}
		}
		return false;
	}
	
	private void addRow(int position){
		for(Vector<ComponentTile<I>> v : grid){
			v.add(position, new ComponentTile<I>(transferer));
		}
		gridSize.setSize(gridSize.width, gridSize.height+1);
	}
	
	private void addColumn(int position){
		Vector<ComponentTile<I>> v = new Vector<ComponentTile<I>>();
		
		for(int i = 0; i < gridSize.height; i++){
			v.add(new ComponentTile<I>(transferer));
		}
		grid.add(position,v);
		
		gridSize.setSize(gridSize.width+1, gridSize.height);
	}
	
	private void updateGrid(ComponentTile<I> c){
		if(c != null && checkBoundries(c)){
			for(Vector<ComponentTile<I>> v : grid){
				for(ComponentTile<I> t : v){
					manager.removeLayoutComponent(t);
				}
			}
			
			this.removeAll();
			
			for(int i = 0; i < gridSize.width; i++){
				for(int j = 0; j < gridSize.height; j++){
					this.add(grid.get(i).get(j));
					if(i > 0){
						manager.putConstraint(
								SpringLayout.WEST, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.EAST,
								grid.get(i-1).get(j));
					}else{
						manager.putConstraint(
								SpringLayout.WEST, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.WEST,
								this);
					}
					if(j > 0){
						manager.putConstraint(
								SpringLayout.NORTH, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.SOUTH, 
								grid.get(i).get(j-1));
					}else{
						manager.putConstraint(
								SpringLayout.NORTH, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.NORTH, 
								this);
					}
				}
			}
		}else{
			for(int i = 0; i < gridSize.width; i++){
				for(int j = 0; j < gridSize.height; j++){
					this.add(grid.get(i).get(j));
					if(i > 0){
						manager.putConstraint(
								SpringLayout.WEST, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.EAST,
								grid.get(i-1).get(j));
					}else{
						manager.putConstraint(
								SpringLayout.WEST, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.WEST,
								this);
					}
					if(j > 0){
						manager.putConstraint(
								SpringLayout.NORTH, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.SOUTH, 
								grid.get(i).get(j-1));
					}else{
						manager.putConstraint(
								SpringLayout.NORTH, 
								grid.get(i).get(j), 
								1, 
								SpringLayout.NORTH, 
								this);
					}
				}
			}
		}
		
		updatePreferredSize();
	}
	
	private void updatePreferredSize(){
		Dimension t = ComponentTile.TILE_SIZE;
		
		this.setPreferredSize(new Dimension(
				(gridSize.width * (t.width+1))+2,
				(gridSize.height * (t.height+1))+2
		));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void componentPlaced(CompPlacementEvent e) {
		if(e.component != null && e.getSource() instanceof ComponentTile){
			updateGrid((ComponentTile<I>) e.getSource());
		}
		
		this.updateUI();
	}

	@Override
	public void eventDispatched(AWTEvent arg0) {
		//TODO: Unknown what to do here.
	}
}
