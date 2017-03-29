package uoa.di.gitReport;

public class Author {
	String name;
	int numberOfCommits;
	String commitPercentage;
	String commitsPerDay;
	String commitsPerMonth;
	String commitsPerYear;
	Integer modifiedLines;
	String modifiedLinesAverage;
	String insertedLinesAverage;
	String deletedLinesAverage;

	public String getInsertedLinesAverage() {
		return insertedLinesAverage;
	}

	public void setInsertedLinesAverage(String insertedLinesAverage) {
		this.insertedLinesAverage = insertedLinesAverage;
	}

	public String getDeletedLinesAverage() {
		return deletedLinesAverage;
	}

	public void setDeletedLinesAverage(String deletedLinesAverage) {
		this.deletedLinesAverage = deletedLinesAverage;
	}

	public String getModifiedLinesAverage() {
		return modifiedLinesAverage;
	}

	public void setModifiedLinesAverage(String modifiedLinesAverage) {
		this.modifiedLinesAverage = modifiedLinesAverage;
	}

	public Integer getModifiedLines() {
		return modifiedLines;
	}

	public void setModifiedLines(Integer modifiedLines) {
		this.modifiedLines = modifiedLines;
	}

	public String getCommitsPerDay() {
		return commitsPerDay;
	}

	public void setCommitsPerDay(String commitsPerDay) {
		this.commitsPerDay = commitsPerDay;
	}

	public String getCommitsPerMonth() {
		return commitsPerMonth;
	}

	public void setCommitsPerMonth(String commmitsPerMonth) {
		this.commitsPerMonth = commmitsPerMonth;
	}

	public String getCommitsPerYear() {
		return commitsPerYear;
	}

	public void setCommitsPerYear(String commitsPerYear) {
		this.commitsPerYear = commitsPerYear;
	}

	public String getName() {
		return name;
	}

	public String getCommitPercentage() {
		return commitPercentage;
	}

	public void setCommitPercentage(String commitPercentage) {
		this.commitPercentage = commitPercentage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfCommits() {
		return numberOfCommits;
	}

	public void setNumberOfCommits(int numberOfCommits) {
		this.numberOfCommits = numberOfCommits;
	}

}
