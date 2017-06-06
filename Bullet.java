import java.util.ArrayList;
import java.awt.*;


public class Bullet extends Oggetto {
		
	private int ttl; //time to live
	public Bullet(double x, double y, int timer, Vettore veloc, double ang)
	{		
		super(timer);
		
		colore = new Color(150, 150, 0);
		
		ttl = 50;
		
		centro = new Vettore(false, x, y);
		angolo = ang;
		acc = new Vettore(true, 0, 0);
		vel = veloc;
		
		sagoma = new ArrayList<Vettore>();
		sagoma.add(new Vettore(true, 5, 0) );
		sagoma.add(new Vettore(true, 0, 0) );
	}
	
	public void aggiornaPos()
	{		
		super.aggiornaPos();
		ttl -= 1;	
	}
	
	public int getTtl() { return ttl; }
	public void setTtl(int t) { ttl = t; }
	
}