package Control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import Exception.ExitException;
import Exception.InvalidCommandException;
import Exception.InvalidMoveException;
import Exception.InvalidTurnException;
import Helper.Location;
import Model.King;
import Model.Pawn;
import Model.Piece;
import Model.PieceCollection;
import Model.Rook;
import View.ChessDisplay;
import View.ChessDisplayGUI;

public class ChessManager {
	protected PieceCollection<Location, Piece> pieces;
	public final static int WIDTH = 8;
	public final static int HEIGHT = 8;
	private final String defaultFilePath = "defaultSetup.txt";

	private MoveDecrypter md;
	ChessDisplay display;
	private ChessDisplayGUI GUIdisplay;
	private final boolean isGUI = true;

	private boolean isWhiteTurn;
	private boolean isInCheck = false;
	private boolean isInCheckMate = false;
	private boolean isInStaleMate = false;

	private boolean isClient;
	private String IPAddress = null;
	private int PORT;
	private Socket socket;
	private InputStream in;
	private ObjectOutputStream out;
	private Thread t;

	public ChessManager() {
		pieces = new PieceCollection<Location, Piece>();
	}

	public void initSetup(File saveFile) throws IOException {
		if (saveFile.getPath().isEmpty()) {
			saveFile = new File(defaultFilePath);
		}

		md = new MoveDecrypter(saveFile);
		display = new ChessDisplay(this);
		GUIdisplay = new ChessDisplayGUI(this);
		this.isWhiteTurn = true;
		runProgram();
		if (isGUI) {
			GUIdisplay.repaint();
		}
	}

	public void initSetup() throws IOException {
		File saveFile = new File(defaultFilePath);
		md = new MoveDecrypter(saveFile);
		display = new ChessDisplay(this);
		GUIdisplay = new ChessDisplayGUI(this);
		this.isWhiteTurn = true;

		// ask server or client. client is always white
		String[] options = { "Server", "Client" };
		Object o = JOptionPane.showInputDialog(null,
				"Are you the server or the client?", "Role Selection",
				JOptionPane.YES_NO_OPTION, null, options, options[0]);

		boolean play = true;
		// if client ask ip address, port
		if (o == null) {
			play = false;
		} else if (o.equals(options[1])) {
			isClient = true;
			do {
				IPAddress = JOptionPane
						.showInputDialog(
								null,
								"Please enter the ip address you would like to connect to:",
								"192.168.173.1");
				if (IPAddress == null) {
					play = false;
				} else if (!IPAddress.matches("(\\d\\d?\\d?\\.){3}\\d\\d?\\d?")) {
					IPAddress = null;
					JOptionPane.showMessageDialog(null,
							"That's not a valid ip adress!");
				}
			} while (IPAddress == null && play);
			if (play) {
				play = askPort();
			}
		}
		// if server, ask port
		else if (o.equals(options[0])) {
			isClient = false;
			play = askPort();
		} else {
			play = false;
		}

		if (play) {
			// Create input/output streams
			boolean okay = false;
			if (!isClient) {
				try {
					ServerSocket server = new ServerSocket(PORT);
					server.setSoTimeout(15000);
					socket = server.accept();
					in = socket.getInputStream();
					out = new ObjectOutputStream(socket.getOutputStream());
					okay = true;
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			} else {

				while (socket == null || !socket.isConnected()) {
					try {
						socket = new Socket(IPAddress, PORT);
						in = socket.getInputStream();
						out = new ObjectOutputStream(socket.getOutputStream());
						okay = true;
					} catch (IOException ex) {
						System.out.println("No server detected on that port. Waiting for connection...");
					}
				}
			}

			if (okay) {
				t = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							ObjectInputStream is = new ObjectInputStream(in);
							while (!Thread.interrupted()) {
								Object o = is.readObject();
								if (o != null && o instanceof String) {
									recievePlay((String) o);
								}
							}
						} catch (IOException | ClassNotFoundException e) {
							System.out.println(new ExitException(!isWhiteTurn)
									.getMessage());
						}

					}

				});

