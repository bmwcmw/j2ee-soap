package tp2saaj;

import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.TransformerException;

public class SaajClient {
	
	@SuppressWarnings({ "restriction", "rawtypes" })
	public static void main(String [] args) throws SOAPException{

		//Pour SOAP 1.1
		MessageFactory factory = MessageFactory.newInstance();
		//Pour SOAP 1.1 et 1.2
		//MessageFactory factory = MessageFactory.newInstance(SOAPConstants.DYNAMIC_SOAP_PROTOCOL);
		
		SOAPConnectionFactory soapConnectionFactory =
			    SOAPConnectionFactory.newInstance();
		SOAPConnection connection = soapConnectionFactory.createConnection();
		java.net.URL endpoint;
		SOAPMessage request;

		Scanner kbd = new Scanner(System.in);
			String menu = ("Choose an operation : \n" 
		            + "1. add Wines to one location\n" 
		            + "2. get Wines from one or more location(s)\n"
		            + "---------------------------------\n");
		    System.out.println(menu);
			int choix = kbd.nextInt();
		    switch(choix){
			    case 1:{
					try {
						endpoint = new URL("http://192.168.56.101:8080/wine-service/services/wine-storage");
						request = addWinesFromLocation(factory);
						printMessage(request,"SEND Message : ");
						SOAPMessage response = connection.call(request, endpoint);
						printMessage(response,"RECEIVE Message : ");
						connection.close();
					} catch (Exception e) {
						System.out.println("Exception in SaajClient.main(Case 1) "+e.getMessage());
					}
			    }
			    case 2:{
					try {
						endpoint = new URL("http://192.168.56.101:8080/wine-service/services/wine-storage");
						request = getWines(factory);
						printMessage(request,"SEND Message : ");
						SOAPMessage response = connection.call(request, endpoint);
						printMessage(response,"RECEIVE Message : ");
						
						Iterator itBody = response.getSOAPBody().getChildElements();
						while(itBody.hasNext()){
							SOAPElement elemTemp = (SOAPElement)itBody.next();
							System.out.println("TEST : "+elemTemp.getTagName());
						}
						
						connection.close();
					} catch (Exception e) {
						System.out.println("Exception in SaajClient.main(Case 1) "+e.getMessage());
					}
			    }
			    default:{
			    	System.out.println("EXIT...");
			    	System.exit(0);
			    }
		    }
	    kbd.close();
	}
	
