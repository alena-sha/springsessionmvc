package conversation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HttpSessionManager;

public class SpringSessionFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// tag::HttpSessionManager[]
		HttpSessionManager sessionManager = (HttpSessionManager) httpRequest
				.getAttribute(HttpSessionManager.class.getName());
		// end::HttpSessionManager[]
		SessionRepository<Session> repo = (SessionRepository<Session>) httpRequest
				.getAttribute(SessionRepository.class.getName());

		String currentSessionAlias = sessionManager.getCurrentSessionAlias(httpRequest);
		System.out.println("======currentSssionAl="+currentSessionAlias);
		Map<String, String> sessionIds = sessionManager.getSessionIds(httpRequest);
		String unauthenticatedAlias = null;

		String contextPath = httpRequest.getContextPath();
		List<Conversation> conversations = new ArrayList<Conversation>();
		Conversation currentConversation = null;
		for (Map.Entry<String, String> entry : sessionIds.entrySet()) {
			String alias = entry.getKey();
			String sessionId = entry.getValue();
			
			Session session = repo.getSession(sessionId);
			if (session == null) {
				continue;
			}

			String name = session.getAttribute("conversation");
			if (name == null) {
				unauthenticatedAlias = alias;
				continue;
			}

			String logoutUrl = sessionManager.encodeURL("./logout", alias);
			String switchAccountUrl = sessionManager.encodeURL("./", alias);
			Conversation conversation = new Conversation(name, logoutUrl, switchAccountUrl);
			if (currentSessionAlias.equals(alias)) {
				currentConversation = conversation;
			}
			else {
				conversations.add(conversation);
			}
		}
		for(int i=0;i<conversations.size();i++){
			System.out.println("name="+conversations.get(i).getName()+",url="+conversations.get(i).getLogoutUrl()+"switch="+conversations.get(i).getSwitchAccountUrl());
		}
		String addAlias = unauthenticatedAlias == null ? // <1>
				sessionManager.getNewSessionAlias(httpRequest)
				: // <2>
				unauthenticatedAlias; // <3>
		//String url = sessionManager.encodeURL("/hotels/search", addAlias); 
		String addConversationUrl = sessionManager.encodeURL("/hotels/search", addAlias); // <4>
		
		httpRequest.setAttribute("currentConversation", currentConversation);
		httpRequest.setAttribute("addConversationUrl", addConversationUrl);
		httpRequest.setAttribute("conversations", conversations);

	
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}


