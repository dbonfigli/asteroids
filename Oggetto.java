import java.util.*;
import java.awt.*;

public class Oggetto {
	
	protected boolean collisione;
	protected Oggetto oggColli;
	
	protected Color colore;
	
	protected int resistenza;
	
	protected Vettore centro; //dove si trova il centro rispetto il disegno
	protected double angolo; //angolo di rotazione rispetto la punta a destra
	protected Vettore vel; //vettore di velocita
	protected Vettore acc; //vettore di accelerazione
	protected int timer; //tempo di tick
	protected ArrayList<Vettore> sagoma; //i vari punti ordinati della sagoma rispetto il centro 
	
	public Oggetto(int timer){ this.timer = timer; }

	public void setAcc(Vettore a) { acc = a; }
	public Vettore getAcc() {return acc.clona();}
	public void setVel(Vettore v) { vel = v; }
	public Vettore getVel() {return vel.clona();}
	public ArrayList<Vettore> getSagoma() { return sagoma; }
	
	public ArrayList<Vettore> getSagomaCartesiana()
	{
		ArrayList<Vettore> sagc = new ArrayList<Vettore>();

		for(int i=0; i<sagoma.size(); i++)
		{
			Vettore p = sagoma.get(i).clona();
			p.setAngolo(p.getAngolo() + angolo);
			p.setX(p.getX() + centro.getX());
			p.setY(p.getY() + centro.getY());
			sagc.add(p);			
		}
			
		return sagc;
	}
	
	public void incAcc(double potenza)
	{
		acc.setRaggio(potenza); //setta la potenza
		acc.setAngolo(angolo);
		
		aggiornaVel(); //aggiorna per il tick
		acc.setRaggio(0); //alla fine del tick, cioè quando finisco di premere, basta acc
	}
	
	public void aggiornaVel()
	{
		vel.setX(vel.getX() + timer * 0.01 * acc.getX() ); 
		vel.setY(vel.getY() + timer * 0.01 * acc.getY() );
	}
	
	public void aggiornaPos()
	{		
		double dx; double dy;
		
		dx = ( vel.getX()* 0.01 * timer ); // + 0.5*a.x*base.timer*base.timer );
		dy = ( vel.getY()* 0.01 * timer ); // + 0.5*a.y*base.timer*base.timer );
		
		centro.setX(centro.getX() + dx);
		centro.setY(centro.getY() + dy);
		
		if (centro.getX() > 800) centro.setX(0);
		if (centro.getY() > 500) centro.setY(0);
		if (centro.getX() < 0) centro.setX(800);
		if (centro.getY() < 0) centro.setY(500);	
	}
	
	public void setPosizione(Vettore pos) { centro = pos; }
	public void setAngolo(double alfa) { angolo = alfa; }
	public void ruotaDestra(double alfa){ angolo += alfa; } //perchè non il contrario?
	public void ruotaSinistra(double alfa){ angolo -= alfa; }
	public void setCollisione(boolean s) {collisione = s; } 
	public boolean getCollisione() { return collisione; }
	public double getAngolo() { return angolo; }
	public Vettore getCentro() { return centro; }
	public void setOggColli(Oggetto o) { oggColli = o; }
	public Oggetto getOggColli(){ return oggColli; }

	public void decResistenza() { resistenza -= 1; }
	public int getResistenza() { return resistenza; }
	public void setResistenza(int r) { resistenza = r; }
	
	public Color getColor() { return colore; }

}


