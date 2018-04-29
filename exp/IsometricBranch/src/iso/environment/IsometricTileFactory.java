package iso.environment;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.display.IEnvironment;
import org.display.ITileFactory;
import org.display.ImagePalette;

public class IsometricTileFactory<I> implements ITileFactory<I> {

	public IsometricTileFactory(){
		// TODO Write this class....
	}
	
	@Override
	public void displayAll(IEnvironment<I,?> arg0, Rectangle arg1, Graphics2D arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayDepth(IEnvironment<I,?> arg0, int arg1, Rectangle arg2,
			Graphics2D arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public ImagePalette<I> getImagePalette() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setImagePalette(ImagePalette<I> arg0) {
		// TODO Auto-generated method stub

	}

}
