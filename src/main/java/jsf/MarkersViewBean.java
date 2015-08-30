package jsf;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

public class MarkersViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private MapModel marker;

	@PostConstruct
	public void init() {
		marker = new DefaultMapModel();

		// Shared coordinates
		LatLng coord = new LatLng(50.0674428, 14.4104003);

		// Basic marker
		marker.addOverlay(new Marker(coord, "IMA s.r.o"));

	}

	public MapModel getMarker() {
		return marker;
	}
}