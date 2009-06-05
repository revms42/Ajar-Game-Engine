package strategy.interaction.movement;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;

import org.IGameManifest;
import org.display.IEnvironment;
import org.interaction.AbstractCondition;
import org.interaction.IEntity;

import strategy.model.StrategyStat;
import strategy.model.map.object.MapObjectStat;
/**
 * This is a *very* simple A* implementation. We assume mahattan movement and equal pathing costs over all terrain.
 * @author mstockbridge
 *
 */
public class ASharp extends AbstractCondition<String,StrategyStat>{
	
	private final Vector<Tile> open;
	private final Vector<Tile> closed;
	private final Vector<Tile> all;
	
	private final int w;
	private final int h;
	
	public static int NO_ENTRY_FLAG = 0;
	
	public final Vector<Point> path;
	
	public ASharp(IGameManifest manifest){
		super(manifest);
		
		open = new Vector<Tile>();
		closed = new Vector<Tile>();
		all = new Vector<Tile>();
		
		path = new Vector<Point>();
		
		int w = 0;
		int h = 0;
		for(IEnvironment<?,?> e : getManifest().getEnvironments(this)){
			for(BufferedImage bi : e.getMaps()){
				w = bi.getWidth() > w ? bi.getWidth() : w;
				h = bi.getHeight() > h ? bi.getHeight() : h;
			}
		}
		this.w = w;
		this.h = h;
		
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				all.add(new Tile(new Point(x,y)));
			}
		}
		for(Tile t : all){
			Point N = new Point(t.p.x,t.p.y-1);
			Point E = new Point(t.p.x+1,t.p.y);
			Point S = new Point(t.p.x,t.p.y+1);
			Point W = new Point(t.p.x-1,t.p.y);
			
			t.setN((check(N)) ? find(N) : null);
			t.setE((check(E)) ? find(E) : null);
			t.setS((check(S)) ? find(S) : null);
			t.setW((check(W)) ? find(W) : null);
		}
	}
	
	public boolean findPath(Point start, Point end){
		if(check(start) && check(end)){
			Tile s = find(start);
			Tile e = find(end);
			
			if(s != null && e != null && s != e){
				path.removeAllElements();
				
				setDest(e);
				open.add(s);
				while(!nextStep(e));
				
				open.removeAllElements();
				closed.removeAllElements();
				
				if(e.parent != null){
					Tile n = e;
					while(n != s){
						path.insertElementAt(n.p,0);
						n = n.parent;
					}
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	private boolean nextStep(Tile dest){
		if(open.contains(dest)){
			return true;
		}else{
			Tile next = null;
			for(Tile t : open){
				if(next == null || t.cost() <= next.cost()){
					next = t;
				}
			}
			
			checkSurrounding(next);
			open.remove(next);
			closed.add(next);
			return false;
		}
	}
	
	private void checkSurrounding(Tile t){
		Tile N = t.getN();
		Tile E = t.getE();
		Tile W = t.getW();
		Tile S = t.getS();
		
		if(N != null && !closed.contains(N)) open(N,t);
		if(E != null && !closed.contains(E)) open(E,t);
		if(W != null && !closed.contains(W)) open(W,t);
		if(S != null && !closed.contains(S)) open(S,t);
	}
	
	private void open(Tile t, Tile parent){
		open.add(t);
		t.setParent(parent);
	}

	@Override
	public boolean isFullfilled(IEntity<StrategyStat> subject, IEntity<StrategyStat>... objects) {
		if(objects != null && objects.length > 0){
			Point start = new Point(
					positionToTile(subject.value(MapObjectStat.MAP_X_POS).intValue()),
					positionToTile(subject.value(MapObjectStat.MAP_Y_POS).intValue())
			);
			Point end = new Point(
					positionToTile(objects[0].value(MapObjectStat.MAP_X_POS).intValue()),
					positionToTile(objects[0].value(MapObjectStat.MAP_Y_POS).intValue())
			);
			
			findPath(start,end);
		}
		
		int dx = subject.minus(MapObjectStat.MAP_X_POS,MapObjectStat.MAP_X_DEST).intValue();
		int dy = subject.minus(MapObjectStat.MAP_Y_POS,MapObjectStat.MAP_Y_DEST).intValue();
		
		if(dx != 0 || dy != 0 || !path.isEmpty()){
			if(dx == 0 && dy == 0){
				Point p = path.remove(0);
				subject.value(MapObjectStat.MAP_X_DEST, p.x * MapObjectStat.TILE_SIZE);
				subject.value(MapObjectStat.MAP_Y_DEST, p.y * MapObjectStat.TILE_SIZE);
			}
			return true;
		}else{
			return false;
		}
	}
	
	private int positionToTile(int value){
		return (int)Math.floor(value/MapObjectStat.TILE_SIZE);
	}
	
	private boolean check(Point p){
		for(IEnvironment<?,?> e : getManifest().getEnvironments(this)){
			for(BufferedImage bi : e.getMaps()){
				int w = bi.getWidth();
				int h = bi.getHeight();
				
				if(p.x >= 0 && p.x < w && p.y >= 0 && p.y < h){
					if((bi.getRGB(p.x, p.y) | NO_ENTRY_FLAG) == 0) return false;
				}else{
					return false;
				}
			}
		}
		
		return true;
	}
	
	private Tile find(Point p){
		Tile t = fastFind(p);
		if(t != null && t.p.equals(p)){
			return t;
		}else{
			return slowFind(p);
		}
		
	}
	
	private Tile fastFind(Point p){
		if(p.x >= 0 && p.x < w && p.y >= 0 && p.y < h){
			return all.get((p.y * h + p.x));
		}else{
			return null;
		}
	}
	
	private Tile slowFind(Point p){
		for(Tile t : all){
			if(t.p.equals(p)) return t;
		}
		
		return null;
	}
	
	private void setDest(Tile d){
		for(Tile a : all){
			a.setDest(d);
		}
	}
	
	private static class Tile {
		public final Point p;
		public Tile parent;
		public Tile dest;
		
		private Tile N;
		private Tile E;
		private Tile S;
		private Tile W;
		
		private int cost;
		private int remaining;
		private int path;
		
		public Tile(Point p){
			this.p = p;
		}
		
		private int manhattanRemaining(){
			return 	(dest.p.x >= p.x ? (dest.p.x - p.x) : (p.x - dest.p.x)) +
					(dest.p.y >= p.y ? (dest.p.y - p.y) : (p.y - dest.p.y));
		}
		
		private void setCost(){
			if(parent != null && dest != null){
				remaining = manhattanRemaining();
				path = parent.cost() + 1;
				cost = path + remaining;
			}
		}
		
		public void setDest(Tile dest){
			this.dest = dest;
			setCost();
		}
		
		public void setParent(Tile parent){
			this.parent = parent;
			setCost();
		}
		
		public int cost(){
			return cost;
		}

		protected Tile getE() {
			return E;
		}

		protected void setE(Tile e) {
			E = e;
		}

		protected Tile getN() {
			return N;
		}

		protected void setN(Tile n) {
			N = n;
		}

		protected Tile getS() {
			return S;
		}

		protected void setS(Tile s) {
			S = s;
		}

		protected Tile getW() {
			return W;
		}

		protected void setW(Tile w) {
			W = w;
		}
	}
}
