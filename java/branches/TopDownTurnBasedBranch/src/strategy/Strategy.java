package strategy;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.IGameManifest;
import org.control.IControlMapping;
import org.control.IController;
import org.display.AnimationPanel;
import org.display.DisplayFactory;
import org.display.FrameAction;
import org.display.IDisplayable;
import org.display.IEnvironment;
import org.display.IEnvironmentContext;
import org.display.ImageBoard;
import org.display.ImagePalette;
import org.display.TileFactory;
import org.interaction.AbstractAction;
import org.interaction.ActionPalette;
import org.interaction.ConditionPalette;
import org.interaction.IEntity;
import org.model.Stats;

import strategy.display.DisplayCondition;
import strategy.display.map.BattleMapContext;
import strategy.interaction.MapCondition;
import strategy.interaction.StrategyCondition;
import strategy.interaction.movement.AStar;
import strategy.model.StrategyStat;
import strategy.model.map.BattleMap;
import strategy.model.map.MapStat;
import strategy.model.map.object.MapObject;
import strategy.model.map.object.MapObjectContext;
import strategy.model.map.object.MapObjectStat;
import strategy.temp.LevelGenerator;

public class Strategy extends JFrame implements IGameManifest {
	private static final long serialVersionUID = 1L;
	private final Vector<MapObject> mapObjects;
	private final Vector<BattleMap> levels;
	private final Vector<MapController> controllers;
	
	private final TileFactory<Integer> tilePainter;
	private final IEnvironmentContext<Integer> envContext;
	private final ConditionPalette<StrategyCondition,String,StrategyStat> palette;
	
	private final DisplayFactory<String,StrategyStat> charPainter;
	private final MapObjectContext charContext;
	
	private final static int ANIMAX = 12;
	private final static Dimension TILESIZE = new Dimension(24,24); 
//	TODO: Load Map Files.
	private final String[] mapFiles = new String[]{"/home/mstockbridge/Projects/TopDownTurnBasedBranch/src/strategy/temp/level.png"};
//	TODO: Load Tile Painter's tile info.
	private final HashMap<Integer,String> tiles = new HashMap<Integer,String>();
//	TODO: Load Char Painter's animation info.
	private final HashMap<String,String> anims = new HashMap<String,String>();
	
	private final AnimationPanel display;

