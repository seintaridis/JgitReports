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
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class App {
	public static void main(String[] args) {
		String repositoryPath = args[0];
		String reportPath = args[1];
		try {
			Configuration cfg = ConfigurationUtil.getConfiguration();
			Template indexTemplate = cfg.getTemplate("indexTemplate.ftl");
			Template branchTemplate = cfg.getTemplate("branchTemplate.ftl");
			JgitReporter gitReporter = new JgitReporter(repositoryPath + File.separator);
			String name = Util.getNameOfPath(repositoryPath);

			// Build the data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("repositoryName", name);
			data.put("message", "GitReports");
			Map<String, Object> dataBranch = new HashMap<String, Object>();

			// List parsing
			List<String> stats = new ArrayList<String>();

			stats.add("Number of files: " + gitReporter.numberOfFiles().toString());
			stats.add("Number of lines: " + gitReporter.numberOfLines().toString());
			stats.add("Number of branches: " + gitReporter.numberOfBranches().toString());
			stats.add("Number of tags: " + gitReporter.numberOfTags().toString());
			stats.add("Number of authors: " + gitReporter.numberOfAuthors().toString());

			HashMap<String, ArrayList<CommitData>> branchCommitsMap = gitReporter.branchCommitsMap;
			for (String branchName : gitReporter.getBranchesList()) {
				ArrayList<CommitData> commits = branchCommitsMap.get(branchName);
				File file1 = new File(reportPath + File.separator + branchName + ".html");
				file1.getParentFile().mkdirs();
				FileWriter writer = new FileWriter(file1);
				dataBranch.put("message", branchName);
				dataBranch.put("commits", commits);
				branchTemplate.process(dataBranch, writer);
				writer.flush();
				writer.close();
			}
			stats.add("Number of commits: " + gitReporter.getNumberOfCommits());

			data.put("stats", stats);
			data.put("branches", gitReporter.getBranchesList());
			data.put("authors", gitReporter.getAuthorStats());
			data.put("branchStats", gitReporter.getBranchStats());
			data.put("branchAuhors", gitReporter.getAuthorCommitsPerBranch());

			// Console output
			Writer out = new OutputStreamWriter(System.out);
			indexTemplate.process(data, out);
			out.flush();

			// File output
			Writer file = new FileWriter(new File(reportPath + "/Report.html"));
			indexTemplate.process(data, file);
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
