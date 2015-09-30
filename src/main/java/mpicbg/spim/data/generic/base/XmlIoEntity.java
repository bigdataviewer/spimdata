package mpicbg.spim.data.generic.base;

import static mpicbg.spim.data.XmlKeys.ID_TAG;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.SpimDataInstantiationException;
import mpicbg.spim.data.XmlHelpers;

import org.jdom2.Element;

/**
 * Writes and reads objects of a specific subclass of {@link Entity} as XML
 * elements.
 *
 * When reading/writing an object, {@link XmlIoEntity} takes care of the
 * {@link Entity#getId() id} of the object. Other attributes are handled in
 * derived classes.
 *
 * Derived classes should add the tag names of children that they handle to the
 * set {@link #handledTags}. When reading an object from an XML element,
 * unhandled children of the element are stored as associated with the objects
 * {@link Entity#getId() id}. When writing an object with the same id to an XML
 * element these children will be added. This provides a way to read / modify /
 * write spimdata XML files without knowing the details of possible extensions.
 *
 * @param <T>
 *            the entity type
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class XmlIoEntity< T extends Entity >
{
	private final String tag;

	private final Constructor< T > ctor;

	protected final HashSet< String > handledTags;

	private final HashMap< Integer, ArrayList< Element > > unhandledContent;

	private final Class< T > klass;

	/**
	 * Construct a {@link XmlIoEntity} that writes and reads objects of the
	 * specified {@link Class} as XML elements with the specified tag name. This
	 * reads/writes the {@link Entity#getId() id} of the object. Other
	 * attributes are handled in derived classes.
	 *
	 * @param tag
	 *            the tag name of the XML elements that are written by this
	 *            {@link XmlIoEntity}.
	 * @param klass
	 *            the class of the objects that are read by this
	 *            {@link XmlIoEntity}.
	 */
	public XmlIoEntity( final String tag, final Class< T > klass )
	{
		this.tag = tag;
		this.klass = klass;
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
		handledTags.add( ID_TAG );
		unhandledContent = new HashMap< Integer, ArrayList< Element > >();
	}

	/**
	 * Get the tag name of the XML element that is written by this
	 * {@link XmlIoEntity}.
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
	public Element toXml( final T object )
	{
		final Element elem = new Element( tag );
		elem.addContent( XmlHelpers.intElement( ID_TAG, object.getId() ) );
		final ArrayList< Element > unhandled = unhandledContent.get( object.getId() );
		if ( unhandled != null )
			for ( final Element c : unhandled )
				elem.addContent( c.clone() );
		return elem;
	}

	/**
	 * Create an object from the given element. Unhandled children of the
	 * element are stored as associated with the objects {@link Entity#getId()
	 * id} and added to any XML element {@link #toXml(Entity) created} for that
	 * id.
	 */
	public T fromXml( final Element elem ) throws SpimDataException
	{
		T entity;
		try
		{
			entity = ctor.newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "Cannot instantiate Entity " + getEntityClass().getName(), e );
		}
		entity.setId( XmlHelpers.getInt( elem, ID_TAG ) );

		final ArrayList< Element > unhandled = new ArrayList< Element >();
		for ( final Element c : elem.getChildren() )
			if ( !handledTags.contains( c.getName() ) )
				unhandled.add( c.clone() );
		if ( !unhandled.isEmpty() )
			unhandledContent.put( entity.getId(), unhandled );

		return entity;
	}

	public Class< T > getEntityClass()
	{
		return klass;
	}
}
