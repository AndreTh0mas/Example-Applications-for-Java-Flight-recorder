package AutoBoxingGC;

/**
 * The kind of object that will be in the map.
 */
final class MapObject {
	private final int id; // Do Integer id: To fix the issue

	MapObject(int id) {
		this.id = id;
	} // Here as well

int getId() {
		return id;
	}
}
