/* Kervon Gibson
   Computer and Information Science (B.S)
   kervongibson@gmail.com
   
   PROBLEM:
   MARS ROVERS
   A squad of robotic rovers are to be landed by NASA on a plateau on Mars. This plateau, which is
   curiously rectangular, must be navigated by the rovers so that their on-board cameras can get a
   complete view of the surrounding terrain to send back to Earth. A rover's position and location
   is represented by a combination of x and y co-ordinates and a letter representing one of the four
   cardinal compass points. The plateau is divided up into a grid to simplify navigation. An example
   position might be 0, 0, N, which means the rover is in the bottom left corner and facing North.
   In order to control a rover, NASA sends a simple string of letters. The possible letters are 'L',
   'R' and 'M'. 'L' and 'R' makes the rover spin 90 degrees left or right respectively, without moving
   from its current spot. 'M' means move forward one grid point, and maintain the same heading. Assume
   that the square directly North from (x, y) is (x, y+1).

   INPUT:
   The first line of input is the upper-right coordinates of the plateau, the lower-left coordinates
   are assumed to be 0,0. The rest of the input is information pertaining to the rovers that have been
   deployed. Each rover has two lines of input. The first line gives the rover's position, and the second
   line is a series of instructions telling the rover how to explore the plateau. The position is made up
   of two integers and a letter separated by spaces, corresponding to the x and y co-ordinates and the
   rover's orientation. Each rover will be finished sequentially, which means that the second rover won't
   start to move until the first one has finished moving.

   OUTPUT:
   The output for each rover should be its final co-ordinates and heading.
   
   INPUT AND OUTPUT
   Test Input:
   5 5
   1 2 N
   LMLMLMLMM
   3 3 E
   MMRMMRMRRM
   
   Expected Output:
   1 3 N
   5 1 E
   ==========

   SOLUTION
   MarsRoverJava.java

   This program implements Jump Tables to reduce the number of comparisons made, this is very useful
   when you have a lot of input data to process. The input is read in from a file one token at a time.
   To run this program type "javac MarsRoverJava.java" followed by "java MarsRoverJava Input.txt" at
   the command line, where "Input.txt" is the name of your input file. The first line of the input file 
   is assumed to be two integers separated by a white space. Every two lines following, we will assume
   the first line of input contains two integers and a character (a direction either a N for North, S 
   for South, W for West, E for East) and the second line contains a string of instructions (either a M
   for move forward one, L for turning in place to the Left, R for turning in place to the Right). This
   program is not case sensitive it accepts 'm', 'l', 'r', 'n', 's', 'w', 'e'.
*/

import java.util.Scanner;
import java.lang.String;
import java.lang.Character;
import java.io.File;
import java.io.FileNotFoundException;

public class MarsRoversJava{
	public static void main(String args[]){
		if(args.length > 0){
			//Input file is read in as an argument from the command line
			File inputfile = new File(args[0]);
            Mission marsMission = new Mission(inputfile);
        }
        return;
	}
}

class Mission{
	//create plateau mars
	private Plateau mars = new Plateau();
	//Create a rover
	private Rover curiosity = new Rover();
	//Create Intreuctions Jump Table
	private InstructionTable orders = new InstructionTable();
	

	//Main Mission
	public Mission(File inputfile){
		//If file does not exist mission is over
		try{
			Scanner input = new Scanner(inputfile);
			readPlateau(input);
			readRovers(input);
			System.out.println("==========");
			return;
		}
		catch(FileNotFoundException fnfe){ 
            System.out.println(fnfe.getMessage());
        }
		return;
	}

	//Read in Plateau Co-ordinates from a file
	protected void readPlateau(Scanner input){
        if(input.hasNext()){
        	// Read and assign Plateau X Co-ordinates
        	mars.setXCoordinate(Integer.parseInt(input.next()));
        }
        else{
        	return;
		}
		if(input.hasNext()){
			//Read and assign Plateau Y Co-ordinates
        	mars.setYCoordinate(Integer.parseInt(input.next()));
        }
        return; 
	}
	
