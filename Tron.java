import java.awt.*;
import java.util.Arrays;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Tron extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
		
    public Tron() {
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
		
		//game.move();
		game.setGrid();
		game.repaint();
	}

    public static void main(String[] arguments) {
    	Lightcycle cycle1 = new Lightcycle(1);
		Tron frame = new Tron();
				
    }
}

class GamePanel extends JPanel implements KeyListener{
	//private int[][]grid = new int[19200][3];
	private int[][]grid = new int[160][108];
	private int gridNum = 0;
	private int boxx,boxy;
	private int xDir = 1;
	private int yDir = 0;
	private boolean newGame=false;
	private boolean []keys;
	private Image back;
	private Tron mainFrame;
	Lightcycle cycle1 = new Lightcycle(1);
	
	public GamePanel(Tron m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("OuterSpace.jpg").getImage();
		mainFrame = m;
	
	    //boxx = 170;
        //boxy = 170;
        
        //Lightcycle cycle1 = new Lightcycle(1);
        
        for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		grid[i][j]=0;
        	}
        } 
        
        grid[cycle1.gridX()][cycle1.gridY()] = cycle1.gridNum();
        //grid[p2.gridX][p2.gridY] = p2.gridNum();
		setSize(800,600);
        addKeyListener(this);
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
    
    public void changeDir(){
    	int[]dir=new int[2];
    	if(keys[KeyEvent.VK_RIGHT] ){
    		if(cycle1.xDir()!=-1){
    			//xDir = 1;
				//yDir = 0;
				dir[0]=1;
				dir[1]=0;
				cycle1.changeVel(dir);
    		}
			
		}
		
		if(keys[KeyEvent.VK_LEFT] ){
			if(cycle1.xDir()!=1){
    			//xDir = -1;
				//yDir = 0;
				dir[0]=-1;
				dir[1]=0;
				cycle1.changeVel(dir);
    		}
			
		}
		
		if(keys[KeyEvent.VK_UP] ){
			if(cycle1.yDir()!=1){
    			//yDir = -1;
				//xDir = 0;
				dir[0]=0;
				dir[1]=-1;
				cycle1.changeVel(dir);
    		}
			
		}
		
		if(keys[KeyEvent.VK_DOWN] ){
			if(cycle1.yDir()!=-1){
    			dir[0] = 0;
				dir[1] = 1;
				cycle1.changeVel(dir);
    		}
		}
		
		cycle1.move();
		
		
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
	
	/*public void move(){
	
		if(boxx+5*xDir>798){ 	//edge
			boxx=0;
		}
		
		else if(boxx+5*xDir<2){
			boxx=795;
		}
		
		else{
			boxx += 5*xDir;
		}
		
		if(boxy+5*yDir>570){
			boxy=60;
		}
		
		else if(boxy+5*yDir<58){
			boxy=570;
		}
		
		else{
			boxy += 5*yDir;
			
			
		}
		
		if(checkCollision()){
			System.out.println("Derp");
			newGame=true;
			clearGrid();
		}
		
		
		grid[boxx/5][(boxy-60)/5] = 1; 
	
		//int[] pos = {boxx,boxy,0};
		//grid[gridNum]=pos;
		//gridNum+=1;
		System.out.printf("%s,%s\n",Integer.toString(boxx),Integer.toString(boxy));
	} */
	
	public void setGrid(){
		if(checkCollision()){
			System.out.println("Derp");
			newGame=true;
			clearGrid();
		}
		System.out.printf("%s..%s\n",Integer.toString(cycle1.xPos()),Integer.toString(cycle1.yPos()));
		grid[cycle1.gridX()][cycle1.gridY()] = cycle1.gridNum();
        //grid[p2.gridX][p2.gridY] = p2.gridNum();
	}
	
	public boolean checkCollision(){
		for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		if(cycle1.gridX()==i && cycle1.gridY()==j){
        			if(grid[i][j]==1){
	        			return true;
	        		}
        		}
	        		
        	}
        }
        
        return false;
	}
	
	public void clearGrid(){
		for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
	        	grid[i][j]=0;	
        	}
        }
        boxx=170;
        boxy=170;
        xDir=1;
    	yDir=0;
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
    	System.out.println(Boolean.toString(newGame));
    	if(newGame){
    		//System.out.println("Derp");
    		newGame=false;
    		setBackground(Color.black);
    		super.paintComponent(g); 		//does something that works
    		
    		
    		
    		//setOpaque(true);
    		//setBackground(Color.black);
    		
    	}
    	System.out.printf("%s,%s\n",Integer.toString(cycle1.xDir()),Integer.toString(cycle1.yDir()));
    	for(int i=0;i<800;i+=15){					//grid will be arraylist of [x,y,cycle1ayer]
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
        	}
        } 
    	
    	
    	g.setColor(tiel); 
    	//g.drawLine(0,0,400,400);
    	g.fillRect(boxx,boxy,5,5);
    	//g.setColor(Color.gray); 	
    	g.drawImage(back,0,0,this);
    	//g.setColor(Color.blue);  
    }
    
 
}

