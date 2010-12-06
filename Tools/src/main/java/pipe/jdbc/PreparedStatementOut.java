package pipe.jdbc;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Iterator;

import pipe.core.AbstractPipe;
import pipe.core.PipeProcessor;

public class PreparedStatementOut extends AbstractPipe implements PipeProcessor {
	private final PreparedStatement preparedStatement;
	private int commitInterval = -1;

	public PreparedStatementOut(PreparedStatement preparedStatement) {
		super();
		this.preparedStatement = preparedStatement;
	}

	public PreparedStatementOut(PreparedStatement preparedStatement,
			int commitInterval) {
		super();
		this.preparedStatement = preparedStatement;
		this.commitInterval = commitInterval;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(is);
		Object obj;
		int count = 0;
		while ((obj = ois.readObject()) != null) {
			if (obj instanceof Object[]) {
				Object[] row = (Object[]) obj;
				for (int i = 0; i < row.length; i++) {
					this.preparedStatement.setObject(i + 1, row[i]);
				}
			} else if (obj instanceof Collection) {
				Collection<?> row = (Collection<?>) obj;
				int i = 1;
				for (Iterator<?> iterator = row.iterator(); iterator.hasNext(); i++) {
					this.preparedStatement.setObject(i, iterator.next());
				}
			} else {
				this.preparedStatement.setObject(1, obj);
			}
			this.preparedStatement.addBatch();
			if (this.commitInterval > 0 && ++count % this.commitInterval == 0) {
				this.preparedStatement.executeBatch();
				this.preparedStatement.getConnection().commit();
			}
		}

		if (this.commitInterval > 0) {
			if (count % this.commitInterval != 0) {
				this.preparedStatement.executeBatch();
				this.preparedStatement.getConnection().commit();
			}
		} else {
			this.preparedStatement.executeBatch();
		}
	}
}
