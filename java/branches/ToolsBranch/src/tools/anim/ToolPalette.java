package tools.anim;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import tools.anim.drawing.ArcTool;
import tools.anim.drawing.LineTool;
import tools.anim.drawing.OvalTool;
import tools.anim.drawing.PolygonTool;
import tools.anim.drawing.RectangleTool;
import tools.anim.drawing.RoundRectangleTool;

public class ToolPalette extends JPanel implements IPaintMode {
	private static final long serialVersionUID = 1671516951583390782L;
	
	private final PaintCanvas pc;
	private final Vector<ITool<?>> tools;
	
	private Paint fore;
	private Paint back;
	
	private ITool<?> selectedTool;
	
	private int paintMode;
	
	public ToolPalette(PaintCanvas c){
		this.pc = c;
		
		this.paintMode = IPaintMode.STROKE_FOREGROUND + IPaintMode.FILLED_BACKGROUND;
		
		this.fore = Color.BLACK;
		this.back = Color.WHITE;
		
		tools = new Vector<ITool<?>>();
		tools.add(new ArcTool(pc,this));
		tools.add(new LineTool(pc,this));
		tools.add(new OvalTool(pc,this));
		tools.add(new PolygonTool(pc,this));
		tools.add(new RectangleTool(pc,this));
		tools.add(new RoundRectangleTool(pc,this));
		this.setLayout(new GridLayout((int)Math.floor((tools.size()/2)+1),2));
		for(int i = 0; i < tools.size(); i++){
			final int j = i;
			
			this.add(new JButton(new AbstractAction(
					tools.get(i).getButtonIcon().toString(),
					tools.get(i).getButtonIcon()
			){
				private static final long serialVersionUID = 0L;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					setSelectedTool(j);
				}
				
			}));
		}
		this.add(new JButton(new AbstractAction("Color Picker"){
			private static final long serialVersionUID = 6770715811822038513L;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fore = JColorChooser.showDialog(pc, "Color Chooser", Color.BLACK);
			}
			
		}));
	}
	
	public int getPaintMode(){
		return paintMode;
	}
	
	public void setPaintMode(int paintMode){
		if(paintMode > 10){
			this.paintMode = 10;
		}else if(paintMode < 1){
			this.paintMode = 1;
		}else{
			this.paintMode = paintMode;
		}
	}

	public ITool<?> getSelectedTool() {
		return selectedTool;
	}

	public void setSelectedTool(int i) {
		ITool<?> selectedTool = tools.get(i);
		
		pc.removeMouseListener(this.selectedTool);
		pc.removeMouseMotionListener(this.selectedTool);
		
		this.selectedTool = selectedTool;
		
		pc.addMouseListener(selectedTool);
		pc.addMouseMotionListener(selectedTool);
	}

	@Override
	public Paint getBackPaint() {
		return back;
	}

	@Override
	public Paint getForePaint() {
		return fore;
	}

	@Override
	public void setBackPaint(Paint p) {
		this.back = p;
	}

	@Override
	public void setForePaint(Paint p) {
		this.fore = p;
	}
}
