package uoa.di.gitReport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        String path = "C:" + File.separator + "hello" + File.separator + "auctioneer" + File.separator;
        
        
        JgitReporter gitReporter=new JgitReporter(path);
        try {
        	System.out.println("Number of files");
        	System.out.println(gitReporter.numberOfFiles());
        	System.out.println("Number of lines");
        	System.out.println(gitReporter.numberOfLines());
        	System.out.println("Number of branches");
        	System.out.println(gitReporter.numberOfBranches());
        	System.out.println("Number of tags");
        	System.out.println(gitReporter.numberOfTagss());
        	System.out.println("Number of authors");
        	System.out.println(gitReporter.numberOfAuthors());
        	
        	gitReporter.printBranches();
		} catch (MissingObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncorrectObjectTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CorruptObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        /*
        
        
        
        

        // Open an existing repository
        try {
            Repository repository = new FileRepositoryBuilder()
                    .setGitDir(new File(path))
                    .build();
        ///.out.println(existingRepo.getDirectory().listFiles().length);
        
            
            Ref head = repository.getRef("HEAD");
            

            // a RevWalk allows to walk over commits based on some filtering that is defined
            RevWalk walk = new RevWalk(repository);
            
          

            RevCommit commit = walk.parseCommit(head.getObjectId());
           
           
            
            RevTree tree = commit.getTree();
            Git git = new Git(repository);
            
            System.out.println("Having tree: " + tree);

            // now use a TreeWalk to iterate over all files in the Tree recursively
            // you can set Filters to narrow down the results if needed
            TreeWalk treeWalk = new TreeWalk(repository);
            treeWalk.addTree(tree);
            
            treeWalk.setRecursive(true);
            
            int numofFiles=0;
            int numOfLines=0;
            
            
            while (treeWalk.next()) {
            	numOfLines=numOfLines+countLines(path1+treeWalk.getPathString());
            
            	numofFiles++;               
            }
            
            
            List<Ref> branchList = new Git(repository).branchList().setListMode( ListMode.ALL ).call();
            
            System.out.println("number of files are");
            System.out.println(numofFiles);
            System.out.println("number of lines are");
            System.out.println(numOfLines);
            System.out.println("number of branches are");  // the remote branhes also
            System.out.println(branchList.size());
            System.out.println("number of tags are");
            System.out.println(new Git(repository).tagList().call().size());
          //  new Git(repository).log().all()
            
            Iterable<RevCommit> commits = new Git(repository).log().all().call();
            HashMap<String,PersonIdent> userMap = new HashMap<String, PersonIdent>();
            for (RevCommit com : commits){
            	userMap.put(com.getAuthorIdent().getEmailAddress(),com.getAuthorIdent());
            	
            	
            }
            System.out.println("number of authors");
            
            
            
            System.out.println(userMap.size());
            
            System.out.println("List of branches");
            for(Ref branch :branchList){
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
            
            

            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
   
        
        
    }
    
    
    
    
    
    public static void branchArray(){
    	System.out.println("List of branches");
    	
    }
    
    
    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
    
    
    */
}
    
}
