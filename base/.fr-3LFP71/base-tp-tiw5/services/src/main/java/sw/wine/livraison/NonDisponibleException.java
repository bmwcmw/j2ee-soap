package sw.wine.livraison;

public class NonDisponibleException extends Exception {

	private static final long serialVersionUID = 1L;
	private String referenceVin;

	public NonDisponibleException(Article article) {
		super("Le vin " + article.getReferenceVin() + " n'est pas disponible(plus de produit ou prix inconnu)");
		this.referenceVin = article.getReferenceVin();
	}

	public String getReferenceVin() {
		return referenceVin;
	}

	public void setReferenceVin(String referenceVin) {
		this.referenceVin = referenceVin;
	}

}
