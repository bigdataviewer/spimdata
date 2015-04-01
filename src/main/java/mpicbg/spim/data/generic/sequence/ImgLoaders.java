package mpicbg.spim.data.generic.sequence;

import java.util.HashMap;

import mpicbg.spim.data.SpimDataInstantiationException;

import org.scijava.annotations.Index;
import org.scijava.annotations.IndexItem;

public class ImgLoaders
{
	private static final HashMap< Class< ? extends BasicImgLoader >, String > imgLoaderClass_to_XmlIoClassName = new HashMap< Class< ? extends BasicImgLoader >, String >();

	private static final HashMap< String, String > format_to_XmlIoClassName = new HashMap< String, String >();

	private static boolean buildWasCalled = false;

	private static void build()
	{
		buildWasCalled = true;
		try
		{
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			final Index< ImgLoaderIo > annotationIndex = Index.load( ImgLoaderIo.class, classLoader );
			for ( final IndexItem< ImgLoaderIo > item : annotationIndex )
			{
				format_to_XmlIoClassName.put( item.annotation().format(), item.className() );
				imgLoaderClass_to_XmlIoClassName.put( item.annotation().type(), item.className() );
			}
		}
		catch( final Exception e )
		{
			throw new RuntimeException( "problem accessing annotation index", e );
		}
	}

	public static XmlIoBasicImgLoader< ? > createXmlIoForFormat( final String format ) throws SpimDataInstantiationException
	{
		if ( !buildWasCalled )
			build();

		final String className = format_to_XmlIoClassName.get( format );
		if ( className == null )
			throw new SpimDataInstantiationException( "could not find " + XmlIoBasicImgLoader.class.getSimpleName() + " implementation for format " + format );

		try
		{
			return ( XmlIoBasicImgLoader< ? > ) Class.forName( className ).newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "could not create " + XmlIoBasicImgLoader.class.getSimpleName() + " instance " + className, e );
		}
	}

	@SuppressWarnings( "unchecked" )
	public static < T extends BasicImgLoader >
	XmlIoBasicImgLoader< T > createXmlIoForImgLoaderClass( final Class< T > klass ) throws SpimDataInstantiationException
	{
		if ( !buildWasCalled )
			build();

		final String className = imgLoaderClass_to_XmlIoClassName.get( klass );
		if ( className == null )
			throw new SpimDataInstantiationException( "could not find " + XmlIoBasicImgLoader.class.getSimpleName() + " implementation for " + klass.getSimpleName() );

		try
		{
			return ( XmlIoBasicImgLoader< T > ) Class.forName( className ).newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "could not create " + XmlIoBasicImgLoader.class.getSimpleName() + " instance " + className, e );
		}
	}

	/**
	 * Register an {@link XmlIoBasicImgLoader} class manually. This can be used
	 * in case scijava-common is not present or cannot be used for other
	 * reasons.
	 *
	 * For example I had the case where I wanted to use a jar build by Eclipse
	 * for a partially compiling project in Fiji. In this case (because it's
	 * build by eclipse, the annotation index was not present.
	 *
	 * @param xmlIoClass
	 *            the class to register.
	 */
	public static void registerManually( final Class< ? extends XmlIoBasicImgLoader< ? > > xmlIoClass )
	{
		final ImgLoaderIo annotation = xmlIoClass.getAnnotation( ImgLoaderIo.class );
		if ( annotation != null )
		{
			final String format = annotation.format();
			final Class< ? extends BasicImgLoader > imgLoaderClass = annotation.type();
			imgLoaderClass_to_XmlIoClassName.put( imgLoaderClass, xmlIoClass.getName() );
			format_to_XmlIoClassName.put( format, xmlIoClass.getName() );
		}
	}
}
