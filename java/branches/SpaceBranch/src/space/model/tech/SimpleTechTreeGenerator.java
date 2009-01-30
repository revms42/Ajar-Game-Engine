package space.model.tech;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.display.DisplayFactory;
import org.display.IDisplayContext;
import org.display.ImageBoard;
import org.display.ImageOpPalette;
import org.display.ImagePalette;
import org.interaction.IEntity;
import org.model.LinearStat;
import org.xml.sax.SAXException;

import space.display.DisplayStats;
import space.model.Resource;
import space.model.component.ComponentType;
import space.model.component.DefaultComponent;
import space.model.component.IComponent;
import space.model.component.IComponentType;
import space.model.component.ComponentType.EngineType;
import space.model.component.ComponentType.WeaponType;
import space.model.ships.Damage;

public class SimpleTechTreeGenerator implements ITechTreeLoader<File,String> {
	private Vector<IComponent<String>> components;
	
	private DefaultCompContext defaultContext;
	private ImagePalette<String> palette;
	private DisplayFactory<String,String> factory;
	
	public SimpleTechTreeGenerator(){
		defaultContext = new DefaultCompContext();
	}
	
	public static int powerSeries(int i, int doubleTime, int initial){
		return (int)((double)initial * Math.pow(2, ((double)i/(double)doubleTime)));
	}
	
	public Vector<IComponent<String>> loadTree(File file) throws SAXException, IOException {
		components = new Vector<IComponent<String>>();
		
		armorTreeGenerator("A");
		beamTreeGenerator("B");
		powerTreeGenerator("P");
		engineTreeGenerator("E");
		
		return components;
	}
	
	public void defaultStatsGenerator(DefaultComponent<String> component, String img){
		addStat(IComponentType.STAT_MASS,0,component);
		addStat(IComponentType.STAT_SIGNATURE,10,component);
		addStat(IComponentType.STAT_HITPOINTS,0,component);
		parseComponentImage(img,component);
	}
	
	public void powerTreeGenerator(String img){
		for(int i = 1; i <= 30; i++){
			String[] bits = randomNameGenerator(ComponentType.POWERPLANT, i);
			
			DefaultComponent<String> component = new DefaultComponent<String>(bits[0], bits[1]);
			
			defaultStatsGenerator(component,img);
			
			addStat(IComponentType.STAT_ENERGY,powerSeries(i,6,100),component);
			addStat("cost"+Resource.PRODUCTION.shortName(),i*5,component);
			addStat("cost"+Resource.MINERAL1.shortName(),i*2,component);
			addStat("cost"+Resource.MINERAL5.shortName(),i*3,component);
			
			components.add(component);
			component.setType(ComponentType.POWERPLANT);
			component.setDisplayContext(defaultContext);
		}
	}
	
	public void engineTreeGenerator(String img){
		for(int i = 1; i <= 30; i++){
			String[] bits = randomNameGenerator(ComponentType.ENGINE, i);
			
			DefaultComponent<String> component = new DefaultComponent<String>(bits[0], bits[1]);
			
			defaultStatsGenerator(component,img);
			
			addStat(IComponentType.STAT_ENERGY,powerSeries(i,6,50),component);
			addStat(IComponentType.STAT_SPEED,powerSeries(i,15,3),component);
			addStat("cost"+Resource.PRODUCTION.shortName(),i*5,component);
			addStat("cost"+Resource.MINERAL1.shortName(),i*2,component);
			addStat("cost"+Resource.MINERAL5.shortName(),i*3,component);
			
			components.add(component);
			component.setType(EngineType.WARP);
			component.setDisplayContext(defaultContext);
		}
	}
	
	public void beamTreeGenerator(String img){
		for(int i = 1; i <= 30; i++){
			String[] bits = randomNameGenerator(ComponentType.WEAPON, i);
			
			DefaultComponent<String> component = new DefaultComponent<String>(bits[0], bits[1]);
			
			defaultStatsGenerator(component,img);
			
			weaponStatsGenerator(component, WeaponType.BEAM, i, new ResourceBlock(Resource.PRODUCTION,i*4),new ResourceBlock(Resource.MINERAL4,i*4));
			
			components.add(component);
			component.setType(WeaponType.BEAM);
			component.setDisplayContext(defaultContext);
		}
	}
	
