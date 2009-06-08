package org.interaction.pathfinding;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Vector;

import org.IGameManifest;
import org.display.IEnvironment;
import org.interaction.AbstractCondition;
import org.interaction.ActionPalette;
import org.interaction.IEntity;

public abstract class AbstractAStar<A,K> extends AbstractCondition<A,K> {
	
	private final Vector<ATile<K>> all;
	private final Vector<ATile<K>> open;
	private final Vector<ATile<K>> closed;
	
	private final Vector<ATile<K>> path;
	
	private IAStarPathingCost<K> heuristic;
	
	private boolean single_step;
	private boolean increment;
	
	private int w;
	private int b;
	private int h;
	
	public AbstractAStar(IGameManifest manifest) {
		super(manifest);
		single_step = false;
		increment = true;
		this.heuristic = null;
		
		all = new Vector<ATile<K>>();
		open = new Vector<ATile<K>>();
		closed = new Vector<ATile<K>>();
		path = new Vector<ATile<K>>();
		
		generateTiles();
	}
	
	public AbstractAStar(ActionPalette<A, K> palette, IGameManifest manifest) {
		super(palette, manifest);
		single_step = false;
		increment = true;
		this.heuristic = null;
		
		all = new Vector<ATile<K>>();
		open = new Vector<ATile<K>>();
		closed = new Vector<ATile<K>>();
		path = new Vector<ATile<K>>();
		
		generateTiles();
	}

	public AbstractAStar(ActionPalette<A, K> palette, Vector<A> actions, IGameManifest manifest) {
		super(palette, actions, manifest);
		single_step = false;
		increment = true;
		this.heuristic = null;
		
		all = new Vector<ATile<K>>();
		open = new Vector<ATile<K>>();
		closed = new Vector<ATile<K>>();
		path = new Vector<ATile<K>>();
		
		generateTiles();
	}
	
	public AbstractAStar(IAStarPathingCost<K> heuristic, IGameManifest manifest) {
		super(manifest);
		single_step = false;
		increment = true;
		this.heuristic = heuristic;
		
		all = new Vector<ATile<K>>();
		open = new Vector<ATile<K>>();
		closed = new Vector<ATile<K>>();
		path = new Vector<ATile<K>>();
		
		generateTiles();
	}
	
	public AbstractAStar(ActionPalette<A, K> palette, IAStarPathingCost<K> heuristic,IGameManifest manifest) {
		super(palette, manifest);
		single_step = false;
		increment = true;
		this.heuristic = heuristic;
		
		all = new Vector<ATile<K>>();
		open = new Vector<ATile<K>>();
		closed = new Vector<ATile<K>>();
		path = new Vector<ATile<K>>();
		
		generateTiles();
	}

	public AbstractAStar(ActionPalette<A, K> palette, Vector<A> actions, IAStarPathingCost<K> heuristic, IGameManifest manifest) {
		super(palette, actions, manifest);
		single_step = false;
		increment = true;
		this.heuristic = heuristic;
		
		all = new Vector<ATile<K>>();
		open = new Vector<ATile<K>>();
		closed = new Vector<ATile<K>>();
		path = new Vector<ATile<K>>();
		
		generateTiles();
	}

	protected IAStarPathingCost<K> getPathingHeuristic() {
		return heuristic;
	}

	protected void setPathingHeuristic(IAStarPathingCost<K> heuristic) {
		this.heuristic = heuristic;
	}
	
