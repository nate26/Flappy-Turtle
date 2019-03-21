package tools;

public class DataException extends RuntimeException {
	private static final long serialVersionUID = 8141739585562375582L;

	public DataException(String error) {
		super(error);
	}
}
