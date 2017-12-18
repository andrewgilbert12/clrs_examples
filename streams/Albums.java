package streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

// https://docs.oracle.com/javase/tutorial/collections/streams/QandE/answers.html

public class Albums {
	private static class Album {
		public List<Track> tracks;
		public String name;
		
		public Album(List<Track> tracks, String name) {
			this.tracks = tracks;
			this.name = name;
		}
	}
	
	private static class Track {
		public int rating;
		
		public Track(int rating) {
			this.rating = rating;
		}
	}
	
	public static void main(String[] args) {
		List<Album> albums = new ArrayList<>();
		
		List<Album> sortedFavs =
				albums.stream()
				      .filter(a -> a.tracks.stream().anyMatch(t -> (t.rating >= 4)))
				      .sorted(Comparator.comparing(a -> a.name))
				      .collect(Collectors.toList());
	}
}
