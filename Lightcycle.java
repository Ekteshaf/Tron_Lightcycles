public class Lightcycle{
	private int pNum; 	//player number
	private int xPos;
	private int yPos;
	private int vx;
	private int vy;
	private int xDir;
	private int yDir; 
	private int gridNum; 	
	
	public Lightcycle(int player){
		pNum=player;
		if(pNum==1){
			xPos=170;
			yPos=170;
			xDir=1;
			yDir=0;
		}
		
		if(pNum==2){
			xPos=630;
			yPos=170;
			xDir=-1;
			yDir=0;
		}
			
	}
	
	public void changeVel(int[]dir){
		xDir=dir[0];
		yDir=dir[1];
		vx = 5*xDir;
		vy = 5*yDir;
		
	}
	
	public void move(){
	
		if(xPos+vx>798){ 	//edge
			xPos=0;
		}
		
		else if(xPos+vx<2){
			xPos=795;
		}
		
		else{
			xPos += vx;
		}
		
		if(yPos+vy>570){
			yPos=60;
		}
		
		else if(yPos+vy<58){
			yPos=570;
		}
		
		else{
			yPos += vy;
		}
		
	}
	
	public int gridX(){
		return xPos/5;
	}
	
	public int gridY(){
		return (yPos-60)/5;
	}
	
	public int gridNum(){
		return pNum;
	}
	
	public int xDir(){
		if(vx>0){
			return 1;
		}
		
		if(vx<0){
			return -1;
		}
		
		else{
			return 0;
		}
	}
	
	public int yDir(){
		if(vy>0){
			return 1;
		}
		
		if(vx<0){
			return -1;
		}
		
		else{
			return 0;
		}
	}
	
	public int xPos(){
		return xPos;
	}
	
	public int yPos(){
		return yPos;
	}
	
		
}