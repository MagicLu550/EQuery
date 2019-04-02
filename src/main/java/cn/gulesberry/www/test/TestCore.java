/*GulesBerry Tech. Co. Ltd. (C) noyark-system Hector Group
 * (GHG China) @Freedom Web of java
 * @noyark - system group for xml
 * @github magiclu550 author
 * @noter K.J author 
 * @qq 843983728
 * @using dom4j
 * @school: JiaoNan No.1 middle School
 * 	override this none
 * 	please see our website:
 * 	###############################################
 * 						
 * 					     www.noyark.net/index.html
 * 
 * 	###############################################
 * 
 * @where China shandong qingdao
 * 
 * author is a middle school student,so he doesn't have much time.If you think his code is good for you,you
 * can touch the 'star',thanks
 * 
 * 
 * finished on 3.12 ,2019 ,7:18
 * /*GulesBerry Tech. Co. Ltd. (C) noyark-system Hector Group
 * (GHG China) @Freedom Web of java
 * @noyark - system group for xml
 * @github magiclu550 author
 * @noter K.J author 
 * @qq 843983728
 * @using dom4j
 * @school: JiaoNan No.1 middle School
 * 	override this none
 * 	please see our website:
 * 	###############################################
 * 						
 * 					     www.noyark.net/index.html
 * 
 * 	###############################################
 * 
 * @where China shandong qingdao
 * 
 * author is a middle school student,so he doesn't have much time.If you think his code is good for you,you
 * can touch the 'star',thanks
 * 
 * 
 * finished on 3.5 ,2019 ,5:18
 * 
 * 
 */
package cn.gulesberry.www.test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.gulesberry.www.core.MainProperties;
import cn.gulesberry.www.properties.SimpleProperties;
import cn.gulesberry.www.runtime.RuntimeListener;
import cn.gulesberry.www.use.SetMavenJar;
import net.noyark.www.annotations.RuntimeAfter;
import net.noyark.www.annotations.RuntimeBefore;
import net.noyark.www.annotations.RuntimeTest;
import net.noyark.www.console.Console;

/**
 * <p>
 * 	The class used to test the tag can inherit from 
 * 	it.After inheriting the class.you can retrive the 
 * 	runtime and memory usage and the result by adding the 
 * 	RuntimeTest annotation to the method.
 * </p>
 * @author magiclu550
 * @since JDK 1.8
 * @since ETest 001
 * @since EQuery 009
 *
 */
public class TestCore {
	/**
	 * This method can parse and load test
	 * classes based on the Test config file
	 * and annotations
	 * @see TestCore#parse(Class)
	 */
	public static void start() {
		try {
			SimpleProperties properties = new SimpleProperties(SetMavenJar.getInstance(), MainProperties.getInstance().getTestPath());
			Class<?> clz = Class.forName(properties.getString("class", ""));
			parse(clz);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Run in the main method of the test class,you can
	 * excute all methods with RuntimeTest annotations
	 * @param cls testClass's Class object
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	  @SuppressWarnings({"rawtypes" })
		public static void parse(Class cls)
	            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
	        int count = 0;
	        
	    	Console.log("-------------XMLTest-------------------");
	        
	        //获取方法
			Object object = cls.newInstance();
	        Method[] m = cls.getDeclaredMethods();
	        for(int i = 0;i<m.length;i++) {
	        	if(m[i].getAnnotation(RuntimeBefore.class)!=null) {
	        				m[i].invoke(object);
	        			}
	        }
	        for(int i = 0;i<m.length;i++) {
	        	Annotation[] annos = m[i].getAnnotations();
	        	  for(Annotation a:annos){
	  	            if(a instanceof RuntimeTest) {
	  	            	long time1 = System.currentTimeMillis();
	  	            	Console.log("the "+(count+1)+" method is loading,name: "+m[i].getName());
	  	            	try {
	  	            		System.out.println("the console message");
	  	            		System.out.println("[");
	  	            		m[i].invoke(object);
	  	            		System.out.println("]");
	  	            	}catch (Exception e) {
							Console.err("there is a exception on "+(count+1)+" method");
							e.getCause().printStackTrace();
						}
	  	                long time2 = System.currentTimeMillis();
	  	                System.out.println("the runtime ms: "+(time2-time1)+"ms");
	  	                count++;
	  	            }
	  	        }
	        }
	    for(int i = 0;i<m.length;i++) {
	      if(m[i].getAnnotation(RuntimeAfter.class)!=null) {
	        	m[i].invoke(object);
	        }
	    }
	        Console.log("------------------------the memory------------------------");
          String free = RuntimeListener.getFreeMemory();
          String max = RuntimeListener.getMaxMemory();
          String total =RuntimeListener.getTotalMemory();
          String process = RuntimeListener.getAvailableProcessors();
          Console.log(free);
          Console.log(max);
          Console.log(total);
          Console.log(process);
          Console.log(RuntimeListener.getTheUsingMemory());
	        Console.log("----------------TestEnd-----------------------------------");
	  }	  
}
