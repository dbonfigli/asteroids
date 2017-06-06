import java.util.*;

public class RiCollis {

	
	
	public void collisione(Oggetto a, ArrayList<Asteroide> arr)
	{	
		//a.setCollisione(false);
		
		for(int i=0; i<arr.size(); i++)
		{
			//arr.get(i).setCollisione(false);
			
			if(collisione(a, arr.get(i)))
			{
				arr.get(i).setCollisione(true);
				arr.get(i).setOggColli(a);
				a.setCollisione(true);
				a.setOggColli(arr.get(i));
			}
		}
	}
	
	public boolean collisione(Oggetto a, Oggetto b)
	{
		
		//if(dentroCerchio(a,b))
		//{
			ArrayList<Vettore> as = a.getSagomaCartesiana();
			ArrayList<Vettore> bs = b.getSagomaCartesiana();
			
			for(int i=0; i<as.size(); i++)
			{
				if (dentroPoli(as.get(i), bs))
				return true;
			}
		//}
		
		return false;
	}
	
	
	private boolean dentroPoli(Vettore p, ArrayList<Vettore> s)
	{
		int cont = 0;
		double intersezioneX;
		Vettore p1, p2;
		p1 = s.get(0);
		
		for(int i=0; i<=s.size(); i++)
		{
			p2 = s.get(i%s.size());
			if( p.getY()> min(p1.getY(), p2.getY()) )
				if( p.getY() <= max(p1.getY(), p2.getY()) )
					if( p.getX() <= max(p1.getX(), p2.getX()) )
						if(p1.getY() != p2.getY())
						{
							intersezioneX = (p.getY()-p1.getY())*(p2.getX()-p1.getX())/(p2.getY()-p1.getY())+p1.getX(); 
							if(p1.getX() == p2.getX() || p.getX() <= intersezioneX)
								cont++;	
						}
			p1 = p2;
		}
		
		if(cont %2 == 0) return false;
		else return true;
	
	}
	/*
	public boolean dentroCerchio(Oggetto a, Oggetto b)
	{
		Vettore vma = maxRaggio(a);
		Vettore vmb = maxRaggio(b);
		
		boolean xd = false;
		boolean yd = false;
		
		if (a.getCentro().getX() < b.getCentro().getX())
		{
			vma.setX(a.getCentro().getX() + vma.getX());
			vmb.setX(b.getCentro().getX() - vmb.getX());
			
			if(vma.getX() > vmb.getX()) 
				xd = true;
			
			if (a.getCentro().getY() < b.getCentro().getY())
			{
				vma.setY(a.getCentro().getY() + vma.getY());
				vmb.setY(b.getCentro().getY() - vmb.getY());
			
				if(vma.getY() > vmb.getY()) 
					yd = true;
				
			}
			else
			{
				vma.setY(a.getCentro().getY() - vma.getY());
				vmb.setY(b.getCentro().getY() + vmb.getY());
			
				if(vma.getY() < vmb.getY()) 
					yd = true;
				
			
			}
			
			
			
		}
		else
		{
			vma.setX(a.getCentro().getX() - vma.getX());
			vmb.setX(b.getCentro().getX() + vmb.getX());
			
			if(vma.getX() < vmb.getX()) 
				xd = true;
			
			if (a.getCentro().getY() < b.getCentro().getY())
			{
				vma.setY(a.getCentro().getY() + vma.getY());
				vmb.setY(b.getCentro().getY() - vmb.getY());
				
				if(vma.getY() > vmb.getY()) 
					yd = true;
			}
			else
			{
				vma.setY(a.getCentro().getY() - vma.getY());
				vmb.setY(b.getCentro().getY() + vmb.getY());
				
				if(vma.getY() < vmb.getY()) 
					yd = true;
			}
		
			
		}
		
		
		if(xd && yd ) 
			return true;
		else return false;
		
		
		
		
		
		
		
		
		
	}
	
	public Vettore maxRaggio(Oggetto a)
	{

		ArrayList<Vettore> as = a.getSagomaCartesiana();
		double maxra = as.get(0).getRaggio();
		Vettore maxpoint = as.get(0);
		for(int i=1; i< as.size(); i++)
		{
			if (maxra < as.get(i).getRaggio()) 
			{
				maxra = as.get(i).getRaggio();
				maxpoint = as.get(i);
			}
		}
		
		return maxpoint;
	
	}
	*/
	
	double min(double x, double y) { if(x>y) return y; else return x; }
	double max(double x, double y) { if(x<y) return y; else return x; }
	

	
	
}
