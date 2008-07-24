package tools.anim;

import java.awt.Paint;
import java.util.Vector;

import javax.swing.JPanel;

import tools.anim.drawing.ArcTool;
import tools.anim.drawing.LineTool;
import tools.anim.drawing.OvalTool;
import tools.anim.drawing.PolygonTool;
import tools.anim.drawing.RectangleTool;
import tools.anim.drawing.RoundRectangleTool;
import tools.anim.drawing.ShapeTool;

public class ToolPalette extends JPanel implements IPaintMode {
	private static final long serialVersionUID = 1671516951583390782L;
	
	private final PaintCanvas pc;
	private final Vector<ITool<?>> tools;
	
	private Paint fore;
	private Paint back;
	
	private ITool<?> selectedTool;
	
	private int paintMode;
	
	public ToolPalette(PaintCanvas pc){
		this.pc = pc;
		
		tools = new Vector<ITool<?>>();
		//tools.add(new ArcTool(pc,this));
		tools.add(new LineTool(pc,this));
		tools.add(new OvalTool(pc,this));
		//tools.add(new PolygonTool(pc,this));
		tools.add(new RectangleTool(pc,this));
		//tools.add(new RoundRectangleTool(pc,this));
		//tools.add(new ShapeTool(pc,this));
		
		//TODO: Add to a button menu.
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

	public void setSelectedTool(ITool<?> selectedTool) {
		pc.removeMouseListener(this.selectedTool);
		
		this.selectedTool = selectedTool;
		
		pc.addMouseListener(selectedTool);
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
