
import java.util.ArrayList;
import java.awt.*;

public class Waste extends Oggetto {
		
	private double incAngolo;
	private int ttl; //time to live
	
	public Waste(double x, double y, int timer, Vettore veloc, Vettore p1, Vettore p2, Color co)
	{		
		super(timer);
		
		colore = co;
		
		ttl = 50;
		
		incAngolo = Math.random()*3;
		
		centro = new Vettore(false, x, y);
		acc = new Vettore(true, 0, 0);
		vel = veloc;
		
		sagoma = new ArrayList<Vettore>();
		sagoma.add(p1);
		sagoma.add(p2);
	}
	
	public void aggiornaPos()
	{		
		super.aggiornaPos();
		ttl -= 1;	
		angolo += incAngolo;
	}
	
	public int getTtl() { return ttl; }
	public void setTtl(int t) { ttl = t; }
	
}