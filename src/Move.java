import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Move{

	private final Piece piece;
	private final Position toMoveTo;
	private final Position originalPosition;

	
	public Move(Piece p, Position toMoveTo) {
		piece = p.copy();
		this.toMoveTo = toMoveTo;
		this.originalPosition = p.getPosition();
	}
	
	public Move(String notation, Board b){
		//this constructor is only for reading in sequences of abstract moves.
		originalPosition = new Position(notation.substring(0, 2));
		toMoveTo = new Position(notation.substring(2));
		piece = b.getPieceAtPosition(originalPosition);
		
	}
	
	public Move(Move input) {
		this(input.getPiece(), input.getToMoveTo());
	}

	public String getNotation(){
		return ("" + getOriginalPosition() + getToMoveTo());
	}

	public static boolean isCapture(Move m, Board b){
		return !b.isPositionEmpty(m.getToMoveTo()) && (m.getPiece().getSide() != Values.getOpposingSide(b.getPieceAtPosition(m.getToMoveTo()).getSide()));
	}
	
	@Override
	public String toString() {
		if(piece!=null)
			return ("Move " + piece.side + " " + piece.getClass().getName() + " at position " + originalPosition + " to " + toMoveTo);
		else return "Piece is null....";
	}

	public Piece getPiece() {
		
		return piece==null ? null: piece.copy();
	}

	//public void setPiece(Piece piece) {
	//	this.piece = piece.copy();
	//}

	public Position getToMoveTo() {
		return toMoveTo;
	}

	//public void setToMoveTo(Position toMoveTo) {
	//	this.toMoveTo = toMoveTo;
	//}

	public Position getOriginalPosition() {
		return originalPosition;
	}

	//public void setOriginalPosition(Position originalPosition) {
	//	this.originalPosition = originalPosition;
	//}
	
	public Move getInverseMove(){
		Piece p = getPiece().copy();
		p.setPosition(getToMoveTo());
		return new Move(p, getOriginalPosition());
	}
	
	public static ArrayList<Move> orderMoves(ArrayList<Move> input, final Board board){
		Collections.sort(input, new Comparator<Move>(){

			@Override
			public int compare(Move arg0, Move arg1) {
				return Values.booleanCompare(Move.isCapture(arg0, board), Move.isCapture(arg1, board));
			}
		});
		return input;
	}
}