	public void weaponStatsGenerator(DefaultComponent<String> component, WeaponType type, int level, ResourceBlock... blocks ){
		switch(type){
		case BEAM:
			addStat(IComponentType.STAT_POWER+"(" + Damage.ENERGY + ")",powerSeries(level,6,10),component);
			addStat(IComponentType.STAT_ENERGY,powerSeries(level,7,20),component);
			addStat(IComponentType.STAT_FIRINGARC,30,component);
			addStat(IComponentType.STAT_ACCURACY,powerSeries(level,20,45),component);
			addStat(IComponentType.STAT_RELOAD,1000,component);
			addStat(IComponentType.STAT_RANGE,powerSeries(level,10,100),component);
			break;
		default:
			break;
		}
		
		for(ResourceBlock block : blocks){
			addStat("cost"+block.res.shortName(),block.ammount,component);
			
			switch(block.res){
			default:
				break;
			}
		}
	}
	
	/*
		Standard armors, as a rule, generally have a MatSci and AppPhys req and need Sid elements.
		As they get more complicated they start to need first Lth elements, then Cal elements, then Xen elements.
		In they're furthest extent they start to need XenSci (like everything else).
		
		Ideal armor rate is 1 per MatSci:
		MS:Pro/Sid/Lth/Cal/Xen	Mass/Sig/HP A/P/I/E/B
		
		4 Sid = 50 HP = 10 Mass = 1 ExDef = 1 ImpDef
		4 Lth = 60 HP = 5 Mass = 1 AcDef = 1 ImpDef
		4 Cal = 80 HP = 30 Mass = 1 BmDef = 1 ExDef
		4 Xen = 100 HP = 50 Mass = 1 PlDef = 1 ExDef = 1 ImpDef
		
		1 Min = 1 Pro
	*/
	public void armorTreeGenerator(String img){
		for(int i = 1; i <= 30; i++){
				String[] bits = randomNameGenerator(ComponentType.ARMOR, i);
				
				DefaultComponent<String> component = new DefaultComponent<String>(bits[0], bits[1]);
				
				addStat(IComponentType.STAT_MASS,0,component);
				addStat(IComponentType.STAT_SIGNATURE,10,component);
				addStat(IComponentType.STAT_HITPOINTS,0,component);
				parseComponentImage(img,component);
				
				armorStatsGenerator(component, new ResourceBlock(Resource.PRODUCTION,i*4), new ResourceBlock(Resource.MINERAL3,i*4));
				
				components.add(component);
				component.setType(ComponentType.ARMOR);
				component.setDisplayContext(defaultContext);
		}
	}
	
	public void armorStatsGenerator(DefaultComponent<String> component, ResourceBlock... blocks){
		for(ResourceBlock block : blocks){
			addStat("cost"+block.res.shortName(),block.ammount,component);
			
			switch(block.res){
			case MINERAL2:
				addStat("Defense(acid)",block.ammount/4,component);
				addStat("Defense(impact)",block.ammount/4,component);
				component.value(IComponentType.STAT_HITPOINTS).plusEq(60*(block.ammount/4));
				component.value(IComponentType.STAT_MASS).plusEq(5*(block.ammount/4));
				break;
			case MINERAL3:
				addStat("Defense(explosive)",block.ammount/4,component);
				addStat("Defense(impact)",block.ammount/4,component);
				component.value(IComponentType.STAT_HITPOINTS).plusEq(60*(block.ammount/4));
				component.value(IComponentType.STAT_MASS).plusEq(10*(block.ammount/4));
				break;
			case MINERAL4:
				addStat("Defense(explosive)",block.ammount/4,component);
				addStat("Defense(beam)",block.ammount/4,component);
				component.value(IComponentType.STAT_HITPOINTS).plusEq(80*(block.ammount/4));
				component.value(IComponentType.STAT_MASS).plusEq(30*(block.ammount/4));
				break;
			case MINERAL5:
				addStat("Defense(explosive)",block.ammount/4,component);
				addStat("Defense(impact)",block.ammount/4,component);
				addStat("Defense(plasma)",block.ammount/4,component);
				component.value(IComponentType.STAT_HITPOINTS).plusEq(100*(block.ammount/4));
				component.value(IComponentType.STAT_MASS).plusEq(50*(block.ammount/4));
				break;
			default:
				break;
			}
		}
	}
	
