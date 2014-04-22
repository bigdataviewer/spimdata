package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.IMGLOADER_CLASS_ATTRIBUTE_NAME;
import static mpicbg.spim.data.sequence.XmlKeys.IMGLOADER_TAG;

import java.io.File;

import org.jdom2.Element;

public class XmlIoImgLoader
{
	/**
	 * TODO
	 */
	public String getTagName()
	{
		return IMGLOADER_TAG;
	}

	public ImgLoader fromXml( final Element imgLoader, final File basePath ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final ImgLoader il = ( ImgLoader ) Class.forName( imgLoader.getAttributeValue( IMGLOADER_CLASS_ATTRIBUTE_NAME ) ).newInstance();
		il.init( imgLoader, basePath );
		return il;
	}

	public Element toXml( final File basePath, final ImgLoader imgLoader )
	{
		return imgLoader.toXml( basePath );
	}
}
