package CSVReader;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CSVRecord implements Serializable,Cloneable, Iterable<String>{

	private static final long serialVersionUID = -2704707764717943835L;
	private String[] values;
	private Map<String,Integer> mapping;
	
	
	public CSVRecord(String[] values, Map<String, Integer> mapping) {
		this.values = values;
		this.mapping = mapping==null?new HashMap<>():mapping;
	}
	
	public String get(String column_name)throws RecordNotFoundException{
		if(mapping==null||mapping.get(column_name)==null) {
			throw new RecordNotFoundException("No header mapping was specified, the record values can't be accessed by "+column_name+" "+mapping.keySet());
		}
		return get(mapping.get(column_name));
	}
	
	public String get(int index)throws RecordNotFoundException{
		if(index<0||index>=size())
			throw new RecordNotFoundException("No header mapping was specified "+mapping.keySet());
		return values[index];
	}
	
	public int size() {
		return values.length;
	}
	
	public String[] values() {
		return values;
	}

	@Override
	public Iterator<String> iterator() {
		return Arrays.asList(values).iterator();
	}

	@Override
	public String toString() {
		return "CSVRecord [values="+Arrays.toString(values)+", mapping=" + mapping + "]";
	}
	
}