	/**
	 * When called this method computes which tiles are adjacent to the tile at the point given by params <code>x</code>,<code>y</code>,and <code>z</code>.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public abstract Vector<ATile<K>> findNeighbors(Number x, Number y, Number z);

	/**
	 * When called with values <code>x</code>,<code>y</code>,and <code>z</code> this returns the next unit in the x direction.
	 * <nl>
	 * When called with <code>-1</code>,<code>-1</code>,<code>-1</code> this returns the first unit in the x direction.
	 * <nl>
	 * This method returns <em>only</em> positive numbers.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public abstract Number nextXValue(Number x, Number y, Number z);
	/**
	 * When called with values <code>x</code>,<code>y</code>,and <code>z</code> this returns the next unit in the y direction.
	 * <nl>
	 * When called with <code>-1</code>,<code>-1</code>,<code>-1</code> this returns the first unit in the y direction.
	 * <nl>
	 * This method returns <em>only</em> positive numbers.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public abstract Number nextYValue(Number x, Number y, Number z);
	/**
	 * When called with values <code>x</code>,<code>y</code>,and <code>z</code> this returns the next unit in the z direction.
	 * <nl>
	 * When called with <code>-1</code>,<code>-1</code>,<code>-1</code> this returns the first unit in the z direction.
	 * <nl>
	 * This method returns <em>only</em> positive numbers.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public abstract Number nextZValue(Number x, Number y, Number z);
	
	private void generateTiles(){
		Collection<IEnvironment<?,?>> environments = getManifest().getEnvironments(this);
		w = 0;
		b = 0;
		h = 0;
		
		for(IEnvironment<?,?> e : environments){
			for(BufferedImage bi : e.getMaps()){
				w = bi.getWidth() > w ? bi.getWidth() : w;
				b = bi.getHeight() > b ? bi.getHeight() : b;
			}
			h++;
		}
		
		Number x = nextXValue(-1,-1,-1);
		Number y = nextYValue(-1,-1,-1);
		Number z = nextZValue(-1,-1,-1);
		for(; z.intValue() < h; z = nextZValue(x,y,z)){
			for(; y.intValue() < b; y = nextZValue(x,y,z)){
				for(; x.intValue() < w; x = nextXValue(x,y,z)){
					all.add(new ATile<K>(x,y,z,heuristic));
				}
			}
		}
		
		for(ATile<K> tile : all){
			tile.setNeigbors(findNeighbors(tile.x,tile.y,tile.z));
		}
	}
	
	/**
	 * True sets this condition's operation to single step. I.e. each time <code>isFullfilled</code> is called it will only return
	 * true until the current waypoint is reached.
	 * <nl>
	 * If not operating in single step mode <code>isFullfilled</code> will return true until the final waypoint is reached.
	 * <nl>
	 * Default is <code>false</code>.
	 * @param step
	 */
	public void setSingleStep(boolean step){
		single_step = step;
	}
	/**
	 * Returns <code>true</code> if single step operation is enabled.
	 * @return
	 */
	public boolean getSingleStep(){
		return single_step;
	}
	
	/**
	 * Increments the current destination to the next point on the path. Returns false if the path is empty (before increment).
	 * @return
	 */
	public boolean incrementStep(){
		if(path.isEmpty()){
			return false;
		}else{
			increment = true;
			return true;
		}
	}

	/**
	 * Finds the x position on the tile grid for a given entity. Used to find the start and end locations for pathfinding.
	 * @param subject
	 * @return
	 */
	public abstract Number getXPosition(IEntity<K> subject);
	/**
	 * Finds the y position on the tile grid for a given entity. Used to find the start and end locations for pathfinding.
	 * @param subject
	 * @return
	 */
	public abstract Number getYPosition(IEntity<K> subject);
	/**
	 * Finds the z position on the tile grid for a given entity. Used to find the start and end locations for pathfinding.
	 * @param subject
	 * @return
	 */
	public abstract Number getZPosition(IEntity<K> subject);
	/**
	 * Finds the remaining distance on the X axis between the subject's current position and this step's destination.
	 * @param subject
	 * @return
	 */
	public abstract Number distanceXRemaining(IEntity<K> subject);
	/**
	 * Finds the remaining distance on the Y axis between the subject's current position and this step's destination.
	 * @param subject
	 * @return
	 */
	public abstract Number distanceYRemaining(IEntity<K> subject);
	/**
	 * Finds the remaining distance on the Z axis between the subject's current position and this step's destination.
	 * @param subject
	 * @return
	 */
	public abstract Number distanceZRemaining(IEntity<K> subject);
	/**
	 * Sets the destination for the next path step based on a tile.
	 * @param subject
	 * @param tile
	 */
	public abstract void setDest(IEntity<K> subject, ATile<K> tile);
	