	//Read in Rover Co-ordinates, heading and instructions from a file
	protected void readRovers(Scanner input){
		if(input.hasNext()){
			//Read and assign rover X Co-ordinates
			curiosity.setXCoordinate(Integer.parseInt(input.next()));
			if(input.hasNext()){
				//Read and assign rover Y Co-ordinates
				curiosity.setYCoordinate(Integer.parseInt(input.next()));
			}
			else{
				return;
			}
			if(input.hasNext()){
				//Read and assign rover heading
				curiosity.setHeading(input.next().toCharArray()[0]);
			}
			else{
				return;
			}
			if(input.hasNext()){
				//Read and execute rover instructions
				executeInstructions(input.next(), 0);
			}
			else{
				return;
			}
			//Print rover Co-ordinates and heading
			curiosity.printOutput();
			readRovers(input);
		}
		return;
	}

	//Recursively executes Instructions
	protected void executeInstructions(String instructions, int count){
		if(count >= instructions.length())
			return;
		//Translate instruction usng the Jump Table and execute it
		//cast int type to get ASCII value of character (0-255) in string instructions
		orders.getInstruction((int)instructions.toCharArray()[count]).doInsrtuction(curiosity, mars);
		executeInstructions(instructions, count + 1);
	}
}
	
//Plateau class used to create planets
class Plateau{
	private int xcoordinate;
	private int ycoordinate;

	//Method to set plateau max X co-ordinate
	protected void setXCoordinate(int coord){
		xcoordinate = coord;
		return;
	}

	//Method to set plateau max Y co-ordinate
	protected void setYCoordinate(int coord){
		ycoordinate = coord;
		return;
	}

	//Method to get plateau  X co-ordinate
	protected int getXCoordinate(){
		return xcoordinate;
	}

	//Method to get plateau Y co-ordinate
	protected int getYCoordinate(){
		return ycoordinate;
	}
}

//Rover class used to create curiosity rovers
class Rover{
	private int xcoordinate;
	private int ycoordinate;
	private Character heading;

	//Method to set rover X co-ordinate
	protected void setXCoordinate(int coord){
		xcoordinate = coord;
		return;
	}

	//Method to set rover Y co-ordinate
	protected void setYCoordinate(int coord){
		ycoordinate = coord;
		return;
	}

	//Method to set rover heading
	protected void setHeading(Character h){
		heading = h;
		return;
	}

	//Method to get rover X co-ordinates
	protected int getXCoordinate(){
		return xcoordinate;
	}

	//Method to get rover Y co-ordinates
	protected int getYCoordinate(){
		return ycoordinate;
	}

	//Method to get rover heading
	protected Character getHeading(){
		return heading;
	}

	//Print formatted output
	protected void printOutput(){
		System.out.println(xcoordinate + " " + ycoordinate + " " + heading);
		return;
	}
}

//Table for instructions the rover can execute
class InstructionTable{
	public final static Character NORTH = 'N';
	public final static Character SOUTH = 'S';
	public final static Character WEST = 'W';
	public final static Character EAST = 'E';
	public final static Character MOVE = 'M';
	public final static Character LEFT = 'L';
	public final static Character RIGHT = 'R';
	private intructionsInterface []jumpInstruction = new intructionsInterface[256];
	private forwardInterface []jumpMoveForward = new forwardInterface[256];
	private leftInterface []jumpLeftTurn = new leftInterface[256];
	private rightInterface []jumpRightTurn = new rightInterface[256];

	public InstructionTable(){
		initializeTables();
		return;
	}

