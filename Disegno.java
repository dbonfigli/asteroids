import java.awt.*;
import java.util.*;

public class Disegno extends Canvas
{
	private Base base;
	private Image offscreenImage;
	private Graphics offscreenGraphics;		//double buffering
	private Image back;						//sfondo
	private int dwidth;						//dimensioni: larghezza
	private int dheight;					//dimensioni: altezza
	
	
	public Disegno(Base b)
	{ 
	  base = b;
	  
	  dwidth = 800;
	  dheight = 500;
	  
	  Image iback = null;
	  iback = base.createImage(dwidth, dheight); 
	  Graphics g = iback.getGraphics();
	  g.setColor(new Color(0,0,0 ));
	  g.fillRect(0,0,dwidth, dheight);
	  
	  //setta l'immagine back (lo sfondo)
	  back = base.createImage(dwidth,dheight);
	  Graphics offback = back.getGraphics();
	  g.setColor(new Color(250,200,200 ));
	  //immagine di sfondo
	  offback.drawImage(iback, 0, 0, dwidth, dheight, this);   
  	  //double buffering  
  	  offscreenImage = base.createImage(dwidth, dheight);
	  offscreenGraphics = offscreenImage.getGraphics();
	  offscreenGraphics.setColor(new Color(150,0,0 ));	  	
    }
	
	public void paint(Graphics g) 
	{					
		offPaint();	
	    g.drawImage(offscreenImage,0,0,this);
	}
				
	public void update(Graphics g)
	{ paint(g); }
	
	private void offPaint()
	{				
		//sfondo
		offscreenGraphics.drawImage(back,0,0, dwidth, dheight, this);
		
		
		offscreenGraphics.setColor(new Color(255,255,255 ));
		
		//if(base.getShip().getCollisione()) 
		//	offscreenGraphics.setColor(new Color(255,255,255 ));		
		
		
		///////////////////////////////////////////////////////////////////
		
		disegnaShip();
		
		//asteroidi///////////////////////////////////////////////////////////
		
		disegnaAsteroidi();
		
		//bullet////////////////////////////////////////////////////////////
			
		disegnaBullet();
		
		//waste/////////////////////////////////////////////////////////////
		
		disegnaWaste();
		
		
		//stampa le vite
		char[] car;
		int v;
		if(base.getShip()!=null) 
			v = base.getShip().getResistenza();
		else v = 0;
		car = ("vite: " + v).toCharArray();
		offscreenGraphics.drawChars(car, 0, car.length, 10, 10);
		
		//stampa il punteggio
		char[] car2;
		car2 = ("punti: " + base.getScore()).toCharArray();
		offscreenGraphics.drawChars(car2, 0, car2.length, 10, 20);
		
		//stampa il livello
		char[] car3;
		car3 = ("livello: " + base.getNaste()).toCharArray();
		offscreenGraphics.drawChars(car3, 0, car3.length, 10, 30);
		
		//stampa il livello
		char[] car4;
		car4 = ("asteroidi: " + base.getAsteList().size()).toCharArray();
		offscreenGraphics.drawChars(car4, 0, car4.length, 10, 40);
		
		
		if(base.getState()==0)
		
		{
				char[] car5;
				
				car5 = (" - GIOCO IN PAUSA - ").toCharArray();
				offscreenGraphics.drawChars(car5, 0, car5.length, 350, 300);
				
				car5 = (
						"TASTI: freccia dx -> ruota a dx; " +
						"freccia sx -> ruota a sx; " +
						"freccia su -> attiva razzi; " +
						"w -> wired / unwired; ").toCharArray();
				offscreenGraphics.drawChars(car5, 0, car5.length, 5, 480);
				
				car5 =  ("e -> aumenta il livello livello; " +
						 "d -> abbassa il livello; " +
						 "p -> metti il pausa il gioco; " +
						 "a -> inizia una nuova partita").toCharArray();
				

				offscreenGraphics.drawChars(car5, 0, car5.length, 5, 493);
		}		
		
	}  
	
