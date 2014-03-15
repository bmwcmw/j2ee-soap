package sw.wine.livraison;

public class CommandeInfos {

	private double prix;
	
	private String cmdId;
	
	public CommandeInfos(double prix, String cmdId) {
		super();
		this.prix = prix;
		this.cmdId = cmdId;
	}
	
//	public CommandeInfos(String cmdId, double prix) {
//		super();
//		this.prix = prix;
//		this.cmdId = cmdId;
//	}

	public CommandeInfos() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmdId == null) ? 0 : cmdId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(prix);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandeInfos other = (CommandeInfos) obj;
		if (cmdId == null) {
			if (other.cmdId != null)
				return false;
		} else if (!cmdId.equals(other.cmdId))
			return false;
		if (Double.doubleToLongBits(prix) != Double
				.doubleToLongBits(other.prix))
			return false;
		return true;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getCmdId() {
		return cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}

	public String getId() {
		return cmdId;
	}

	public void setId(String id) {
		this.cmdId = id;
	}
	
}
