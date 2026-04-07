package pack;

import pack.File;
import pack.Coordinates;
import piece.CoordinatesShift;

public class Coordinates {
	public final File file;
	public final Integer rank;

	public Coordinates(File file, Integer rank) {

		this.file = file;
		this.rank = rank;
	}

	public Coordinates shift(CoordinatesShift shift) {
		return new Coordinates(File.values()[this.file.ordinal() + shift.fileShift], this.rank + shift.rankShift);
		// so difficult for File because i have to replace enum
	}

	public boolean canShift(CoordinatesShift shift) {
		int f = file.ordinal() + shift.fileShift;
		int r = rank + shift.rankShift;

		if ((f < 0) || (f > 8))
			return false;
		if ((r < 1) || (r > 9))
			return false;

		return true;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Coordinates that = (Coordinates) obj;

		if (file != that.file)
			return false;
		return rank.equals(that.rank);
	}

	@Override
	public int hashCode() {
		int result = file.hashCode();
		result = 31 * result + rank.hashCode();
		return result;
	}

}