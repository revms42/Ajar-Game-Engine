package space.ui.designer;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.util.Vector;

public class DesignPanel<I> extends JPanel {
	private static final long serialVersionUID = -7632875733043601841L;

	private final Vector<Vector<ComponentTile<I>>> grid;
	private final Dimension gridSize;
	private final CostPanel costPanel;
	private final ComponentTransferHandler<I> transferer;
	
	public DesignPanel(ComponentTransferHandler<I> transferer){
		super();
		this.transferer = transferer;
		
		grid = new Vector<Vector<ComponentTile<I>>>();
		grid.add(new Vector<ComponentTile<I>>());
		grid.get(0).add(new ComponentTile<I>(transferer));
		
		costPanel = new CostPanel();
		
		gridSize = new Dimension(1,1);
	}
	
	private void layoutGrid(){
		/*
		 * %----------
		 * |A.........
		 * |A.........
		 * |A.........
		 * |0.........
		 * %----------
		 *  ^ -->
		 */
		//Check the first element of every column.
		for(ComponentTile<I> tile : grid.get(0)){
			//If there is a tile in it than we need another row.
			if(tile.getSelectedComponent() != null){
				grid.add(0, new Vector<ComponentTile<I>>());
				
				//Once we have a new row lets fill it with tiles.
				for(int i = 0; i < gridSize.height; i++){
					grid.get(0).add(new ComponentTile<I>(transferer));
				}
				gridSize.height++;
			}
		}
		
		/*
		 * %----------
		 * |X.........
		 * |A.........
		 * |A.........
		 * |A.........
		 * %----------
		 *  ^ -->
		 */
		//Check the last element of every column.
		for(ComponentTile<I> tile : grid.get(gridSize.height - 1)){
			//If there is a tile in it than we need another row.
			if(tile.getSelectedComponent() != null){
				grid.add(gridSize.height, new Vector<ComponentTile<I>>());
				
				//Once we have a new row lets fill it with tiles.
				for(int i = 0; i < gridSize.height; i++){
					grid.get(0).add(new ComponentTile<I>(transferer));
				}
				gridSize.height++;
			}
		}
	}
}
