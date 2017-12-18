package effectiveJava;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayReflection {
	public static void main(String[] args){
	int[] intArr = new int[10];
	Integer[] IntArr = new Integer[10];
	String[] StrArr = new String[10];
	Integer integer = new Integer(10);
	System.out.println(intArr.getClass());
	System.out.println(IntArr.getClass().getSimpleName());
	System.out.println(StrArr.getClass());
	System.out.println(integer.getClass());
	genericSub((new Thread(){}).getClass()); // send anonymous class
	}

	private static <E> void genericSub(Class<E> clazz) {
		@SuppressWarnings("unchecked")
		E[] array = (E[]) Array.newInstance(clazz, 10);
		System.out.println(array.getClass());
		System.out.println(array.getClass().getSimpleName()); // simple name is just [] when class is anonymous
		System.out.println(Arrays.asList(array.getClass().getComponentType().getSuperclass().getInterfaces()));
	}
}
