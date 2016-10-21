import java.util.Random;

public class Board {

    private boolean[][] field;
    private Random generator;

    /**
     * Create Board of definite size with randomly distributed life cells
     * 
     * @param maxX
     *            dimension in x-direction
     * @param maxY
     *            dimension in y-direction
     * @param seed
     *            seed for pseudo random number generator, null if system should
     *            choose one
     */
    public Board(int maxX, int maxY, Long seed) {
	field = new boolean[maxX][maxY];
	if (null == seed) {
	    generator = new Random();
	} else {
	    generator = new Random(seed);
	}
	for (int y = 0; y < maxY; y++) {
	    for (int x = 0; x < maxX; x++) {
		field[y][x] = generator.nextBoolean();
	    }
	}
    }

    /**
     * Create Board of template string where '_' means dead cell 'X' means life
     * cell '\n' means end of line e. g. "___\n_X_\n___" creates a 3x3 board
     * with just one life cell in center
     * 
     * @param template
     *            input string
     * @throws Exception
     *             thrown, when dimensions don't match
     */
    public Board(String template) throws Exception {
	String[] parts = template.split("\n");
	Integer xdim = null;
	boolean memAllocated = false;
	int partNo = 0;
	for (String part : parts) {
	    if (xdim == null) {
		xdim = Integer.valueOf(part.length());
	    } else {
		if (part.length() != xdim) {
		    throw new Exception("Board: dimension error");
		}
	    }
	    if (memAllocated == false) {
		field = new boolean[parts.length][xdim];
		memAllocated = true;
	    }
	    for (int pos = 0; pos < xdim; pos++) {
		if (part.charAt(pos) == '_') {
		    field[partNo][pos] = false;
		} else if (part.charAt(pos) == 'X') {
		    field[partNo][pos] = true;
		} else {
		    throw new Exception("Board: parse error");
		}
	    }
	    partNo++;
	}
    }

    /**
     * Creates a board from tailored field
     * 
     * @param field
     *            two-dimensional boolean field (true = life cell false = dead
     *            cell)
     */
    public Board(boolean[][] field) {
	int xDim = field[0].length;
	int yDim = field.length;
	this.field = new boolean[yDim][xDim];
	for (int y = 0; y < yDim; y++) {
	    for (int x = 0; x < xDim; x++) {
		this.field[y][x] = field[y][x];
	    }
	}
    }

    /** prints board as string */
    public String toString() {
	String result = "";
	for (int y = 0; y < field.length; y++) {
	    for (int x = 0; x < field[0].length; x++) {
		if (field[y][x] == false) {
		    result += "_";
		} else {
		    result += "X";
		}
	    }
	    result += "\n";
	}
	return result;
    }

    /**
     * compares two boards for equality
     * 
     * @return true when boards are equal, false else
     */
    public boolean equals(Object o) {
	if (!(o instanceof Board)) {
	    return false;
	}
	Board other = (Board) o;
	int otherDimX = other.field[0].length;
	int otherDimY = other.field.length;
	if (field[0].length != otherDimX || field.length != otherDimY) {
	    return false;
	}
	for (int y = 0; y < otherDimY; y++) {
	    for (int x = 0; x < otherDimX; x++) {
		if (field[y][x] != other.field[y][x]) {
		    return false;
		}
	    }
	}
	return true;
    }

    /** calculates next generation */
    public void calculateNext() {
	int xDim = field[0].length;
	int yDim = field.length;
	boolean[][] nextField = new boolean[yDim][xDim];
	for (int y = 0; y < yDim; y++) {
	    for (int x = 0; x < xDim; x++) {
		nextField[y][x] = field[y][x];
	    }
	}

	for (int y = 0; y < field.length; y++) {
	    for (int x = 0; x < field[0].length; x++) {
		int neighbourCount = calculateNeighboursForPos(x, y);
		if (field[y][x] == true) {
		    // live cell
		    if (neighbourCount < 2) {
			// rule 1: underpopulation
			nextField[y][x] = false;
		    } else if (neighbourCount > 3) {
			// rule 2: overcrowding
			nextField[y][x] = false;
		    }
		    // rule 3: everyday life
		    // nothing to do for alive cells
		} else {
		    // dead cell
		    if (neighbourCount == 3) {
			// rule 4: reproduction
			nextField[y][x] = true;
		    }
		}
	    }
	}
	field = nextField;
    }

    /**
     * calculates number of neighbours at given position
     * 
     * @param xPos
     *            the position of cell in x-dimension
     * @param yPos
     *            the position of cell in y-dimension
     * @return number of neighbours
     */
    private int calculateNeighboursForPos(int xPos, int yPos) {
	int result = 0;

	if (xPos < 0 || xPos >= field[0].length || yPos < 0
		|| yPos >= field.length) {
	    result = 0;
	} else {
	    for (int relY = -1; relY <= 1; relY++) {
		for (int relX = -1; relX <= 1; relX++) {
		    int nborX = xPos + relX;
		    int nborY = yPos + relY;
		    if (0 == relX && 0 == relY || nborX < 0
			    || nborX >= field[0].length || nborY < 0
			    || nborY >= field.length) {
			continue;
		    }
		    // System.out.println("field[" + nborX + "][" + nborY +
		    // "]");
		    if (true == field[nborY][nborX]) {
			result++;
		    }
		}
	    }
	}

	return result;
    }

    /** prints board containing neighbours count */
    public String getNeighborBoard() {
	String neighborBoard = "";
	for (int y = 0; y < field.length; y++) {
	    if (y != 0) {
		neighborBoard += "\n";
	    }
	    for (int x = 0; x < field[0].length; x++) {
		neighborBoard += calculateNeighboursForPos(x, y);
	    }
	}
	return neighborBoard;
    }

    /** calculates field of ints containing number of neighbours */
    public int[][] calculateNeighborBoard() {
	int[][] result = new int[field.length][field[0].length];

	for (int y = 0; y < field.length; y++) {
	    for (int x = 0; x < field[0].length; x++) {
		result[y][x] = calculateNeighboursForPos(x, y);
	    }
	}

	return result;
    }

    /** getter for x dimension */
    public int getDimX() {
	return field[0].length;
    }

    /** getter for y dimension */
    public int getDimY() {
	return field.length;
    }

    /**
     * calculates, if cell at given position is alive
     * 
     * @param cellPosX
     *            x position
     * @param cellPosY
     *            y position
     * @return true if alive, false if dead
     */
    public boolean cellAlive(int cellPosX, int cellPosY) {
	return field[cellPosY][cellPosX];
    }
}
