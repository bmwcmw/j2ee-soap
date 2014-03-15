package sw.wine.livraison.serviceprovider;

import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.Service;
import sw.wine.livraison.servlet.JAXBTools;

//import sw.wine.livraison.servlet.JAXBTools;

//@WebServiceProvider(wsdlLocation = "WebContent/livraisons.wsdl")

@WebServiceProvider(portName="LivraisonServicePort",serviceName="LivraisonServiceService",
targetNamespace="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/",wsdlLocation = "wptiwe5/base-tp-tiw5/services/WebContent/livraisons.wsdl")
@ServiceMode(value=Service.Mode.MESSAGE)  
public class LivraisonServiceProvider implements Provider<DOMSource>   {
	MessageFactory messageFactory ;
	
	public LivraisonServiceProvider(){
		try {
			messageFactory=MessageFactory.newInstance();
		} catch (SOAPException e) {
			System.out.println(e.getMessage());
		}
	}
	@Override
	public DOMSource invoke(DOMSource request) {
		try {
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage();
			message.getSOAPPart().setContent(request);
			message.writeTo(System.out);
			SOAPMessage response = JAXBTools.traiterSOAPMessage(messageFactory, message);
			DOMSource domSource = new DOMSource();
			domSource.setNode(response.getSOAPPart());
			return domSource;
		} catch (SOAPException e) {
			System.out.println(e.getMessage());
			try {
				SOAPMessage response = JAXBTools.messageFault(messageFactory, e);
				DOMSource domSource = new DOMSource();
				domSource.setNode(response.getSOAPPart());
				return domSource;
			} catch (SOAPException e1) {
				System.out.println(e.getMessage());
				return null;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		
		
	}

}
