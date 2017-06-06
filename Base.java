import java.awt.*;
import java.util.*;
import java.applet.*;


public class Base extends Applet implements  Runnable	{
	
	private RiCollis colli;
	private int score = 0;
	private Thread thread;
	private Disegno disegno; //canvas
	private boolean sx, dx, up, down, spazio;
	private int timer = 16; //timer
	private Ship ship;
	private ArrayList<Asteroide> asteList;
	private ArrayList<Bullet> bulletList;
	private ArrayList<Waste> wasteList;
	private final int nastepartenza = 7;
	private int naste;
	private boolean wired = false;
	private int stato = 0;
		//	0 inizio
 		//	1 vai	
	
	//##### metodo per la tastiera ##############################

	public boolean keyDown(Event e, int key)
	{
		switch (key) 
		{
			case 32:	if (ship!=null)
						bulletList.add(new Bullet(ship.getCentro().getX(),
												  ship.getCentro().getY(),
												  timer,
												  new Vettore(true, 80, ship.getAngolo()),
												  ship.getAngolo()));	
						break;
			case 1006:	sx = true;
						break;		
			case 1004:	up = true; //freccia su
						break;
			case 1007:	dx = true;
						break;	
			case 1005:	down = true; //freccia giu'
						break;
			case 112:	if (stato == 1) stato = 0; else stato = 1; //tasto p
						disegno.repaint();
						break;
			case 101:	naste++;
						if (ship!= null)
						{
							 asteList = null; creaAste(); //tasto e
						}	
						break;
			case 100:	if(naste>=2) naste--;
						if (ship!= null)
						{
							 asteList = null; creaAste(); //tasto d
						}	
						break;
			case 97:	//naste=nastepartenza;
						score=0; 
						ship = new Ship(400,400,timer); creaAste(); 
						break; //tasto a
			case 119:	if(wired==true) wired = false; else wired = true; 
						break; //tasto w

		}
		
		//System.out.println(key);
		
		return false;
	}
	
	public boolean keyUp(Event e, int key)
	{
		switch (key) 
		{	
			case 1006:	sx = false;
						break;		
			case 1004:	up = false;	//freccia su
						break;
			case 1007:	dx = false;
						break;	
			case 1005:	down = false;	//freccia giu'
						break;
		}
		
		
		return false;
	}
	
//##### init e destroy #######################################
	
	public void init()
	{	
		
		colli = new RiCollis();
		
		naste = nastepartenza;
		
		sx = false; dx = false;
		up = false; down = false;
		
		// nave
		ship = new Ship(400,400, timer);
		
		//waste
		wasteList = new ArrayList<Waste>();
		
		// bullet
		bulletList = new ArrayList<Bullet>();
		
		//asteroidi
		asteList = new ArrayList<Asteroide>();
		creaAste();
		
		///
		disegno = new Disegno(this); //disegno
		setBackground(Color.lightGray); //aspetto applet
		setLayout(new BorderLayout());
		add(disegno);
	}
	
	//##### metodi Start, Stop, Runnable #########################

    public void run()
    {   
        while ( thread == Thread.currentThread()) 
        {  	
       		if (stato == 1) tick();
       		try { Thread.currentThread().sleep(timer); } 
       		catch (InterruptedException e) {}
       	}
    }	

	public void stop() 
	{	
		thread=null;
	}
			
	public void start() 
	{ 
		if (thread == null)
		{
			thread = new Thread(this); 
			thread.start(); 
		}
	}
	
	/////////////////////////////////////////////
	
	
	public void tick()
	{
		
		if (asteList.size() == 0) 
		{
			naste++;
			creaAste();
		}
		
	
		if (ship != null)
		{
			if (sx) ship.ruotaSinistra(8);
			if (dx) ship.ruotaDestra(8);
			if (up) ship.incAcc(8);
			
			ship.aggiornaPos();
			ship.setCollisione(false);
		}
		
	//	if (asteList.get(0)!= null) System.out.println(asteList.get(0).getAngolo());
		
		for(int i=0; i<asteList.size(); i++)
		{
			asteList.get(i).aggiornaPos();
			asteList.get(i).setCollisione(false);
		}
		
		for(int i=0; i<bulletList.size(); i++)
		{
			bulletList.get(i).aggiornaPos();
			bulletList.get(i).setCollisione(false);
		}
		
		for(int i=0; i<wasteList.size(); i++)
		{
			wasteList.get(i).aggiornaPos();
		}

		for(int i=wasteList.size()-1; i>=0; i--)
			if (wasteList.get(i).getTtl()<0)
				wasteList.remove(i);
		
		for(int i=bulletList.size()-1; i>=0; i--)
			if (bulletList.get(i).getTtl()<0)
				bulletList.remove(i);
		
		
		
		//collisioni
		
		//setta la collisione della navicella
		if(ship!=null)
			colli.collisione(ship, asteList);
		
		//per far fermare i proiettili quando colpiscono gli asteroidi
		for(int i=0; i<bulletList.size(); i++)
		{
			colli.collisione(bulletList.get(i), asteList);
			if(bulletList.get(i).getCollisione())
				bulletList.get(i).setTtl(0);			
		}
		
		manageCollision();
		
		disegno.repaint();
	}
	