	@SuppressWarnings("restriction")
	public static SOAPMessage addWinesFromLocation(MessageFactory factory) throws SOAPException{
		int wineNb;
		int compositionNb;
		int bottleNb;
		
		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("stor", "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/storage");
		envelope.addNamespaceDeclaration("mod", "http://www.univ-lyon1.fr/M2TI/TIW5/wine/model");
		String storNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/storage";
		String modNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/model";
		//SOAPHeader header = message.getSOAPHeader();
		SOAPBody body = message.getSOAPBody();
			QName opName = new QName(storNS, "addWinesFromLocation", "stor");
			SOAPBodyElement opElement = body.addBodyElement(opName);
			
				Scanner kbd = new Scanner(System.in);
				System.out.println("------ Location information ------");
				System.out.println("COUNTRY?");
				String countryStr = kbd.nextLine();
				System.out.println("REGION?");
				String regionStr = kbd.nextLine();
				System.out.println("SUBREGION?");
				String subregionStr = kbd.nextLine();

				QName locationName = new QName("location");
				SOAPElement locationElement = opElement.addChildElement(locationName);
					QName countryName = new QName(modNS, "country", "mod");
					SOAPElement countryElement = locationElement.addChildElement(countryName);
					countryElement.addTextNode(countryStr);
					QName regionName = new QName(modNS, "region", "mod");
					SOAPElement regionElement = locationElement.addChildElement(regionName);
					regionElement.addTextNode(regionStr);
					QName subregionName = new QName(modNS, "subregion", "mod");
					SOAPElement subregionElement = locationElement.addChildElement(subregionName);
					subregionElement.addTextNode(subregionStr);
		
						System.out.println("How many wines do you want to add?");
						wineNb=Integer.parseInt(kbd.nextLine());
						for(int i=1;i<=wineNb;i++){
							System.out.println("------ Wine"+i+" ------");
							System.out.println("ID?");
							String wineId = kbd.nextLine();
							System.out.println("APPELLATION?");
							String appellationStr = kbd.nextLine();
							System.out.println("COLOR?");
							String colorStr = kbd.nextLine();
							QName wineName = new QName(modNS, "wine", "mod");
							SOAPElement wineElement = locationElement.addChildElement(wineName);
							wineElement.addAttribute(new QName("id"),wineId);
								QName appellationName = new QName(modNS, "appellation", "mod");
								SOAPElement appellationElement = wineElement.addChildElement(appellationName);
								appellationElement.addTextNode(appellationStr);
								QName colorName = new QName(modNS, "color", "mod");
								SOAPElement colorElement = wineElement.addChildElement(colorName);
								colorElement.addTextNode(colorStr);
								
								System.out.println("------ How many compositions do you want to add? ------");
								compositionNb=Integer.parseInt(kbd.nextLine());
								for(int j=1;j<=compositionNb;j++){
									System.out.println("------ Composition"+j+" of wine"+i+" ------");
									System.out.println("VARIETY?");
									String varietyStr = kbd.nextLine();
									System.out.println("PERCENTAGE?");
									String percentageStr = kbd.nextLine();
									QName compositionName = new QName(modNS, "composition", "mod");
									SOAPElement compositionElement = wineElement.addChildElement(compositionName);
										QName varietyName = new QName(modNS, "variety", "mod");
										SOAPElement varietyElement = compositionElement.addChildElement(varietyName);
										varietyElement.addTextNode(varietyStr);
										QName percentageName = new QName(modNS, "percentage", "mod");
										SOAPElement percentageElement = compositionElement.addChildElement(percentageName);
										percentageElement.addTextNode(percentageStr);
								}
								
								System.out.println("Percentage Alcohol?");
								String percentageAlcoholStr = kbd.nextLine();
								System.out.println("PRODUCER?");
								String producerStr = kbd.nextLine();
								System.out.println("VINEYARD?");
								String vineyardStr = kbd.nextLine();
								System.out.println("VINTAGE?");
								String vintageStr = kbd.nextLine();
								System.out.println("STYLE?");
								String styleStr = kbd.nextLine();
								System.out.println("TYPE?");
								String typeStr = kbd.nextLine();

								QName percentageAlcoholName = new QName(modNS, "percentageAlcohol", "mod");
								SOAPElement percentageAlcoholElement = wineElement.addChildElement(percentageAlcoholName);
								percentageAlcoholElement.addTextNode(percentageAlcoholStr);
								QName producerName = new QName(modNS, "producer", "mod");
								SOAPElement producerElement = wineElement.addChildElement(producerName);
								producerElement.addTextNode(producerStr);
								QName vineyardName = new QName(modNS, "vineyard", "mod");
								SOAPElement vineyardElement = wineElement.addChildElement(vineyardName);
								vineyardElement.addTextNode(vineyardStr);
								QName vintageName = new QName(modNS, "vintage", "mod");
								SOAPElement vintageElement = wineElement.addChildElement(vintageName);
								vintageElement.addTextNode(vintageStr);
								QName styleName = new QName(modNS, "style", "mod");
								SOAPElement styleElement = wineElement.addChildElement(styleName);
								styleElement.addTextNode(styleStr);
								QName typeName = new QName(modNS, "type", "mod");
								SOAPElement typeElement = wineElement.addChildElement(typeName);
								typeElement.addTextNode(typeStr);
								
								System.out.println("------ How many bottles do you want to add? ------");
								bottleNb=Integer.parseInt(kbd.nextLine());
								for(int k=1;k<=bottleNb;k++){
									QName bottleName = new QName(modNS, "bottle", "mod");
									SOAPElement bottleElement = wineElement.addChildElement(bottleName);
									bottleElement.addAttribute(new QName("id"),"0");	
								}
						}
									
		kbd.close();
		return message;
	}
	
	@SuppressWarnings("restriction")
	public static SOAPMessage getWines(MessageFactory factory) throws SOAPException{
		int locationNb;
		
		Scanner kbd = new Scanner(System.in);
		
		SOAPMessage message = factory.createMessage();
		
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("stor", "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/storage");
		envelope.addNamespaceDeclaration("mod", "http://www.univ-lyon1.fr/M2TI/TIW5/wine/model");
		String storNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/storage";
		String modNS = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/model";
		//SOAPHeader header = message.getSOAPHeader();
		SOAPBody body = message.getSOAPBody();
			QName opName = new QName(storNS, "getWines", "stor");
			SOAPBodyElement opElement = body.addBodyElement(opName);
				System.out.println("How many locations do you want to get?");
				locationNb=Integer.parseInt(kbd.nextLine());
				for(int i=1;i<=locationNb;i++){
					System.out.println("------ Location"+i+" ------");
					System.out.println("COUNTRY?");
					String countryStr = kbd.nextLine();
					System.out.println("REGION?");
					String regionStr = kbd.nextLine();
					System.out.println("SUBREGION?");
					String subregionStr = kbd.nextLine();
					QName locationName = new QName("location");
					SOAPElement locationElement = opElement.addChildElement(locationName);
						QName countryName = new QName(modNS, "country", "mod");
						SOAPElement countryElement = locationElement.addChildElement(countryName);
						countryElement.addTextNode(countryStr);
						QName regionName = new QName(modNS, "region", "mod");
						SOAPElement regionElement = locationElement.addChildElement(regionName);
						regionElement.addTextNode(regionStr);
						QName subregionName = new QName(modNS, "subregion", "mod");
						SOAPElement subregionElement = locationElement.addChildElement(subregionName);
						subregionElement.addTextNode(subregionStr);
				}
		kbd.close();
		return message;
	}
	
	@SuppressWarnings("restriction")
	public static void printMessage(SOAPMessage msg, String ...info) throws SOAPException, TransformerException{
		System.out.println("\n-------------------------------------------------");
		if(info!=null&&info.length>0){
			System.out.println(info[0]);
		}
		System.out.println(SaajHelper.getSOAPMessageAsString(msg));
		System.out.println("\n-------------------------------------------------");
	}
}