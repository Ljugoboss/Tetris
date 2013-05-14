package server;
import java.io.Serializable;
import java.util.Random;

/**
 * 
 * @author Hugo Nissar
 * @version 1.0
 *
 */
public class Shape implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5101577890876338638L;

	public enum Tetrominoes { NoShape, ZShape, SShape, LineShape, 
		TShape, SquareShape, LShape, MirroredLShape, RowShape};

	private Tetrominoes pieceShape;
	private int coords[][];
	private int[][][] coordsTable;
	private int x = 4;
	private int y = 2;
	
	public Shape() {
		coords = new int[x][y];
		setShape(Tetrominoes.NoShape);
	}
	
	public void setShape(Tetrominoes shape) {
		coordsTable = new int[][][] {
	            { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
	            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
	            { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
	            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
	            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
	            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
	            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
	            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };

        for (int i = 0; i < x ; i++) {
            for (int j = 0; j < y; ++j) {
                coords[i][j] = coordsTable[shape.ordinal()][i][j];
            }
        }
        pieceShape = shape;
	}
	
	private void setX(int index, int x) { 
		coords[index][0] = x; 
	}
	private void setY(int index, int y) { 
		coords[index][1] = y;
	}
	public int x(int index) { 
		return coords[index][0]; 
	}
	public int y(int index) { 
		return coords[index][1]; 
	}
	public Tetrominoes getShape()  { 
		return pieceShape; 
	}
	
	public void setRandomShape() {
		Random r = new Random();
		int i = Math.abs(r.nextInt()) % 7 + 1;
		Tetrominoes[] values = Tetrominoes.values(); 
        setShape(values[i]);
	}
	public int minX() {
      int m = coords[0][0];
      for (int i=0; i < 4; i++) {
          m = Math.min(m, coords[i][0]);
      }
      return m;
    }


    public int minY() {
      int m = coords[0][1];
      for (int i=0; i < 4; i++) {
          m = Math.min(m, coords[i][1]);
      }
      return m;
    }

    public Shape rotateLeft() {
        if (pieceShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }

    public Shape rotateRight() {
        if (pieceShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        return result;
    }
}
