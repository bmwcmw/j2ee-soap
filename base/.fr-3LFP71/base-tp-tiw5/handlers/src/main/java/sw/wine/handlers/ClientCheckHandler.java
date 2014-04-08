package sw.wine.handlers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import sw.wine.model.dao.ClientDAO;
import sw.wine.model.user.Client;

public class ClientCheckHandler implements SOAPHandler<SOAPMessageContext>{

	private static final Logger LOG = Logger.getLogger(SoapHeadHandler.class.getName());
    	
	private Client client;
	
	@Resource
    private ClientDAO clientDAO=new ClientDAO();
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		try {
			SOAPHeader header = context.getMessage().getSOAPHeader();
			SOAPElement se;
			if(header.getChildElements().hasNext()){
				Iterator<SOAPElement> it=header.getChildElements();				
				it.next();
				se=it.next();
				LOG.info(""+se.getLocalName()+" out ");
				if("client-id".equalsIgnoreCase(se.getLocalName())){
					LOG.info(""+se.getLocalName()+" in "+se.getFirstChild().getNodeValue());
					if(clientDAO==null) LOG.info("dao null");
					this.client=clientDAO.findClientById(Integer.parseInt(se.getFirstChild().getNodeValue()));
					if(client!=null){						
//						Marshaller clientMarshaller=JAXBContext.newInstance(Client.class).createMarshaller();
//						Unmarshaller clientUnmarshaller=JAXBContext.newInstance(Client.class).createUnmarshaller();
//						clientMarshaller.marshal(client, se);
//						Client c2=(Client)clientUnmarshaller.unmarshal(se.getFirstChild().getNextSibling());
						
						//LOG.info(""+c2.getNom()+" "+c2.getCompte());
						Map<String, Object> attachments = (Map<String, Object>) context.get(javax.xml.ws.handler.MessageContext.INBOUND_MESSAGE_ATTACHMENTS); 
//				        for(String attachmentKey: attachments.keySet()) {
//				            Object handler =  attachments.get(attachmentKey);
//				            LOG.info("Got attachment " + attachmentKey + " of type " + attachments.get(attachmentKey));
//				        }
				        attachments.put("client", client);
				        if(attachments.isEmpty()) {
				        	LOG.info("Got No attachments");
				        }else LOG.info("Got "+attachments.size()+" attachments");
					}
					else {
						LOG.info("client null");
						return false;
					
					}
				}				
			}
			
		} catch (Exception e) {
			LOG.info(e.toString());
			return false;
		} 	
		
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

}
