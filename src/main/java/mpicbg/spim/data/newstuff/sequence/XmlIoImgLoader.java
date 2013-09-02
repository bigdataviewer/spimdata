package mpicbg.spim.data.newstuff.sequence;

import static mpicbg.spim.data.newstuff.sequence.XmlKeys.IMGLOADER_TAG;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		final ImgLoader il = ( ImgLoader ) Class.forName( imgLoader.getAttribute( XmlKeys.IMGLOADER_CLASS_ATTRIBUTE_NAME ) ).newInstance();
		il.init( imgLoader, basePath );
		return il;
	}

	public Element toXml( final Document doc, final File basePath, final ImgLoader imgLoader )
	{
		return imgLoader.toXml( doc, basePath );
	}
}
