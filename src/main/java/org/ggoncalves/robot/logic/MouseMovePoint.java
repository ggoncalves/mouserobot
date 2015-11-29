package org.ggoncalves.robot.logic;

import static org.ggoncalves.robot.logic.config.Settings.POINT_GAP;

import java.awt.Point;

public class MouseMovePoint {
	
	private Point fixedPoint;
	private Point movedPoint;
	
	public MouseMovePoint(final Point fixedPoint) {
		this.setFixedPoint(fixedPoint);
		Point movedPoint = this.calculateMovedPoint();
		this.setMovedPoint(movedPoint);
	}
	
	private Point calculateMovedPoint() {
	  return new Point(getFixedPoint().x + POINT_GAP, getFixedPoint().y + POINT_GAP);
	  
  }

	private void setFixedPoint(Point fixedPoint) {
	  this.fixedPoint = fixedPoint;
  }
	
	private void setMovedPoint(Point movedPoint) {
	  this.movedPoint = movedPoint;
  }
	
	public Point getFixedPoint() {
	  return this.fixedPoint;
  }
	
	public Point getMovedPoint() {
	  return this.movedPoint;
  }
}
