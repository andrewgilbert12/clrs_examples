package defaultImplementations;

public class DefaultImplementations implements A, B {

	@Override
	public void doStuff() {
		A.super.doStuff(); // new syntax in Java 8 for selecting the default implementation in Diamond problem situations
		B.super.doStuff();
	}

	public static void main(String[] args) {
		DefaultImplementations d = new DefaultImplementations();
		d.doStuff();
	}
}
