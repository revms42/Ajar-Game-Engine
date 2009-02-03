package org.display;

import java.awt.AWTError;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JPanel;

import org.IGameManifest;
import org.interaction.IAccountant;

public class AnimationPanel extends JPanel implements IFrameListener, IAccountant{
	private static final long serialVersionUID = -6557003269310071017L;

	private final IGameManifest manifest;
	
	private BufferedImage buffer;
	private Graphics2D g2;
	
	private Collection<? extends IDisplayable<?,?>> displayables;
	private Vector<Vector<IDisplayable<?,?>>> displaymatrix;
	private Collection<? extends IEnvironment<?,?>> environments;
	private IEnvironment<?,?> environment;
	
	private Rectangle screen;
	
	public AnimationPanel(IGameManifest manifest){
		super();
		
		this.manifest = manifest;
		
		displaymatrix = new Vector<Vector<IDisplayable<?,?>>>();
		for(int i = 0; i <= manifest.getMaxDepth(); i++){
			displaymatrix.add(i, new Vector<IDisplayable<?,?>>());
		}
		
	}
	
	protected void paintComponent(Graphics g){
		if(this.isVisible() && g != null){
			for(Vector<IDisplayable<?,?>> v : displaymatrix){
				v.removeAllElements();
			}
			
			//TODO: Trying new way... displayables = manifest.getDisplayables(this);
			
			if((displayables = manifest.getDisplayables(this)) != null){
				for(IDisplayable<?,?> i : displayables){
					displaymatrix.get(i.getDisplayDepth()).add(i);
				}
			}

			
			if(buffer == null){
				buffer = new BufferedImage(
						this.getWidth(), 
						this.getHeight(), 
						BufferedImage.TYPE_INT_ARGB
				);
			}
			
			synchronized(this){
				screen = this.getBounds();
				
				g2 = buffer.createGraphics();
				g2.setBackground(this.getBackground());
				g2.fillRect(screen.x, screen.y, screen.width, screen.height);
				
				
				for(int depth = 0; depth <= manifest.getMaxDepth(); depth++){
					environments = manifest.getEnvironments(this);
					
					if(environments != null){
						for(IEnvironment<?,?> i : environments){
							environment = i;
							paintEnvironment(depth);
						}
					}
					
					if(displaymatrix.size() > 0){
						displayables = displaymatrix.get(depth);
						paintDisplayable();
					}
				}
				
				g2.dispose();
				((Graphics2D)g).drawImage(buffer, null, 0, 0);
			}
		}else{
			super.paintComponent(g);
		}
	}
	
	protected synchronized void paintDisplayable(){
		if(displayables != null && displayables.size() > 0){
			for(IDisplayable<?,?> i : displayables){
				if(i.isOnScreen(screen)){
					i.draw(g2);
				}
			}
		}
	}
	
	protected synchronized void paintEnvironment(int depth){
		if(environment != null){
			environment.draw(g2,screen,depth);
		}
	}
	
	public Component add(Component c){
		throw(new AWTError("AnimationPanels cannot have children"));
	}

	@Override
	public IGameManifest getManifest() {
		return manifest;
	}

	@Override
	public synchronized void frameChange(FrameAction ifa) {
		if(this.isVisible()){
			this.repaint();
		}
	}
}
