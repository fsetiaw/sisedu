package servlets.process;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepPhp
 */
@WebServlet("/PrepPhp")
public class PrepPhp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepPhp() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			System.out.println("iam in bos");
			try {
				String endpointUrl = "http://192.168.1.106:8882/ws/sandbox.php?wsdl";

				QName serviceName = new QName("http://192.168.1.106:8882/ws/sandbox.php?wsdl","GetToken");
				QName portName = new QName("http://192.168.1.106:8882/ws/sandbox.php?wsdl","WSPDDIKTIPortType");

				// Create a service and add at least one port to it.  
				Service service = Service.create(serviceName);
				service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);

				// Create a Dispatch instance from a service 
				Dispatch <SOAPMessage> dispatch = service.createDispatch(portName, 
				SOAPMessage.class, Service.Mode.MESSAGE);

				// Create SOAPMessage request.
				// compose a request message
				MessageFactory mf = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

				// Create a message.  This example works with the SOAPPART.
				SOAPMessage req = mf.createMessage();
				SOAPPart part = req.getSOAPPart();

				// Obtain the SOAPEnvelope and header and body elements.
				SOAPEnvelope env = part.getEnvelope();
				SOAPHeader header = env.getHeader();
				SOAPBody body = env.getBody();

				// Construct the message payload.
				SOAPElement operation = body.addChildElement("invoke", "ns1","http://com/ibm/was/wssample/echo/");
				SOAPElement value = operation.addChildElement("arg0");
				value.addTextNode("ping");
				req.saveChanges();

				// Invoke the service endpoint.
				SOAPMessage res = dispatch.invoke(req);
				System.out.println(res.toString());

			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
			
			
			/** Process the response. **/
			/*
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Mhs/ListInfoMhsType2.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url_ff+"?listNpmhs="+listNpmhs).forward(request,response);
			*/
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
