package uoa.di.gitReport;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerConfig {
	private Configuration cfg=null;
	
	
	public Configuration getCfg() {
		return cfg;
	}


	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}


	public FreeMarkerConfig() {
		// TODO Auto-generated constructor stub
	
	
	 // Create your Configuration instance, and specify if up to what FreeMarker
    // version (here 2.3.25) do you want to apply the fixes that are not 100%
    // backward-compatible. See the Configuration JavaDoc for details.
    cfg = new Configuration(Configuration.VERSION_2_3_23);

    // Specify the source where the template files come from. Here I set a
    // plain directory for it, but non-file-system sources are possible too:
    try {
		cfg.setDirectoryForTemplateLoading(new File("src/"));
		
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

    // Set the preferred charset template files are stored in. UTF-8 is
    // a good choice in most applications:
    cfg.setDefaultEncoding("UTF-8");

    // Sets how errors will appear.
    // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
    cfg.setLogTemplateExceptions(false);
	}
}
