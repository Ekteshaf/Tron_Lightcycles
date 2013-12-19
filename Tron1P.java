import java.awt.*;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Tron1P extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
    public Tron1P() {
		super("Move the Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setBackground(Color.black);

		myTimer = new Timer(25, this);	 // trigger every 25 ms
		game = new GamePanel(this);
		add(game);
		setResizable(false);
		setVisible(true);
    }
	
	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		if(game.timeTrack()>=0){
			game.changeDir();
			game.move();
			game.repaint();
			
		}
		

		
	}

    /*public static void main(String[] arguments) {
		Tron1P frame = new Tron1P();		
    } */
}

class GamePanel extends JPanel implements KeyListener{
	//private int[][]grid = new int[19200][3];
	private int counter=30;
	private int loopNum = 1;
	private Image backImage;
	private Graphics backg;
	private int[][]grid = new int[160][108];
	private int gridNum = 0;
	private int boxx1,boxy1,boxx2,boxy2;
	private int xDir1 = 1;
	private int yDir1 = 0;
	private int xDir2 = -1;
	private int yDir2 = 0;
	private int p1Score=0;
	private int p2Score=0;
	private int boost1=0;
	private int boost2=0;
	private boolean newGame=false;
	private boolean []keys;
	private Image head1;
	private Image head2;
	private Image p1Wins;
	private Image p2Wins;
	private Image drawGame;
	private Tron1P mainFrame;
	
	public GamePanel(Tron1P m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		
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
		head1 = new ImageIcon("blueHead.png").getImage();
		head2 = new ImageIcon("orangeHead.png").getImage();
        p1Wins = new ImageIcon("p1Win.png").getImage();
        p2Wins = new ImageIcon("compWin.png").getImage();
        drawGame = new ImageIcon("draw.png").getImage();
        addKeyListener(this);
	}
	
	/*public int winner(){
		if(p1Score>p2Score){
			return 1;
		}
		
		if(p2Score>p1Score){
			return 2;
		}
		
		if(p1Score==p2Score){
			return 0;
		}
	} */
	
