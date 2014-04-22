package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.SEQUENCEDESCRIPTION_TAG;

import java.io.File;
import java.util.ArrayList;

import org.jdom2.Element;

public class XmlIoSequenceDescription< T extends TimePoint, V extends ViewSetup >
{
	protected final XmlIoTimePointsAbstract< T > xmlTimePoints;

	protected final XmlIoViewSetupsAbstract< V > xmlViewSetups;

	protected final XmlIoMissingViews xmlMissingViews;

	protected final XmlIoImgLoader xmlImgLoader;

	// TODO: this is just so that the code runs for testing if set to true
	public static boolean catchClassNotFoundException = false;

	/**
	 * TODO
	 */
	public String getTagName()
	{
		return SEQUENCEDESCRIPTION_TAG;
	}

	public XmlIoSequenceDescription(
			final XmlIoTimePointsAbstract< T > xmlTimePoints,
			final XmlIoViewSetupsAbstract< V > xmlViewSetups,
			final XmlIoMissingViews xmlMissingViews,
			final XmlIoImgLoader xmlImgLoader )
	{
		this.xmlTimePoints = xmlTimePoints;
		this.xmlViewSetups = xmlViewSetups;
		this.xmlMissingViews = xmlMissingViews;
		this.xmlImgLoader = xmlImgLoader;
	}

	public static XmlIoSequenceDescription< TimePoint, ViewSetup > createDefault()
	{
		return new XmlIoSequenceDescription< TimePoint, ViewSetup >( new XmlIoTimePoints(), new XmlIoViewSetups(), new XmlIoMissingViews(), new XmlIoImgLoader() );
	}

	/**
	 * Read a {@link SequenceDescription} from the specified DOM element.
	 *
	 * @param element
	 *            a {@value XmlKeys#SEQUENCEDESCRIPTION_TAG} DOM element.
	 * @return sequence represented by the specified DOM element.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public SequenceDescription< T, V > fromXml( final Element sequenceDescription, final File basePath ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Element elem = sequenceDescription.getChild( xmlTimePoints.getTagName() );
		if ( elem == null )
			throw new IllegalArgumentException( "no <" + xmlTimePoints.getTagName() + "> element found." );
		final TimePoints< T > timepoints = xmlTimePoints.fromXml( elem );

		elem = sequenceDescription.getChild( xmlViewSetups.getTagName() );
		if ( elem == null )
			throw new IllegalArgumentException( "no <" + xmlViewSetups.getTagName() + "> element found." );
		final ArrayList< V > setups = xmlViewSetups.fromXml( elem );

		MissingViews missingViews = null;
		elem = sequenceDescription.getChild( xmlMissingViews.getTagName() );
		if ( elem != null )
			missingViews = xmlMissingViews.fromXml( elem );

		ImgLoader imgLoader = null;
		elem = sequenceDescription.getChild( xmlImgLoader.getTagName() );

		// TODO: this is really ugly but the only way right now test it without having an ImgLoader implementation
		if ( catchClassNotFoundException )
		{
			try
			{
				if ( elem != null )
					imgLoader = xmlImgLoader.fromXml( elem, basePath );
			}
			catch ( final ClassNotFoundException e )
			{
					e.printStackTrace( System.err );
			}
		}
		else
		{
			if ( elem != null )
				imgLoader = xmlImgLoader.fromXml( elem, basePath );
		}

		return new SequenceDescription< T, V >( timepoints, setups, missingViews, imgLoader );
	}

	public Element toXml( final SequenceDescription< T, V > sequenceDescription, final File basePath )
	{
		final Element elem = new Element( SEQUENCEDESCRIPTION_TAG );
		if ( sequenceDescription.getImgLoader() != null )
			elem.addContent( xmlImgLoader.toXml( basePath, sequenceDescription.getImgLoader() ) );
		elem.addContent( xmlViewSetups.toXml( sequenceDescription.getViewSetups() ) );
		elem.addContent( xmlTimePoints.toXml( sequenceDescription.getTimePoints() ) );
		if ( sequenceDescription.getMissingViews() != null && sequenceDescription.getMissingViews().missingViews.size() > 0 )
			elem.addContent( xmlMissingViews.toXml( sequenceDescription.getMissingViews() ) );
		return elem;
	}
}
