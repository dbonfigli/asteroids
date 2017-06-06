import java.util.ArrayList;
import java.awt.*;

public class Asteroide extends Oggetto {
	
	
	private double incAngolo;
	private double deltarag;
	
	public Asteroide(double x, double y, int timer, Vettore veloc, double incAngolo, double deltaraggio, Color co)
	{		
		super(timer);
		
		colore = co;
		
		resistenza = 4;
		this.incAngolo = incAngolo;
		
		centro = new Vettore(false, x, y);
		angolo = 0d;
		acc = new Vettore(true, 0, 0);
		vel = veloc;
		
		sagoma = new ArrayList<Vettore>();
		
		int npunti = 8;
		double ang = 360 / npunti;
		double deltaang = 10;
		Vettore v;
		double rag = 10;
		deltarag = deltaraggio; 

		for(int i=0; i<npunti; i++)
		{
			v = new Vettore(true, Math.random()*deltarag + rag, 
					              Math.random()*deltaang + ang*i );
			
			sagoma.add(v);
			
		}
	}
	
	public void aggiornaPos()
	{
		ruotaDestra(incAngolo);
		super.aggiornaPos();	
	}
	
	public double getDeltaRag() { return deltarag; }
	
	
	
}