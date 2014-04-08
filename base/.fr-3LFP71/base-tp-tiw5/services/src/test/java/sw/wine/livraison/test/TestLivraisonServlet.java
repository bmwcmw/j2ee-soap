//package sw.wine.livraison.test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//
//import javax.xml.namespace.QName;
//import javax.xml.soap.MessageFactory;
//import javax.xml.soap.SOAPBody;
//import javax.xml.soap.SOAPBodyElement;
//import javax.xml.soap.SOAPElement;
//import javax.xml.soap.SOAPEnvelope;
//import javax.xml.soap.SOAPMessage;
//
//import sw.wine.livraison.servlet.LivraisonServlet;
//
//import com.meterware.httpunit.GetMethodWebRequest;
//import com.meterware.httpunit.WebRequest;
//import com.meterware.httpunit.WebResponse;
//import com.meterware.servletunit.InvocationContext;
//import com.meterware.servletunit.ServletRunner;
//import com.meterware.servletunit.ServletUnitClient;
//import junit.framework.Assert;
//import junit.framework.TestCase;
//
//public class TestLivraisonServlet extends TestCase {
//
//	protected void setUp() throws Exception {
//		super.setUp();
//	}
//
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//
//	public void testLivraison() {
//
//		try {
//			File webXml = new File("WebContent/WEB-INF/web.xml");
//			
//			ServletRunner sr = new ServletRunner(webXml,"");
//
//			sr.registerServlet("LivraisonServlet",
//					LivraisonServlet.class.getName());
//
//			ServletUnitClient servClient = sr.newClient();
//
//			WebRequest request = new GetMethodWebRequest(
//					"http://localhost:8080/livraison");
//			
//			MessageFactory factory = MessageFactory.newInstance();
//			SOAPMessage commandeMsg = factory.createMessage();
//			SOAPEnvelope envelope = commandeMsg.getSOAPPart().getEnvelope();
//			String livNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons";
//			envelope.addNamespaceDeclaration("liv", livNS);
//			SOAPBody body = commandeMsg.getSOAPBody();
//			QName opName = new QName(livNS, "commande", "liv");
//			SOAPBodyElement opElement = body.addBodyElement(opName);
//			QName locationName = new QName("vin");
//			SOAPElement locationElement1 = opElement.addChildElement(locationName);
//				QName referenceName = new QName("reference");
//				SOAPElement countryElement1 = locationElement1.addChildElement(referenceName);
//				countryElement1.addTextNode("cmw");
//				QName quantiteName = new QName("quantite");
//				SOAPElement regionElement1 = locationElement1.addChildElement(quantiteName);
//				regionElement1.addTextNode("1");
//			SOAPElement locationElement2 = opElement.addChildElement(locationName);
//				SOAPElement countryElement2 = locationElement2.addChildElement(referenceName);
//				countryElement2.addTextNode("cmw2013");
//				SOAPElement regionElement2 = locationElement2.addChildElement(quantiteName);
//				regionElement2.addTextNode("1");
//			System.out.println("Message à envoyer : \n "+SaajHelper.getSOAPMessageAsString(commandeMsg));
//			
//			System.out.println("____________________________");
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			commandeMsg.writeTo(out);
//			String strMsg = new String(out.toByteArray());
//			request.setParameter("par", strMsg);
//			
//			System.out.println();
//			System.out.println("____________________________");
//			System.out.println(request.toString());
//
//			InvocationContext ic = servClient.newInvocation(request);
//
//			LivraisonServlet is = (LivraisonServlet) ic.getServlet();
//
//			WebResponse response = servClient.getResponse(request);
//			
//			sr.shutDown();
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			Assert.fail();
//
//		}
//
//	}
//
//}
