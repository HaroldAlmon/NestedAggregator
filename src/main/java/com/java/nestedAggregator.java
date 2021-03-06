package com.java;
/*
 * This is an example of a nested aggregator that uses Spring error channels
 * to process exceptions.
 * 
 * The input has two deliberate errors
 * 1. The first word list has less than three words and marks the list with  LIST-TOO-SHORT<list>
 * 2. The second word list is long enough but contains words with less than five caharcters
 * that are marked as WORD-TOO-SHORT<word>
 * 
 * Note the use of the logging channel adapter that allows you to see the contents of
 * the message and the message headers.
 */
import java.util.Arrays;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class nestedAggregator 
{
    public static void main( String[] args )
    {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("nestedAggregator.xml");
		ErrorDemoGateway gateway = context.getBean(ErrorDemoGateway.class);
		
		// This list is too short (length < 3) and the list filter will catch the error.
		List<String> wordList1 = Arrays.asList( "Satis", "est");
		
		// Words must have at least 6 characters, otherwise the word filter will catch the error.
		List<String> wordList2 = Arrays.asList("Veritas", "omnia", "vincit"); 
		
		gateway.process( Arrays.asList( wordList1, wordList2 )  );
    }
    
	public interface ErrorDemoGateway {
		public Object process(List<List<String>> list);
	}
}