	/**
	 * Performs two seperate related actions based on the inputs. If an object is passed the algorithm
	 * uses it's position as the ultimate destination and performs pathfinding to that point. If the 
	 * point cannot be reached <code>false</code> is returned.
	 * <nl>
	 * Whether an object is passed or not the remaining protion of the method determines if the subject
	 * has reached either their next waypoint (in the case of single step operation) or their final position
	 * (in the case of continuous operation).
	 * <nl>
	 * For single step operation, if they have reached their current waypoint, the method returns true.
	 * Otherwise it returns false. Once the waypoint has been reached it will continue to return false
	 * until <code>incrementStep</code> is called to trigger setting the next waypoint.
	 * <nl>
	 * For continuous operation, if they have not reached their final destination, but they have reached 
	 * their intended position for the current waypoint, a new waypoint is removed from the path and the 
	 * subject's destination is set for that waypoint. Either way, if the subject is not at their final 
	 * position, true is returned.
	 * <nl>
	 * If the subject is at their final position false is returned. 
	 */
	@Override
	public boolean isFullfilled(IEntity<K> subject, IEntity<K>... objects) {
		if(objects != null && objects.length > 0){
			Number startX = getXPosition(subject);
			Number startY = getYPosition(subject);
			Number startZ = getZPosition(subject);
			
			Number endX = getXPosition(objects[0]);
			Number endY = getYPosition(objects[0]);
			Number endZ = getZPosition(objects[0]);
			
			boolean complete = findPath(subject,startX,startY,startZ,endX,endY,endZ);
			if(!complete) return false;
		}
		
		Number dx = distanceXRemaining(subject);
		Number dy = distanceYRemaining(subject);
		Number dz = distanceZRemaining(subject);
		
		//If you're at your dest (dx = 0, dy = 0) and there's no more path return false.
		if(dx.doubleValue() != 0 || dy.doubleValue() != 0 || dz.doubleValue() != 0 || !path.isEmpty()){
			//If you've reached your dest then you must have more path to go. So get it.
			if(increment){
				if(dx.doubleValue() == 0 && dy.doubleValue() == 0 && dz.doubleValue() == 0){
					ATile p = path.remove(0);
					setDest(subject,p);
					
					if(single_step){
						increment = false;
					}
				}
			}else{
				return false;
			}

			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Performs the actual pathfinding using an A* algorithm.
	 * @param start
	 * @param end
	 */
	protected boolean findPath(IEntity<K> entity, Number startX, Number startY, Number startZ, Number endX, Number endY, Number endZ) {
		if(check(entity,startX,startY,startZ) && check(entity,endX,endY,endZ)){
			ATile<K> s = find(startX,startY,startZ);
			ATile<K> e = find(endX,endY,endZ);
			
			if(s != null && e != null && s != e){
				path.removeAllElements();
				
				setDest(entity,e);
				open.add(s);
				while(!nextStep(entity,e));
				
				open.removeAllElements();
				closed.removeAllElements();
				
				if(e.parent != null){
					ATile<K> n = e;
					while(n != s){
						path.insertElementAt(n,0);
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
	
	/**
	 * Finds the step with the next lowest cost and puts its neighbors into the open state, removing the passed tile to the closed state.
	 * @param dest
	 * @return
	 */
	private boolean nextStep(IEntity<K> entity, ATile<K> dest){
		if(open.contains(dest)){
			return true;
		}else{
			ATile<K> next = null;
			for(ATile<K> t : open){
				if(next == null || t.cost <= next.cost){
					next = t;
				}
			}
			
			checkSurrounding(entity,next);
			open.remove(next);
			closed.add(next);
			return false;
		}
	}
	
	
	private void checkSurrounding(IEntity<K> entity, ATile<K> t){
		for(ATile<K> n : t.neighbors){
			if(n != null && !closed.contains(n)) open(entity,n,t);
		}
	}
	
	private void open(IEntity<K> entity, ATile<K> t, ATile<K> parent){
		if(open.contains(t)){
			double newPath = parent.cost + heuristic.oneStepCost(entity, t, parent);
			
			if(newPath < t.path){
				t.setParent(entity, parent);
			}
		}else{
			open.add(t);
			t.setParent(entity, parent);
		}
	}
	
	private boolean check(IEntity<K> entity, Number x, Number y, Number z){
		IEnvironment<?,?> e = (IEnvironment<?,?>)getManifest().getEnvironments(this).toArray()[z.intValue()];
		
		for(BufferedImage bi : e.getMaps()){
			int w = bi.getWidth();
			int h = bi.getHeight();
			
			if(x.doubleValue() >= 0 && x.doubleValue() < w && y.doubleValue() >= 0 && y.doubleValue() < h){
				if(!isEnterable(e,bi.getRGB(x.intValue(),y.intValue()))) return false;
			}else{
				return false;
			}
		}
		
		return true;
	}
	
	private ATile<K> find(Number x, Number y, Number z){
		ATile<K> t = fastFind(x,y,z);
		if(	t != null &&
			t.x.doubleValue() == x.doubleValue() && 
			t.y.doubleValue() == y.doubleValue() && 
			t.z.doubleValue() == z.doubleValue()
		){
			return t;
		}else{
			return slowFind(x,y,z);
		}
		
	}
	
	private ATile<K> fastFind(Number x, Number y, Number z){
		if(		x.doubleValue() >= 0 && x.doubleValue() < w && 
				y.doubleValue() >= 0 && y.doubleValue() < b &&
				z.doubleValue() >= 0 && z.doubleValue() < h 
		){
			return all.get(((((z.intValue() * h) + y.intValue()) * w) + x.intValue()));
		}else{
			return null;
		}
	}
	
	private ATile<K> slowFind(Number x, Number y, Number z){
		for(ATile<K> t : all){
			if(	t.x.doubleValue() == x.doubleValue() && 
				t.y.doubleValue() == y.doubleValue() && 
				t.z.doubleValue() == z.doubleValue()
			) return t;
		}
		
		return null;
	}
	
	/**
	 * Returns <code>true</code> if the given color code for a tile is enterable for the given environment.
	 * @param e
	 * @param color
	 * @return
	 */
	public abstract boolean isEnterable(IEnvironment<?,?> e, int color);
	
	/**
	 * 
	 * @author mstockbridge
	 *
	 */
	public static class ATile<K> {
		private static final long serialVersionUID = 1L;
		
		private final Number x;
		private final Number y;
		private final Number z;
		
		private ATile<K> parent;
		private ATile<K> destination;
		
		private double cost;
		private double remaining;
		private double path;
		
		private IAStarPathingCost<K> heuristic;
		
		private Vector<ATile<K>> neighbors;
		
		private ATile(Number x, Number y, Number z, IAStarPathingCost<K> heuristic){
			this.x = x;
			this.y = y;
			this.z = z;
			
			this.heuristic = heuristic;
		}
		
		private void setCost(IEntity<K> entity){
			if(parent != null && destination != null){
				remaining = heuristic.remainingPathCost(entity, this, destination);
				path = parent.cost + heuristic.oneStepCost(entity, this, parent);
				cost = path + remaining;
			}
		}
		
		public void setDest(IEntity<K> entity, ATile<K> dest){
			this.destination = dest;
			setCost(entity);
		}
		
		public void setParent(IEntity<K> entity, ATile<K> parent){
			this.parent = parent;
			setCost(entity);
		}
		
		public void setPathCoster(IEntity<K> entity, IAStarPathingCost<K> heuristic){
			this.heuristic = heuristic;
			setCost(entity);
		}
		
		public void setNeigbors(Vector<ATile<K>> neighbors){
			this.neighbors = neighbors;
		}
		
		public Number distanceTo(ATile dest){
			Point2D.Double cartStart = new Point2D.Double(x.doubleValue(), y.doubleValue());
			Point2D.Double cartEnd = new Point2D.Double(dest.x.doubleValue(), dest.y.doubleValue());
			
			double cartLength = cartStart.distance(cartEnd);
			
			if(z.doubleValue() != dest.z.doubleValue()){
				Point2D.Double projStart = new Point2D.Double(0,0);
				Point2D.Double projEnd = new Point2D.Double(cartLength,z.doubleValue() - dest.z.doubleValue());
				
				return projStart.distance(projEnd);
			}else{
				return cartLength;
			}
		}
	}
	
	/**
	 * Defines an A* estimated cost heuristic.
	 * @author mstockbridge
	 *
	 */
	public interface IAStarPathingCost<K> {
		public double remainingPathCost(IEntity<K> entity, ATile current, ATile end);
		public double oneStepCost(IEntity<K> entity, ATile current, ATile previous);
	}
}