	public void disegnaShip()
	{
		if(base.getShip() != null)
		{	
			
			offscreenGraphics.setColor(base.getShip().getColor());
			
			ArrayList<Vettore> al = base.getShip().getSagomaCartesiana();
		
		//	if(base.getShip().getCollisione()) 
			//	offscreenGraphics.setColor(new Color(255,0,0 ));
		
			if (base.getWired())
			{	
			
			for(int i=0; i<al.size(); i++)
			{	
				Vettore next;
				if (i == al.size()-1 ) next = al.get(0); 
				else next = al.get(i+1);
			
				offscreenGraphics.drawLine((int)al.get(i).getX(), (int)al.get(i).getY(), 
											(int)next.getX(), (int)next.getY()   );
			}
			
			}
			 
			else
			{
			
			int[] xPoints = new int[al.size()];
			int[] yPoints = new int[al.size()];
			
			for(int i=0; i<al.size(); i++)
			{
				xPoints[i] = (int) al.get(i).getX();
				yPoints[i] = (int) al.get(i).getY();
				
			}
			
			offscreenGraphics.fillPolygon(xPoints, yPoints, al.size());
			}
			
		}
		else
		{
			char[] car;
			car = ("hai perso!" +
					" per iniziare una nuova partita premi 'a',\n" +
					" per aumentare (diminuire) di livello premi 'e' ('d')").toCharArray();;
			offscreenGraphics.drawChars(car, 0, car.length, 110, 200);
		}
			
	}
	
	public void disegnaAsteroidi()
	{
		for(int j=0; j<base.getAsteList().size(); j++)
		{
			//if(base.getAsteList().get(j).getCollisione()) 
				//offscreenGraphics.setColor(new Color(255,0,0 ));
			//else
				//offscreenGraphics.setColor(new Color(255,255,0 ));

			offscreenGraphics.setColor(base.getAsteList().get(j).getColor());
			
			
			ArrayList<Vettore> al = base.getAsteList().get(j).getSagomaCartesiana();
			
			if (base.getWired())
			
			for(int i=0; i<al.size(); i++)
			{
				Vettore next;
				if (i == al.size()-1 ) next = al.get(0); 
				else next = al.get(i+1);
			
				offscreenGraphics.drawLine((int)al.get(i).getX(), (int)al.get(i).getY(), 
										   (int)next.getX(), (int)next.getY()   );
			}
			
			else
			
			{
				
			
			int[] xPoints = new int[al.size()];
			int[] yPoints = new int[al.size()];
			
			for(int i=0; i<al.size(); i++)
			{
				xPoints[i] = (int) al.get(i).getX();
				yPoints[i] = (int) al.get(i).getY();
				
			}
			
			offscreenGraphics.fillPolygon(xPoints, yPoints, al.size());
			
			}
			
		}
		
		
	}
	
	public void disegnaBullet()
	{
		for(int j=0; j<base.getBulletList().size(); j++)
		{			
			ArrayList<Vettore> al = base.getBulletList().get(j).getSagomaCartesiana();
		
			offscreenGraphics.setColor(base.getBulletList().get(j).getColor());
			
			
			
			//if(base.getBulletList().get(j).getCollisione()) 
				//offscreenGraphics.setColor(new Color(0,0,255 ));
		
			for(int i=0; i<al.size(); i++)
			{
				Vettore next;
				if (i == al.size()-1 ) next = al.get(0); 
				else next = al.get(i+1);
		
				offscreenGraphics.drawLine((int)al.get(i).getX(), (int)al.get(i).getY(), 
						(int)next.getX(), (int)next.getY()   );
			}
		
			offscreenGraphics.setColor(new Color(255,255,255 ));
		
		}

	}
	
	public void disegnaWaste()
	{
		offscreenGraphics.setColor(new Color(90,90,90));
		
		for(int j=0; j<base.getWasteList().size(); j++)
		{			
			ArrayList<Vettore> al = base.getWasteList().get(j).getSagomaCartesiana();
		
		//	if(base.getWasteList().get(j).getCollisione()) 
			//	offscreenGraphics.setColor(new Color(0,0,255 ));
			
			offscreenGraphics.setColor(base.getWasteList().get(j).getColor());
			
			
		
			for(int i=0; i<al.size(); i++)
			{
				Vettore next;
				if (i == al.size()-1 ) next = al.get(0); 
				else next = al.get(i+1);
		
				offscreenGraphics.drawLine((int)al.get(i).getX(), (int)al.get(i).getY(), 
						(int)next.getX(), (int)next.getY()   );
			}
		
		//	offscreenGraphics.setColor(new Color(255,255,255));
		
		}
		offscreenGraphics.setColor(new Color(255,255,255));

	}
	
	
		
}
