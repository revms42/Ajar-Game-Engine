package tools.anim;

import java.awt.Paint;

public interface IPaintMode {

	public static final int STROKE_FOREGROUND = 1;
	public static final int STROKE_BACKGROUND = 2;

	public static final int FILLED_FOREGROUND = 4;
	public static final int FILLED_BACKGROUND = 8;
	
	public int getPaintMode();
	public void setPaintMode(int mode);
	
	public Paint getForePaint();
	public Paint getBackPaint();
	
	public void setForePaint(Paint p);
	public void setBackPaint(Paint p);
}
