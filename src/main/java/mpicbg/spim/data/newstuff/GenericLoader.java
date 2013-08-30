package mpicbg.spim.data.newstuff;

import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.GENERIC_FACTORY_ATTRIBUTE_NAME;

import java.util.HashMap;

import org.w3c.dom.Element;

// TODO: versioned factories
public class GenericLoader
{
	public static interface Factory< T >
	{
		T create( Element element );

		Class< T > productClass();
	}

	protected static HashMap< String, Factory< ? > > factories = new HashMap< String, Factory<?> >();

	public static Object load( final Element element, final String version )
	{
		final String nodeName = element.getNodeName();
		final String factoryAttr = element.getAttribute( GENERIC_FACTORY_ATTRIBUTE_NAME );
		Factory< ? > factory = null;
		if ( factoryAttr.isEmpty() )
			factory = factories.get( nodeName );
		else
		{
			try
			{
				final Class< ? > clazz = Class.forName( factoryAttr );
				factory = ( Factory<?> ) clazz.newInstance();
			}
			catch ( final Exception e )
			{
			}
		}
		return factory == null ? null : factory.create( element );
	}

	public static void registerFactory( final String tag, final Factory< ? > factory )
	{
		factories.put( tag, factory );
	}
}