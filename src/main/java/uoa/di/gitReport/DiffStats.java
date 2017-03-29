package uoa.di.gitReport;

public class DiffStats {
	private int linesInserted = 0;
	private int linesDeleted = 0;
	private int linesUpdated = 0;
	private int linesChanged = 0;

	public int getLinesChanged() {
		return linesChanged;
	}

	public void setLinesChanged(int linesChanged) {
		this.linesChanged = linesChanged;
	}

	public int getLinesInserted() {
		return linesInserted;
	}

	public void setLinesInserted(int linesInserted) {
		this.linesInserted = linesInserted;
	}

	public int getLinesDeleted() {
		return linesDeleted;
	}

	public void setLinesDeleted(int linesDeleted) {
		this.linesDeleted = linesDeleted;
	}

	public int getLinesUpdated() {
		return linesUpdated;
	}

	public void setLinesUpdated(int linesUpdated) {
		this.linesUpdated = linesUpdated;
	}

}
