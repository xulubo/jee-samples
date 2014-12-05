import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@WebAppConfiguration
public class AuditTest {

	@Autowired
	protected RequestMappingHandlerAdapter handlerAdapter;

	@Autowired
	protected RequestMappingHandlerMapping requestMappingHandlerMapping;
    
	@Autowired
	protected WebApplicationContext webApplicationContext;
    
    protected MockMvc mockMvc;

 
	@Before
	final public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		assertNotNull(mockMvc);		
	}
	
	@Test
	public void test() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/help").param("pageId", "24")).andExpect(status().isOk());
	}
}
