package pipe.jdbc;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import pipe.core.AbstractPipe;
import pipe.core.PipeProcessor;

public class ResultSetIn extends AbstractPipe implements PipeProcessor {
	private final ResultSet resultSet;

	public ResultSetIn(ResultSet resultSet) {
		super();
		this.resultSet = resultSet;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		ResultSetMetaData md = resultSet.getMetaData();
		int columnCount = md.getColumnCount();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		Object[] row;
		while (resultSet.next()) {
			row = new Object[columnCount];
			for (int i = 0; i < row.length; i++) {
				row[i] = resultSet.getObject(i + 1);
			}
			oos.writeObject(row);
		}
	}
}
