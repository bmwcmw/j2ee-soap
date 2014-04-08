package sw.wine.handlers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;


import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SoapHeadHandler implements SOAPHandler<SOAPMessageContext>{

	private static final Logger LOG = Logger.getLogger(SoapHeadHandler.class.getName());
    
	
	@Override
	public boolean handleMessage(SOAPMessageContext  context) {
		
		try {
			SOAPHeader header = context.getMessage().getSOAPHeader();
			SOAPElement se;
			if(header.getChildElements().hasNext()){
				Iterator<SOAPElement> it=header.getChildElements();
				it.next();
				se=it.next();
				LOG.info("header "+se.getLocalName()+" "+se.getValue());
			}
			
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext  context) {
		LOG.warning("faute");
		return true;
	}
	



	@Override
	public Set<QName> getHeaders() {
//	    LOG.info("Inside SOAP handler of get Headers");
//	    QName securityUsernameHeader = new QName("mod",
//	            "client-id");
//	    HashSet<QName> headers = new HashSet<QName>();
//	    headers.add(securityUsernameHeader);
//	    LOG.info("got Headers:  " + headers);
//	    return headers; 
		return null;
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}

}
