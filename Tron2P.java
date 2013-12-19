import java.awt.*;
import java.util.Arrays;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Tron2P extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
		
    public Tron2P() {
		super("Move the Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setBackground(Color.black);

		myTimer = new Timer(25, this);	 // trigger every 100 ms
		game = new GamePanel(this);
		add(game);
		setResizable(false);
		setVisible(true);
    }
	
	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		game.changeDir();
		game.move();
		game.repaint();
	}

    public static void main(String[] arguments) {
		Tron2P frame = new Tron2P();		
    }
}

class GamePanel extends JPanel implements KeyListener{
	//private int[][]grid = new int[19200][3];
	private int[][]grid = new int[160][108];
	private int gridNum = 0;
	private int boxx1,boxy1,boxx2,boxy2;
	private int xDir1 = 1;
	private int yDir1 = 0;
	private int xDir2 = -1;
	private int yDir2 = 0;
	private boolean newGame=false;
	private boolean []keys;
	private Image back;
	private Tron2P mainFrame;
	
	public GamePanel(Tron2P m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("OuterSpace.jpg").getImage();
		mainFrame = m;
	    boxx1 = 170;
        boxy1 = 170;
        
        boxx2 = 630;
        boxy2 = 170;
        
        for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		grid[i][j]=0;
        	}
        } 
        
        grid[170/5][(170-60)/5] = 1;
        grid[630/5][(170-60)/5]=2;
		setSize(800,600);
        addKeyListener(this);
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
    
    public void changeDir(){
    	if(keys[KeyEvent.VK_D] ){
    		if(xDir1!=-1){
    			xDir1 = 1;
				yDir1 = 0;
    		}
			
		}
		
		if(keys[KeyEvent.VK_A] ){
			if(xDir1!=1){
    			xDir1 = -1;
				yDir1 = 0;
    		}
			yDir1 = 0;
		}
		
		if(keys[KeyEvent.VK_W] ){
			if(yDir1!=1){
    			yDir1 = -1;
				xDir1 = 0;
    		}
			
		}
		
		if(keys[KeyEvent.VK_S] ){
			if(yDir1!=-1){
    			yDir1 = 1;
				xDir1 = 0;
    		}
		}
		
		if(keys[KeyEvent.VK_RIGHT] ){
    		if(xDir2!=-1){
    			xDir2 = 1;
				yDir2 = 0;
    		}
			
		}
		
		if(keys[KeyEvent.VK_LEFT] ){
			if(xDir2!=1){
    			xDir2 = -1;
				yDir2 = 0;
    		}
			yDir2 = 0;
		}
		
		if(keys[KeyEvent.VK_UP] ){
			if(yDir2!=1){
    			yDir2 = -1;
				xDir2 = 0;
    		}
			
		}
		
		if(keys[KeyEvent.VK_DOWN] ){
			if(yDir2!=-1){
    			yDir2 = 1;
				xDir2 = 0;
    		}
		}
    }
    
    /*public boolean checkMove(String move){
    	if(move.equals("Right")){
    		if 
    	}
    }
    
    public boolean inGrid(int[]nextPos){
    	for(int[] pos:grid){
    		if(Arrays.equals(pos,nextPos)){
    			g.setColor(tiel);
    		}
    		
    		g.fillRect(pos[0],pos[1],5,5);
    	}
    } */
	
	public void move(){
	
		if(boxx1+5*xDir1>798){ 	//edge
			boxx1=0;
		}
		
		else if(boxx1+5*xDir1<2){
			boxx1=795;
		}
		
		else{
			boxx1 += 5*xDir1;
		}
		
		if(boxy1+5*yDir1>570){
			boxy1=60;
		}
		
		else if(boxy1+5*yDir1<58){
			boxy1=570;
		}
		
		else{
			boxy1 += 5*yDir1;
			
			
		}
		
		if(boxx2+5*xDir2>798){ 	//p2
			boxx2=0;
		}
		
		else if(boxx2+5*xDir2<2){
			boxx2=795;
		}
		
		else{
			boxx2 += 5*xDir2;
		}
		
		if(boxy2+5*yDir2>570){
			boxy2=60;
		}
		
		else if(boxy2+5*yDir2<58){
			boxy2=570;
		}
		
		else{
			boxy2 += 5*yDir2;
			
			
		}
		
		if(checkCollision()!=0){
			System.out.println("Derp");
			newGame=true;
			clearGrid();
		}
		
		
		grid[boxx1/5][(boxy1-60)/5] = 1;
		grid[boxx2/5][(boxy2-60)/5] = 2; 
	
		//int[] pos = {boxx1,boxy1,0};
		//grid[gridNum]=pos;
		//gridNum+=1;
		System.out.printf("%s,%s\n",Integer.toString(boxx1),Integer.toString(boxy1));
	}
	
	public int checkCollision(){ 	//0:none , 1: p1 collide , 2: p2 collide
		for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		if(boxx1/5==i && (boxy1-60)/5==j){
        			if(grid[i][j]!=0){
	        			return 1;
	        		}
        		}
	        		
        	}
        }
        
        for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		if(boxx2/5==i && (boxy2-60)/5==j){
        			if(grid[i][j]!=0){
	        			return 2;
	        		}
        		}
	        		
        	}
        }
        
        return 0;
	}
	
	public void clearGrid(){
		for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
	        	grid[i][j]=0;	
        	}
        }
        boxx1=170;
        boxy1=170;
        xDir1=1;
    	yDir1=0;
    	
    	boxx2=630;
        boxy2=170;
        xDir2=-1;
    	yDir2=0;
	}
	
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    

    public void paintComponent(Graphics g){ 		//160 x 120 = 19200 elements in grid
    	Color tiel = new Color(32,178,170);
    	Color orange = new Color(255,140,0);
    	System.out.println(Boolean.toString(newGame));
    	if(newGame){
    		//System.out.println("Derp");
    		newGame=false;
    		setBackground(Color.black);
    		super.paintComponent(g); 		//does something that works
    		
    		
    		
    		//setOpaque(true);
    		//setBackground(Color.black);
    		
    	}
    	System.out.printf("%s,%s\n",Integer.toString(xDir1),Integer.toString(yDir1));
    	for(int i=0;i<800;i+=15){					//grid will be arraylist of [x,y,p1ayer]
    		g.drawLine(i,60,i,600);
    	}
    	
    	for(int i=0;i<540;i+=15){
    		g.drawLine(0,i+60,800,i+60);
    	}
    	
    	/*for(int[] pos:grid){
    		if(pos[2]==0){
    			g.setColor(tiel);
    		}
    		
    		g.fillRect(pos[0],pos[1],5,5);
    	}*/
    	
    	for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		if(grid[i][j]==1){
        			g.setColor(tiel);
        			g.fillRect(i*5,5*j+60,5,5);
        		}
        		
        		if(grid[i][j]==2){
        			g.setColor(orange);
        			g.fillRect(i*5,5*j+60,5,5);
        		}
        	}
        } 
    	
    	
    	g.setColor(tiel); 
    	//g.drawLine(0,0,400,400);
    	g.fillRect(boxx1,boxy1,5,5);
    	g.setColor(orange);
    	g.fillRect(boxx2,boxy2,5,5);
    	//g.setColor(Color.gray); 	
    	g.drawImage(back,0,0,this);
    	//g.setColor(Color.blue);  
    }
    
 
}

