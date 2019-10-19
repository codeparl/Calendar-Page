/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author HASSAN
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

public class AnalogClock extends JPanel {
	private Timer clock;
	private String name;
	private int rSeconds;
	private int rMinutes;
	private int rHours;
	private int[] xSeconds = new int[4];
	private int[] ySeconds = new int[4];
	private int[] xMinutes = new int[4];
	private int[] yMinutes = new int[4];
	private int[] xHours = new int[4];
	private int[] yHours = new int[4];
	private int angleSeconds;
	private int angleMinutes;
	private int angleHours;
	private double inc;
	private static final double RADIANS_PER_DEGREE = Math.PI / 180.0;
	private int clockSize;
	private Color clockColor;
	
	
	private class ClockAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}

	public AnalogClock(int size, Color color, int frameSize) {
		super();
		clockSize = size;
		clockColor = color;
		int radius = size / 2;
		inc = size * 0.0003;
		xSeconds[0] = radius;
		ySeconds[0] = radius;
		xMinutes[0] = radius;
		yMinutes[0] = radius;
		xHours[0] = radius;
		yHours[0] = radius;
		this.setPreferredSize(new Dimension(frameSize,frameSize));
		rSeconds = (int)(0.75 * radius);
		rMinutes = (int)(0.75 * radius);
		rHours = (int)(0.55 * radius);
		setTime();
		getPolygon(angleSeconds,rSeconds,xSeconds,ySeconds);
		getPolygon(angleMinutes,rMinutes,xMinutes,yMinutes);
		getPolygon(angleHours,rHours,xHours,yHours);
		clock = new Timer(1000,new ClockAction());
		clock.start();
	}

	void setTime() { // set to current time
		Calendar now = Calendar.getInstance();
		angleHours = (270 + 30 * now.get(now.HOUR) + now.get(now.MINUTE) / 2 ) % 360;
		angleMinutes = (270 + 6 * now.get(now.MINUTE)) % 360;
		angleSeconds = (270 + 6 * now.get(now.SECOND)) % 360;
	}

	public void nextTime() {
		setTime();
		getPolygon(angleSeconds,rSeconds,xSeconds,ySeconds);
		getPolygon(angleMinutes,rMinutes,xMinutes,yMinutes);
		getPolygon(angleHours,rHours,xHours,yHours);
	} // end nextTime

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		
		// this draws the clock background
		g.setColor(clockColor);
		g.fillOval(xSeconds[0]-clockSize/2,ySeconds[0]-clockSize/2,clockSize,clockSize);
		
		// this draws the numbers around the clock
		int fontSize = clockSize / 10;
		Font numberFont = new Font("Courier",Font.BOLD,fontSize);
		g2D.setColor(Color.red);
		if( clockColor.equals(Color.red) ) {
			g2D.setColor(Color.white);
		} // end if
		g2D.setFont(numberFont);
		for( int angle = 30; angle <= 360; angle += 30) {
			double theta = (270 + angle) * RADIANS_PER_DEGREE;
			String S = "" + angle / 30;
			int adj = (int)(clockSize * 0.025);
			if( S.length() > 1 ) adj *= 2;
			int x = (int)(clockSize * 0.45 * Math.cos(theta)) + xSeconds[0] - adj;
			int y = (int)(clockSize * 0.45 * Math.sin(theta)) + ySeconds[0] + adj;
			g2D.drawString(S,x,y);
		} // end for
		nextTime();
		Color secondsColor = Color.white;
		if( secondsColor.equals(clockColor) ) {
			secondsColor = Color.black;
		} // end if
		Color handsColor = Color.blue;
		if( handsColor.equals(clockColor) ) {
			handsColor = Color.red;
		} // end if
		g2D.setColor(handsColor);
		g2D.fillPolygon(xMinutes,yMinutes,4);
		g2D.setColor(Color.white);
		g2D.drawPolygon(xMinutes,yMinutes,4);
		g2D.setColor(handsColor);
		g2D.fillPolygon(xHours,yHours,4);
		g2D.setColor(Color.white);
		g2D.drawPolygon(xHours,yHours,4);
		g2D.setColor(secondsColor);
		g2D.fillPolygon(xSeconds,ySeconds,4);
	} // end paintComponent
 	
 	public void start() {
 		clock.start();
 	} // end start
 	
	public void stop() {
		clock.stop();
	} // end stop

  	private void getPolygon(int angle, int radius, int[] x, int[] y) {
  		double theta = RADIANS_PER_DEGREE * angle;
  		double thetaL = theta - inc;
  		double thetaR = theta + inc;
		x[1] = (int)(radius *0.8 * Math.cos(thetaL)) + x[0];
		y[1] = (int)(radius* 0.8 * Math.sin(thetaL)) + y[0];
		x[2] = (int)(radius * Math.cos(theta)) + x[0];
		y[2] = (int)(radius * Math.sin(theta)) + y[0];
		x[3] = (int)(radius*0.8 * Math.cos(thetaR)) + x[0];
		y[3] = (int)(radius*0.8 * Math.sin(thetaR)) + y[0];
  	} // end getPolygon
  	
  	public void setClock(int x, int y) {
  		xSeconds[0] = x;
  		ySeconds[0] =  y;
		xMinutes[0] =  x;
		yMinutes[0] =  y;
		xHours[0] =  x;
		yHours[0] =  y;
		repaint();
	}
  	

} // end Analog clock

