/*
 CET 350
 Group #11
 Bounce.java
 Created: 3/13/2017
 Edited: 3/30/17
 Description:
 Program creates a frame with a bouncing ball 
 object.  User can toggle pause or quit and 
 control the speed and size of the ball.  User 
 can also draw rectangles on the drawing board,
 which the ball will bounce off of.  
*/

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;
import java.nio.file.*;

public class Bounce extends Frame implements ActionListener, WindowListener, AdjustmentListener, ComponentListener, MouseListener, 
MouseMotionListener, Runnable 
{
	
   //declare program globabl variables
	private static final long serialVersionUID = 1L;
	private Insets I;
	private Thread theThread;
	private board c = new board();
   boolean pause = false;
   boolean shape = false; 
   boolean tails = false;  
   boolean more = true;
   int H = 500;
	int W = 1000;
	int sh, sw;
   int mx, my; 
   int rx, ry; 
	int xUnits;
	int yUnits;
   int delay = 10;
   
   //declare components for frame
   BorderLayout bdr = new BorderLayout();
   private Panel screen = new Panel(); 
   private Panel control = new Panel(); 

   //declare component scrollbars, buttons, and labels
   Scrollbar speedSB, sizeSB;
	Label speedL = new Label(" SPEED ");
	Label sizeL = new Label(" SIZE ");
	Button startB = new Button(" PAUSE ");
	Button shapeB = new Button(" OVAL ");
	Button tailsB = new Button(" TAILS OFF ");
	Button clearB = new Button(" CLEAR ");
	Button quitB = new Button(" QUIT ");
   
	
	Bounce(){ //constructor for Bounce

     this.setMinimumSize(new Dimension (W,H));
	  setLayout(bdr); //set layout
	  setVisible(true); //set layout visible
	  MakeSheet(); //create sheet 
	  try{ //run initcomponents to assemble screen and canvas objects
       initComponents(); 
     }
	  catch(Exception e) { //catch any errors or exceptions
       e.printStackTrace();
	  }
	  SizeScreen(); //run screen size
	}
	
	public void initComponents(){ //assembles components for bounce object
		// Main Frame Parameters
      
      this.setLayout(bdr); 
      this.setSize(W,H); 
      this.setTitle(" Bounce: The Sequel "); 
      this.setResizable(true); 
      this.addWindowListener(this); 
      this.addComponentListener(this); 
      this.setVisible(true); 
      add(screen, BorderLayout.CENTER); 
      add(control, BorderLayout.SOUTH);  
      
      screen.add(c); 
      c.setSize(xUnits/2); 
      	
		
		// Speed scrollbar
			 speedSB = new Scrollbar(Scrollbar.HORIZONTAL);
			 speedSB.setVisible(true);
			 speedSB.setBackground(Color.blue);
			 speedSB.setMinimum(5);
			 speedSB.setMaximum(100);
			 speedSB.setUnitIncrement(20);
			 speedSB.setValue(1);
			 speedSB.setBlockIncrement(1);
			 speedSB.setBackground(Color.ORANGE);
			 speedSB.addAdjustmentListener(this);
          
				
				//Size scrollbar
			  sizeSB = new Scrollbar(Scrollbar.HORIZONTAL);
			  sizeSB.setVisible(true);
			  sizeSB.setBackground(Color.blue);
			  sizeSB.setMinimum(10);
			  sizeSB.setMaximum(100);
			  sizeSB.setUnitIncrement(10);
			  sizeSB.setValue(10);
			  sizeSB.setBackground(Color.ORANGE);
			  sizeSB.addAdjustmentListener(this);
			
				// Label for sizeSB size scrollbar
				
				// Start/Stop Button
			  startB.setVisible(true);
			  startB.setBackground(Color.blue);
			  startB.addActionListener(this);
								
				// Quit Button
			   quitB.setVisible(true);
			   quitB.setBackground(Color.blue);
			   quitB.addActionListener(this);
            
            GridBagLayout gb = new GridBagLayout();
            GridBagConstraints gc = new GridBagConstraints();
            
            gc.weightx = 1.0; 
            gc.ipadx = 200; 
            gc.insets = new Insets(1, 1, 1, 1); 
            gc.anchor = GridBagConstraints.CENTER;
            
            gc.gridwidth = 3; 
            gc.gridheight = 0; 
            gc.gridx = 1; 
            gc.gridy = 0; 
            gb.setConstraints(speedL, gc);
             
            gc.gridx = 1; 
            gc.gridy = 1; 
            gb.setConstraints(speedSB, gc);
            
            gc.gridwidth = GridBagConstraints.RELATIVE; 
            gb.setConstraints(startB, gc);
            
            gc.gridwidth = GridBagConstraints.RELATIVE; 
            gb.setConstraints(quitB, gc);
            
            gc.gridwidth = GridBagConstraints.RELATIVE; 
            gb.setConstraints(sizeSB, gc);
            
            gc.gridwidth = GridBagConstraints.RELATIVE; 
            gb.setConstraints(sizeL, gc);
            
            control.add(speedL);
            control.add(speedSB);  
            control.add(startB); 
            control.add(quitB); 
            control.add(sizeSB); 
            control.add(sizeL); 
            
            c.setSize(xUnits/2);           

			   SizeScreen();
				Start();

				validate();

	}

	private void Start() {  //creates thread for program 
	   if(theThread == null){
         theThread = new Thread(this);
         theThread.start();
         c.repaint();
      }   
	}

	private void SizeScreen() { //function to calculate screen size
	        
				//setSize(sw, sh);	
			   this.setSize(W, H);
				this.setLocation((xUnits), (yUnits));
				      
			       c.setSize((xUnits*32), (yUnits*17));
				   c.setLocation(xUnits, (yUnits*3));
				
		     speedSB.setSize((xUnits*8), (yUnits*2));				//not constants make sheet got new scrren size and calculated button sizes...
		     speedSB.setLocation((xUnits), (yUnits*21));							//using new spacing size resize each component and position
			  speedL.setLocation((xUnits*2), (yUnits*20));
			  speedL.setSize((xUnits*6), yUnits);
				
			  sizeSB.setSize((xUnits*8), (yUnits*2));
			  sizeSB.setLocation(xUnits*25, yUnits*21);
			   sizeL.setLocation((xUnits*27), (yUnits*20));
			   sizeL.setSize((xUnits*6), yUnits);
				
			  startB.setSize((xUnits*2), (yUnits*2));
			  startB.setLocation((xUnits*10), (yUnits*21));
				
			  shapeB.setLocation((xUnits*13),(yUnits*21));
			  shapeB.setSize((xUnits*2), (yUnits*2));
				
			  tailsB.setSize((xUnits*2), (yUnits*2));
			  tailsB.setLocation((xUnits*16), (yUnits*21));
				
			  clearB.setSize((xUnits*2), (yUnits*2));
		      clearB.setLocation((xUnits*19), (yUnits*21));
				
			   quitB.setSize((xUnits*2), (yUnits*2));
			   quitB.setLocation((xUnits*22), (yUnits*21));
            
	}
	
	public void MakeSheet(){ //creates sheet, calculates sheet size
		
		xUnits = W/34;
		yUnits = H/24;
		
		I = getInsets();
		
		sh = H - I.top - I.bottom - (yUnits*6);
		sw = W - I.left - I.right - (xUnits*2);            
      	
		//calc your inc size 
	}
	
	public static void main(String[] args){ //main function, creates new instance of bounce
		Bounce b0 = new Bounce();
	}

	@Override
	public void run() //activate and run thread
   {
		
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
      while(theThread != null)
      {
         if(!pause) //run thread while program is not paused
         {
            c.setSheet(sw,sh);  // these functions are all in bounce class
            c.boundary();
            c.step();
            c.drawit(tails);
            
            
            try
            {
               Thread.sleep(delay);
            }catch(InterruptedException e){}
         }
         else
         {
           System.out.print(""); //this print line allows for the program to successfully pause/start
           //we don't know why, but if you take it out it will never leave pause
         }
      }
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void componentResized(ComponentEvent ce) { //function to adjust components if window is resized
	
		H = getHeight();
		W = getWidth();
      
		MakeSheet();
		SizeScreen();
		//let the ball object know the change too 
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	// Handles scroll bar events and updates accordingly (finished)
	public void adjustmentValueChanged(AdjustmentEvent ae) { //function for scrollbar adjustments

		Object src0 = ae.getSource(); //create scrollbar source object
		
		if(src0 == speedSB){ //if user moves speed scrollbar, adjust speed
		Scrollbar s0 = (Scrollbar)ae.getSource();
		int temp = ae.getValue();
		speedSB.setValue(temp);
		//c.setSpeed(temp);
      
      delay = (100 - ae.getValue())/8;
		} else{ //if user adjusts size scrollbar, adjust size
			Scrollbar s0 = (Scrollbar)ae.getSource();
			int temp = ae.getValue();
			sizeSB.setValue(temp);
         c.setSize(ae.getValue());
		}
  	}
   
   public void stop() //function to end program 
   {
      theThread = null;  
		dispose();		
		this.setVisible(false);
   }
   
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent we) {
      stop();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void actionPerformed(ActionEvent ae) { //Function for action peformed 
		Object src1 = ae.getSource();
		
		if(src1 == startB){ //Toggles start/pause button
			if (pause == true)
         {
           pause = false; 
           startB.setLabel("Pause");
         }
         else
         {
           pause = true; 
           startB.setLabel("Start");
         } 
         theThread.interrupt();
         System.out.println(pause);
		}
		
		if(src1 == shapeB){ //toggles shape button 
			if (shape == true)
         {
           shapeB.setLabel(" Square ");
           shape = false;
           c.getShape(shape);  
         }
         else 
         {
           shapeB.setLabel(" Oval ");
           shape = true;
           c.getShape(shape); 
         }
		}
		
		if(src1 == tailsB){ //toggles tails button 
			tailsB.setLabel(" Tails "); 
         tails = !tails;
         if(!tails)
         {
           tailsB.setLabel(" No Tails "); 
         }
		}
		
		if(src1 == clearB){ //toggles clear button 
			
         //c.clearit(c.d);
         c.isClear = !c.isClear;
		}
	
		if(src1 == quitB){ //toggles quit button 
			stop();
		}
	}
   
   public void mousePressed(MouseEvent m)
   {
     mx = m.getX();
     my = m.getY(); 
     repaint();
   }
  
   public void mouseReleased(MouseEvent m)
   {
     rx = m.getX(); 
     ry = m.getY(); 
     repaint();
   }
   
   public void mouseExited(MouseEvent m)
   {
   
   }
   
   public void mouseEntered(MouseEvent m)
   {
   
   }
   
   public void mouseClicked(MouseEvent m)
   {
   
   }
   
   public void mouseMoved(MouseEvent m)
   {
   
   }
   
   public void mouseDragged(MouseEvent m)
   {
     mx = m.getX();
     my = m.getY(); 
     repaint();

   }

}//end class Bounce

//------------------this class does all the drawing------------------------------------
class board extends Canvas //canvas class
{
 //declare variables for board class
 int x,y,w,h; 
 int down,right;
 int sw, sh;
 int speed = 5;
 boolean shape, isClear, tails; 
 Graphics d;
 
 
  public void getShape(boolean sh) //function to change shape 
  {
    shape = sh; 
  }
  
  public void setSheet(int cw, int ch){ //function to set canvas sheet
  
  sw = cw;
  sh = ch;
  
  }

  public void setSize(int H) //function to set object size 
  {
    w = H;
    h = H;
  }
  
  public void setSpeed(int S){ //function to set object speed
	  
	  speed = S;
  }
  
  public board() //constructor for board class
  {
    x = 0;
    y = 0;
    down = 1;
    right = 1;
    isClear = false;
  
    //System.out.println("this is our test canvas");
    setBackground(Color.GRAY); 
    //setSize(500,500); 
  }
  
  public void boundary(){ //function to set boundary on canvas
  
         if(y > sh - h){
               down = -1;
         }
         if(y < 0){
               down = 1;
         }
         if(x > sw - w){
               right = -1;
         }
         if(x < 0){
               right = 1;
         }
  }
  
  
  public void drawit(boolean t){ //function to place object
    tails = t;
    repaint();    
  }
  
  public void paint(Graphics g) //function to draw object 
  {
    d = g;
    
    int p = 6;  // changes border 
        
    g.setColor(Color.BLACK);
    g.fillOval(x,y,w,h);
    g.setColor(Color.YELLOW);
    g.fillOval(x+w/p,y+h/p,w- 2*w/p,h-2*h/p);
      
  }
  
  public void step(){ //function to move object across the screen
  
      x = x + right;
      y = y + down;
  }
  
} //end class board