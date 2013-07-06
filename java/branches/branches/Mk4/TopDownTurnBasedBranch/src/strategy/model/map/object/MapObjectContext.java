package strategy.model.map.object;

import java.awt.Point;
import java.awt.image.BufferedImageOp;

import org.display.IDisplayContext;
import org.display.IDisplayable;
import org.display.ImageOpPalette;

import strategy.model.StrategyStat;

public class MapObjectContext implements IDisplayContext<String, StrategyStat> {

	private final String key;
	
	public MapObjectContext(String key){
		//TODO: Right now only one board;
		this.key = key;
	}
	
	public String getBoard(IDisplayable<String,StrategyStat> subject) {
		return key;
	}

	public ImageOpPalette<?> getImageOpPalette() {
		// TODO Auto-generated method stub
		return null;
	}

	public Point getPosition(IDisplayable<String,StrategyStat> subject) {
		int x = subject.value(MapObjectStat.MAP_X_POS).intValue();
		int y = subject.value(MapObjectStat.MAP_Y_POS).intValue();
		return new Point(x,y);
	}

	public Point getTile(IDisplayable<String,StrategyStat> subject) {
		int x = 0;
		int y = 0;
		if(	subject.value(MapObjectStat.MAP_X_DEST) != null &&
			!subject.value(MapObjectStat.MAP_X_DEST).equals(subject.value(MapObjectStat.MAP_X_POS))
		){
			x = subject.value(MapObjectStat.MAP_X_DEST).compareTo(subject.value(MapObjectStat.MAP_X_POS));
		}
		
		if(	subject.value(MapObjectStat.MAP_Y_DEST) != null &&
			!subject.value(MapObjectStat.MAP_Y_DEST).equals(subject.value(MapObjectStat.MAP_Y_POS))
		){
			y = subject.value(MapObjectStat.MAP_Y_DEST).compareTo(subject.value(MapObjectStat.MAP_Y_POS));
		}
		
		if(x == 0 && y == 0){
			switch(subject.value(MapObjectStat.MAP_FACING).intValue()){
			case MapObjectStat.MAP_FACE_E:
				x = 1;
				break;
			case MapObjectStat.MAP_FACE_N:
				y = -1;
				break;
			case MapObjectStat.MAP_FACE_S:
				y = 1;
				break;
			case MapObjectStat.MAP_FACE_W:
				x = -1;
				break;
			default:
				break;
			}
		}
		
		return new Point(x+1,y+1);
	}

	public BufferedImageOp getTransform(IDisplayable<String,StrategyStat> subject) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setImageOpPalette(ImageOpPalette<StrategyStat> palette) {
		// TODO Auto-generated method stub

	}

}
