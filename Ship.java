import java.util.*;
import java.awt.*;

public class Ship extends Oggetto {
	
	public Ship(double x, double y, int timer)
	{		
		super(timer);
		
		colore = new Color(255,0,0);
		
		resistenza = 5;
		
		centro = new Vettore(false, x, y);
		angolo = 0d;
		acc = new Vettore(true, 0, 0);
		vel = new Vettore(true, 0, 0); //50, angolo);
		
		sagoma = new ArrayList<Vettore>();
		sagoma.add(new Vettore(true, 5d, -90d) );
		sagoma.add(new Vettore(true, 20d, 0d) );
		sagoma.add(new Vettore(true, 5d, 90d) );
	}
	
}
