package tools.anim;

import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.JPanel;

public interface ITool<P> extends MouseListener, MouseMotionListener {

	public void paint(IPaintMode p, Graphics2D g, P... parameters);
	
	public JPanel parametricPanel();
	
	public Icon getButtonIcon();
}
