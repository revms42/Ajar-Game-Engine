package test.ai;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.IGameManifest;
import org.control.IController;
import org.display.AnimationPanel;
import org.display.DisplayFactory;
import org.display.FrameAction;
import org.display.IDisplayable;
import org.display.IEnvironment;
import org.interaction.ConditionPalette;
import org.interaction.IEntity;
import org.model.number.Number;

public class AITest extends JFrame implements IGameManifest {
	private static final long serialVersionUID = 4238361448304708379L;
	private Vector<IEntity<?>> ships;
	private Vector<IDisplayable<?,?>> dships;
	
	//private IGameManifest manifest;
	private JTree events;
	private Timer anitimer;
	
	public final static String CLOSE = "close";
	public final static String MOVE = "move";
	
	private void init(){
		ships = new Vector<IEntity<?>>();
		dships = new Vector<IDisplayable<?,?>>();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		final AnimationPanel panel = new AnimationPanel(this);
		//final JPanel panel = new JPanel();
		panel.setSize(400, 400);
		panel.setPreferredSize(new Dimension(400,400));

		this.getContentPane().add(panel,BorderLayout.CENTER);
		
		events = new JTree();
		
		this.getContentPane().add(new JScrollPane(events),BorderLayout.SOUTH);
		
		ShipContext context = new ShipContext();
		DisplayFactory<String,String> fact = 
			new DisplayFactory<String,String>(ShipContext.createPalette());
		
		final Ship one = new Ship(Ship.makeStats(), context, fact);
		one.value(Ship.X,Number.parse(200));
		one.value(Ship.Y,Number.parse(200));
		ships.add(one);
		dships.add(one);
		
		final Ship two = one.clone();
		
		two.value(Ship.X, two.max(Ship.X).intValue() - 25);
		two.value(Ship.Y, two.max(Ship.Y).intValue() - 25);
		two.value(Ship.RANGE, Number.parse(50));
		two.max(Ship.RANGE, Number.parse(50));
		two.min(Ship.RANGE, Number.parse(50));
		two.nominal(Ship.RANGE, Number.parse(50));
		two.value(Ship.THRUST, Number.parse(4));
		two.max(Ship.THRUST, Number.parse(4));
		two.min(Ship.THRUST, Number.parse(4));
		two.nominal(Ship.THRUST, Number.parse(4));
		ships.add(two);
		dships.add(two);
		
		final ConditionPalette<String,String,String> palette = 
			new ConditionPalette<String,String,String>();
		palette.put(CLOSE, new CloseToRange(this, events));
		palette.put(MOVE, new Update(this));
		
		ActionListener listener = new ActionListener(){
			int turns = 0;
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO: Perform turn.
				turns++;
				DefaultMutableTreeNode node = 
					(DefaultMutableTreeNode)events.getModel().getRoot();
				DefaultMutableTreeNode next = new DefaultMutableTreeNode("Turn " + turns);
				node.add(next);
				events.setSelectionPath(new TreePath(next.getPath()));
				
				events.updateUI();
				
				palette.performAction(CLOSE, one, two);
				palette.performAction(MOVE, one, two);

				palette.performAction(CLOSE, two, one);
				palette.performAction(MOVE, two, one);
				
				node.add(new DefaultMutableTreeNode("X = " + one.value(FieldConstants.X).intValue()));
				node.add(new DefaultMutableTreeNode("Y = " + one.value(FieldConstants.Y).intValue()));
				
				if(turns >= 400){
					anitimer.stop();
				}
				
				panel.frameChange(FrameAction.FRAME_ENTER);
			}
		};
		
		anitimer = new Timer(100, listener);
		anitimer.setInitialDelay(1000);
		
		this.pack();
		this.setVisible(true);
		anitimer.start();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new AITest()).init();
	}
	@Override
	public Collection<IController<?, ?>> getControllers(Object caller) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<IDisplayable<?, ?>> getDisplayables(Object caller) {
		return dships;
	}
	@Override
	public Collection<IEntity<?>> getEntities(Object caller) {
		return ships;
	}
	@Override
	public Collection<IEnvironment<?, ?>> getEnvironments(Object caller) {
		return null;
	}
	@Override
	public int getMaxDepth() {
		return 0;
	}

}
