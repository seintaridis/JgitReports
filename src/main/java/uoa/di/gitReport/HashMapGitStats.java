package uoa.di.gitReport;

import java.util.HashMap;

public class HashMapGitStats extends HashMap<String, Integer> {

	/**
	 * add current value to the previous one for the specific key
	 * 
	 **/
	public void addValue(String key, Integer value) {
		Integer numbertoAdd = this.get(key);
		if (numbertoAdd == null)
			numbertoAdd = 0;
		numbertoAdd += value;
		this.put(key, numbertoAdd);

	}

}