	//Initialize all Jump Tables 
	protected void initializeTables(){
		//set default values of Jump Tables
		fillInstructions(255);
		fillMoveForward(255);
		fillLeftTurn(255);
		fillRighTurn(255);
		//cast int type to get ASCII value of character (0-255). Store appropriate method at
		//that index in the appropriate array
		jumpInstruction[(int)MOVE] = new moveFoward();
		jumpInstruction[(int)LEFT] = new turnLeft();
		jumpInstruction[(int)RIGHT] = new turnRight();
		jumpInstruction[(int)Character.toLowerCase(MOVE)] = new moveFoward();
		jumpInstruction[(int)Character.toLowerCase(LEFT)] = new turnLeft();
		jumpInstruction[(int)Character.toLowerCase(RIGHT)] = new turnRight();
		jumpMoveForward[(int)NORTH] = new forwardNorth();
		jumpMoveForward[(int)SOUTH] = new forwardSouthth();
		jumpMoveForward[(int)WEST] = new forwardWest();
		jumpMoveForward[(int)EAST] = new forwardEast();
		jumpMoveForward[(int)Character.toLowerCase(NORTH)] = new forwardNorth();
		jumpMoveForward[(int)Character.toLowerCase(SOUTH)] = new forwardSouthth();
		jumpMoveForward[(int)Character.toLowerCase(WEST)] = new forwardWest();
		jumpMoveForward[(int)Character.toLowerCase(EAST)] = new forwardEast();
		jumpLeftTurn[(int)NORTH] = new leftNorth();
		jumpLeftTurn[(int)SOUTH] = new leftSouth();
		jumpLeftTurn[(int)WEST] = new leftWest();
		jumpLeftTurn[(int)EAST] = new leftEast();
		jumpLeftTurn[(int)Character.toLowerCase(NORTH)] = new leftNorth();
		jumpLeftTurn[(int)Character.toLowerCase(SOUTH)] = new leftSouth();
		jumpLeftTurn[(int)Character.toLowerCase(WEST)] = new leftWest();
		jumpLeftTurn[(int)Character.toLowerCase(EAST)] = new leftEast();
		jumpRightTurn[(int)NORTH] = new rightNorth();
		jumpRightTurn[(int)SOUTH] = new rightSouth();
		jumpRightTurn[(int)WEST] = new rightWest();
		jumpRightTurn[(int)EAST] = new rightEast();
		jumpRightTurn[(int)Character.toLowerCase(NORTH)] = new rightNorth();
		jumpRightTurn[(int)Character.toLowerCase(SOUTH)] = new rightSouth();
		jumpRightTurn[(int)Character.toLowerCase(WEST)] = new rightWest();
		jumpRightTurn[(int)Character.toLowerCase(EAST)] = new rightEast();
		return;
	}

	//Recursive function to fill Instructions Jump Table
	protected void fillInstructions(int count){
		if(count < 0) 
			return;
		jumpInstruction[count] = new badInput();
		fillInstructions(count - 1);
	}

	//Recursive function to fill Move Jump Table
	protected void fillMoveForward(int count){
		if(count < 0) 
			return;
		jumpMoveForward[count] = new dontMoveForward();
		fillMoveForward(count - 1);
	}

	//Recursive function to fill Turn Left Jump Table
	protected void fillLeftTurn(int count){
		if(count < 0) 
			return;
		jumpLeftTurn[count] = new dontTurnLeft();
		fillLeftTurn(count - 1);
	}

	//Recursive function to fill Turn Right Jump Table
	protected void fillRighTurn(int count){
		if(count < 0) 
			return;
		jumpRightTurn[count] = new dontTurnRight();
		fillRighTurn(count - 1);
	}

	//Return the appropriate instruction
	protected intructionsInterface getInstruction(int instruction){
		return jumpInstruction[instruction];
	}

	//Interface for intstructions. Excutes appropriate instruction method
	protected interface intructionsInterface{	
		public void doInsrtuction(Rover curiosity, Plateau mars);
	}

	//If input is not a 'M', 'm', 'L', 'l', 'R', 'r' do nothing
	protected class badInput implements intructionsInterface{
		public void doInsrtuction(Rover curiosity, Plateau mars){
			return;
		}
	}

	//If input is a 'M' or 'm' call appropriate move instruction method
	protected class moveFoward implements intructionsInterface{
		public void doInsrtuction(Rover curiosity, Plateau mars){
			jumpMoveForward[(int)curiosity.getHeading()].forward(curiosity, mars);
			return;
		}
	}

	//If input is a 'L' or 'l' call appropriate instruction method
	protected class turnLeft implements intructionsInterface{
		public void doInsrtuction(Rover curiosity, Plateau mars){
			jumpLeftTurn[(int)curiosity.getHeading()].turn(curiosity);
			return;
		}
	}

