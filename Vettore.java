
public class Vettore {

	private double x;
	private double y;
	
	
	public Vettore(boolean polare, double xra, double yan)
	{
		if (!polare)
		{	
			x = xra;
			y = yan;
		}
		else
		{
			//x = 10;
			//y = 10;
			
			setRaggio(xra);
			setAngolo(yan);
			
			//x = xra*java.lang.Math.cos(java.lang.Math.toRadians(yan) );
			//y = xra*java.lang.Math.sin(java.lang.Math.toRadians(yan) );
		}
	}
	
	public Vettore()
	{
		x = 0; y = 0;
	}
	
	public double getX() { return x; }
	public void setX(double x) { this.x = x; }
	

	public double getY() { return y; }
	public void setY(double y) { this.y = y; }
	
	public double getRaggio() { return java.lang.Math.sqrt(x*x + y*y); }
	public double getAngolo() 
	{ return java.lang.Math.toDegrees(java.lang.Math.atan2(y, x)); }
	
	public void setRaggio(double raggio) 
	{
		double angolo = getAngolo();
		x = raggio*java.lang.Math.cos(java.lang.Math.toRadians(angolo) );
		y = raggio*java.lang.Math.sin(java.lang.Math.toRadians(angolo) );		
	}
	
	public void setAngolo(double angolo) 
	{
		double raggio = getRaggio();
		x = raggio*java.lang.Math.cos(java.lang.Math.toRadians(angolo) );
		y = raggio*java.lang.Math.sin(java.lang.Math.toRadians(angolo) );		
	}
	
	public Vettore clona()
	{
		return new Vettore(false, x, y);
	}
	
	
	
}
