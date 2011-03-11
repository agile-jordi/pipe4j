package pipe4j.core;

/**
 * When declared as connector for the head and tail of a pipeline. Example:
 * FileIn will declare input as Null, as the input will be a file, and not the
 * output from a previous pipe.
 * 
 * Also used in object pipes to flag that no more objects will be produced.
 * 
 * @author bbennett
 */
public final class Null {
	/**
	 * Singleton instance.
	 */
	public static final Null INSTANCE = new Null();

	private Null() {
	}
}
