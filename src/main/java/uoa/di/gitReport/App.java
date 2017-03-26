package uoa.di.gitReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		String path = "C:" + File.separator + "hello" + File.separator + "auctioneer" + File.separator;

		try {
			Template template = new FreeMarkerConfig().getCfg().getTemplate("helloworld.ftl");
			Template branchTemplate = new FreeMarkerConfig().getCfg().getTemplate("branchTemplate.ftl");

			JgitReporter gitReporter = new JgitReporter(path);

			// Build the data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("message", "GitReports");
			Map<String, Object> dataBranch = new HashMap<String, Object>();

			// List parsing
			List<String> stats = new ArrayList<String>();

			stats.add("Number of files: " + gitReporter.numberOfFiles().toString());
			stats.add("Number of lines: " + gitReporter.numberOfLines().toString());
			stats.add("Number of branches: " + gitReporter.numberOfBranches().toString());
			stats.add("Number of tags: " + gitReporter.numberOfTags().toString());
			stats.add("Number of authors: " + gitReporter.numberOfAuthors().toString());

			int numberOfCommits = 0;

			HashMap<String, ArrayList<CommitData>> branchCommitsMap = gitReporter.getBranchCommitsMap();
			for (String branchName : gitReporter.getBranchesList()) {
				ArrayList<CommitData> commits = branchCommitsMap.get(branchName);
				HashMap<String, Integer> authorMap = gitReporter.commitsPerAuthor();
				HashMap<String, HashMap<String, Integer>> commitsperbranchperauthor = gitReporter
						.commitsPerBranchPerAuthor();

				File file1 = new File("C:/hello/" + branchName + ".html");
				file1.getParentFile().mkdirs();
				FileWriter writer = new FileWriter(file1);
				dataBranch.put("message", branchName);
				dataBranch.put("commits", commits);
				branchTemplate.process(dataBranch, writer);
				writer.flush();
				writer.close();
			}

			stats.add("Number of commits: " + numberOfCommits);

			data.put("stats", stats);
			data.put("branches", gitReporter.getBranchesList());
			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(data, out);
			out.flush();

			// File output
			Writer file = new FileWriter(new File("C:/hello/FTL_helloworld.html"));
			template.process(data, file);

			file.flush();
			file.close();

		} catch (TemplateNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
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
}
