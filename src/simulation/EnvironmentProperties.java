package simulation;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class EnvironmentProperties {
	private static final String GRAVITY_KEYWORD = "gravity";
	private static final String VISCOSITY_KEYWORD = "viscosity";
	private static final String CENTEROFMASS_KEYWORD ="centerofmass";
	private static final String WALLREPULSION_KEYWORD = "wall";
	
	private Gravity myGravity;
	private Viscosity myViscosity;
	private CenterOfMass myCenter;
	private List<WallRepulsion> myWalls = new ArrayList<WallRepulsion>();

	
	
	 public void loadEnvironment (File modelFile) {
	     reset();   
		 try {
	            Scanner input = new Scanner(modelFile);
	            while (input.hasNext()) {
	                Scanner line = new Scanner(input.nextLine());
	                if (line.hasNext()) {
	                    String type = line.next();
	                    if (GRAVITY_KEYWORD.equals(type)) {
	                        myGravity = Gravity.gravityCommand(line);
	                    }
	                    else if (VISCOSITY_KEYWORD.equals(type)) {
	                        myViscosity = Viscosity.viscosityCommand(line);
	                    }
	                    else if(CENTEROFMASS_KEYWORD.equals(type)){
	                    	myCenter = CenterOfMass.centerOfMassCommand(line);
	                    }
	                    else if(WALLREPULSION_KEYWORD.equals(type)){
                               WallRepulsion wall = new WallRepulsion();
                               wall = WallRepulsion.wallRepulsionCommand(line);
                               myWalls.add(wall);

	                    	
	                    
	                    }
	                    
	                }
	            }
	            input.close();
	        }
	        catch (FileNotFoundException e) {
	            // should not happen because File came from user selection
	            e.printStackTrace();
	        }
	 }
	 
	 
	 public Gravity getMyGravity() {
		return myGravity;
	}


	public Viscosity getMyViscosity() {
		return myViscosity;
	}

	public CenterOfMass getMyCenter() {
		return myCenter;
	}

	
	public void applyGravity(Mass m){
		m.applyForce(myGravity.getForce(m));
	}
	
	public void applyEnvironment(Mass m,Dimension bounds){
		
		myCenter.applyCenterOfMass(m);
		myViscosity.applyViscosity(m);
		for(WallRepulsion w : myWalls){
			w.applyWallRepulsion(m,bounds);
		}
	}
	
	
	public void reset(){
		myGravity = new Gravity();
		myViscosity = new Viscosity();
		myCenter = new CenterOfMass(0,0);
		myWalls.clear();
	}
	
	/*
	
		Vector g = new Vector(getMyGravity());
		g.scale(m.getMyMass());
		m.applyForce(g);
	}
	
	public void applyVescosity(Mass m){
		Vector vescoForce = new Vector(m.getVelocity());
		vescoForce.scale(myViscosity);
		vescoForce.negate();
		m.applyForce(vescoForce);
	}
	public void applyCenterAttraction(Mass m){
		Vector attForce  = myCenter.getForce(m);
	    m.applyForce(attForce);
	}
	public void applyRepulsionForce(Mass m, Dimension bounds){
		for(WallRepulsion wr : myWalls){
			Vector repForce = wr.getForce(m,bounds);
			m.applyForce(repForce);
		}
		
	}
	public void applyEnvironment(Mass m, Dimension bounds){
		this.applyCenterAttraction(m);
		this.applyRepulsionForce(m, bounds);
		this.applyVescosity(m);
	}
    */
}
