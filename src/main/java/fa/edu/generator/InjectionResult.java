package fa.edu.generator;

import java.io.Serializable;

import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class InjectionResult implements IdentifierGenerator {
	
	private String prefix = "INR";
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		 String query = "SELECT si.injectionResultId FROM InjectionResult si";
		    Stream<String> ids = session.createQuery(query, String.class).stream();
		    Long max = ids.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
		    return prefix + (String.format("%04d", max + 1));
	}

}
