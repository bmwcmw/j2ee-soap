package sw.wine.handlers;
     
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;


import java.util.logging.Logger;
     
     
    public class SimpleLogHandler implements LogicalHandler<LogicalMessageContext> {
     
    	private static final Logger LOG = Logger.getLogger(SimpleLogHandler.class.getName());
     
    	@Override
    	public void close(MessageContext ctx) {
    	}
     
    	@Override
    	public boolean handleFault(LogicalMessageContext ctx) {
    		LOG.warning("faute");
    		return true;
    	}
     
    	@Override
    	public boolean handleMessage(LogicalMessageContext ctx) {
    		boolean outbound = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    		final LogicalMessage message = ctx.getMessage();
            final DOMSource body = (DOMSource) message.getPayload();
            final String localName = body.getNode().getLocalName();

    		LOG.info("message "+(outbound?"sortant":"entrant")+"\r\n");
    		LOG.info("message "+localName);
    		return true;
    	}
     
    }

