package mpicbg.spim.data.generic.base;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.SpimDataInstantiationException;

import org.jdom2.Element;

/**
 * Writes and reads objects of a specific class as XML elements. This is for
 * sections in the XML that only occur once, i.e., &lt;ViewSetups&gt; or
 * &lt;ViewRegistrations&gt;.
 *
 * Derived classes should add the tag names of children that they handle to the
 * set {@link #handledTags}. When reading an (the) object from an XML element,
 * unhandled children of the element are stored. When writing an object to an XML
 * element these children will be added. This provides a way to read / modify /
 * write spimdata XML files without knowing the details of possible extensions.
 *
 * @param <T>
 *            the entity type
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class XmlIoSingleton< T >
{
	private final String tag;

	private final Constructor< T > ctor;

	protected final HashSet< String > handledTags;

	private final ArrayList< Element > unhandledContent;

	/**
	 * Construct a {@link XmlIoSingleton} that writes and reads objects of the
	 * specified {@link Class} as XML elements with the specified tag name. This
	 * takes care of unhandled tags.
	 *
	 * @param tag
	 *            the tag name of the XML elements that are written by this
	 *            {@link XmlIoEntity}.
	 * @param klass
	 *            the class of the objects that are read by this
	 *            {@link XmlIoEntity}.
	 */
	public XmlIoSingleton( final String tag, final Class< T > klass )
	{
		this.tag = tag;
		try
		{
			this.ctor = klass.getDeclaredConstructor();
		}
		catch ( final Exception e )
		{
			throw new IllegalArgumentException( klass.getSimpleName() + " must have a no-argument constructor.", e );
		}
		ctor.setAccessible( true );
		handledTags = new HashSet< String >();
		unhandledContent = new ArrayList< Element >();
	}

	/**
	 * Get the tag name of the XML element that is written by this
	 * {@link XmlIoSingleton}.
	 *
	 * @return the tag name of the XML elements that is written.
	 */
	public String getTag()
	{
		return tag;
	}

	/**
	 * Create an XML element from the given object.
	 */
	protected Element toXml()
	{
		final Element elem = new Element( tag );
		for ( final Element c : unhandledContent )
			elem.addContent( c.clone() );
		return elem;
	}

	/**
	 * Create an object from the given element. Unhandled children of the
	 * element are stored and added to any XML element that is
	 * {@link #toXml(Object) created}.
	 *
	 * Calling this method clears all unhandled content from previous
	 * invocations.
	 */
	protected T fromXml( final Element elem ) throws SpimDataException
	{
		T entity;
		try
		{
			entity = ctor.newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "Cannot instantiate " + ctor.getDeclaringClass().getName(), e );
		}

		unhandledContent.clear();
		for ( final Element c : elem.getChildren() )
			if ( !handledTags.contains( c.getName() ) )
				unhandledContent.add( c.clone() );

		return entity;
	}

}
