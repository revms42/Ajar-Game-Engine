package tools.anim.drawing;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseEvent;

import tools.anim.IPaintMode;
import tools.anim.ITool;

public abstract class AbstractTool<K> implements ITool<K> {
	protected int button;
	protected Point startPoint;
	protected Point endPoint;
	protected int modifiers;
	protected boolean inProgress;

	@Override
	public void paint(IPaintMode p, Graphics2D g, K... parameters) {
		if((modifiers & MouseEvent.CTRL_DOWN_MASK) == 0){
			if(button == MouseEvent.BUTTON1){
				switch(p.getPaintMode()){
				case 1:
					draw(p.getForePaint(),g,parameters);
					break;
				case 2:
					draw(p.getBackPaint(),g,parameters);
					break;
				case 4:
					fill(p.getForePaint(),g,parameters);
					break;
				case 5:
					fill(p.getForePaint(),g,parameters);
					draw(p.getForePaint(),g,parameters);
					break;
				case 6:
					fill(p.getForePaint(),g,parameters);
					draw(p.getBackPaint(),g,parameters);
					break;
				case 8:
					fill(p.getBackPaint(),g,parameters);
					break;
				case 9:
					fill(p.getBackPaint(),g,parameters);
					draw(p.getForePaint(),g,parameters);
					break;
				case 10:
					fill(p.getBackPaint(),g,parameters);
					draw(p.getBackPaint(),g,parameters);
					break;
				default:
					break;
				}
			}else if(button == MouseEvent.BUTTON3){
				switch(p.getPaintMode()){
				case 1:
					draw(p.getBackPaint(),g,parameters);
					break;
				case 2:
					draw(p.getForePaint(),g,parameters);
					break;
				case 4:
					fill(p.getBackPaint(),g,parameters);
					break;
				case 5:
					fill(p.getBackPaint(),g,parameters);
					draw(p.getBackPaint(),g,parameters);
					break;
				case 6:
					fill(p.getBackPaint(),g,parameters);
					draw(p.getForePaint(),g,parameters);
					break;
				case 8:
					fill(p.getForePaint(),g,parameters);
					break;
				case 9:
					fill(p.getForePaint(),g,parameters);
					draw(p.getBackPaint(),g,parameters);
					break;
				case 10:
					fill(p.getForePaint(),g,parameters);
					draw(p.getForePaint(),g,parameters);
					break;
				default:
					break;
				}
			}
		}
	}
	
	protected abstract void draw(Paint t, Graphics2D g, K... parameters);
	
	protected abstract void fill(Paint t, Graphics2D g, K... parameters);
	
	@Override
	public void mouseClicked(MouseEvent e) {
		button = e.getButton();
		startPoint = e.getPoint();
		endPoint = e.getPoint();
		modifiers = e.getModifiers();
		inProgress = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		button = e.getButton();
		endPoint = e.getPoint();
		modifiers = e.getModifiers();
		inProgress = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		button = e.getButton();
		endPoint = e.getPoint();
		modifiers = e.getModifiers();
		inProgress = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		button = e.getButton();
		startPoint = e.getPoint();
		modifiers = e.getModifiers();
		inProgress = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		button = e.getButton();
		endPoint = e.getPoint();
		modifiers = e.getModifiers();
		inProgress = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		button = e.getButton();
		endPoint = e.getPoint();
		modifiers = e.getModifiers();
		inProgress = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}