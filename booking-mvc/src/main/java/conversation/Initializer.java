package conversation;

import javax.servlet.ServletContext;

import org.springframework.core.annotation.Order;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Order(value=100)
public class Initializer extends AbstractHttpSessionApplicationInitializer {



	public Initializer() {
	//	super(Config.class);
		super();
	}

	@Override
	protected void afterSessionRepositoryFilter(ServletContext servletContext) {
		appendFilters(servletContext, new SpringSessionFilter());
	}
}



