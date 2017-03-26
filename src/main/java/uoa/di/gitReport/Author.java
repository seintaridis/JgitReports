package uoa.di.gitReport;

public class Author {
	String name;
	int numberOfCommits;
	String commitPercentage;

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