				t.start();
				runProgram();

			} else {
				System.out.println("SOMETHING IS WRONG!!!");
			}
		}
		GUIdisplay.repaint();
	}

	private boolean askPort() {
		boolean cont = false;
		boolean toReturn = false;
		do {
			Object val = JOptionPane.showInputDialog(null,
					"What port would you like to communicate on?", "1234");
			if (val == null) {
				toReturn = false;
				cont = true;
			} else {
				try {
					PORT = Integer.parseInt((String) val);
					cont = true;
					toReturn = true;
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null,
							"That's not a valid port number.");
					cont = false;
				}
			}
		} while (!cont);
		return toReturn;
	}

	/**
	 * This runs the program
	 * 
	 * @throws IOException
	 */
	private void runProgram() throws IOException {

		boolean exit = false;

		while (!exit) {
			this.setInCheck(false);
			// ressetting en passant from the previous turn
			for (Entry<Location, Piece> e : this.getPieces().entrySet()) {
				if (e.getValue() instanceof Pawn
						&& e.getValue().getColor() != this.isWhiteTurn()) {
					Pawn p = (Pawn) e.getValue();
					p.setEnPassant(false);
				}
			}
			for (BoardBehavior b : md.getBehaviors()) {
				try {
					if (b.checkTurn(this.getPieces().get(b.getP1()),
							isWhiteTurn)) {
						if (!checkForCheck(tempMove(b.getP1(), b.getP2()))) {
							if (b.getPiece1() != null
									&& !(b instanceof PawnPromotionBehavior)) {
								addPiece(b);
								this.isWhiteTurn = !this.isWhiteTurn;
							} else if (b.getP3() != null && b.getP4() != null) {
								castePieces(b);
							} else if (b instanceof MoveBehavior
									|| (b instanceof PawnPromotionBehavior && !((PawnPromotionBehavior) b)
											.getCapture())) {
								movePiece(b);
							} else if (b instanceof CaptureBehavior
									|| b instanceof PawnPromotionBehavior) {
								capturePiece(b);
							} else if (b instanceof EnPassantBehavior) {
								EnPassant(b);
							}

							this.isWhiteTurn = !this.isWhiteTurn;
						} else {
							throw new InvalidMoveException(
									"Can't put yourself in check.", b.getP1(),
									b.getP2());
						}

						if (this.checkForCheck(this)) {
							this.setInCheck(true);
							System.out
									.println("\n"
											+ (this.isWhiteTurn() ? "White "
													: "Black ")
											+ "is in Check.");
						}

						ArrayList<Location> validMoves = new ArrayList<Location>();
						for (Entry<Location, Piece> e : this.getPieces()
								.entrySet()) {
							if (this.isWhiteTurn() == !e.getValue().getColor()
									&& this.getModifiedMoves(
											this.getPieceMoves(e.getKey()),
											e.getKey()).size() > 0) {
								validMoves.add(e.getKey());
							}
						}
						if (!this.isInCheck() && validMoves.isEmpty()) {
							this.setInStaleMate(true);
							System.out.println("StaleMate");
							t.interrupt();
						}

						if (this.isInCheck && validMoves.isEmpty()) {
							this.setInCheckMate(true);
							System.out
									.println(this.isWhiteTurn() ? "Black wins by checkmate!!!"
											: "White wins by checkmate!!!");
							t.interrupt();
						}

					} else {
						throw new InvalidTurnException(b.getP1(), b.getP2(),
								isWhiteTurn);
					}

					System.out.println("\n\n" + b);
					// display.displayBoard();
				} catch (InvalidMoveException ex) {
					System.out.println("\n\n" + ex.getMessage());
					// display.displayBoard();
				} catch (InvalidTurnException ex) {
					System.out.println("\n\n" + ex.getMessage());
					// display.displayBoard();
				}
			}

			if (!isGUI) {
				try {
					md = new MoveDecrypter(
							display.displayWhoCanMove(isWhiteTurn));
				} catch (ExitException ex) {
					System.err.println(ex.getMessage());
					exit = true;
				} catch (InvalidMoveException ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				exit = true;
			}
		}
	}

	public void recievePlay(String play) {
		try {
			if (isWhiteTurn() == isClient()) {
				out.writeObject(play);
			}
			md = new MoveDecrypter(play);
			runProgram();
			GUIdisplay.repaint();
		} catch (IOException e) {
			System.out.println("Error recieving the play.....");
		}
	}

	public PieceCollection<Location, Piece> getPieces() {
		return this.pieces;
	}

	private void movePiece(BoardBehavior b) throws InvalidMoveException {
		Location difference = b.getP1().difference(b.getP2());

		if (pieces.get(b.getP1()).checkIfMoveIsValid(difference)
				&& checkIfWayIsClear(b.getP1(), b.getP2()))// &&
															// !checkForCheck(b.getP1()))
		{
			// setting pawn into en passant
			if ((pieces.get(b.getP1()) instanceof Pawn)
					&& Math.abs(difference.getY()) == 2) {
				Pawn pawn = (Pawn) (pieces.get(b.getP1()));
				pawn.setEnPassant(true);
			}

			pieceManip(b);
		} else {
			throw new InvalidCommandException(
					"Piece can't move like that or wrong format for move",
					b.getP1(), b.getP2());
		}
	}

	private void EnPassant(BoardBehavior b) throws InvalidMoveException
	{
		Location difference = b.getP1().difference(b.getP2());
		Piece piece1 = pieces.get(b.getP1());

		// special capture for en passant.
		int color = piece1.getColor() ? 1 : -1;
		Piece piece2 = pieces.get(new Location(b.getP2().getX(), b.getP2()
				.getY() + (1 * color)));
		if (piece2 != null && piece2 instanceof Pawn
				&& (piece1 instanceof Pawn && piece1.validCapture(difference))
				&& ((Pawn) piece2).isEnPassant()
				&& checkIfWayIsClear(b.getP1(), b.getP2())
				&& piece1.getColor() != piece2.getColor())
		{
			pieces.get(b.getP1()).setMoved();
			pieces.remove(new Location(b.getP2().getX(), b.getP2().getY() + (1 * color)));
			pieces.put(b.getP2(), pieces.get(b.getP1()));
			pieces.remove(b.getP1());
		}
	}

	private void capturePiece(BoardBehavior b) throws InvalidMoveException
	{
		Location difference = b.getP1().difference(b.getP2());
		Piece piece1 = pieces.get(b.getP1());

		if (piece1.getColor() != pieces.get(b.getP2()).getColor()
				&& piece1.validCapture(difference))// &&
													// !checkForCheck(b.getP1()))
		{
			pieceManip(b);
		} else 
		{
			throw new InvalidMoveException("Can't capture your own piece", b.getP1(), b.getP2());
		}
	}

	private void pieceManip(BoardBehavior b) {
		if (b instanceof PawnPromotionBehavior) 
		{
			pieces.remove(b.getP2());
			pieces.remove(b.getP1());
			pieces.put(b.getP2(), b.getPiece1());
		} else
		{
			pieces.get(b.getP1()).setMoved();
			pieces.remove(b.getP2());
			pieces.put(b.getP2(), pieces.get(b.getP1()));
			pieces.remove(b.getP1());
		}
	}

	private void addPiece(BoardBehavior b) throws InvalidCommandException 
	{
		pieces.put(b.getP1(), b.getPiece1());
	}

	private void castePieces(BoardBehavior b) throws InvalidMoveException
	{
		Piece kingPiece = pieces.get(b.getP1());
		Piece rookPiece = pieces.get(b.getP3());

		// castling exclusion
		if ((kingPiece instanceof King && rookPiece instanceof Rook)
				&& kingPiece.getColor() == rookPiece.getColor()
				&& checkIfWayIsClear(b.getP1(), b.getP3())
				&& !kingPiece.getHasMoved() && !rookPiece.getHasMoved()) 
		{
			kingPiece.setMoved();
			pieces.put(b.getP2(), kingPiece);
			pieces.remove(b.getP1());

			rookPiece.setMoved();
			pieces.put(b.getP4(), rookPiece);
			pieces.remove(b.getP3());
		}

		else {
			throw new InvalidMoveException("Can't complete castling action");
		}
	}

	// checks all the spaces between two locations to see if there are any
	// pieces between them on a set path.
	private boolean checkIfWayIsClear(Location startPoint, Location endPoint)
	{
		boolean isWayClear = true;
		Location difference = startPoint.difference(endPoint);
		difference.setX(Math.abs(difference.getX()));
		difference.setY(Math.abs(difference.getY()));

		if (difference.getX() > 0 && difference.getY() == 0) 
		{
			int larger = startPoint.getX() > endPoint.getX() ? startPoint.getX() : endPoint.getX();
			for (int j = larger - (difference.getX() - 1); j < larger; j++)
			{
				if (pieces.containsKey(new Location(j, startPoint.getY()))) 
				{
					isWayClear = false;
				}
			}
		}

		else if (difference.getY() > 0 && difference.getX() == 0)
		{
			int larger = startPoint.getY() > endPoint.getY() ? startPoint.getY() : endPoint.getY();
			for (int j = larger - (difference.getY() - 1); j < larger; j++) 
			{
				if (pieces.containsKey(new Location(startPoint.getX(), j))) 
				{
					isWayClear = false;
				}
			}
		}

		else if (difference.getX() == difference.getY()) {
			int leftP = startPoint.getX() < endPoint.getX() ? startPoint.getY()
					: endPoint.getY();
			int rightP = startPoint.getX() > endPoint.getX() ? startPoint
					.getY() : endPoint.getY();
			int largerX = startPoint.getX() > endPoint.getX() ? startPoint
					.getX() : endPoint.getX();

			int slope = leftP - rightP;

			int y = slope > 0 ? leftP - 1 : leftP + 1;
			for (int j = largerX - (difference.getX() - 1); j < largerX; j++) {
				if (pieces.containsKey(new Location(j, y))) {
					isWayClear = false;
				}
				y = slope > 0 ? y - 1 : y + 1;
			}
		}
		return isWayClear;
	}

	// gets the piece moves without any restraints to the moves...
	public ArrayList<Location> getPieceMoves(Location pieceSpot) {
		ArrayList<Location> goodMoves = new ArrayList<Location>();

		for (int y = ChessManager.HEIGHT; y > 0; y--) {
			for (int x = 1; x <= ChessManager.WIDTH; x++) {
				Location goodSpot = new Location(x, y);
				Location difference = pieceSpot.difference(goodSpot);
				Piece piece1 = pieces.get(pieceSpot);
				Piece piece2 = pieces.get(goodSpot);

				if (((piece2 == null && piece1.checkIfMoveIsValid(difference)) || (piece2 != null
						&& piece1.getColor() != piece2.getColor() && piece1
							.validCapture(difference)))
						&& checkIfWayIsClear(pieceSpot, goodSpot)) {
					goodMoves.add(goodSpot);
				}

				// for en passant
				int color = piece1.getColor() ? 1 : -1;
				piece2 = pieces.get(new Location(goodSpot.getX(), goodSpot
						.getY() + (1 * color)));
				if (piece2 != null
						&& piece2 instanceof Pawn
						&& (piece1 instanceof Pawn
								&& Math.abs(difference.getX()) == 1 && Math
								.abs(difference.getY()) == 1)
						&& ((Pawn) piece2).isEnPassant()
						&& checkIfWayIsClear(pieceSpot, goodSpot)) {
					goodMoves.add(goodSpot);
				}
			}
		}
		return goodMoves;
	}

	// passes in a list of piece moves and restrains those moves to keep check
	// from happening.
	public ArrayList<Location> getModifiedMoves(ArrayList<Location> locations,
			Location pieceLoc) {
		ArrayList<Location> newLocations = new ArrayList<Location>();
		for (Location loc : locations) {
			if (!checkForCheck(tempMove(pieceLoc, loc))) {
				newLocations.add(loc);
			}
		}

		// stops king from castling through a check where he doesn't stop
		if ((pieces.get(pieceLoc) instanceof King && (newLocations.contains(new Location(pieceLoc.getX() + 2, pieceLoc.getY()))
				&& !newLocations.contains(new Location(pieceLoc.getX() + 1, pieceLoc.getY()))))
				|| checkForCheck(this)) 
		{
			newLocations.remove(new Location(pieceLoc.getX() + 2, pieceLoc.getY()));
		} else if ((pieces.get(pieceLoc) instanceof King && (newLocations.contains(new Location(pieceLoc.getX() - 2, pieceLoc.getY()))
				&& !newLocations.contains(new Location(pieceLoc.getX() - 1, pieceLoc.getY()))))
				|| checkForCheck(this)) 
		{
			newLocations.remove(new Location(pieceLoc.getX() - 2, pieceLoc.getY()));
		}

		return newLocations;
	}

	public ChessManager tempMove(Location pieceStart, Location pieceEnd)
	{
		// temp board with piece moved to see if you put yourself in check
		ChessManager cm2 = new ChessManager();
		cm2.createNewBoardCopy(this.pieces);
		cm2.isWhiteTurn = this.isWhiteTurn;
		cm2.getPieces().remove(pieceEnd);
		cm2.getPieces().put(pieceEnd, cm2.getPieces().get(pieceStart));
		cm2.getPieces().remove(pieceStart);

		return cm2;
	}

	// finds the king, then creates an instance of the board for each possible
	// move to see if that is a check.
	public boolean checkForCheck(ChessManager cm) 
	{
		boolean isInCheck = false;
		Location kingSpot = null;
		// go through the list of enemy pieces to see if they put you in check
		// once you move that piece.

		for (Entry<Location, Piece> e : cm.getPieces().entrySet()) {
			if (e.getValue() instanceof King
					&& e.getValue().getColor() == !isWhiteTurn) {
				kingSpot = e.getKey();
				break;
			}
		}

		for (Entry<Location, Piece> e : cm.getPieces().entrySet()) {
			if (e.getValue() != null && e.getValue().getColor() == isWhiteTurn
					&& cm.getPieceMoves(e.getKey()).contains(kingSpot)) {
				isInCheck = true;
				break;
			}
		}
		return isInCheck;
	}

	private void createNewBoardCopy(PieceCollection<Location, Piece> pieces) {
		this.pieces.putAll(pieces);
	}

	public boolean isWhiteTurn() {
		return isWhiteTurn;
	}

	public void setWhiteTurn(boolean isWhiteTurn) {
		this.isWhiteTurn = isWhiteTurn;
	}

	public boolean isInCheck() {
		return isInCheck;
	}

	public void setInCheck(boolean isInCheck) {
		this.isInCheck = isInCheck;
	}

	public boolean isInCheckMate() {
		return isInCheckMate;
	}

	public void setInCheckMate(boolean isInCheckMate) {
		this.isInCheckMate = isInCheckMate;
	}

	public boolean isInStaleMate() {
		return isInStaleMate;
	}

	public void setInStaleMate(boolean isInStaleMate) {
		this.isInStaleMate = isInStaleMate;
	}

	public boolean isClient() {
		return isClient;
	}

	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

	public void redrawGUI() {
		GUIdisplay.repaint();
	}
}
