package Control;

import java.awt.event.MouseEvent;

public class MouseControlMovement extends MouseControlClick
{
	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		this.setLastX(arg0.getX());
		this.setLastY(arg0.getY());
		this.setChanged();
	    this.notifyObservers();
	}
}
