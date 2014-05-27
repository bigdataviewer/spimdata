package mpicbg.spim.data.generic.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mpicbg.spim.data.SpimDataException;

import org.jdom2.Element;

/**
 * Writes and reads collections of a specific {@link Entity} subclass as an XML section.
 *
 * @param <T>
 *            the entity type
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class XmlIoEntityMap< T extends Entity > extends XmlIoSingleton< HashMap< Integer, T > >
{
	private final XmlIoEntity< T > entityIo;

	/**
	 * @param tag
	 *            the tag-name of the element written (this is the tag-name of
	 *            the collection, not the entries, e.g., &lt;ViewSetups&gt; not
	 *            &lt;ViewSetup&gt;
	 * @param klass
	 *            the class of the concrete {@link Entity} type.
	 * @param entityIo
	 *            a reader/writer for the concrete {@link Entity} type.
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public XmlIoEntityMap( final String tag, final Class< T > klass, final XmlIoEntity< T > entityIo )
	{
		super( tag, ( Class ) HashMap.class );
		this.entityIo = entityIo;
		handledTags.add( entityIo.getTag() );
	}

	/**
	 * Sort the {@link Map#values() values} in the entity map by id and add them
	 * to a new {@link #getTag()} element.
	 *
	 * @param map maps id to entity.
	 */
	public Element toXml( final Map< Integer, ? extends T > map ) throws SpimDataException
	{
		final ArrayList< T > values = new ArrayList< T >( map.values() );
		return toXml( Entity.sortById( values ) );
	}

	/**
	 * Add the values to a new {@link #getTag()} element (in the iteration order of the collection).
	 */
	public Element toXml( final Collection< T > values ) throws SpimDataException
	{
		final Element elem = super.toXml();
		for ( final T value : values )
			elem.addContent( entityIo.toXml( value ) );
		return elem;
	}

	@Override
	public HashMap< Integer, T > fromXml( final Element elem ) throws SpimDataException
	{
		final HashMap< Integer, T > map = super.fromXml( elem );
		for ( final Element c : elem.getChildren( entityIo.getTag() ) )
		{
			final T value = entityIo.fromXml( c );
			map.put( value.getId(), value );
		}
		return map;
	}
}
