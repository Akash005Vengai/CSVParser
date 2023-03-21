package CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;


public class CSVParser implements Iterable<CSVRecord> {

	private File file;
	private String format;
	private boolean head;
	private List<CSVRecord> par = new LinkedList<>();
	
	public CSVParser() throws Exception{
		JFileChooser chose = new JFileChooser("./");
		chose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chose.showSaveDialog(chose);
        file =  chose.getSelectedFile();
        if(file==null)
        	throw new FileNotFoundException();
        format = " , ";
        head = true;
        read();
	}
	
	public CSVParser(String path) throws Exception {
		this(new File(path));
	}
	
	public CSVParser(File file) throws Exception{
		this(file,",",true);
	}
	
	public CSVParser(String path,boolean v) throws Exception {
		this(new File(path),v);
	}
	
	public CSVParser(String path,String format) throws Exception {
		this(new File(path),format);
	}
	
	public CSVParser(File file,String format) throws Exception {
		this(file,format,true);
	}
	
	public CSVParser(File file,boolean b) throws Exception {
		this(file,",",b);
	}
	
	public CSVParser(File file,String format,boolean head) throws Exception{
		if(file==null)
			throw new FileSystemNotFoundException();
		this.file = file;
		this.format = format;
		this.head = head;
		read();
	}
	
	public CSVParser(String path, String format, boolean b) throws Exception {
		this(new File(path),format,b);
	}

	private void read() throws Exception {
		BufferedReader br =  new BufferedReader(new FileReader(file));
		HashMap<String, Integer> map = new HashMap<>();
		int headercount = 0;
		if(head) {
			String header = br.readLine();
			int i =0;
			for(String head:header.split(format)) 
				map.put(head, i++);
			headercount = header.split(format).length;
		}
		String line ="";
		if(format !=",") {
			while((line=br.readLine())!=null)
				par.add(new CSVRecord(line.split(format), map));
		}else {
			while((line=br.readLine())!=null) {
				String[] arr = new String[headercount];
				boolean sp = false;
				String b = "";
				int index = 0;
				for(char a : line.toCharArray()) {
					if(a == '\"') {
						sp = !sp;
					}
					if(!sp&&a==','){
						arr[index++] = b;
						b = "";
						continue;
					}
					if(a=='\"') {
						continue;
					}
					b+=a;
				}
				if(b!=""){
					arr[index++] = b;
				}
				par.add(new CSVRecord(arr, map));
			}
		}
		br.close();
	}

	@Override
	public Iterator<CSVRecord> iterator() {
		return par.iterator();
	}
	
}
