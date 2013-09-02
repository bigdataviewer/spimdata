package mpicbg.spim.data.newstuff;

import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.BASEPATH_TAG;
import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.SPIMDATA_TAG;
import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.SPIMDATA_VERSION_ATTRIBUTE_DEFAULT;
import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.SPIMDATA_VERSION_ATTRIBUTE_NAME;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mpicbg.spim.data.XmlHelpers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class SpimData
{
	protected final File xmlFile;

	protected final Element root;

	/**
	 * The &lt;SpimData&gt; version.
	 */
	protected final String version;

	/**
	 * Relative paths in the XML should be interpreted with respect to this.
	 */
	protected final File basePath;

	public SpimData( final String xmlFilename ) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		xmlFile = new File( xmlFilename );

		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document dom = db.parse( xmlFilename );
		root = dom.getDocumentElement();

		if ( root.getNodeName() != SPIMDATA_TAG )
			throw new RuntimeException( "expected <" + SPIMDATA_TAG + "> root element. wrong file?" );

		final String versionAttr = root.getAttribute( SPIMDATA_VERSION_ATTRIBUTE_NAME );
		if ( versionAttr.isEmpty() )
		{
			System.out.println( "<" + SPIMDATA_TAG + "> does not specify \"" + SPIMDATA_VERSION_ATTRIBUTE_NAME + "\" attribute." );
			System.out.println( "assuming \"" + SPIMDATA_VERSION_ATTRIBUTE_DEFAULT + "\"" );
			version = SPIMDATA_VERSION_ATTRIBUTE_DEFAULT;
		}
		else
			version = versionAttr;

		basePath = loadBasePath( root );
	}

	protected File loadBasePath( final Element root ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		File xmlFileParentDirectory = xmlFile.getParentFile();
		if ( xmlFileParentDirectory == null )
			xmlFileParentDirectory = new File( "." );
		return XmlHelpers.loadPath( root, BASEPATH_TAG, ".", xmlFileParentDirectory );
	}

	/**
	 * Get the base path of the sequence. Relative paths in the XML sequence
	 * description are interpreted with respect to this.
	 *
	 * @return the base path of the sequence
	 */
	public File getBasePath()
	{
		return basePath;
	}

}