package mpicbg.spim.data;

import java.io.File;
import java.io.IOException;

import net.imglib2.Dimensions;
import net.imglib2.FinalDimensions;
import net.imglib2.realtransform.AffineGet;
import net.imglib2.realtransform.AffineTransform3D;

import org.jdom2.Element;

public class XmlHelpers
{
	public static Element textElement( final String name, final String text )
	{
		return new Element( name ).addContent( text );
	}

	public static String getText( final Element parent, final String name )
	{
		return parent.getChildText( name );
	}

	public static String getText( final Element parent, final String name, final String defaultValue )
	{
		final String text = parent.getChildText( name );
		return text == null ? defaultValue : text;
	}

	public static Element intElement( final String name, final int value )
	{
		return new Element( name ).addContent( Integer.toString( value ) );
	}

	public static int getInt( final Element parent, final String name )
	{
		return Integer.parseInt( parent.getChildText( name ) );
	}

	public static int getInt( final Element parent, final String name, final int defaultValue )
	{
		final String text = parent.getChildText( name );
		return text == null ? defaultValue : Integer.parseInt( text );
	}

	public static Element booleanElement( final String name, final boolean value )
	{
		return new Element( name ).addContent( Boolean.toString( value ) );
	}

	public static boolean getBoolean( final Element parent, final String name )
	{
		return Boolean.parseBoolean( parent.getChildText( name ) );
	}

	public static boolean getBoolean( final Element parent, final String name, final boolean defaultValue )
	{
		final String text = parent.getChildText( name );
		return text == null ? defaultValue : Boolean.parseBoolean( text );
	}

	public static Element doubleElement( final String name, final double value )
	{
		return new Element( name ).addContent( Double.toString( value ) );
	}

	public static double getDouble( final Element parent, final String name )
	{
		return Double.parseDouble( parent.getChildText( name ) );
	}

	public static double getDouble( final Element parent, final String name, final double defaultValue )
	{
		final String text = parent.getChildText( name );
		return text == null ? defaultValue : Double.parseDouble( text );
	}

	/**
	 * Append a double array as space-separated list of values
	 */
	public static Element doubleArrayElement( final String name, final double[] value )
	{
		final StringBuffer valueString = new StringBuffer();
		if ( value.length > 0 )
		{
			valueString.append( value[ 0 ] );
			for ( int i = 1; i < value.length; ++i )
			{
				valueString.append( " " );
				valueString.append( value[ i ] );
			}
		}

		return new Element( name ).addContent( valueString.toString() );
	}

	public static double[] getDoubleArray( final Element parent, final String name )
	{
		final String text = parent.getChildText( name );
		final String[] entries = text.split( "\\s+" );
		if ( entries.length == 1 && entries[ 0 ].isEmpty() )
			return new double[ 0 ];
		final double[] array = new double[ entries.length ];
		for ( int i = 0; i < entries.length; ++i )
			array[ i ] = Double.parseDouble( entries[ i ] );
		return array;
	}

	public static double[] getDoubleArray( final Element parent, final String name, final double[] defaultValue )
	{
		return parent.getChild( name ) == null ? defaultValue : getDoubleArray( parent, name );
	}

	public static Element intArrayElement( final String name, final int[] value )
	{
		final StringBuffer valueString = new StringBuffer();
		if ( value.length > 0 )
		{
			valueString.append( value[ 0 ] );
			for ( int i = 1; i < value.length; ++i )
			{
				valueString.append( " " );
				valueString.append( value[ i ] );
			}
		}

		return new Element( name ).addContent( valueString.toString() );
	}

	public static int[] getIntArray( final Element parent, final String name )
	{
		final String text = parent.getChildText( name );
		final String[] entries = text.split( "\\s+" );
		if ( entries.length == 1 && entries[ 0 ].isEmpty() )
			return new int[ 0 ];
		final int[] array = new int[ entries.length ];
		for ( int i = 0; i < entries.length; ++i )
			array[ i ] = Integer.parseInt( entries[ i ] );
		return array;
	}

	public static int[] getIntArray( final Element parent, final String name, final int[] defaultValue )
	{
		return parent.getChild( name ) == null ? defaultValue : getIntArray( parent, name );
	}

	public static Element longArrayElement( final String name, final long[] value )
	{
		final StringBuffer valueString = new StringBuffer();
		if ( value.length > 0 )
		{
			valueString.append( value[ 0 ] );
			for ( int i = 1; i < value.length; ++i )
			{
				valueString.append( " " );
				valueString.append( value[ i ] );
			}
		}

		return new Element( name ).addContent( valueString.toString() );
	}