	public Strategy(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		levels = new Vector<BattleMap>();
		setupTempTiles();
		tilePainter = new TileFactory<Integer>(loadPalette(tiles));
		envContext = new BattleMapContext(TILESIZE);
		
		mapObjects = new Vector<MapObject>();
		setupTempAnims();
		charPainter = new DisplayFactory<String,StrategyStat>(loadPalette(anims));
		/* TODO: Marker is temporary. */
		charContext = new MapObjectContext("Marker");
		
		LevelGenerator.generateLevel(mapFiles[0], new Dimension(10,10), new Dimension(2,2));
		loadLevels(mapFiles);
		setCurrentLevel(levels.firstElement());
		
		createMapObjects();
		
		display = new AnimationPanel(this);
		display.setPreferredSize(new Dimension(480,480));
		
		controllers = new Vector<MapController>();
		palette = new ConditionPalette<StrategyCondition,String,StrategyStat>();
		createController();
		display.addMouseListener(controllers.get(0));
		display.addMouseMotionListener(controllers.get(0));
		display.addMouseWheelListener(controllers.get(0));
		controllers.get(0).mapTarget(mapObjects.get(0));
		
		this.setLayout(new BorderLayout());
		Thread t = new Thread(new Runnable(){

			public void run() {
				while(true){
					for(MapController controller : controllers){
						controller.evaluateAllEvents();
						controller.flushEvents();
					}
					for(MapObject subject : mapObjects){
						palette.performAction(DisplayCondition.UPDATE_DISPLAY, subject);
					}
					display.frameChange(FrameAction.FRAME_ENTER);
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
	
	private void createController() {
		MapController controller = new MapController(this);
		controllers.add(controller);
		loadConditions(palette);
		controller.setPalette(palette);
	}
	
	private void loadConditions(ConditionPalette<StrategyCondition,String,StrategyStat> palette){
		ActionPalette<String,StrategyStat> ap = loadActions();
		
		AStar setDest = new AStar(this); setDest.setActionPalette(ap); setDest.addAction("move");
		
		palette.put(MapCondition.SET_DEST, setDest);
		palette.put(DisplayCondition.UPDATE_DISPLAY, setDest);
	}
	
	private ActionPalette<String,StrategyStat> loadActions(){
		ActionPalette<String,StrategyStat> ap = new ActionPalette<String,StrategyStat>();
		
		AbstractAction<StrategyStat> move = new AbstractAction<StrategyStat>(this){
			@Override
			public void performAction(IEntity<StrategyStat> subject, IEntity<StrategyStat>... objects) {
				int dx = 0;
				int dy = 0;
				if(Math.abs(subject.minus(MapObjectStat.MAP_X_DEST, MapObjectStat.MAP_X_POS).doubleValue())  > 12){
					if(subject.value(MapObjectStat.MAP_X_POS).compareTo(subject.value(MapObjectStat.MAP_X_DEST)) > 0){
						subject.minusEq(MapObjectStat.MAP_X_POS, 12);
						dx = -1;
					}else{
						subject.plusEq(MapObjectStat.MAP_X_POS, 12);
						dx = 1;
					}
				}else{
					subject.value(MapObjectStat.MAP_X_POS, subject.value(MapObjectStat.MAP_X_DEST));
				}
				if(Math.abs(subject.minus(MapObjectStat.MAP_Y_DEST, MapObjectStat.MAP_Y_POS).doubleValue())  > 12){
					if(subject.value(MapObjectStat.MAP_Y_POS).compareTo(subject.value(MapObjectStat.MAP_Y_DEST)) > 0){
						subject.minusEq(MapObjectStat.MAP_Y_POS, 12);
						dy = -1;
					}else{
						subject.plusEq(MapObjectStat.MAP_Y_POS, 12);
						dy = 1;
					}
				}else{
					subject.value(MapObjectStat.MAP_Y_POS, subject.value(MapObjectStat.MAP_Y_DEST));
				}
				
				if(dx == 1 && dy == 0){
					subject.value(MapObjectStat.MAP_FACING,MapObjectStat.MAP_FACE_E);
				}else if(dx == 0 && dy == 1){
					subject.value(MapObjectStat.MAP_FACING,MapObjectStat.MAP_FACE_S);
				}else if(dx == -1 && dy == 0){
					subject.value(MapObjectStat.MAP_FACING,MapObjectStat.MAP_FACE_W);
				}else if(dx == 0 && dy == -1){
					subject.value(MapObjectStat.MAP_FACING,MapObjectStat.MAP_FACE_N);
				}
			}
		}; ap.put("move", move);
		return ap;
	}
	
	/**
	 * @deprecated
	 *
	 */
	private void setupTempTiles() {
		tiles.put(0,"/home/mstockbridge/Projects/TopDownTurnBasedBranch/src/strategy/temp/tiles.png");
	}
	
	/**
	 * @deprecated
	 *
	 */
	private void setupTempAnims() {
		anims.put("Marker", "/home/mstockbridge/Projects/TopDownTurnBasedBranch/src/strategy/temp/Marker.png");
	}
	
	private void createMapObjects() {
		MapObjectStat.initialize(480, 480, 0, 0, 0, 0, 48, 2);
		createMapObject();
	}
	
	private void createMapObject() {
		MapObject mo = new MapObject(new Stats<StrategyStat>(),charContext,charPainter);
		mapObjects.add(mo);
		mo.value(MapObjectStat.MAP_X_POS,0);
		mo.value(MapObjectStat.MAP_Y_POS,0);
		mo.value(MapObjectStat.MAP_WIDTH,TILESIZE.width);
		mo.value(MapObjectStat.MAP_HEIGHT,TILESIZE.height);
		mo.updateBounds();
		
		mo.setMapping(createMapObjectControlMapping());
	}
	
	private IControlMapping<StrategyCondition, StrategyStat> createMapObjectControlMapping() {
		IControlMapping<StrategyCondition, StrategyStat> defaultMapping = new IControlMapping<StrategyCondition, StrategyStat>(){

			public StrategyCondition getMapingFor(IEntity<StrategyStat> entity, AWTEvent e) {
				//TODO: Only one mapping: Go to dest.
				return MapCondition.SET_DEST;
			}
			
		};
		return defaultMapping;
	}

	private void loadLevels(String... path) {
		//TODO: Right now just one image per level.
		for(String p : path){
			Vector<BufferedImage> v = new Vector<BufferedImage>();
			BufferedImage bi = loadImage(p);
			v.add(bi);
			loadLevel(v);
		}
	}
	
	private void loadLevel(Vector<BufferedImage> imgs) {
		BattleMap map = new BattleMap(imgs,new Stats<StrategyStat>(),envContext,tilePainter);
		levels.add(map);
	}
	
	private void setCurrentLevel(BattleMap map) {
		MapObjectStat.initialize(
				map.value(MapStat.MAP_WIDTH).intValue(), 
				map.value(MapStat.MAP_HEIGHT).intValue(), 
				MapStat.Z_MAX, 
				0, 
				0, 
				0, 
				MapStat.TILE_SIZE, 
				ANIMAX
		);
	}
	
	private <A extends Comparable> ImagePalette<A> loadPalette(HashMap<A,String> paths){
		ImagePalette<A> palette = new ImagePalette<A>();
		for(A key : paths.keySet()){
			//TODO: Better method. Allow different tilesizes.
			BufferedImage img = loadImage(paths.get(key));
			if(img != null){
				ImageBoard board = new ImageBoard(img,TILESIZE);
				palette.put(key, board);
			}
		}
		return palette;
	}
	
	private BufferedImage loadImage(String path){
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new Strategy()).start();
	}

	public void start() {
		this.add(display,BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public <C extends IController<?, ?>> Collection<C> getControllers(Object caller) {
		return (Collection<C>) controllers;
	}

	@SuppressWarnings("unchecked")
	public <D extends IDisplayable<?, ?>> Collection<D> getDisplayables(Object caller) {
		return (Collection<D>) mapObjects;
	}

	@SuppressWarnings("unchecked")
	public <E extends IEntity<?>> Collection<E> getEntities(Object caller) {
		return (Collection<E>) mapObjects;
	}

	@SuppressWarnings("unchecked")
	public <V extends IEnvironment<?, ?>> Collection<V> getEnvironments(Object caller) {
		return (Collection<V>) levels;
	}

	public int getMaxDepth() {
		return MapObjectStat.Z_MAX;
	}

}
