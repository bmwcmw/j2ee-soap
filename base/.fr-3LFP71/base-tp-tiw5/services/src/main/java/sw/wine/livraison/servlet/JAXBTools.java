package sw.wine.livraison.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import sw.wine.livraison.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

public class JAXBTools{
	final static String livNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons";
	
	public static SOAPMessage traiterSOAPMessage(MessageFactory messageFactory, SOAPMessage mySoapMsg) 
			throws SOAPException{
		/* Traitement de SOAP requête. */
		SOAPMessage reply = messageFactory.createMessage();
		String operation;
		SOAPElement elemPrincipal;
		try{
			System.out.println(SaajHelper.getSOAPMessageAsString(mySoapMsg));			
			elemPrincipal = (SOAPElement) mySoapMsg.getSOAPBody().getChildNodes().item(1);
			String firstTag=mySoapMsg.getSOAPBody().getChildNodes().item(1).getNodeName();
			operation=firstTag.split(":")[1];
			System.out.println("JAXBTools - opération à traiter : "+operation);
			if(operation.equals("commande")){
				reply = commande(messageFactory,elemPrincipal);
			} else if (operation.equals("confirmation")){
				reply = confirmation(messageFactory,elemPrincipal);
			} else if (operation.equals("livraisonEffectuee")){
				reply = livraisonEffectuee(messageFactory,elemPrincipal);
			} else {
				reply = messageFault(messageFactory,new Exception("Opération inconnue"));
			}
		} catch(Exception e) {
			reply = messageFault(messageFactory,e);
		}
		return reply;
	}
	
	@SuppressWarnings("unchecked")
	public static SOAPMessage commande(MessageFactory factory, SOAPElement inputElem) 
			throws XMLStreamException, JAXBException, TransformerException, SOAPException{
		ArrayList<Article> ar = new ArrayList<Article>();
		Unmarshaller unmarshaller = 
				JAXBContext.newInstance(Article.class).createUnmarshaller();
		
		//SOAP ==> Objects
		System.out.println("JAXBTools - commande en cours de traitement...");
		Iterator<SOAPElement> itElem;// = 
				//((SOAPElement) inputElem.getChildElements().next()).getChildElements();
		itElem=inputElem.getChildElements();
		while(itElem.hasNext()){
			System.out.println("in the loop");
			try{
				ar.add( (Article) unmarshaller.unmarshal((SOAPElement)itElem.next()) );
			}catch (Exception e){
				
			}
			
		}
		System.out.println("JAXBTools - "+ar.size()+" articles détectés : ");
		Livraison liv = new Livraison();
		try {//Si NonDisponibleException
			Article[] ar2 = (Article[])ar.toArray(new Article[ar.size()]);
			for(int i=0;i<ar2.length;i++){
				System.out.println("JAXBTools - "+"Article"
					+(i+1)+"/"+ar.size()+" : wine_id="+ar2[i].getReferenceVin()+" Nb="+ar2[i].getNombre());
			}
			CommandeInfos ci = liv.commande(ar2);
			System.out.println("JAXBTools - Commande "+ci.getCmdId()
					+" créé(prix="+ci.getPrix()+")");
	        SOAPMessage returnMsg = factory.createMessage();
	        SOAPEnvelope envelope = returnMsg.getSOAPPart().getEnvelope();
			envelope.addNamespaceDeclaration("liv", livNS);
			SOAPBody body = returnMsg.getSOAPBody();
			QName opName = new QName(livNS, "infos", "liv");
			SOAPBodyElement opElement = body.addBodyElement(opName);
				QName idName = new QName("id");
				SOAPElement idElement = opElement.addChildElement(idName);
				idElement.addTextNode(ci.getCmdId());
				QName prixName = new QName("prix");
				SOAPElement prixElement = opElement.addChildElement(prixName);
				prixElement.addTextNode( String.valueOf(ci.getPrix()) );
	        return returnMsg;
		} catch (NonDisponibleException e) {
			return messageFault(factory,e);
		}
	}
	
	public static SOAPMessage confirmation(MessageFactory factory, SOAPElement inputElem) 
			throws XMLStreamException, JAXBException, SOAPException{
		System.out.println("JAXBTools - confirmation en cours de traitement...");
		String idCmd=inputElem.getTextContent();
		System.out.println("JAXBTools - confirmation pour Commande#"+idCmd);
		Livraison liv = new Livraison();
		try {
			System.out.println("JAXBTools - confirmation en cours de traitement...");
			Date datePrevu = liv.confirmeCommande(idCmd);
	        SOAPMessage returnMsg = factory.createMessage();
	        SOAPEnvelope envelope = returnMsg.getSOAPPart().getEnvelope();
			envelope.addNamespaceDeclaration("liv", livNS);
			SOAPBody body = returnMsg.getSOAPBody();
			QName opName = new QName(livNS, "dateLivraison", "liv");
			SOAPBodyElement opElement = body.addBodyElement(opName);
			opElement.addTextNode(datePrevu.toString());
	        return returnMsg;
		} catch (CommandeInconnueException e) {
			return messageFault(factory,e);
		} catch (NonDisponibleException e) {
			return messageFault(factory,e);
		}
	}
	
	public static SOAPMessage livraisonEffectuee(MessageFactory factory, SOAPElement inputElem) throws XMLStreamException, JAXBException, SOAPException{
		System.out.println("JAXBTools - livraisonEffectuee en cours de traitement...");
		String idCmd=inputElem.getTextContent();
		System.out.println("JAXBTools - livraisonEffectuee pour Commande#"+idCmd);
		Livraison liv = new Livraison();
		try {
			boolean effectue = liv.livraisonEffectuee(idCmd);
			SOAPMessage returnMsg = factory.createMessage();
	        SOAPEnvelope envelope = returnMsg.getSOAPPart().getEnvelope();
			envelope.addNamespaceDeclaration("liv", livNS);
			SOAPBody body = returnMsg.getSOAPBody();
			QName opName = new QName(livNS, "livraisonEffectueeResponse", "liv");
			SOAPBodyElement opElement = body.addBodyElement(opName);
			opElement.addTextNode(String.valueOf(effectue));
	        return returnMsg;
		} catch (CommandeInconnueException e) {
			return messageFault(factory,e);
		}
	}
	
	public static SOAPMessage messageFault(MessageFactory factory, Exception e) throws SOAPException{
		System.out.println("JAXBTools - exception reçue : "+e.getClass().getName());
		SOAPMessage reply = factory.createMessage();
		SOAPBody someBody = reply.getSOAPPart().getEnvelope().getBody();
		SOAPFault fault = someBody.addFault();
		if(e instanceof CommandeInconnueException){
			System.out.println("JAXBTools - liv:commandeInconnueFault détecté");
			fault.setFaultString("liv:commandeInconnueFault");
		} else if(e instanceof NonDisponibleException){
			System.out.println("JAXBTools - liv:nonDisponibleFault détecté");
			fault.setFaultString("liv:nonDisponibleFault");
		} else {
			fault.setFaultString(e.getClass().getName());
		}
		Detail myDetail = fault.addDetail();
		myDetail.addTextNode(e.getMessage());
		reply.saveChanges();
		return reply;
	}
}
