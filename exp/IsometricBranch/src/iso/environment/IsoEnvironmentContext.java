package iso.environment;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

import org.display.IEnvironment;
import org.display.IEnvironmentContext;
import org.display.ImageOpPalette;

public class IsoEnvironmentContext<I> implements IEnvironmentContext<I> {

	public IsoEnvironmentContext(){
		// TODO Write this class....
	}
	
	@Override
	public Point[] getAll(IEnvironment<I,?> arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImageOp[] getAllTransform(IEnvironment<I,?> arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I getBoard(IEnvironment<I,?> arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageOpPalette<?> getImageOpPalette() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPoint(IEnvironment<I,?> arg0, Point arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImageOp getPointTransform(IEnvironment<I,?> arg0, Point arg1,
			int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point[] getRange(IEnvironment<I,?> arg0, Rectangle arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImageOp[] getRangeTransform(IEnvironment<I,?> arg0,
			Rectangle arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getTilesInWindow(Rectangle arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setImageOpPalette(ImageOpPalette<?> arg0) {
		// TODO Auto-generated method stub

	}

}
