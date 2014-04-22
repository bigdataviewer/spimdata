package mpicbg.spim.data.registration;

import org.jdom2.Element;

public interface ViewTransformGeneric extends ViewTransform
{
	/**
	 * initialize the transform from a &lt;{@value XmlKeys#VIEWTRANSFORM_TAG}&gt; DOM element.
	 */
	public void init( final Element elem );

	/**
	 * create a &lt;{@value XmlKeys#VIEWTRANSFORM_TAG}&gt; DOM element for this {@link ViewTransform}.
	 */
	public Element toXml();
}
