package defaultImplementations;

public interface A {
	public default void doStuff() {
		System.out.println("A's implementation of doStuff");
	}
}
