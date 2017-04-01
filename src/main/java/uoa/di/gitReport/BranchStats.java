package uoa.di.gitReport;

import java.util.ArrayList;

public class BranchStats {
	String name;
	String commitPercentage;
	int numOfCommits;
	BranchDates branchDates;

	public BranchDates getBranchDates() {
		return branchDates;
	}

	public void setBranchDates(BranchDates branchDates) {
		this.branchDates = branchDates;
	}

	public int getNumOfCommits() {
		return numOfCommits;
	}

	public void setNumOfCommits(int numOfCommits) {
		this.numOfCommits = numOfCommits;
	}

	ArrayList<Author> listOfAthors;

	public ArrayList<Author> getListOfAthors() {
		return listOfAthors;
	}

	public void setListOfAthors(ArrayList<Author> listOfAthors) {
		this.listOfAthors = listOfAthors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommitPercentage() {
		return commitPercentage;
	}

	public void setCommitPercentage(String commitPercentage) {
		this.commitPercentage = commitPercentage;
	}

}
