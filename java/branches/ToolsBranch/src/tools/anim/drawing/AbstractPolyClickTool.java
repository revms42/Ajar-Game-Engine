package tools.anim.drawing;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import tools.anim.IPaintMode;
import tools.anim.PaintCanvas;

public abstract class AbstractPolyClickTool<K> extends AbstractTool<K> {
	
	protected Vector<Point> points;

	public AbstractPolyClickTool(PaintCanvas p, IPaintMode t) {
		super(p, t);
		points = new Vector<Point>();
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getClickCount() == 1){
			button = e.getButton();
			
			if(points.isEmpty()){
				startPoint = e.getPoint();
			}
			
			points.add(e.getPoint());
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}else{
			button = e.getButton();
			
			if(points.isEmpty()){
				startPoint = e.getPoint();
			}
			
			points.add(e.getPoint());
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = false;
		}

		doMouse();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getClickCount() == 1){
			button = e.getButton();
			
			points.add(e.getPoint());
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}else{
			button = e.getButton();
			
			points.add(e.getPoint());
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = false;
		}
		
		doMouse();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getClickCount() == 1){
			button = e.getButton();
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}else{
			button = e.getButton();
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = false;
		}
		
		doMouse();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(inProgress){
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
			doMouse();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 1){
			button = e.getButton();
			
			if(points.isEmpty()){
				startPoint = e.getPoint();
			}
			
			points.add(e.getPoint());
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}else{
			button = e.getButton();
			
			if(points.isEmpty()){
				startPoint = e.getPoint();
			}
			
			points.add(e.getPoint());
			
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = false;
		}

		doMouse();
	}
}
