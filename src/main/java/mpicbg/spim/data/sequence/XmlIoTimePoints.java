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

import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_LIST_TYPE;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_PATTERN_TAG;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_PATTERN_TYPE;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_RANGE_FIRST;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_RANGE_LAST;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_RANGE_TYPE;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_TAG;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_TIMEPOINT_TAG;
import static mpicbg.spim.data.XmlKeys.TIMEPOINTS_TYPE_ATTRIBUTE_NAME;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.SpimDataIOException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.XmlKeys;
import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.XmlIoEntity;
import mpicbg.spim.data.generic.base.XmlIoSingleton;

import org.jdom2.Element;

public class XmlIoTimePoints extends XmlIoSingleton< TimePoints >
{
	public XmlIoTimePoints()
	{
		super( TIMEPOINTS_TAG, TimePoints.class );
		handledTags.add( TIMEPOINTS_TIMEPOINT_TAG );
		handledTags.add( TIMEPOINTS_RANGE_FIRST );
		handledTags.add( TIMEPOINTS_RANGE_LAST );
		handledTags.add( TIMEPOINTS_PATTERN_TAG );
	}

	public Element toXml( final TimePoints timepoints )
	{
		final Element elem = super.toXml();

		if ( timepoints instanceof TimePointsPattern )
		{
			toXmlPattern( ( TimePointsPattern ) timepoints, elem );
		}
		else
		{
			if ( !toXmlTryRange( timepoints, elem ) )
				toXmlList( timepoints, elem );
		}

		return elem;
	}

	@Override
	public TimePoints fromXml( final Element elem ) throws SpimDataException
	{
		final TimePoints timepoints = super.fromXml( elem );

		final String type = elem.getAttributeValue( TIMEPOINTS_TYPE_ATTRIBUTE_NAME );
		if ( TIMEPOINTS_PATTERN_TYPE.equals( type ) )
			return fromXmlPattern( elem );
		else if ( TIMEPOINTS_RANGE_TYPE.equals( type ) )
			fromXmlRange( timepoints, elem );
		else if ( TIMEPOINTS_LIST_TYPE.equals( type ) )
			fromXmlList( timepoints, elem );
		else
			throw new IllegalArgumentException( "unknown " + TIMEPOINTS_TAG + " type " + type );

		return timepoints;
	}

	/**
	 * Save {@link TimePointsPattern}.
	 */
	protected void toXmlPattern( final TimePointsPattern timepoints, final Element elem )
	{
		elem.setAttribute( TIMEPOINTS_TYPE_ATTRIBUTE_NAME, TIMEPOINTS_PATTERN_TYPE );
		elem.addContent( XmlHelpers.textElement( TIMEPOINTS_PATTERN_TAG, timepoints.getPattern() ) );
	}

	/**
	 * Create {@link TimePointsPattern} from &lt;{@value XmlKeys#TIMEPOINTS_TAG}
	 * &gt; element of type {@value XmlKeys#TIMEPOINTS_PATTERN_TYPE}
	 */
	protected TimePointsPattern fromXmlPattern( final Element elem ) throws SpimDataIOException
	{
		final String integerPattern = XmlHelpers.getText( elem, TIMEPOINTS_PATTERN_TAG );
		try
		{
			return new TimePointsPattern( integerPattern );
		}
		catch ( final ParseException e )
		{
			throw new SpimDataIOException( "cannot parse <" + TIMEPOINTS_TAG + "> pattern: " + integerPattern, e );
		}
	}

