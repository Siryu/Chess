package View;


import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import Control.ChessManager;
import Exception.ExitException;
import Exception.InvalidMoveException;
import Helper.Location;
import Model.King;
import Model.Piece;

public class ChessDisplay
{
	ChessManager cm;

	
	public ChessDisplay(ChessManager cm)
	{
		this.cm = cm;
	}
	
	public void displayBoard()
	{
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println("-----------------------");
		
		for(int y = ChessManager.HEIGHT; y > 0; y--)
		{
			for(int x = 1; x <= ChessManager.WIDTH; x++)
			{
				Piece piece = cm.getPieces().get(new Location(x, y));
				
				if(piece != null)
				{
					System.out.print(" " + piece + " ");
				}
				else 
				{
					System.out.print(" - ");
				}
			}
			System.out.println("|" + y);
		}
		
	}
	
	public String displayWhoCanMove(boolean isWhitesTurn) throws ExitException, InvalidMoveException
	{
		final String legitSelection = "[a-hA-H][1-8]";
		//boolean check = false;
		
		StringBuilder moveString = new StringBuilder();
	
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
			
		System.out.println("\nIt's " + (isWhitesTurn ? "Whites turn" : "Blacks turn"));
	/*	
		if(cm.checkForCheck(cm))
		{
			System.out.println("\n" + (isWhitesTurn ? "White " : "Black ") + "is in Check\n");
			check = true;
		}
	*/	
		System.out.println("type \"QUIT\" to quit.");
		
		ArrayList<Location> validMoves = new ArrayList<Location>();
		
		//System.out.println(cm.majorCheckForCheck(10));		
		
		for(Entry<Location, Piece> e : cm.getPieces().entrySet())
		{
			if(isWhitesTurn == !e.getValue().getColor() && cm.getModifiedMoves(cm.getPieceMoves(e.getKey()), e.getKey()).size() > 0)
			{
				System.out.println(e.getKey() + " has Moves.");
				validMoves.add(e.getKey());
			}
		}
	/*	
		if(!check && validMoves.isEmpty())
		{
			System.out.println("StaleMate");
			throw new ExitException(isWhitesTurn);
		}
		
		else if(check && validMoves.isEmpty())
		{
			System.out.println(isWhitesTurn ? "Black wins by checkmate!!!" : "White wins by checkmate!!!");
			throw new ExitException(isWhitesTurn);
		}
	*/			
		String inputChoice = input.nextLine().trim();
		
		if(inputChoice.toUpperCase().equals("QUIT"))
		{
			throw new ExitException(isWhitesTurn);
		}
		else if(inputChoice.matches(legitSelection))
		{
			Location pieceLocation = new Location(inputChoice);
			Piece p = this.cm.getPieces().get(pieceLocation);
			
			if(p != null && validMoves.contains(pieceLocation))
			{
				moveString.append(inputChoice + " ");
				System.out.println("type \"Return\" to go back.");
				
				for(Location loc : cm.getModifiedMoves(cm.getPieceMoves(pieceLocation), pieceLocation))
				{
					System.out.println("Move to " + loc.toString());
				}
				String inputMove = input.nextLine().trim();
				if(inputMove.toUpperCase().equals("RETURN"))
				{
					System.err.println("\nMove Cancelled.");
				}
				else if(inputMove.matches("[a-zA-Z][0-9]"))
				{
					Location moveToLocation = new Location(inputMove);
					Location difference = pieceLocation.difference(moveToLocation);
					
					if(cm.getPieceMoves(pieceLocation).contains(moveToLocation))
					{
						moveString.append(inputMove);
						
						if(cm.getPieces().get(moveToLocation) != null)
						{
							moveString.append("*");
						}
						else if(p instanceof King && Math.abs(difference.getX()) == 2)
						{
							if(difference.getX() < 0)
							{
								moveString.append(" h" + pieceLocation.getY() + " f" + pieceLocation.getY()); // h8 f8
							}
							else if(difference.getX() > 0)
							{
								moveString.append(" a" + pieceLocation.getY() + " d" + pieceLocation.getY());
							}
						}
					}
				}
				else
				{
					System.err.println("\n Invalid format for a piece... please try letter number, a-h 1-8");
				}
			}
			else
			{
				System.err.println("That position doesn't have your piece.");
			}
		}
		else
		{
			System.err.println("\nInvalid format for a piece... please try letter number");	
		}
		return moveString.toString();
	}
}
