import shared.locations.HexLocation;
import client.models.ResourceList;

/**
 * Port object contains data of hex location, direction, trade resource, and
 * ratio
 *
 */

public class Port {
	ResourceList resource;
	HexLocation location;
	String direction;
	int ratio;
}