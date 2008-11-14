package space.ui.designer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.Vector;

import org.display.IDisplayContext;
import org.display.IDisplayFactory;
import org.display.IDisplayable;
import org.display.ImagePalette;
import org.model.IStat;
import org.model.IStats;
import org.model.Stats;
import org.model.number.Number;

import space.model.Resource;
import space.model.component.ComponentType;
import space.model.component.IComponent;
import space.model.component.IComponentType;

public class ComponentLoader {

	public static Vector<IComponent<String>> loadComponents(File file){
		if(file == null){
			return loadNullComponents();
		}else{
			return null;
		}
	}
	
	private static Vector<IComponent<String>> loadNullComponents(){
		Vector<IComponent<String>> v = new Vector<IComponent<String>>();
		
		v.add(new IComponent<String>() {

			private final int[] d1x = {24,2,2,24};
			private final int[] d1y = {24,11,35,46};
			
			private final int[] d2x = {24,2,24,46};
			private final int[] d2y = {24,11,2,11};
			
			private final int[] d3x = {24,46,46,24};
			private final int[] d3y = {24,11,35,46};
			
			private final IComponent<String> ref = this;
			@Override
			public int getCost(Resource resource) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "A null component";
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "Null Component";
			}

			@Override
			public IComponentType getPrimaryType() {
				// TODO Auto-generated method stub
				return ComponentType.MISC;
			}

			@Override
			public void draw(Graphics2D arg0) {
				arg0.setColor(Color.MAGENTA.darker());
				arg0.fillPolygon(d1x, d1y, 4);
				arg0.setColor(Color.CYAN.darker());
				arg0.fillPolygon(d2x, d2y, 4);
				arg0.setColor(Color.GREEN.darker());
				arg0.fillPolygon(d3x, d3y, 4);
			}

			@Override
			public IDisplayContext<String, String> getDisplayContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getDisplayDepth() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public IDisplayFactory<String, String> getDisplayFactory() {
				// TODO Auto-generated method stub
				return new IDisplayFactory<String,String>(){

					@Override
					public void display(IDisplayable<String, String> arg0,
							Graphics2D arg1) {
						ref.draw(arg1);
					}

					@Override
					public ImagePalette<String> getImagePalette() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void setImagePalette(ImagePalette<String> arg0) {
						// TODO Auto-generated method stub
						
					}
				};
			}

			@Override
			public boolean isOnScreen(Rectangle arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setDisplayContext(IDisplayContext<String, String> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setDisplayFactory(IDisplayFactory<String, String> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public IStats<String> getStats() {
				// TODO Auto-generated method stub
				return new Stats<String>();
			}

			@Override
			public void setStats(IStats<String> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int compareTo(IStats<String> arg0, String arg1) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public IStat getStat(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number into(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number intoEq(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number max(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void max(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Number min(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void min(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Number minus(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number minusEq(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number nominal(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void nominal(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Number plus(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number plusEq(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number power(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number powerEq(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setStat(String arg0, IStat arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Number times(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number timesEq(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Number value(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void value(String arg0, java.lang.Number arg1) {
				// TODO Auto-generated method stub
				
			}
			
			public IStats<String> clone(){
				return this;
			}

			@Override
			public Vector<IComponentType> getSecondaryTypes() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		return v;
	}
}
