package conversation;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableJdbcHttpSession 
public class Config {

        @Bean
        public EmbeddedDatabase dataSource() {
                return new EmbeddedDatabaseBuilder() 
                                .setType(EmbeddedDatabaseType.HSQL)
                                .addScript("org/springframework/session/jdbc/schema-hsqldb.sql").build();
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
                return new DataSourceTransactionManager(dataSource); 
        }

}