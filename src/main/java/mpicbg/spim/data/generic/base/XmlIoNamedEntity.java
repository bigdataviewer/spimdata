package mpicbg.spim.data.generic.base;

import static mpicbg.spim.data.XmlKeys.NAME_TAG;
import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.XmlKeys;

import org.jdom2.Element;

/**
 * {@link XmlIoEntity} for {@link NamedEntity} that also handles &lt;{@value XmlKeys#NAME_TAG}&gt;
 *
 * @param <T>
 *            the {@link NamedEntity} type
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class XmlIoNamedEntity< T extends NamedEntity > extends XmlIoEntity< T >
{
	public XmlIoNamedEntity( final String tag, final Class< T > klass )
	{
		super( tag, klass );
		handledTags.add( NAME_TAG );
	}

	@Override
	public Element toXml( final T entity )
	{
		final Element elem = super.toXml( entity );
		elem.addContent( XmlHelpers.textElement( NAME_TAG, entity.getName() ) );
		return elem;
	}

	@Override
	public T fromXml( final Element elem ) throws SpimDataException
	{
		final T entity = super.fromXml( elem );
		entity.setName( elem.getChildText( NAME_TAG ) );
		return entity;
	}
}
