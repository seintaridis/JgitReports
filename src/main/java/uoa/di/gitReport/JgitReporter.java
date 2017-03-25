package uoa.di.gitReport;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
	 Ref head=null;
	 RevWalk walk=null;
	 RevCommit commit=null;
	 RevTree tree = null;
     Git git = null;
     String gitRepositoryPath=null;
    
	
	
	JgitReporter(String path){ 		
		try {
			this.gitRepositoryPath=path;
			repository = new FileRepositoryBuilder()
			        .setGitDir(new File(gitRepositoryPath+".git"))
			        .build();			
			head = repository.getRef("HEAD");	
			walk = new RevWalk(repository);
	        commit = walk.parseCommit(head.getObjectId());
	        tree = commit.getTree();
	        git = new Git(repository);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	int numberOfFiles() throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException{		
		  TreeWalk treeWalk = new TreeWalk(repository);
          treeWalk.addTree(tree);       
          treeWalk.setRecursive(true);         
          int numofFiles=0;         
          while (treeWalk.next()) {            
          	numofFiles++;               
          }
          return numofFiles;
	}
	
	
	int numberOfLines() throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException{
	    // now use a TreeWalk to iterate over all files in the Tree recursively
        // you can set Filters to narrow down the results if needed
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);      
        treeWalk.setRecursive(true);
        int numOfLines=0;        
        while (treeWalk.next()) {
        	try {
				numOfLines=numOfLines+Util.countLines(gitRepositoryPath+treeWalk.getPathString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}                      
        }
        return numOfLines;
        
        
	}
	
	
	int numberOfBranches() throws GitAPIException{
		List<Ref> branchList = git.branchList().setListMode( ListMode.ALL ).call();
		return branchList.size();
	}
	
	int numberOfTagss() throws GitAPIException{
		return git.tagList().call().size();
		
	}
	
	int numberOfAuthors() throws NoHeadException, GitAPIException, IOException{
        Iterable<RevCommit> commits = git.log().all().call();
        HashMap<String,PersonIdent> userMap = new HashMap<String, PersonIdent>();
        for (RevCommit com : commits){
        	userMap.put(com.getAuthorIdent().getEmailAddress(),com.getAuthorIdent());        	
        }
        return userMap.size();      
	}
	
	public void printBranches () throws RevisionSyntaxException, NoHeadException, MissingObjectException, IncorrectObjectTypeException, AmbiguousObjectException, GitAPIException, IOException{
		  System.out.println("List of branches");
          for(Ref branch :git.branchList().setListMode( ListMode.ALL ).call()){
          	String branchName = branch.getName();

              System.out.println("Commits of branch: " + branch.getName());
              System.out.println("-------------------------------------");

              Iterable<RevCommit> commits2 = git.log().all().call();

              for (RevCommit commit2 : commits2) {
                  boolean foundInThisBranch = false;

                  RevCommit targetCommit = walk.parseCommit(repository.resolve(
                          commit2.getName()));
                  for (Map.Entry<String, Ref> e : repository.getAllRefs().entrySet()) {
                      if (e.getKey().startsWith(Constants.R_HEADS)) {
                          if (walk.isMergedInto(targetCommit, walk.parseCommit(
                                  e.getValue().getObjectId()))) {
                              String foundInBranch = e.getValue().getName();
                              if (branchName.equals(foundInBranch)) {
                                  foundInThisBranch = true;
                                  break;
                              }
                          }
                      }
                  }

                  if (foundInThisBranch) {
                      System.out.println(commit2.getName());
                      System.out.println(commit2.getAuthorIdent().getName());
                      System.out.println(new Date(commit2.getCommitTime()));
                      System.out.println(commit2.getFullMessage());
                  }
              }
          }
		
		
	}

}