	/**
	 * Try to save a {@link TimePoints} list as a range. For this to work,
	 * time-point {@link TimePoint#getId() ids} must form a contiguous range of
	 * integers and the time-point {@link TimePoint#getName() names} must equal
	 * the ids {@link Integer#toString(int) represented }as strings.
	 *
	 * @param timepoints
	 * @param elem
	 * @return <code>true</code>, if timepoints could be saved,
	 *         <code>false</code> otherwise. If <code>false</code> is returned,
	 *         elem is not modified.
	 */
	protected boolean toXmlTryRange( final TimePoints timepoints, final Element elem )
	{
		try
		{
			final ArrayList< TimePoint > tps = new ArrayList< TimePoint >( timepoints.getTimePoints().values() );
			Entity.sortById( tps );

			final Iterator< TimePoint > iter = tps.iterator();
			TimePoint tp = iter.next();
			String name = tp.getName();
			final int first = Integer.parseInt( name );
			if ( !( Integer.toString( first ).equals( name ) && first == tp.getId() ) )
				return false;
			int previous = first;
			int last = first;
			while ( iter.hasNext() )
			{
				tp = iter.next();
				name = tp.getName();
				last = Integer.parseInt( name );
				if ( !( Integer.toString( last ).equals( name ) && last == tp.getId() && last == previous + 1 ) )
					return false;
				previous = last;
			}

			// time-point names form a contiguous range from first to last
			// create <TimePoints> element
			elem.setAttribute( TIMEPOINTS_TYPE_ATTRIBUTE_NAME, TIMEPOINTS_RANGE_TYPE );
			elem.addContent( XmlHelpers.intElement( TIMEPOINTS_RANGE_FIRST, first ) );
			elem.addContent( XmlHelpers.intElement( TIMEPOINTS_RANGE_LAST, last ) );
			return true;
		}
		catch ( final NumberFormatException e )
		{
			// some timepoint name doesn't match. cannot save as "range".
			return false;
		}
	}

	/**
	 * Create {@link TimePointsPattern} from &lt;{@value XmlKeys#TIMEPOINTS_TAG}
	 * &gt; element of type {@value XmlKeys#TIMEPOINTS_RANGE_TYPE}
	 */
	protected void fromXmlRange( final TimePoints timepoints, final Element elem )
	{
		final int first = XmlHelpers.getInt( elem, TIMEPOINTS_RANGE_FIRST );
		final int last = XmlHelpers.getInt( elem, TIMEPOINTS_RANGE_LAST );
		final HashMap< Integer, TimePoint > map = new HashMap< Integer, TimePoint >();
		for ( int t = first; t <= last; ++t )
			map.put( t, new TimePoint( t ) );
		timepoints.setTimePoints( map );
	}

	/**
	 * Save {@link TimePoints} as a list of {@link TimePoint}.
	 *
	 * @param timepoints
	 * @param elem
	 */
	protected void toXmlList( final TimePoints timepoints, final Element elem )
	{
		elem.setAttribute( TIMEPOINTS_TYPE_ATTRIBUTE_NAME, TIMEPOINTS_LIST_TYPE );
		final XmlIoEntity< TimePoint > io = new XmlIoEntity< TimePoint >( TIMEPOINTS_TIMEPOINT_TAG, TimePoint.class );
		final ArrayList< TimePoint > tps = new ArrayList< TimePoint >( timepoints.getTimePoints().values() );
		Entity.sortById( tps );
		for ( final TimePoint tp : tps )
			elem.addContent( io.toXml( tp ) );
	}

	/**
	 * Create {@link TimePointsPattern} from &lt;{@value XmlKeys#TIMEPOINTS_TAG}
	 * &gt; element of type {@value XmlKeys#TIMEPOINTS_LIST_TYPE}
	 */
	protected void fromXmlList( final TimePoints timepoints, final Element elem ) throws SpimDataException
	{
		final XmlIoEntity< TimePoint > io = new XmlIoEntity< TimePoint >( TIMEPOINTS_TIMEPOINT_TAG, TimePoint.class );
		final HashMap< Integer, TimePoint > map = new HashMap< Integer, TimePoint >();
		for ( final Element c : elem.getChildren( TIMEPOINTS_TIMEPOINT_TAG ) )
		{
			final TimePoint tp = io.fromXml( c );
			map.put( tp.getId(), tp );
		}
		timepoints.setTimePoints( map );
	}
}
