package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		prototypeBean1.addCount();
		assertThat(prototypeBean1.getCount()).isEqualTo(1);
		
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		prototypeBean2.addCount();
		assertThat(prototypeBean2.getCount()).isEqualTo(1);
	}
	
	@Test
	void singletonClientPrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);
		ClientBean clinetBean1 =  ac.getBean(ClientBean.class);
		int count1 = clinetBean1.logic();
		assertThat(count1).isEqualTo(1);
		
		ClientBean clinetBean2 =  ac.getBean(ClientBean.class);
		int count2 = clinetBean1.logic();
		assertThat(count1).isEqualTo(2);
	}
	
	@Scope("singleton")
	static class ClientBean{
		private final PrototypeBean prototypeBean; // 생성시점에 주입 @x01
		
		
		@Autowired
		public ClientBean(PrototypeBean prototypeBean) {
			this.prototypeBean = prototypeBean;
		}
		
		public int logic() {
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
	
	@Scope("singleton")
	static class ClientBean2{
		private final PrototypeBean prototypeBean; // 생성시점에 주입 @x02
		
		@Autowired
		public ClientBean2(PrototypeBean prototypeBean) {
			this.prototypeBean = prototypeBean;
		}
		
		public int logic() {
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
	
	
	@Scope("prototype")
	static class PrototypeBean{
		
		private int count = 0;
		
		public void addCount() {
			count++;
		}
		
		public int getCount() {
			return count;
		}
		
		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init " + this);
		}
		
		@PreDestroy
		public void destory() {
			System.out.println("close");
		}
	}
}
