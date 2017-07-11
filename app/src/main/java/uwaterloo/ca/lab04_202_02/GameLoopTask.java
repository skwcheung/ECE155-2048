package uwaterloo.ca.lab04_202_02;

//package uwaterloo.ca.lab03_202_02;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

import static uwaterloo.ca.lab04_202_02.stateMachine.states.WAITING;


/**
 * Created by steve on 2017-06-25.
 */

public class GameLoopTask extends TimerTask {
    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private GameBlock myGameBlock;
    boolean somethingMoved=false;

    //contains linked list of all gameblocks
    public LinkedList<GameBlock> blockList;
    //private Random gen = new Random();

    //Constants
    public static int leftBoundary = -40; //Left X boundary
    public static int rightBoundary = 775; //Right X boundary
    public static int upBoundary = -50; //Up Y boundary
    public static int downBoundary = 775; //Down Y boundary


    //enumerates the possible directions for the gameblock
    public enum gameDirection {LEFT, RIGHT, UP, DOWN, NO_MOVEMENT}

    //creates a variable to store the gameblocks current direction
    public static gameDirection currentDirection = gameDirection.NO_MOVEMENT;
    String Direction;

    //constructor
    GameLoopTask(Activity Activity, RelativeLayout RL, Context Context) {
        myActivity = Activity;
        myRL = RL;
        myContext = Context;
        blockList = new LinkedList();

        //Call the CreateBlock Method
        createBlock(-50, -50);
        createBlock(-50, 204);
        createBlock(204, 204);
        createBlock(-50, 474);
        createBlock(-50, 744);
        /*createBlock(774, 774);
        createBlock(774, -33);*/

    }

    private void createBlock(int startX, int startY) {

        GameBlock newBlock = new GameBlock(myRL, myContext, startX, startY); //Or any (x,y) of your choice

        //adds the game block to the layout
        //myRL.addView(newBlock);
        myGameBlock = newBlock;
        blockList.add(myGameBlock); //adds the new gameBlock to the list

    }


    //method to create a game block
    private void createBlock() {
        int startX = (int) (Math.random()*4); //Generatig values from 0 to 3 to select which grid
        Log.d("", "createBlock: Starting x is  " +startX);

        int startY = (int) (Math.random()*4);
        Log.d("", "createBlock: Starting y is  " +startY);

        //creates the game block and initializes it to the top left corner
        int gridLength = 270;//270 is 1080 divided by 4 since the gameboard is 1080x1080 each cell is 270x270
        Log.d("", "createBlock: Starting x is  " + startX*gridLength);
        Log.d("", "createBlock: Starting y is  " + startY*gridLength);

        int initialX = upBoundary + startX*gridLength; //Offsetting since the image blac is 130x130 we scaled by 0.7 to a 90x90 pic
        int initialY = upBoundary + startY*gridLength;


        GameBlock newBlock = new GameBlock(myRL, myContext, initialX, initialY); //Or a//ny (x,y) of your choice

        //adds the game block to the layout
        //myRL.addView(newBlock);
        myGameBlock = newBlock;
        blockList.add(newBlock); //adds the new gameBlock to the list

    }

    @Override
    public void run() {

        myActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("size","Size is "+ blockList.size());
                        for(int x=0;x<blockList.size();x++){ //iterates through every block to move
                            blockList.get(x).move();
                        }

                    }
                }
        );
    }

    //sets the blocks direction by taking in a direction and assigning it to the game block created
    public void setDirection(gameDirection newDirection) {
        //changes the dierection only if the blocks aren't moving
        //Log.d("call", "set direction");
        currentDirection = newDirection;

        //Log.d("SomethingMoved Value", "It is "+somethingMoved);
        int counter=0;
        for(int x=0;x<blockList.size();x++){ //iterates through ever block
            if (blockList.get(x).getVelocity()==0){
                if (somethingMoved && counter<1 && stateMachine.getCurrentState()==WAITING){
                    createBlock();
                    somethingMoved=false;
                    counter++;
                }
                //Pass the direction into the GameBlock class setBlockDirection()
                //Log.d("Is Velocity", "Velocity "+blockList.get(x).getVelocity());
                blockList.get(x).setBlockDirection(newDirection);
                if (currentDirection != gameDirection.NO_MOVEMENT){
                    somethingMoved=true;
                }
            }

        }


    }

    public boolean isOccupied(LinkedList<GameBlock> gameBlockList,GameBlock newBlock){
        for(int x=0;x<gameBlockList.size();x++){
            int newXCoord = newBlock.getLocationX();
            int newYCoord = newBlock.getLocationY();

            if(gameBlockList.get(x).getLocationX() == newXCoord && gameBlockList.get(x).getLocationX() == newYCoord) {
                return true; //if something has the same x y coords then return true for occupied block
            }
        }
        return false; //If nothing is occupied return false
    }

}
