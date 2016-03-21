/*
 * #%L
 * SPIM Data: representation of registered, multi-angle, multi-channel (etc.) image sequences
 * %%
 * Copyright (C) 2013 - 2015 BigDataViewer authors
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package mpicbg.spim.data.sequence;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Class with static methods to deal with integer patterns defining ranges of numbers and create String instances based 
 * on simple expressions like {aaa} or {t}.
 * 
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 */
public class IntegerPattern 
{
	/**
	 * Analyzes a file pattern and returns the number of digits as well as the replace pattern
	 * for a certain type, 
	 * e.g. analyze spim_TL{t}_Angle{aaa}.tif for how many 'a' need to be replaced. It would 
	 * return {aaa}, so you know it is with leading zeros up to a length of 3.
	 * 
	 * @param inputFilePattern - e.g. spim_TL{t}_Angle{aaa}.tif
	 * @param type - e.g. 't' or 'a'
	 * @return the replace pattern "{t}" or "{aaa}", null if {type} does not exist in the String
	 */
    public static String getReplaceString( final String inputFilePattern, final char type )
    {
    	String replacePattern = null;
    	int numDigitsTL = 0;

		final int i1 = inputFilePattern.indexOf( "{" + type );
		final int i2 = inputFilePattern.indexOf( type + "}" );
		
		if ( i1 >= 0 && i2 > 0 )
		{
			replacePattern = "{";

			numDigitsTL = i2 - i1;
			
			for ( int i = 0; i < numDigitsTL; ++i )
				replacePattern += type;

			replacePattern += "}";
		}

		return replacePattern;
    }

	/**
	 * Parse a pattern provided by the user defining a range of integers. Allowed are enumerations seperated by
	 * commas, each entry can be a single number, a range e.g. 4-100 or a range in intervals e.g. 0-30:10 - which
	 * is equivalent to 0,10,20,30
	 * 
	 * @param integers - the input
	 * @return a list of integers that were described, an empty list with the entry 0 if the String is "" or null
	 * @throws ParseException if the input string was illegal
	 */
    public static ArrayList< Integer > parseIntegerString( final String integers ) throws ParseException
    {
    	ArrayList< Integer > tmp = null;

    	if ( integers == null || integers.trim().length() == 0 )
    	{
    		tmp = new ArrayList<Integer>();
    		tmp.add( 0 );
    		return tmp;
    	}
    	
		try
		{
	    	tmp = new ArrayList<Integer>();
	    	final String[] entries = integers.split( "," );
	    	
	    	for ( String s : entries )
	    	{
	    		s = s.trim();
	    		s = s.replaceAll( " ", "" );

	    		if ( s.contains( "-" ) )
	    		{
	    			int start = 0, end = 0, step;
	    			start = Integer.parseInt( s.substring( 0, s.indexOf("-") ) );

	    			if ( s.indexOf( ":" ) < 0 )
	    			{
	    				end = Integer.parseInt( s.substring( s.indexOf("-") + 1, s.length() ) );
	    				step = 1;
	    			}
	    			else
	    			{
	    				end = Integer.parseInt( s.substring( s.indexOf("-") + 1, s.indexOf(":") ) );
	    				step = Integer.parseInt( s.substring( s.indexOf(":") + 1, s.length() ) );
	    			}

	    			if ( end > start )
	    				for ( int i = start; i <= end; i += step )
	    					tmp.add( i );
	    			else
	    				for ( int i = start; i >= end; i -= step )
	    					tmp.add( i );
	    		}
	    		else
	    		{
	    			tmp.add( Integer.parseInt( s ) );
	    		}
	    	}
		}
		catch ( final Exception e )
		{
			e.printStackTrace();
			throw new ParseException( "Cannot parse: '" + integers + "'", 0 );
		}

		return tmp;
    }

}
