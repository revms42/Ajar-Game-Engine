package space.model;

import java.util.Vector;

public interface IMovable extends IMappable {

	public Vector<TaskPoint> getDestination();
	public void setDestination(TaskPoint... dest);
	public void addDestination(TaskPoint dest);
	public void removeDestination(TaskPoint dest);
	
	public double getHeading();
}