	public void manageCollision()
	{
		int size = asteList.size();
		for(int i=0; i<size; i++)
		{	
			if(asteList.get(i).getCollisione())
			{		
				if (ship!=null)
				if(ship.getCollisione()==true)
				{
					ship.decResistenza();
					asteList.get(i).setResistenza(0);
					if (ship.getResistenza()==0)
					{
						creaWaste(ship);
						ship = null;
						//naste = nastepartenza;
						
						for(int j=(int)(asteList.size()/1.5); j>=1; j--)
						{
							creaWaste(asteList.get(j));
							asteList.remove(j);
							
						}
							
						break;
					
						
						
					}
				}
				else
					{
						asteList.get(i).decResistenza();
						score++;
						
					}
				
				if (asteList.get(i).getResistenza() <= 0)
				{
					if (asteList.get(i).getDeltaRag()>40) 
					{	
					asteList.add(new Asteroide(asteList.get(i).getCentro().getX(),
							                   asteList.get(i).getCentro().getY(),
							                   timer,
							                   new Vettore(true, Math.random()*15, Math.random()*360),
							                   Math.random()*0.4,
							                   asteList.get(i).getDeltaRag()/2,
							                  // asteList.get(i).getColor()));
							                   new Color((int)(Math.random()*245+10),(int)(Math.random()*245+10),(int)(Math.random()*245+10))));
					
					asteList.add(new Asteroide(asteList.get(i).getCentro().getX(),
			                   				   asteList.get(i).getCentro().getY(),
			                   				   timer,
			                   				   new Vettore(true, Math.random()*15, Math.random()*360),
			                   				   Math.random()*0.4,
			                   				   asteList.get(i).getDeltaRag()/2,
			                   				  // asteList.get(i).getColor()));
			                   				new Color((int)(Math.random()*245+10),(int)(Math.random()*245+10),(int)(Math.random()*245+10))));
					
					}
					
						creaWaste(asteList.get(i));

					asteList.remove(i);
					size--; i--;
				}
				
			}	
		}	
	}
	
	public void creaWaste(Oggetto ogg)
	{			
			ArrayList<Vettore> al = ogg.getSagomaCartesiana();
		
			for(int i=0; i<al.size(); i++)
			{
				Vettore next;
				if (i == al.size()-1 ) next = al.get(0); 
				else next = al.get(i+1);
		
				Vettore v1 = new Vettore(false, (int)al.get(i).getX(), (int)al.get(i).getY()); 
				Vettore v2 = new Vettore(false,	(int)next.getX(), (int)next.getY());
			
				//vettore v2 - v1
				Vettore vris = new Vettore(false,
										   (int)v2.getX()-(int)v1.getX(),
										   (int)v2.getY()-(int)v1.getY());
				
				Vettore vetVel;
				if(ogg.getOggColli()==null)
					vetVel = new Vettore(true, ogg.getVel().getRaggio()+Math.random()*5,
									 	       ogg.getVel().getAngolo()+Math.random()*95);
				else
					vetVel = new Vettore(true, ogg.getVel().getRaggio()+Math.random()*5,
									 	       ogg.getOggColli().getVel().getAngolo()+Math.random()*35);
				
				
				wasteList.add(new Waste( v1.getX(), v1.getY(),
										timer, 
										vetVel,
										new Vettore(false, 0,0),
										vris,
										ogg.getColor()));
			}
	}
	
	
	private void creaAste()
	{
		asteList = new ArrayList<Asteroide>();
		if(ship!=null)
		for (int i=0; i<naste; i++)
		{
			double x = 0;
			do{ x = Math.random()*800; }
			while (x>ship.getCentro().getX()-100 && x<ship.getCentro().getX()+100);
			
			double y = 0;
			do{ y = Math.random()*500; }
			while (y>ship.getCentro().getY()-100 && y<ship.getCentro().getY()+100);
			
			
			asteList.add(new Asteroide(x, y,
									   timer,
									   new Vettore(true, Math.random()*15, Math.random()*360),
									   Math.random()*0.4,
									   90,
									   new Color((int)(Math.random()*245+10),(int)(Math.random()*245+10),(int)(Math.random()*245+10))));
	
		}
	}
	
	public int getTimer() { return timer; }
	public Ship getShip() { return ship; }
	public ArrayList<Asteroide> getAsteList() { return asteList; }
	public ArrayList<Bullet> getBulletList() { return bulletList; }
	public ArrayList<Waste> getWasteList() { return wasteList; }
	public int getScore() { return score; }
	public int getNaste() { return naste; }
	public int getState() { return stato; }
	public boolean getWired() { return wired; }
	
}