	private void addStat(String name, int value, IComponent<String> comp){
		if(comp.value(name) == null){
			LinearStat lstat = new LinearStat(
					value,
					Integer.MAX_VALUE,
					0,
					value
			);
			
			comp.setStat(name, lstat);
		}else{
			comp.value(name).plusEq(value);
		}
	}
	
	private void parseComponentImage(String image, DefaultComponent<String> component) {
		BufferedImage bi = loadImage(image);
		
		if(bi == null){
			bi = new BufferedImage(48,48,BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = bi.createGraphics();
			
			g2.setColor(Color.MAGENTA.darker());
			g2.fillPolygon(SimpleTechTreeLoader.d1x, SimpleTechTreeLoader.d1y, 4);
			g2.setColor(Color.CYAN.darker());
			g2.fillPolygon(SimpleTechTreeLoader.d2x, SimpleTechTreeLoader.d2y, 4);
			g2.setColor(Color.GREEN.darker());
			g2.fillPolygon(SimpleTechTreeLoader.d3x, SimpleTechTreeLoader.d3y, 4);
			
			if(image.length() == 1){
				g2.setColor(Color.BLACK);
				AffineTransform at = AffineTransform.getShearInstance(0.0d,-0.5d);
				FontRenderContext frc = new FontRenderContext(at,true,true);
				GlyphVector gv = (new Font("Arial Bold",Font.BOLD,23)).createGlyphVector(frc, image.toCharArray());
				gv.setGlyphTransform(0, at);
				g2.drawGlyphVector(gv, 30, 41);
			}
			
			g2.finalize();
			g2.dispose();
		}
		
		ImageBoard board = new ImageBoard(bi,new Dimension(48,48));
		
		if(palette == null){
			palette = new ImagePalette<String>();
		}
		
		palette.put(component.getName(), board);
		
		if(factory == null){
			factory = new DisplayFactory<String,String>(palette);
		}
		
		component.setDisplayFactory(factory);
	}
	
	private BufferedImage loadImage(String uri){
		if(uri != null && uri.length() <= 1){
			try {
				File file = new File(new URI(uri));
				
				try {
					return ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				return null;
			} catch (IllegalArgumentException e2){
				//e2.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}
	
	private String[] randomNameGenerator(IComponentType type, int i){
		return new String[]{"Random " + type.getName() + " " + i,"Random " + type.getName() + " " + i};
	}

	private class ResourceBlock {
		public final Resource res;
		public final int ammount;
		
		public ResourceBlock(Resource res, int ammount){
			this.res = res;
			this.ammount = ammount;
		}
	}
	
	private class TechReq {
		public final Technology tech;
		public final int level;
		
		public TechReq(Technology tech, int level){
			this.tech = tech;
			this.level = level;
		}
	}
	
	public class DefaultCompContext implements IDisplayContext<String,String>,DisplayStats {
		
		protected Point tile;
		
		protected AffineTransformOp op;
		protected AffineTransform rotation;
		
		@SuppressWarnings("unchecked")
		@Override
		public String getBoard(IEntity<String> arg0) {
			if(arg0 instanceof IComponent){
				return ((IComponent<String>)arg0).getName();
			}else{
				return null;
			}
		}

		@Override
		public ImageOpPalette<?> getImageOpPalette() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point getPosition(IEntity<String> arg0) {
			if(arg0.value(STAT_X_POS) != null && arg0.value(STAT_Y_POS) != null){
				return new Point(
						arg0.value(STAT_X_POS).intValue(),
						arg0.value(STAT_Y_POS).intValue()
				);
			}else{
				return null;
			}
		}

		@Override
		public Point getTile(IEntity<String> arg0) {
			if(tile == null){
				tile = new Point(0,0);
			}
			
			return tile;
		}

		@Override
		public BufferedImageOp getTransform(IEntity<String> arg0) {
			if(arg0.value(STAT_ROTATION) != null){
				if(op == null){
					if(rotation == null){
						rotation = AffineTransform.getRotateInstance(
								arg0.value(STAT_ROTATION).doubleValue()
						);
					}else{
						rotation.setToRotation(arg0.value(STAT_ROTATION).doubleValue());
					}
					
					op = new AffineTransformOp(rotation,AffineTransformOp.TYPE_BILINEAR);
				}else{
					op.getTransform().setToRotation(arg0.value(STAT_ROTATION).doubleValue());
				}
				
				return op;
			}else{
				return null;
			}
		}

		@Override
		public void setImageOpPalette(ImageOpPalette<String> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}