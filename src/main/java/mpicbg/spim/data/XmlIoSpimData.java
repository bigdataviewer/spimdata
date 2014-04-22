package mpicbg.spim.data;

import static mpicbg.spim.data.SpimDataXmlKeys.BASEPATH_TAG;
import static mpicbg.spim.data.SpimDataXmlKeys.SPIMDATA_TAG;
import static mpicbg.spim.data.SpimDataXmlKeys.SPIMDATA_VERSION_ATTRIBUTE_DEFAULT;
import static mpicbg.spim.data.SpimDataXmlKeys.SPIMDATA_VERSION_ATTRIBUTE_NAME;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mpicbg.spim.data.registration.ViewRegistrations;
import mpicbg.spim.data.registration.XmlIoViewRegistrations;
import mpicbg.spim.data.sequence.SequenceDescription;
import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.ViewSetup;
import mpicbg.spim.data.sequence.XmlIoSequenceDescription;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlIoSpimData< T extends TimePoint, V extends ViewSetup >
{
	protected final XmlIoSequenceDescription< T, V > xmlSequenceDescription;

	protected final XmlIoViewRegistrations xmlViewRegistrations;

	public XmlIoSpimData(
			final XmlIoSequenceDescription< T, V > xmlSequenceDescription,
			final XmlIoViewRegistrations xmlViewRegistrations )
	{
		this.xmlSequenceDescription = xmlSequenceDescription;
		this.xmlViewRegistrations = xmlViewRegistrations;
	}

	public static XmlIoSpimData< TimePoint, ViewSetup > createDefault()
	{
		return new XmlIoSpimData< TimePoint, ViewSetup >( XmlIoSequenceDescription.createDefault(), new XmlIoViewRegistrations() );
	}

	// TODO: wrap InstantiationException, IllegalAccessException, ClassNotFoundException into IllegalArgumentException?
	public SpimData< T, V > load( final String xmlFilename ) throws JDOMException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final SAXBuilder sax = new SAXBuilder();
		final Document doc = sax.build( xmlFilename );
		final Element root = doc.getRootElement();

		if ( root.getName() != SPIMDATA_TAG )
			throw new RuntimeException( "expected <" + SPIMDATA_TAG + "> root element. wrong file?" );

		final File basePath = loadBasePath( root, new File( xmlFilename ) );

		return fromXml( root, basePath );
	}

	public void save( final SpimData< T, V > spimData, final String xmlFilename ) throws IOException
	{
		final File xmlFileDirectory = new File( xmlFilename ).getParentFile();
		final Document doc = new Document( toXml( spimData, xmlFileDirectory ) );
		final XMLOutputter xout = new XMLOutputter( Format.getPrettyFormat() );
		xout.output( doc, new FileWriter( xmlFilename ) );
	}

	protected File loadBasePath( final Element root, final File xmlFile )
	{
		File xmlFileParentDirectory = xmlFile.getParentFile();
		if ( xmlFileParentDirectory == null )
			xmlFileParentDirectory = new File( "." );
		return XmlHelpers.loadPath( root, BASEPATH_TAG, ".", xmlFileParentDirectory );
	}

	protected String getVersion( final Element root )
	{
		final String versionAttr = root.getAttributeValue( SPIMDATA_VERSION_ATTRIBUTE_NAME );
		final String version;
		if ( versionAttr.isEmpty() )
		{
			System.out.println( "<" + SPIMDATA_TAG + "> does not specify \"" + SPIMDATA_VERSION_ATTRIBUTE_NAME + "\" attribute." );
			System.out.println( "assuming \"" + SPIMDATA_VERSION_ATTRIBUTE_DEFAULT + "\"" );
			version = SPIMDATA_VERSION_ATTRIBUTE_DEFAULT;
		}
		else
			version = versionAttr;
		return version;
	}

	// TODO: wrap InstantiationException, IllegalAccessException, ClassNotFoundException into IllegalArgumentException?
	public SpimData< T, V > fromXml( final Element root, final File basePath ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		// TODO: why is this commented out?
//		String version = getVersion( root );

		Element elem = root.getChild( xmlSequenceDescription.getTagName() );
		if ( elem == null )
			throw new IllegalArgumentException( "no <" + xmlSequenceDescription.getTagName() + "> element found." );
		final SequenceDescription< T, V > seq = xmlSequenceDescription.fromXml( elem, basePath );

		elem = root.getChild( xmlViewRegistrations.getTagName() );
		if ( elem == null )
			throw new IllegalArgumentException( "no <" + xmlViewRegistrations.getTagName() + "> element found." );
		final ViewRegistrations reg = xmlViewRegistrations.fromXml( elem );

		return new SpimData< T, V >( basePath, seq, reg );
	}

	public Element toXml( final SpimData< T, V > spimData, final File xmlFileDirectory )
	{
		final Element root = new Element( SPIMDATA_TAG );
		root.setAttribute( SPIMDATA_VERSION_ATTRIBUTE_NAME, SPIMDATA_VERSION_ATTRIBUTE_DEFAULT );
		root.addContent( XmlHelpers.pathElement( "BasePath", spimData.getBasePath(), xmlFileDirectory ) );
		root.addContent( xmlSequenceDescription.toXml( spimData.getSequenceDescription(), spimData.getBasePath() ) );
		root.addContent( xmlViewRegistrations.toXml( spimData.getViewRegistrations() ) );
		return root;
	}
}
