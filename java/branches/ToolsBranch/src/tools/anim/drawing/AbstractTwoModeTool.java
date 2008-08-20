package tools.anim.drawing;

import java.awt.Point;
import java.awt.event.MouseEvent;

import tools.anim.IPaintMode;
import tools.anim.PaintCanvas;

public abstract class AbstractTwoModeTool<K> extends AbstractTool<K> {
	
	protected Point startPoint2;
	protected Point endPoint2;
	protected boolean mode2;

	public AbstractTwoModeTool(PaintCanvas p, IPaintMode t) {
		super(p, t);
		mode2 = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!mode2){
			button = e.getButton();
			startPoint = e.getPoint();
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}else{
			button = e.getButton();
			startPoint2 = e.getPoint();
			endPoint2 = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}
		doMouse();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!mode2){
			button = e.getButton();
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
			mode2 = true;
		}else{
			button = e.getButton();
			endPoint2 = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = false;
		}
		doMouse();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(!mode2){
			endPoint = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}else{
			endPoint2 = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
		}
		doMouse();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(mode2){
			startPoint2 = e.getPoint();
			endPoint2 = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
			doMouse();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(mode2){
			startPoint2 = e.getPoint();
			endPoint2 = e.getPoint();
			modifiers = e.getModifiers();
			inProgress = true;
			doMouse();
		}
	}
}
