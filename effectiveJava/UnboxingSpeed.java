package effectiveJava;


public class UnboxingSpeed {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		Long sum = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println("Using Long takes " + (System.currentTimeMillis() - start)/1000. + " sec");

		start = System.currentTimeMillis();

		long suml = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			suml += i;
		}
		System.out.println("Using long takes " + (System.currentTimeMillis() - start)/1000. + " sec");
	}
}
