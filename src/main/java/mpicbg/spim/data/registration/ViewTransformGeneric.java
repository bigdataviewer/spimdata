package mpicbg.spim.data.registration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ViewTransformGeneric extends ViewTransform
{
	/**
	 * initialize the transform from a &lt;{@value XmlKeys#VIEWTRANSFORM_TAG}&gt; DOM element.
	 */
	public void init( final Element elem );

	/**
	 * create a &lt;{@value XmlKeys#VIEWTRANSFORM_TAG}&gt; DOM element for this {@link ViewTransform}.
	 */
	public Element toXml( final Document doc );
}
