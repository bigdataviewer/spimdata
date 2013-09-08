package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_RANGE_FIRST;
import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_PATTERN_TYPE;
import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_RANGE_LAST;
import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_RANGE_TYPE;
import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_TYPE_ATTRIBUTE_NAME;
import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_PATTERN_STRING;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mpicbg.spim.data.XmlHelpers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * TODO
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class XmlIoTimePoints extends XmlIoTimePointsAbstract< TimePoint >
{
	public static enum TimepointsDefType
	{
		RANGE( "range" );

		private final String name;

		public String getName()
		{
			return name;
		}

		private TimepointsDefType( final String name )
		{
			this.name = name;
		}
	}

	/**
	 * Load a list of {@link TimePoint}s from the given DOM element.
	 *
	 * @param element
	 *            a {@value XmlKeys#TIMEPOINTS_TAG} DOM element.
	 * @return list of timepoints loaded from xml.
	 */
	@Override
	public ArrayList< TimePoint > fromXml( final Element timepoints )
	{
		final String type = timepoints.getAttribute( TIMEPOINTS_TYPE_ATTRIBUTE_NAME );
		if ( TIMEPOINTS_RANGE_TYPE.equals( type ) )
		{
			final int first = Integer.parseInt( timepoints.getElementsByTagName( TIMEPOINTS_RANGE_FIRST ).item( 0 ).getTextContent() );
			final int last = Integer.parseInt( timepoints.getElementsByTagName( TIMEPOINTS_RANGE_LAST ).item( 0 ).getTextContent() );
			final ArrayList< TimePoint > tp = new ArrayList< TimePoint >();
			for ( int t = first; t <= last; ++t )
				tp.add( new TimePoint( tp.size(), Integer.toString( t ) ) );
			return tp;
		}
		else if ( TIMEPOINTS_PATTERN_TYPE.equals( type ) )
		{
			final String integerPattern = timepoints.getElementsByTagName( TIMEPOINTS_PATTERN_STRING ).item( 0 ).getTextContent();
			try 
			{
				final ArrayList< Integer > ints = IntegerPattern.parseIntegerString( integerPattern );
				final ArrayList< TimePoint > tp = new ArrayList< TimePoint >();
				
				for ( int t : ints )
					tp.add( new TimePoint( tp.size(), Integer.toString( t ) ) );
				
				return tp;
				
			} 
			catch ( final ParseException e )
			{
				throw new RuntimeException( "cannot parse <" + TIMEPOINTS_TAG + "> pattern: " + integerPattern );
			}
		}
		else
		{
			throw new RuntimeException( "unknown <" + TIMEPOINTS_TAG + "> type: " + type );
		}
	}

	/**
	 * TODO: Add support for non-contiguous range of time-points. (Timepoints
	 * type="list")
	 *
	 * @param doc
	 * @param sequence
	 */
	@Override
	public Element toXml( final Document doc, final List< TimePoint > timepoints )
	{
		if ( timepoints.size() == 0 )
			throw new IllegalArgumentException( "sequence must have at least one timepoint" );

		final Element tp = toXmlTryRange( doc, timepoints );
		if ( tp == null )
			throw new RuntimeException( "non-contiguous time-point range not implemented." );

		return tp;
	}


	/**
	 * Try to save as {@link TimePoint} list as a range. For this to work,
	 * time-point {@link TimePoint#getName() names} must form a contiguous range
	 * of integers.
	 *
	 * @param doc
	 * @param timepoints
	 * @return
	 */
	protected Element toXmlTryRange( final Document doc, final List< TimePoint > timepoints )
	{
		try
		{
			final Iterator< TimePoint > iter = timepoints.iterator();
			String name = iter.next().getName();
			final int first = Integer.parseInt( name );
			if ( ! Integer.toString( first ).equals( name ) )
				return null;
			int last = first;
			while ( iter.hasNext() )
			{
				name = iter.next().getName();
				last = Integer.parseInt( name );
				if ( ! Integer.toString( last ).equals( name ) )
					return null;
			}

			// time-point names form a contiguous range from first to last
			// create <TimePoints> element
			final Element tp = doc.createElement( TIMEPOINTS_TAG );
			tp.setAttribute( TIMEPOINTS_TYPE_ATTRIBUTE_NAME, TIMEPOINTS_RANGE_TYPE );
			tp.appendChild( XmlHelpers.intElement( doc, TIMEPOINTS_RANGE_FIRST, first ) );
			tp.appendChild( XmlHelpers.intElement( doc, TIMEPOINTS_RANGE_LAST, last ) );
			return tp;
		}
		catch ( final NumberFormatException e )
		{
			// some timepoint name doesn't match. cannot save as "range".
			return null;
		}
	}
}

