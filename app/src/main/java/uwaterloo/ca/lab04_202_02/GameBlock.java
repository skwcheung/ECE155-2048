package uwaterloo.ca.lab04_202_02;


import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static uwaterloo.ca.lab04_202_02.GameLoopTask.gameDirection.NO_MOVEMENT;


public class GameBlock extends android.support.v7.widget.AppCompatImageView {

    private final float IMAGE_SCALE = .70f;
    //stores the blocks location
    private int myCoordX;
    private int myCoordY;
    private int numCoordX;
    private int numCoordY;
    private GameLoopTask.gameDirection myDir= NO_MOVEMENT;
    private int blockNumber;
    private TextView numberText;

    //velocity and acceleration for animations
    private int velocity=0;
    private final int ACCELERATION=7;

    //holds the blocks direction and sets it to not moving initially
    //private GameLoopTask.gameDirection myDir= NO_MOVEMENT;


    //The four boundary Coordinates
    private final int LX = -50; //Left X boundary
    private final int RX = 750; //Right X boundary
    private final int UY = -50; //Up Y boundary
    private final int DY = 750; //Down Y boundary

    private final int numOffset = 135; //Offset to try and centre the number


    public GameBlock(RelativeLayout myRelativeLayout, Context myContext, int coordX, int coordY) {

        super(myContext);//Used to create the gameblock

        //Store coordX and coordY into the private fields
        myCoordX = coordX;
        myCoordY = coordY;
        numCoordX = coordX;
        numCoordY = coordY;
        Log.d("", "GameBlock: coordX and coord Y are " + coordX + coordY);

        int gen = (int) (2*Math.random()); //Generates  0 or 1 to determine if block will be 2 or 4
        Log.d("", "gen is " + gen);

        if(gen == 0){
            blockNumber = 2;
        }
        else if(gen == 1){
            blockNumber = 4;
        }

        TextView newBlockNumberText= new TextView(myContext); //Trying to make a textview to hold each blocks number
        this.numberText = newBlockNumberText;
        this.numberText.setText(String.valueOf(blockNumber)); //setting the blocks textview to hold the blocks number
        myRelativeLayout.addView(this.numberText,this.myCoordX + numOffset,this.myCoordY + numOffset); //trying to add the textview to relativelayout
        Log.d("", "GameBlock: Adding the textview to location" + this.myCoordX + " " + this.myCoordY);
        //this.numberText.bringToFront(); //need to bring textview to front in front of


        //Call the ImageView Constructor
        ImageView myBlock = new ImageView(myContext);

        //Set up the image when the GameBlock constructor is called
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);

        //Set up which point the block will start at
        this.setX(myCoordX);
        this.setY(myCoordY);

        myRelativeLayout.addView(this);
        this.numberText.bringToFront(); //need to bring textview to front in front of


    }

    //Method that takes in a game direction input, and save the game direction
    //to the corresponding local private field.


    //Add stuff soon
    public void setDestination(){}

    public int getLocationX(){
        return (myCoordX);
    } //for getter method

    public int getLocationY(){
        return (myCoordY);
    }



    //a function to be used to check if the velocity is zero to decide if block will move
    public int getVelocity(){

        return velocity;
    }

    public void setBlockDirection(GameLoopTask.gameDirection newDirection){
        myDir=newDirection;
    }


    //function to move the block's location
    public void move() {

            //Uses a switch statement to figure out what direction the block will move in
            switch (myDir) {
                case UP:
                    //UY DY RX LX will be changed in the future!!!!!!!!!!




                    //before the block hits the boundary animates the block accelerating
                    if (myCoordY > UY) {
                        myCoordY += -1 * velocity;
                        this.setY(myCoordY);
                       // this.numberText.setY(myCoordY - numOffset);

                        velocity += ACCELERATION;
                    }

                    //Once the block has hit the edge has it stop moving places it percisely at the edge and resets all changed values
                    else {
                        this.setY(UY);
                       // this.numberText.setY(numOffset);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                case DOWN:
                    if (myCoordY < DY) {
                        myCoordY += 1 * velocity;
                        this.setY(myCoordY);
                       // this.numberText.setY(myCoordY + numOffset);
                        velocity += ACCELERATION;
                    }
                    else {
                        this.setY(DY);
                       // this.numberText.setY(DY + numOffset);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                case RIGHT:
                    if (myCoordX < RX) {
                        myCoordX += 1 * velocity;
                        this.setX(myCoordX);
                       // this.numberText.setX(myCoordX + numOffset);
                        velocity += ACCELERATION;
                    }
                    else {
                        this.setX(RX);
                        //this.numberText.setX(RX + numOffset);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                case LEFT:
                    if (myCoordX > LX) {
                        myCoordX += -1 * velocity;
                        this.setX(myCoordX);
                        //this.numberText.setX(myCoordX + numOffset);
                        velocity += ACCELERATION;
                    }
                    else {
                        this.setX(LX);
                        //this.numberText.setX(LX + numOffset);
                        velocity = 0;
                        myDir= NO_MOVEMENT;
                    }
                    break;

                //If not moving do nothing
                default:
                    break;
            }
            this.numberText.setX(getLocationX() + numOffset); //At the end of every move call, the textview will update its location according to the block plus an offset
            this.numberText.setY(getLocationY() + numOffset); //same as above
    }
}