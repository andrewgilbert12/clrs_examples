package effectiveJava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationTest {
	private static class Person implements java.io.Serializable {
		public String name;
		public int id;
		public transient int secret; // will not be serialized

		Person(String name, int id, int secret){
			this.name = name;
			this.id = id;
			this.secret = secret;
		}
	}


	public static void main(String[] args){
		Person p = new Person("test", 123, 12345);
		
		try {
			FileOutputStream f = new FileOutputStream("person.ser");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(p);
		} catch(IOException e){
			System.out.println("File output failed! " + e);
		}
		
		try {
			FileInputStream f = new FileInputStream("person.ser");
			ObjectInputStream o = new ObjectInputStream(f);
			Person p2 = (Person) o.readObject();
			
			System.out.println("loaded person:");
			System.out.println("name: " + p2.name);
			System.out.println("id: " + p2.id);
			System.out.println("secret: " + p2.secret);
		} catch(IOException e){
			System.out.println("File output failed! " + e);
		} catch(ClassNotFoundException e){
			System.out.println("Class not found! " + e);
		}
	}
}
