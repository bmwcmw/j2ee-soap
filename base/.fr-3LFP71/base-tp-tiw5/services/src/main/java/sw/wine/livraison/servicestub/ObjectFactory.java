
package sw.wine.livraison.servicestub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LivraisonEffectuee_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "livraisonEffectuee");
    private final static QName _Confirmation_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "confirmation");
    private final static QName _CommandeInconnue_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "commandeInconnue");
    private final static QName _Nondisponible_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "nondisponible");
    private final static QName _DateLivraison_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "dateLivraison");
    private final static QName _LivraisonEffectueeResponse_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "livraisonEffectueeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Infos }
     * 
     */
    public Infos createInfos() {
        return new Infos();
    }

    /**
     * Create an instance of {@link Commande }
     * 
     */
    public Commande createCommande() {
        return new Commande();
    }

    /**
     * Create an instance of {@link VinT }
     * 
     */
    public VinT createVinT() {
        return new VinT();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "livraisonEffectuee")
    public JAXBElement<String> createLivraisonEffectuee(String value) {
        return new JAXBElement<String>(_LivraisonEffectuee_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "confirmation")
    public JAXBElement<String> createConfirmation(String value) {
        return new JAXBElement<String>(_Confirmation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "commandeInconnue")
    public JAXBElement<String> createCommandeInconnue(String value) {
        return new JAXBElement<String>(_CommandeInconnue_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "nondisponible")
    public JAXBElement<String> createNondisponible(String value) {
        return new JAXBElement<String>(_Nondisponible_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "dateLivraison")
    public JAXBElement<XMLGregorianCalendar> createDateLivraison(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateLivraison_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "livraisonEffectueeResponse")
    public JAXBElement<Boolean> createLivraisonEffectueeResponse(Boolean value) {
        return new JAXBElement<Boolean>(_LivraisonEffectueeResponse_QNAME, Boolean.class, null, value);
    }

}
