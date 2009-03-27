package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.*;
import org.control.*;
import org.display.*;
import org.interaction.*;
import org.model.*;

import test.experimental.TestFactory;

public class TestBed extends JFrame {
	private static final long serialVersionUID = 8124433348638469648L;

	private IGameManifest manifest;
	private AbstractCharacter<String,String> character;
	private AbstractCondition<String,String> performTurn;
	
	private String path = 
		"C:\\Documents and Settings\\mstockbr\\workspace\\MDJ\\test\\data\\MDJ.xml";
	
	@SuppressWarnings("unchecked")
	private void setup() throws Exception{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		manifest = new IGameManifest(){
			private Vector<IController<?,?>> controllers;
			private Vector<IDisplayable<?,?>> displayables;
			private Vector<IEntity<?>> entities;
			private Vector<IEnvironment<?,?>> environments;

			@Override
			public Collection<IController<?, ?>> getControllers(Object caller) {
				if(controllers == null)
					controllers = new Vector<IController<?,?>>();
				
				return controllers;
			}

			@Override
			public Collection<IDisplayable<?, ?>> getDisplayables(Object caller) {
				if(displayables == null)
					displayables = new Vector<IDisplayable<?,?>>();
				
				return displayables;
			}

			@Override
			public Collection<IEntity<?>> getEntities(Object caller) {
				if(entities == null)
					entities = new Vector<IEntity<?>>();
				
				return entities;
			}

			@Override
			public Collection<IEnvironment<?, ?>> getEnvironments(Object caller) {
				if(environments == null)
					environments = new Vector<IEnvironment<?,?>>();
				
				return environments;
			}

			@Override
			public int getMaxDepth() {
				return 0;
			}
		};
		/*
		Stat x = new Stat(10,200,0,100);
		Stat y = new Stat(30,200,0,100);
		Stat dx = new Stat(2,2,-2,2);
		Stat dy = new Stat(1,1,-1,1);
		
		String[] keys = {"x","y","dx","dy"};
		Stat[] stat = {x,y,dx,dy};
		
		Stats<String> stats = new Stats<String>(keys,stat);
		*/

		Stats<String> stats = null;
		try {
			HashMap<String,Stats<String>> map = TestFactory.parseDefaultStats(
					TestFactory.loadRoot(new File(path))
			);
			
			if(map.containsKey("default")){
				stats = map.get("default");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IDisplayContext<String,String> dc = new IDisplayContext<String,String>(){

			private final Point tile = new Point(0,0);
			private volatile Point position;
			
			@Override
			public String getBoard(IDisplayable<String,String> subject) {
				return "foo";
			}

			@Override
			public ImageOpPalette<?> getImageOpPalette() {
				return null;
			}

			@Override
			public Point getPosition(IDisplayable<String,String> subject) {
				if(position == null){
					position = new Point();
				}
				
				position.x = subject.value("x").intValue();
				position.y = subject.value("y").intValue();
				
				return position;
			}

			@Override
			public Point getTile(IDisplayable<String,String> subject) {
				return tile;
			}

			@Override
			public BufferedImageOp getTransform(IDisplayable<String,String> subject) {
				return null;
			}

			@Override
			public void setImageOpPalette(ImageOpPalette<String> palette) {
				//Do nothing.
			}
			
		};
		
		BufferedImage bi = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bi.createGraphics();
		
		g2.setPaint(Color.RED);
		g2.fillRect(0, 0, 10, 10);
		
		g2.finalize();
		g2.dispose();
		
		ImageBoard board = new ImageBoard(bi, new Dimension(10,10));
		
		ImagePalette<String> palette = new ImagePalette<String>();
		palette.put("foo", board);
		
		DisplayFactory<String,String> factory = new DisplayFactory<String,String>(palette);
		
		character = new AbstractCharacter<String,String>(stats,dc,factory){

			private Rectangle bounds;
			
			@Override
			public AbstractCharacter<String,String> clone() {
				return null;
			}

			@Override
			public int getDisplayDepth() {
				return 0;
			}

			@Override
			public boolean isOnScreen(Rectangle screen) {
				return true;
			}

			@Override
			public Shape getBounds() {
				if(bounds == null){
					bounds = new Rectangle(0,0,10,10);
				}
				
				bounds.x = this.value("x").intValue();
				bounds.y = this.value("y").intValue();
				
				return bounds;
			}
			
			
		};
		
		manifest.getDisplayables(this).add(character);
		/*
		IAction<String> reverseX = new IAction<String>(){

			@Override
			public void performAction(IEntity<String> subject, IEntity<String>... objects) {
				subject.setValue("dx", -subject.getValue("dx").intValue());
			}

			@Override
			public IGameManifest getManifest() {
				return manifest;
			}
			
		};
		
		IAction<String> reverseY = new IAction<String>(){

			@Override
			public void performAction(IEntity<String> subject, IEntity<String>... objects) {
				subject.setValue("dy", -subject.getValue("dy").intValue());
			}

			@Override
			public IGameManifest getManifest() {
				return manifest;
			}
			
		};
		
		IAction<String> incriX = new IAction<String>(){

			@Override
			public void performAction(IEntity<String> subject, IEntity<String>... objects) {
				subject.setValue("x", 
						subject.getValue("x").intValue() + 
						subject.getValue("dx").intValue()
				);
			}

			@Override
			public IGameManifest getManifest() {
				return manifest;
			}
			
		};
		
		IAction<String> incriY = new IAction<String>(){

			@Override
			public void performAction(IEntity<String> subject, IEntity<String>... objects) {
				subject.setValue("y", 
						subject.getValue("y").intValue() + 
						subject.getValue("dy").intValue()
				);
			}

			@Override
			public IGameManifest getManifest() {
				return manifest;
			}
			
		};
		
		IAction<String>[] actions = 
			(IAction<String>[])new IAction[]{reverseX,reverseY,incriX,incriY};
		String[] actnames = {"revDx","revDy","incriX","incriY"};
		
		ActionPalette<String,String> ap = new ActionPalette<String,String>(actnames,actions);
		*/
		
		Map.Entry<String,ActionPalette<String,String>> map = TestFactory.parseActionPalette(
				TestFactory.loadRoot(new File(path))
		);
		ActionPalette<String,String> ap = map.getValue();
		
		AbstractCondition<String,String> flipX = new AbstractCondition<String,String>(manifest){

			@Override
			public boolean isFullfilled(IEntity<String> subject,
					IEntity<String>... objects) {
				if(subject.value("x").intValue() >= subject.max("x").intValue() ||
				   subject.value("x").intValue() <= subject.min("x").intValue()
				){
					return true;
				}else{
					return false;
				}
			}
		};
		flipX.setActionPalette(ap);
		flipX.addAction("revDx");
		
		AbstractCondition<String,String> flipY = new AbstractCondition<String,String>(manifest){

			@Override
			public boolean isFullfilled(IEntity<String> subject,
					IEntity<String>... objects) {
				if(subject.value("y").intValue() >= subject.max("y").intValue() ||
				   subject.value("y").intValue() <= subject.min("y").intValue()
				){
					return true;
				}else{
					return false;
				}
			}
		};
		flipY.setActionPalette(ap);
		flipY.addAction("revDy");
		
		ap.put("flipX", flipX);
		ap.put("flipY", flipY);
		
		performTurn = new AbstractCondition<String,String>(manifest){

			@Override
			public boolean isFullfilled(IEntity<String> subject,
					IEntity<String>... objects) {
				return true;
			}
			
		};
		performTurn.setActionPalette(ap);
		performTurn.addAction("flipX");
		performTurn.addAction("flipY");
		performTurn.addAction("incriX");
		performTurn.addAction("incriY");
		
		final AnimationPanel panel = new AnimationPanel(manifest);
		
		panel.setSize(200, 200);
		panel.setPreferredSize(new Dimension(200,200));
		
		this.setLayout(new BorderLayout());
		
		this.getContentPane().add(panel,BorderLayout.SOUTH);
		
		Timer anitimer = new Timer(10, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				performTurn.performAction(character);
				panel.repaint();
			}
		});
		anitimer.setInitialDelay(1000);
		
		this.pack();
		this.setVisible(true);
		anitimer.start();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			(new TestBed()).setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
