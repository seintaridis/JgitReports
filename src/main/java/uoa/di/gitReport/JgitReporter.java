package uoa.di.gitReport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

public class JgitReporter {
	Repository repository = null;
	Ref head = null;
	RevWalk walk = null;
	RevCommit commit = null;
	RevTree tree = null;
	Git git = null;
	String gitRepositoryPath = null;
	HashMap<String, ArrayList<CommitData>> branchCommitsMap;
	int numberOfCommits;

	public int getNumberOfCommits() {
		return numberOfCommits;
	}

	JgitReporter(String path) {
		try {
			this.gitRepositoryPath = path;
			repository = new FileRepositoryBuilder().setGitDir(new File(gitRepositoryPath + ".git")).build();
			head = repository.getRef("HEAD");

			walk = new RevWalk(repository);
			commit = walk.parseCommit(head.getObjectId());
			tree = commit.getTree();
			git = new Git(repository);
			branchCommitsMap = getBranchCommitsMap();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RevisionSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	Integer numberOfFiles()
			throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException {
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		int numofFiles = 0;
		while (treeWalk.next()) {
			numofFiles++;
		}
		return numofFiles;
	}

	Integer numberOfLines()
			throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException {
		// now use a TreeWalk to iterate over all files in the Tree recursively
		// you can set Filters to narrow down the results if needed
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		int numOfLines = 0;
		while (treeWalk.next()) {
			try {
				numOfLines = numOfLines + Util.countLines(gitRepositoryPath + treeWalk.getPathString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return numOfLines;

	}

	Integer numberOfBranches() throws GitAPIException {
		List<Ref> branchList = git.branchList().setListMode(ListMode.ALL).call();
		return branchList.size();
	}

	Integer numberOfTags() throws GitAPIException {
		return git.tagList().call().size();

	}

	Integer numberOfAuthors() throws NoHeadException, GitAPIException, IOException {
		Iterable<RevCommit> commits = git.log().all().call();
		HashMap<String, PersonIdent> userMap = new HashMap<String, PersonIdent>();
		for (RevCommit com : commits) {
			userMap.put(com.getAuthorIdent().getEmailAddress(), com.getAuthorIdent());
		}
		return userMap.size();
	}

	public List<String> getBranchesList() throws GitAPIException {
		List<String> branchList = new ArrayList<String>();

		for (Ref branch : git.branchList().setListMode(ListMode.ALL).call()) {
			branchList.add(branch.getName());
		}
		return branchList;

	}

	public HashMap<String, ArrayList<CommitData>> getBranchCommitsMap()
			throws RevisionSyntaxException, NoHeadException, MissingObjectException, IncorrectObjectTypeException,
			AmbiguousObjectException, GitAPIException, IOException {

		HashMap<String, ArrayList<CommitData>> branchMap = new HashMap<String, ArrayList<CommitData>>();
		System.out.println("List of branches");
		for (Ref branch : git.branchList().setListMode(ListMode.ALL).call()) {
			String branchName = branch.getName();

			System.out.println("Commits of branch: " + branch.getName());
			System.out.println("-------------------------------------");

			Iterable<RevCommit> commits2 = git.log().all().call();
			ArrayList<CommitData> listCommits = new ArrayList<CommitData>();
			for (RevCommit commit2 : commits2) {
				boolean foundInThisBranch = false;

				RevCommit targetCommit = walk.parseCommit(repository.resolve(commit2.getName()));
				for (Map.Entry<String, Ref> e : repository.getAllRefs().entrySet()) {
					if (e.getKey().startsWith(Constants.R_HEADS)) {
						if (walk.isMergedInto(targetCommit, walk.parseCommit(e.getValue().getObjectId()))) {
							String foundInBranch = e.getValue().getName();
							if (branchName.equals(foundInBranch)) {
								foundInThisBranch = true;
								break;
							}
						}
					}
				}

				if (foundInThisBranch) {
					CommitData commitData = new CommitData();
					commitData.setId(commit2.getName());
					commitData.setMessage(commit2.getFullMessage());
					commitData.setAuthor(commit2.getAuthorIdent().getName());
					int sec = commit.getCommitTime();
					Long longSec = new Long(sec);
					Long millisec = longSec * 1000;
					commitData.setDate(new Date(millisec).toString());
					listCommits.add(commitData);

				}
			}
			numberOfCommits += listCommits.size();
			branchMap.put(branchName, listCommits);
		}
		return branchMap;

	}

	public ArrayList<Author> getAuthorStats() throws RevisionSyntaxException, NoHeadException, MissingObjectException,
			IncorrectObjectTypeException, AmbiguousObjectException, GitAPIException, IOException {
		HashMap<String, Integer> commitsPerAuthorMap = commitsPerAuthor();
		ArrayList<Author> authorList = new ArrayList<Author>();
		Iterator it = commitsPerAuthorMap.entrySet().iterator();
		while (it.hasNext()) {
			Author author = new Author();
			Map.Entry pair = (Map.Entry) it.next();
			author.setName(pair.getKey().toString());
			author.setNumberOfCommits(Integer.parseInt(pair.getValue().toString()));
			Double numAuthorCommits = 1.0 * (Integer) pair.getValue();
			Double percentage = (numAuthorCommits / numberOfCommits) * 100;
			author.setCommitPercentage(percentage.toString());

			authorList.add(author);
			it.remove(); // avoids a ConcurrentModificationException
		}

		return authorList;

	}

	public ArrayList<BranchStats> getBranchStats()
			throws RevisionSyntaxException, NoHeadException, MissingObjectException, IncorrectObjectTypeException,
			AmbiguousObjectException, GitAPIException, IOException {
		HashMap<String, Integer> commitsPerAuthorMap = commitsPerAuthor();
		ArrayList<BranchStats> authorList = new ArrayList<BranchStats>();
		Iterator it = commitsPerBranch().entrySet().iterator();
		while (it.hasNext()) {
			BranchStats branchStat = new BranchStats();
			Map.Entry pair = (Map.Entry) it.next();
			branchStat.setName(pair.getKey().toString());
			Double numBranchCommits = 1.0 * (Integer) pair.getValue();
			Double percentage = (numBranchCommits / numberOfCommits) * 100;
			branchStat.setCommitPercentage(percentage.toString());

			authorList.add(branchStat);
			it.remove(); // avoids a ConcurrentModificationException
		}

		return authorList;

	}

	public HashMap<String, Integer> commitsPerAuthor()
			throws RevisionSyntaxException, NoHeadException, MissingObjectException, IncorrectObjectTypeException,
			AmbiguousObjectException, GitAPIException, IOException {
		HashMap<String, Integer> authorMap = new HashMap<String, Integer>();
		for (String branchName : getBranchesList()) {
			ArrayList<CommitData> commits = branchCommitsMap.get(branchName);
			for (CommitData commit : commits) {
				Integer value = authorMap.get(commit.getAuthor());
				if (value == null)
					value = 0;
				value++;
				authorMap.put(commit.getAuthor(), value);

			}
		}
		return authorMap;

	}

	public HashMap<String, Integer> commitsPerBranch()
			throws RevisionSyntaxException, NoHeadException, MissingObjectException, IncorrectObjectTypeException,
			AmbiguousObjectException, GitAPIException, IOException {
		HashMap<String, Integer> branchMap = new HashMap<String, Integer>();
		for (String branchName : getBranchesList()) {
			ArrayList<CommitData> commits = branchCommitsMap.get(branchName);
			branchMap.put(branchName, commits.size());
		}
		return branchMap;

	}

	public HashMap<String, HashMap<String, Integer>> commitsPerBranchPerAuthor()
			throws RevisionSyntaxException, NoHeadException, MissingObjectException, IncorrectObjectTypeException,
			AmbiguousObjectException, GitAPIException, IOException {
		HashMap<String, HashMap<String, Integer>> commitsPerBranchPerAuthor = new HashMap<String, HashMap<String, Integer>>();
		for (String branchName : getBranchesList()) {
			HashMap<String, Integer> commitsPerAuthor = new HashMap<String, Integer>();
			ArrayList<CommitData> commits = branchCommitsMap.get(branchName);
			for (CommitData commit : commits) {
				Integer value = commitsPerAuthor.get(commit.getAuthor());
				if (value == null)
					value = 0;
				value++;
				commitsPerAuthor.put(commit.getAuthor(), value);

			}
			commitsPerBranchPerAuthor.put(branchName, commitsPerAuthor);
		}
		return commitsPerBranchPerAuthor;

	}

}
