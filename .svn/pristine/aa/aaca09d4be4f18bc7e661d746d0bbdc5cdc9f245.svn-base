package View;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

public class PieceSelector {
	
	private int x;
	private int y;
	private int size;
	private Image i;
	private Color color;
	private char type;
	
	public PieceSelector(int x, int y, int size, Image i, char type) {
		super();
		this.x = x;
		this.y = y;
		this.size = size;
		this.i = i;
		this.color = Color.WHITE;
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public Image getImage() {
		return i;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public boolean contains(Point p) {
		return (p.x > x && p.x < x + size && p.y > y && p.y < y + size);
	}
	
	public char getType() {
		return type;
	}

}
