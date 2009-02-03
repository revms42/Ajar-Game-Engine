package org.display;

import java.awt.AWTError;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JWindow;

import org.IGameManifest;
import org.interaction.IAccountant;

public class AnimationWindow extends JWindow implements IFrameListener, IAccountant{
	private static final long serialVersionUID = 7981824607903285555L;

	private final IGameManifest manifest;
	
	private BufferStrategy buffer;
	private Graphics2D g2;
	
	private Collection<? extends IDisplayable<?,?>> displayables;
	private Vector<Vector<IDisplayable<?,?>>> displaymatrix;
	private IEnvironment<?,?> environment;
	
	private Rectangle screen;
	
	public AnimationWindow(IGameManifest manifest){
		super();
		
		this.manifest = manifest;
		
		displaymatrix = new Vector<Vector<IDisplayable<?,?>>>(manifest.getMaxDepth());
	}
	
	public Component add(Component c){
		throw(new AWTError("AnimationWindows cannot have children"));
	}
	
	@Override
	public synchronized void frameChange(FrameAction fa) {
		if(this.isVisible()){
			if(buffer == null){
				this.createBufferStrategy(2);
				buffer = this.getBufferStrategy();
			}
			for(Vector<IDisplayable<?,?>> v : displaymatrix){
				v.removeAllElements();
			}
			
			displayables = manifest.getDisplayables(this);
			
			for(IDisplayable<?,?> i : displayables){
				displaymatrix.get(i.getDisplayDepth()).add(i);
			}
			
			synchronized(this){
				screen = this.getBounds();
				
				g2 = (Graphics2D)buffer.getDrawGraphics();
				g2.setBackground(this.getBackground());
				g2.fillRect(screen.x, screen.y, screen.width, screen.height);
				
				
				for(int depth = 0; depth <= manifest.getMaxDepth(); depth++){
					for(IEnvironment<?,?> i : manifest.getEnvironments(this)){
						environment = i;
						paintEnvironment(depth);
					}
					
					displayables = displaymatrix.get(depth);
					paintDisplayable();
				}
				
				g2.dispose();
				buffer.show();
			}
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
	
	@Override
	public IGameManifest getManifest() {
		return manifest;
	}

}
