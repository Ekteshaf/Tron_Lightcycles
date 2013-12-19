import java.awt.*;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class TronMenu extends JFrame implements MouseListener,MouseMotionListener{
	GamePanel game;
	public int curGame=0; 	//0: None selected, 1: 1 player, 2: 2 Player
	private Image menuPic;
	private Image P1Icon;
	private Image P2Icon;
	private Image inIcon;
	private Image contIcon;
	private Image backImage;
	private Graphics backg;
	public int mx,my;
	private boolean click = false;
	private boolean controlDisplay = false;
	

	public TronMenu(){
		super("Tron Lightcycles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		menuPic = new ImageIcon("menuPic.png").getImage();
		P1Icon = new ImageIcon("P1Icon.png").getImage();
        P2Icon = new ImageIcon("P2Icon.png").getImage();
        inIcon = new ImageIcon("inIcon.png").getImage();
        contIcon = new ImageIcon("contPic.png").getImage();
		setResizable(false);
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paint(Graphics g){
		if (backImage==null){
    		backImage=createImage(800,600);
    		backg=backImage.getGraphics();
    	}
    	
    	
    	if(mx>650 && mx<780){
    		if(click){
    			if(my>290 && my<307){
    				curGame = 1;
	    		}
	    			
	    		if(my>315 && my<332){
	    			curGame = 2;
	    		}
    			
    		}
    		
    			
    		if(my>340 && my<357){
    			controlDisplay=true;
    		}
    	}
    		
    	
    	
    	backg.drawImage(menuPic,0,0,this);
    	backg.drawImage(P1Icon,650,290,this);
    	backg.drawImage(P2Icon,650,315,this);
    	backg.drawImage(inIcon,650,340,this); 
    	if(controlDisplay){
    		backg.drawImage(contIcon,388,425,this);
    		controlDisplay=false;
    	}
    	if (click){
    		
    		click=false;
    	}
 
    	g.drawImage(backImage,0,0,this);
		
	}
	
	public static void delay(long len){
    	try{
    		Thread.sleep(len);
    	}
    	catch(InterruptedException ex){
    		System.out.println(ex);
    	}
    }
	
	
	public void mousePressed(MouseEvent e){}
	public void mouseMoved(MouseEvent e){
		this.mx=e.getX();
		this.my=e.getY();
	}//updates the x and y coordinate of the mouse when it moves
	public void mouseDragged(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){//when the mouse is clicked it sets click to be true
		click=true;
	}
	public void mouseReleased(MouseEvent e){}

	
	public static void main(String[] args) {
		TronMenu menu = new TronMenu();
		while(true){
			System.out.printf("%d,%d\n",menu.mx,menu.my);
			//System.out.printf("%d,%d",menu.mx,menu.my);
			while(menu.curGame==0){
				menu.repaint();
				menu.delay(10);
			}
			
			if(menu.curGame==1){
				Tron1P frame = new Tron1P();
				menu.setVisible(false);
				break;
			
			}
			
			
			if(menu.curGame==2){
				Tron2P frame = new Tron2P();
				menu.setVisible(false);
				break;
			}
			
			
			//menu.setVisible(false);
			//frame.repaint();
			
		}
		
			
    }
}

