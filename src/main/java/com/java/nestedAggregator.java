package com.java;
/*
 * This is an example of a nested aggregator that uses Spring error channels
 * to process exceptions.
 * 
 * The input has two deliberate errors
 * 1. The first word list has less than three words and marks the list with @@ <list> @@
 * 2. The second word list is long enough but contains words with less than five caharcters
 * that are marked as ## <word> ## 
 * 
 * Note the use of the logging channel adapter that allows you to see the contents of
 * the message and the message headers.
 */
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;

public class nestedAggregator 
{
	//private Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("error-config.xml");
		//, ErrorDemo.class
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/error-aggregator-config.xml");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("nestedAggregator.xml");
		//, App.class
		context.registerShutdownHook();

		ErrorDemoGateway gateway = context.getBean(ErrorDemoGateway.class);
		List<String> wordList1 = Arrays.asList( "bye", "hello");			// This list is too short (length < 3) and the listFilter will generate an exception.
		List<String> wordList2 = Arrays.asList("yes", "goodbye", "no"); // Words must have at least 5 characters.
		
		//gateway.process( Arrays.asList( wordList1 )  );	
		Object o = gateway.process( Arrays.asList( wordList1, wordList2 )  );
		System.out.println("Finished.");
		context.close();
    }
    
	public interface ErrorDemoGateway {
		public Object process(List<List<String>> list);
		//public Future process(List<List<String>> list);
	}
}
