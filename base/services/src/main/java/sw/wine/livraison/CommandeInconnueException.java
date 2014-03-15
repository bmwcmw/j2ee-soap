package sw.wine.livraison;

public class CommandeInconnueException extends Exception {
	private static final long serialVersionUID = 1L;
	private String commandeId;

	public CommandeInconnueException(String commandeId) {
		super("La commande '" + commandeId + "' est inconnue");
		this.commandeId = commandeId;
	}

	public String getCommandeId() {
		return commandeId;
	}

	public void setCommandeId(String commandeId) {
		this.commandeId = commandeId;
	}
}