	public int timeTrack(){
		if(loopNum%40==0){
			counter-=1;
		}
		loopNum++;

		return counter;
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
    
    public int emptDescending(int[]nums,int num){ 	//returns element of certain array with highest int value
    	int[]numsCopy = Arrays.copyOf(nums,4);
    	Arrays.sort(numsCopy);
    	return numsCopy[3-num];
    	//int[] sortedNums = new int[4] ;	
    }
    
    
    
    public int wrapAroundX(int xVal){
    	if(xVal>798){ 	//edge
			return 0;
		}
		
		else if(xVal<2){
			return 795;
		}
		
		else{
			return xVal;
		}
    }
    
    public int wrapAroundY(int yVal){
		if(yVal>570){
			return 60;
		}
		
		else if(yVal<58){
			return 570;
		}
		
		else{
			return yVal;
			
			
		}
    	
    }
    
    public int rollDice(int x){
    	Random r = new Random();
   		int val = 1+r.nextInt(x);
   		return val;
   		
    }
    
    public boolean turn(int val){
    	if(val<=2){
    		return true;
    	}
    	
    	else{
    		return false; 
    	}
    }
    
    public void bestDir(int pos){
    	if(pos==0){
    		xDir2 = 0;
			yDir2 = 1;
    	}
    	
    	if(pos==1){
    		xDir2 = 0;
			yDir2 = -1;
    	}
    	
    	if(pos==2){
    		xDir2 = 1;
			yDir2 = 0;
    	}
    	
    	if(pos==3){
    		xDir2 = -1;
			yDir2 = 0;
    	}
    }
    
    public void AI(){ 	//edges
    	//int[]directionData = new int[4][10];
    	int arrayNum=0;
    	int[]emptSpaces = {0,0,0,0}; 	//each ele in array represents the next x empty spaces in that direction
    	int downVal,upVal,rightVal,leftVal;
    	for(int i=0;i<20;i++){
    		downVal = wrapAroundY(boxy2+i*5);
    		upVal = wrapAroundY(boxy2-i*5);
    		rightVal = wrapAroundX(boxx2+i*5);
    		leftVal = wrapAroundX(boxx2-i*5);
    		System.out.printf("%d\n",downVal);
    		System.out.printf("%d\n",upVal);
    		System.out.printf("%d\n",rightVal);
    		System.out.printf("%d\n",leftVal);
    	
    		if(grid[boxx2/5][(downVal-60)/5] ==0){
    			emptSpaces[0] += 1; 	//placeholder for that direction not being viable
    		}
    		
    		if(grid[boxx2/5][(upVal-60)/5] ==0){
    			emptSpaces[1] += 1; 	//placeholder for that direction not being viable
    		}
    		
    		if(grid[(rightVal)/5][(boxy2-60)/5] ==0){
    			emptSpaces[2] += 1; 	//placeholder for that direction not being viable
    		}
    		
    		if(grid[(leftVal)/5][(boxy2-60)/5] ==0){
    			emptSpaces[3] +=1; 	//placeholder for that direction not being viable
    		}
    	}
    	
    	//int pos = 
    	/*int val = rollDice(50);
    	if(val>5){
    		bestDir(pos);
    	}
    	
    	else{
    		Random r = new Random();
    		int randNum = r.nextInt(4);
    		bestDir(randNum);
    	}*/
    	
    	
    	
    	
    	/*for(int i=0;i<;i++){
    		if(grid[boxx2/5][(boxy2+i-60)/5]==0)
    	}
    	
    	for(int i=0;i<10;i++){
    		directionData[0][arrayNum] = grid[boxx2/5][(boxy2+i-60)/5]; 	//adds grid values N,S,E,W
    		arrayNum++;
    		directionData[1][arrayNum] = grid[boxx2/5][(boxy2-i-60)/5];
    		arrayNum++;
    		directionData[2][arrayNum] = grid[(boxx2+i)/5][(boxy2-60)/5];
    		arrayNum++;
    		directionData[3][arrayNum] = grid[(boxx2-i)/5][(boxy2-60)/5];
    		arrayNum++;
			
    	}
    	
    	char[] directionProb = new char[40];
    	arrayNum=0;
    	for(int i=0;i<4;i++){
    		for(int j=0;j<10;j++){
    			if(i=0){
    				if(directionData[i][j]==0){
    					directionProb[arrayNum] = 'S';
    					arrayNum++;
    				}
    			}
    			
    			if(i=1){
    				if(directionData[i][j]==0){
    					directionProb[arrayNum] = 'N';
    					arrayNum++;
    				}
    			}
    			
    			if(i=2){
    				if(directionData[i][j]==0){
    					directionProb[arrayNum] = 'E';
    					arrayNum++;
    				}
    			}
    			
    			if(i=3){
    				if(directionData[i][j]==0){
    					directionProb[arrayNum] = 'W';
    					arrayNum++;
    				}
    			}	
    		}
    	} 
    	
    	Random r = new Random();
    	int eleNum = r.nextInt(arrayNum);
    	
    	if(directionProb[eleNum]==)  */	
    	//look 10 all direction 
    	//probability of going in certain direction based on empty spaces (no threats)
    	//random choose
    }
    
    public void printArray(int[]array){
    	for(int n:array){
    		System.out.printf("%d,",n);
    	}
    }
    
    public int[] copyArray(int[]array,int len){
    	int[] newArray = new int[len];
    	for(int i=0;i<array.length;i++){
    		newArray[i] = array[i];
    	}
    	return newArray;
    }
    
    public int newDirec(int[]nums,int bestNum){ 		//bestNum=0 is best direc, 1 is second best
    	System.out.println("____");
    	int[] direcNums = copyArray(nums,4);
    	
    	//printArray(direcNums);
    	System.out.println("Original Nums:");
    	printArray(direcNums);
    	Arrays.sort(nums);
    	System.out.println("Sorted Nums:");
    	printArray(nums);
    	System.out.println("Original Nums:");
   		printArray(direcNums);
    	System.out.printf("BestNum: %d\n",bestNum);
    	int direcSpaces = nums[3-bestNum]; 	//the highest number of emptyspaces in a certain direction
    	System.out.printf("Empt Space Num: %d\n",direcSpaces);
    	int[] pos={0,0,0,0};
    	int validPos=0;
    	int newDirecPos=100; 	//returns the index of nums
    	for(int i=0;i<4;i++){
    		if(direcNums[i]==direcSpaces){
    			pos[i]=1;
    			validPos++;
    		}
    	}
    	
    	if(validPos==1){
    		for(int i=0;i<4;i++){
    			if(pos[i]==1){
    				newDirecPos=i;
    			}
    		}
    	}
    	
    	else{
    		int randNum=rollDice(2);
    		System.out.printf("%d\n",randNum);
    		if(randNum==1){
    			for(int i=0;i<4;i++){
	    			if(pos[i]==1){
	    				newDirecPos=i;
	    			}
    			}
    		}
    		
    		else{
    			for(int i=0;i<4;i++){
	    			if(pos[3-i]==1){
	    				newDirecPos=3-i;
	    			}
    			}
    		}
    	} 
    	
    	
    	
    	
    	System.out.printf("%s...%d\n","POS:",newDirecPos);
    	return newDirecPos;
    	
    }
    
    public int[] emptySpacesAround(){
    	int arrayNum=0;
    	int[]emptSpaces = {0,0,0,0}; 	//each ele in array represents the next x empty spaces in that direction
    	int downVal,upVal,rightVal,leftVal;
    	int[] moreSpaces = {1,1,1,1};
    	for(int i=1;i<51;i++){
    		downVal = wrapAroundY(boxy2+i*5);
    		upVal = wrapAroundY(boxy2-i*5);
    		rightVal = wrapAroundX(boxx2+i*5);
    		leftVal = wrapAroundX(boxx2-i*5);
    		/*System.out.printf("%d\n",downVal);
    		System.out.printf("%d\n",upVal);
    		System.out.printf("%d\n",rightVal);
    		System.out.printf("%d\n",leftVal); */
    	
    		if(grid[boxx2/5][(downVal-60)/5] ==0){
    			//System.out.println("SDBKFHSDKFBSF");
    			//System.out.printf("%d\n",moreSpaces[0]);
    			if(moreSpaces[0]==1){
    				//System.out.println("Hmmmm.....");
    				emptSpaces[0] += 1; 
    			}
    		}
    		
    		else{
    			moreSpaces[0]=-1;
    		}
    		
    		if(grid[boxx2/5][(upVal-60)/5] ==0){
    			if(moreSpaces[1]==1){
    				emptSpaces[1] += 1; 	//placeholder for that direction not being viable
    			}
  
    		}
    		
    		else{
    			moreSpaces[1]=-1;
    		}
    		
    		if(grid[(rightVal)/5][(boxy2-60)/5] ==0){
    			if(moreSpaces[2]==1){
    				emptSpaces[2] += 1; 	//placeholder for that direction not being viable
    			}
    			
    		}
    		
    		else{
    			moreSpaces[2]=-1;
    		}
    		
    		if(grid[(leftVal)/5][(boxy2-60)/5] ==0){
    			if(moreSpaces[3]==1){
    				emptSpaces[3] += 1; 	//placeholder for that direction not being viable
    			}
    		}
    		
    		else{
    			moreSpaces[3]=-1;
    		}
    	}
    	
    	return emptSpaces;	
    }
    
   
    
    public int direcToInt(){
    	int num=0;
    	if(xDir2==0 && yDir2==1){
    		num=0;
    	}
    	
    	if(xDir2==0 && yDir2==-1){
    		num=1;
    	}
    	
    	if(xDir2==1 && yDir2==0){
    		num=2;
    	}
    	
    	if(xDir2==-1 && yDir2==0){
    		num=3;
    	}
    	
    	return num;
    }
   
    
    public void AI2(){
    	int curDirec = direcToInt();
    	System.out.printf("%s:%d","Direction:",curDirec);
    	int[]emptSpaces = emptySpacesAround();
    	System.out.println("Empt:");
    	printArray(emptSpaces);
    	System.out.println();
    	int[] sortSpace = {0,0,0,0}; 	// empty spaces ahead in descending order
    	for(int i=0;i<4;i++){
    		sortSpace[i]=emptDescending(emptSpaces,i);
    	}
    	System.out.println("Sort:");
    	printArray(sortSpace);
    	int randNum = rollDice(100);
    	/*if(randNum>10){
    		bestDir(newDirec(emptSpaces,0));
    		System.out.printf("...........%d",newDirec(emptSpaces,0));
    	}
    	
    	else{
    		bestDir(newDirec(emptSpaces,1));
    		System.out.printf("...........%d",newDirec(emptSpaces,1));
    	} */
    	//printArray(emptSpaces);
    	
    	if(emptSpaces[curDirec]<10){
    		System.out.printf("%s....%d\n","CHANGE DIRECTION!!!!!!",curDirec);
    		bestDir(newDirec(emptSpaces,0));
    	} 
    	
    	/*if(randNum>60){
    		bestDir(newDirec(emptSpaces,0));
    	}
    	*/
    	/*if(randNum<5 && sortSpace[1]>10){
    		bestDir(newDirec(emptSpaces,1));
    	} */
    	
    	if(randNum>90 && sortSpace[0]>10){
    		bestDir(newDirec(emptSpaces,0));
    	}
    	
    	/*if(randNum==50 && sortSpace[2]>5){
    		bestDir(newDirec(emptSpaces,2));
    	} */
    	
    	/*
    	if(randNum==10 && sortSpace[0]>5){
    		bestDir(newDirec(emptSpaces,0));
    	}
    	
    	if(randNum==95 && sortSpace[1]>5){
    		bestDir(newDirec(emptSpaces,1));
    	}
    	
    	if(randNum==55 && sortSpace[2]>5){
    		bestDir(newDirec(emptSpaces,2));
    	}
    	*/
    	
    	
    	System.out.println();
    	//System.out.printf("%s=%d","RandNum",randNum);
    	/*if(randNum>=30){
    		bestDir(newDirec(emptSpaces,0));
    	} */
    	
    	/*else if(sortSpace[1]>0){
    		if(randNum>=5 && randNum<40){
    			bestDir(newDirec(emptSpaces,1));
    		}
    		
    		if(randNum<5){
    			bestDir(newDirec(emptSpaces,2));
    		}
    	} */
    	
    	
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
		
		if(keys[KeyEvent.VK_SPACE]){
			if(boost1==0){
				System.out.println("BOOOOOOOOOOOOOOOOOOOOOOOOOST");
				boost1=10;
			}
		}
		
		if(boost1>0){
			boost1-=1;
		}
		
		AI2();
		
		
		
		
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
    
    public void moveP1(){
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
    }
    
    public void moveP2(){
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
    }
	
	public void move(){
		
		moveP1();
		moveP2();
			
		if(checkCollision()!=0){
			System.out.println("COLLIDE!");
			if(checkCollision()==1){
				p2Score++;
			}
			
			if(checkCollision()==2){
				p1Score++;
			}
			//Thread.sleep(100000);
			newGame=true;
			clearGrid();
			
		}
		
		
		grid[boxx1/5][(boxy1-60)/5] = 1;
		grid[boxx2/5][(boxy2-60)/5] = 2;
			
		 
		
		
	
		//int[] pos = {boxx1,boxy1,0};
		//grid[gridNum]=pos;
		//gridNum+=1;
		//System.out.printf("%s,%s\n",Integer.toString(boxx1),Integer.toString(boxy1));
	}
	
	/*public void move(int boost1,int boost2){
		if(boost1>0){
			
		}
	} 	*/
	
	public int checkCollision(){ 	//0:none , 1: p1 collide , 2: p2 collide 3: head on
		for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		if(boxx1/5==i && (boxy1-60)/5==j){
        			if(boxx2/5==i && (boxy2-60)/5==j){
        				return 3;
        			}
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
    	if (backImage==null){
    		backImage=createImage(800,600);
    		backg=backImage.getGraphics();
    	}
    	int curTime = timeTrack();
    	Color tiel = new Color(32,178,170);
    	Color orange = new Color(255,140,0);
    	Color lineClr = new Color(45,45,45);
    	
    	backg.setColor(Color.WHITE);
    	
    	setBackground(Color.black);
    	super.paintComponent(backg);
    	//System.out.println(Boolean.toString(newGame));
    	if(newGame){
    		//System.out.println("Derp");
    		newGame=false;
    		setBackground(Color.black);
    		super.paintComponent(backg); 		//does something that works
    		
    		
    		
    		//setOpaque(true);
    		//setBackground(Color.black);
    		
    	}
    	//System.out.printf("%s,%s\n",Integer.toString(xDir1),Integer.toString(yDir1));
    	backg.setColor(lineClr);
    	for(int i=0;i<800;i+=15){					//grid will be arraylist of [x,y,p1ayer]
    		backg.drawLine(i,60,i,600);
    	}
    	
    	for(int i=0;i<540;i+=15){
    		backg.drawLine(0,i+60,800,i+60);
    	}
    	
    	for(int[] pos:grid){
    		if(pos[2]==0){
    			g.setColor(tiel);
    		}
    		
    		g.fillRect(pos[0],pos[1],5,5);
    	}
    	
    	for(int i=0;i<160;i++){
        	for(int j=0;j<108;j++){
        		if(grid[i][j]==1){
        			backg.setColor(tiel);
        			backg.fillRect(i*5,5*j+60,5,5);
        		}
        		
        		if(grid[i][j]==2){
        			backg.setColor(orange);
        			backg.fillRect(i*5,5*j+60,5,5);
        		}
        	}
        } 
    	
    	backg.setColor(Color.WHITE);
    	Font nameFont=new Font("Neuropol",Font.PLAIN,25);
		backg.setFont(nameFont);
		backg.drawString("",100,10);
		
    	String p1Info = "Player: "+Integer.toString(p1Score);
    
    	String p2Info = "Computer: "+Integer.toString(p2Score);

    	String time = Integer.toString(curTime);
    
    	backg.drawString(p1Info,50,30);
    	backg.drawString(p2Info,570,30);
    	
    	backg.drawString(time,400,30);
    	g.drawImage(backImage,0,0,this);
    	
    	
		
    	backg.setColor(tiel);  
    	//g.drawLine(0,0,400,400);
    	backg.fillRect(boxx1,boxy1,5,5);
    	backg.setColor(orange);
    	backg.fillRect(boxx2,boxy2,5,5);
    	//g.setColor(Color.gray); 
    	
    	backg.drawImage(head1,0,0,this);
    	backg.drawImage(head2,755,0,this);
    	if(curTime<=0){
    		if(p1Score>p2Score){
    			System.out.println("P!WINS!!!!!!!!!!!!!!!!!!!");
    			backg.drawImage(p1Wins,0,0,this);
    		}
    		if(p2Score>p1Score){
    			System.out.println("COMP WINS!!!!!!!!!!!!!!!!!!!");
    			backg.drawImage(p2Wins,0,0,this);
    		}
    		
    		if(p1Score==p2Score){
    			backg.drawImage(drawGame,0,0,this);
    		}
    		
    	}	
    	g.drawImage(backImage,0,0,this);
    	//g.setColor(Color.blue);  
    	
    }
    
 
}