	public static long[] getLongArray( final Element parent, final String name )
	{
		final String text = parent.getChildText( name );
		final String[] entries = text.split( "\\s+" );
		if ( entries.length == 1 && entries[ 0 ].isEmpty() )
			return new long[ 0 ];
		final long[] array = new long[ entries.length ];
		for ( int i = 0; i < entries.length; ++i )
			array[ i ] = Long.parseLong( entries[ i ] );
		return array;
	}

	public static long[] getLongArray( final Element parent, final String name, final long[] defaultValue )
	{
		return parent.getChild( name ) == null ? defaultValue : getLongArray( parent, name );
	}

	public static Element dimensionsElement( final String name, final Dimensions size )
	{
		final long[] array = new long[ size.numDimensions() ];
		size.dimensions( array );
		return longArrayElement( name, array );
	}

	public static Dimensions getDimensions( final Element parent, final String name )
	{
		return FinalDimensions.wrap( getLongArray( parent, name ) );
	}

	public static Dimensions getDimensions( final Element parent, final String name, final Dimensions defaultValue )
	{
		return parent.getChild( name ) == null ? defaultValue : getDimensions( parent, name );
	}

	public static Element affineTransform3DElement( final String name, final AffineGet value )
	{
		assert value.numDimensions() == 3;
		final double[] v = value.getRowPackedCopy();
		return doubleArrayElement( name, v );
	}

	public static AffineTransform3D getAffineTransform3D( final Element parent, final String name )
	{
		return getAffineTransform3D( parent, name, null );
	}

	public static AffineTransform3D getAffineTransform3D( final Element parent, final String name, final AffineTransform3D defaultValue )
	{
		final double[] values = getDoubleArray( parent, name, null );
		if ( values == null )
		{
			return defaultValue;
		}
		else if ( values.length == 12 )
		{
			final AffineTransform3D a = new AffineTransform3D();
			a.set( values );
			return a;
		}
		else
			throw new NumberFormatException( "Inappropriate parameters for " + AffineTransform3D.class.getCanonicalName() );
	}

	public static File loadPath( final Element parent, final String name, final String defaultRelativePath, final File basePath )
	{
		final Element elem = parent.getChild( name );
		final String path = ( elem == null ) ? defaultRelativePath : elem.getText();
		final boolean isRelative = ( elem == null ) ? true : elem.getAttributeValue( "type" ).equals( "relative" );
		if ( isRelative )
		{
			if ( basePath == null )
				return null;
			else
				return new File( basePath + "/" + path );
		}
		else
			return new File( path );
	}

	public static File loadPath( final Element parent, final String name, final File basePath )
	{
		final Element elem = parent.getChild( name );
		if ( elem == null )
			return null;
		final String path = elem.getText();
		final boolean isRelative = elem.getAttributeValue( "type" ).equals( "relative" );
		if ( isRelative )
		{
			if ( basePath == null )
				return null;
			else
				return new File( basePath + "/" + path );
		}
		else
			return new File( path );
	}

	/**
	 * @param basePath if null put the absolute path, otherwise relative to this
	 */
	public static Element pathElement( final String name, final File path, final File basePath )
	{
		final Element e = new Element( name );

		if ( basePath == null )
			e.setText( path.getAbsolutePath() );
		else
		{
			e.setAttribute( "type", "relative" );
			e.setText( getRelativePath( path, basePath ).getPath() );
		}

		return e;
	}

	public static File getRelativePath( final File file, final File relativeToThis )
	{
		try
		{
			return getRelativePath( file.getCanonicalFile(), relativeToThis.getCanonicalFile(), "" );
		}
		catch ( final IOException e )
		{
			e.printStackTrace();
			return null;
		}
	}

	private static File getRelativePath( final File file, final File relativeToThis, final String relativeInitial )
	{
		File parent = file;
		String relative = null;
		while( parent != null )
		{
			if ( parent.equals( relativeToThis ) )
			{
				return new File( relativeInitial + ( relative == null ? "." : relative ) );
			}
			relative = parent.getName() + ( relative == null ? "" : "/" + relative );
			parent = parent.getParentFile();
		}
		final File toParent = relativeToThis.getParentFile();
		if ( toParent == null )
			return null;
		else
			return getRelativePath( file, toParent, "../" + relativeInitial );
	}
}
