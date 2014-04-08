package sw.wine.model;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import sw.wine.itf.IWine;
import sw.wine.itf.IWineCollection;

@XmlRootElement(name = "collection")
@XmlAccessorType(XmlAccessType.FIELD)
public class WineCollection implements IWineCollection {

	@XmlElement(name = "location")
	private Collection<Location> locations = new ArrayList<Location>();

	@Override
	public Collection<IWine> getWines() {
		Collection<IWine> wines = new ArrayList<IWine>();
		for (Location loc : locations) {
			wines.addAll(loc.getWines());
		}
		return wines;
	}

	public void addLocation(Location loc) {
		locations.add(loc);
	}
}
