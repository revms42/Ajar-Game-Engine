package space.model;

import java.awt.Point;


public class TaskPoint {
	
	private String task;
	private IMappable point;
	
	public TaskPoint(){
		task = "none";
		point = null;
	}
	
	public TaskPoint(IMappable target){
		task = "none";
		point = target;
	}
	
	public TaskPoint(String task, IMappable target){
		this.task = task;
		point = target;
	}
	
	public TaskPoint(final Point target){
		task = "Go To";
		point = new IMappable(){
			public Point getPosition() {
				return target;
			}
		};
	}
	
	public TaskPoint(String task, final Point target){
		this.task = task;
		point = new IMappable(){
			public Point getPosition() {
				return target;
			}
		};
	}
	
	public String getTask(){
		return task;
	}
	
	public void setTask(String task){
		this.task = task;
	}
	
	public IMappable getTarget() {
		return point;
	}
	
	public void getTarget(IMappable target) {
		this.point = target;
	}
}
