//package sw.wine.livraison.test;
//
//import java.net.MalformedURLException;
//
//import javax.xml.bind.JAXBException;
//import javax.xml.namespace.QName;
//import javax.xml.soap.MessageFactory;
//import javax.xml.soap.SOAPBody;
//import javax.xml.soap.SOAPBodyElement;
//import javax.xml.soap.SOAPElement;
//import javax.xml.soap.SOAPEnvelope;
//import javax.xml.soap.SOAPException;
//import javax.xml.soap.SOAPMessage;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.transform.TransformerException;
//
//import sw.wine.itf.DAOException;
//import sw.wine.livraison.servlet.JAXBTools;
//
//import junit.framework.TestCase;
//
//public class TestJAXBTools extends TestCase {
//	protected void setUp() throws Exception {
//		super.setUp();
//	}
//
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//
//	public void testCommande() throws SOAPException, MalformedURLException, XMLStreamException, JAXBException, TransformerException, DAOException {
//		
//		MessageFactory factory = MessageFactory.newInstance();
//		
//		SOAPMessage commandeMsg = factory.createMessage();
//		SOAPEnvelope envelope = commandeMsg.getSOAPPart().getEnvelope();
//		String livNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons";
//		envelope.addNamespaceDeclaration("liv", livNS);
//		SOAPBody body = commandeMsg.getSOAPBody();
//		QName opName = new QName(livNS, "commande", "liv");
//		SOAPBodyElement opElement = body.addBodyElement(opName);
//		QName locationName = new QName("vin");
//		SOAPElement locationElement1 = opElement.addChildElement(locationName);
//			QName referenceName = new QName("reference");
//			SOAPElement countryElement1 = locationElement1.addChildElement(referenceName);
//			countryElement1.addTextNode("cmw");
//			QName quantiteName = new QName("quantite");
//			SOAPElement regionElement1 = locationElement1.addChildElement(quantiteName);
//			regionElement1.addTextNode("1");
//		SOAPElement locationElement2 = opElement.addChildElement(locationName);
//			SOAPElement countryElement2 = locationElement2.addChildElement(referenceName);
//			countryElement2.addTextNode("cmw2013");
//			SOAPElement regionElement2 = locationElement2.addChildElement(quantiteName);
//			regionElement2.addTextNode("1");
//			
//		System.out.println("Message à traiter : \n "+SaajHelper.getSOAPMessageAsString(commandeMsg));
//		//SOAPMessage commandeRep = JAXBTools.commande(factory,commandeMsg.getSOAPPart().getEnvelope().getBody());
//		SOAPMessage commandeRep = JAXBTools.traiterSOAPMessage(factory,commandeMsg);
//		System.out.println("Message reçu : \n "+SaajHelper.getSOAPMessageAsString(commandeRep));
//	}
//	
////	public void testConfirmation() throws SOAPException, MalformedURLException, XMLStreamException, JAXBException, TransformerException, DAOException {
////		
////		MessageFactory factory = MessageFactory.newInstance();
////		
////		SOAPMessage confirmationMsg = factory.createMessage();
////		SOAPEnvelope envelope = confirmationMsg.getSOAPPart().getEnvelope();
////		String livNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons";
////		envelope.addNamespaceDeclaration("liv", livNS);
////		SOAPBody body = confirmationMsg.getSOAPBody();
////		QName opName = new QName(livNS, "confirmation", "liv");
////		SOAPBodyElement opElement = body.addBodyElement(opName);
////		opElement.addTextNode("489");
////		System.out.println("Message à traiter : \n "+SaajHelper.getSOAPMessageAsString(confirmationMsg));
////		SOAPMessage commandeRep = JAXBTools.confirmation(factory,confirmationMsg.getSOAPPart().getEnvelope().getBody());
////		
////		System.out.println("Message reçu : \n "+SaajHelper.getSOAPMessageAsString(commandeRep));
////	}
//	
//	public void testLivraisonEffectuee() throws SOAPException, MalformedURLException, XMLStreamException, JAXBException, TransformerException, DAOException {
//	
//		MessageFactory factory = MessageFactory.newInstance();
//		
//		SOAPMessage confirmationMsg = factory.createMessage();
//		SOAPEnvelope envelope = confirmationMsg.getSOAPPart().getEnvelope();
//		String livNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons";
//		envelope.addNamespaceDeclaration("liv", livNS);
//		SOAPBody body = confirmationMsg.getSOAPBody();
//		QName opName = new QName(livNS, "livraisonEffectuee", "liv");
//		SOAPBodyElement opElement = body.addBodyElement(opName);
//		opElement.addTextNode("zaza");
//		System.out.println("Message à traiter : \n "+SaajHelper.getSOAPMessageAsString(confirmationMsg));
//		//SOAPMessage commandeRep = JAXBTools.livraisonEffectuee(factory,confirmationMsg.getSOAPPart().getEnvelope().getBody());
//		SOAPMessage commandeRep = JAXBTools.traiterSOAPMessage(factory,confirmationMsg);
//		System.out.println("Message reçu : \n "+SaajHelper.getSOAPMessageAsString(commandeRep));
//	}
//}
