package enterpriseExceptions;

public class MalfunctionException extends Exception{
	public MalfunctionException(){
		super();
		killRedshirt();
	}
	
	public MalfunctionException(String message){
		super(message);
		killRedshirt();
	}
	
	private void killRedshirt(){
		System.out.println("Well done, you just killed another redshirt!");
	}
}
