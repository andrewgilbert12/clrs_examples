package defaultImplementations;

public interface B {
	public default void doStuff() {
		System.out.println("B's implementation of doStuff");
	}
}