	//If input is a 'R' or 'r' call appropriate instruction method
	protected class turnRight implements intructionsInterface{
		public void doInsrtuction(Rover curiosity, Plateau mars){
			jumpRightTurn[(int)curiosity.getHeading()].turn(curiosity);
			return;
		}
	}

	//Interface for moving forward. Excutes appropriate forward method
	protected interface forwardInterface{	
		public void forward(Rover curiosity, Plateau mars);
	}

	//If heading is not 'N', 'n', 'S', 's', 'W', 'w', 'E', 'e' do nothing
	protected class dontMoveForward implements forwardInterface{
		public void forward(Rover curiosity, Plateau mars){
			return;
		}
	}

	//If heading is 'N' or 'n' and the rover is not at top edge, move forward one
	protected class forwardNorth implements forwardInterface{
		public void forward(Rover curiosity, Plateau mars){
			if(curiosity.getYCoordinate() < mars.getYCoordinate())
				curiosity.setYCoordinate(curiosity.getYCoordinate()+1);
			return;
		}
	}

	//If heading is 'S' or 's' and the rover is not at bottom edge, move forward one
	protected class forwardSouthth implements forwardInterface{
		public void forward(Rover curiosity, Plateau mars){
			if(curiosity.getYCoordinate() > 0)
				curiosity.setYCoordinate(curiosity.getYCoordinate()-1);
			return;
		}
	}

	///If heading is 'W' or 'w' and the rover is not at left edge, move forward one
	protected class forwardWest implements forwardInterface{
		public void forward(Rover curiosity, Plateau mars){
			if(curiosity.getXCoordinate() > 0)
				curiosity.setXCoordinate(curiosity.getXCoordinate()-1);
			return;
		}
	}

	///If heading is 'E' or 'e' and the rover is not at right edge, move forward one
	protected class forwardEast implements forwardInterface{
		public void forward(Rover curiosity, Plateau mars){
			if(curiosity.getXCoordinate() < mars.getXCoordinate())
				curiosity.setXCoordinate(curiosity.getXCoordinate()+1);
			return;
		}
	}

	//Interface for turning left. Excutes appropriate turn left method
	protected interface leftInterface{	
		public void turn(Rover curiosity);
	}

	//If heading is not 'N', 'n', 'S', 's', 'W', 'w', 'E', 'e' do nothing
	protected class dontTurnLeft implements leftInterface{
		public void turn(Rover curiosity){
			return;
		}
	}

	//If heading is 'N' or 'n', turn West(W)
	protected class leftNorth implements leftInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(WEST);
			return;
		}
	}

	//If heading is 'S' or 's', turn East(E)
	protected class leftSouth implements leftInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(EAST);
			return;
		}
	}

	//If heading is 'W' or 'w', turn South(S)
	protected class leftWest implements leftInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(SOUTH);
			return;
		}
	}

	//If heading is 'E' or 'e', turn North(N)
	protected class leftEast implements leftInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(NORTH);
			return;
		}
	}

	//Interface for turning right. Excutes appropriate turn right method
	protected interface rightInterface{	
		public void turn(Rover curiosity);
	}

	//If heading is not 'N', 'n', 'S', 's', 'W', 'w', 'E', 'e' do nothing
	protected class dontTurnRight implements rightInterface{
		public void turn(Rover curiosity){
			return;
		}
	}

	//If heading is 'N' or 'n', turn East(E)
	protected class rightNorth implements rightInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(EAST);
			return;
		}
	}

	//If heading is 'S' or 's', turn West(W)
	protected class rightSouth implements rightInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(WEST);
			return;
		}
	}

	//If heading is 'W' or 'w', turn North(N)
	protected class rightWest implements rightInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(NORTH);
			return;
		}
	}

	//If heading is 'E' or 'e', turn South(S)
	protected class rightEast implements rightInterface{
		public void turn(Rover curiosity){
			curiosity.setHeading(SOUTH);
			return;
		}
	}
}

