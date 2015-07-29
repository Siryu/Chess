package Helper;

public class Location
{
	private int x;
	private int y;
	
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Location(char x, int y)
	{
		this.x = (int)(x - 96);
		this.y = y;
	}
	
	public Location(String s)
	{
		char x = s.charAt(0);
		int y = Integer.parseInt(s.substring(1,2));
		this.x = (int)(x - 96);
		this.y = y;
	}
	
	public Location difference(Location p2)
	{
		int dx = (int) (this.x - p2.getX());
		int dy = (int) (this.y - p2.getY());
		return new Location(dx, dy);
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public char getXasChar()
	{
		return (char)(this.getX() + 96);
	}
	
	@Override
	public String toString()
	{
		return "" + getXasChar() + (int)getY();
	}

	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	@Override
	public int hashCode()
	{
		int result = 1;
		result = (x  * 10) + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
