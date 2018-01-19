import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ProxyFactory {

	private Object target;

	public ProxyFactory(Object target){
		this.target = target;
	}

	public Object getInstanceProxy(){
		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				new InvocationHandler(){
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("before method call : " + method.getName());
						Object result = method.invoke(target, args);
						System.out.println("after method call : " + method.getName());
						return result;
					}
				});
	}
}
