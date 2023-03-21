package CSVReader;

public class RecordNotFoundException extends Exception {

	private static final long serialVersionUID = 9220810422227735926L;

	public RecordNotFoundException() {
	}
	
	public RecordNotFoundException(String message) {
		super(message);
	}
	
}
