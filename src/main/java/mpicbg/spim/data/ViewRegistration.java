package mpicbg.spim.data;

import java.util.ArrayList;

import net.imglib2.realtransform.AffineTransform3D;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ViewRegistration
{
	/**
	 * The timepoint index.
	 */
	protected final int timepoint;

	/**
	 * The setup index (within the timepoint).
	 */
	protected final int setup;

	/**
	 * The affine registration model of this view mapping local into world
	 * coordinates.
	 */
	protected final AffineTransform3D model;
	
	protected final ArrayList< AffineTransform3D > transformList;

	/**
	 * Creates a new {@link ViewRegistration} object using a list of {@link AffineTransform3D}
	 * 
	 * @param timepointIndex
	 * @param setupIndex
	 * @param models
	 */
	public ViewRegistration( final int timepointIndex, final int setupIndex, final ArrayList< AffineTransform3D > models )
	{
		this.timepoint = timepointIndex;
		this.setup = setupIndex;
		this.model = models.get( 0 ).copy();
		this.transformList = models;
		
		concatenateAffineTransforms();
	}

	/**
	 * Creates a new {@link ViewRegistration} object using one {@link AffineTransform3D}
	 * 
	 * @param timepointIndex
	 * @param setupIndex
	 * @param model
	 */
	public ViewRegistration( final int timepointIndex, final int setupIndex, final AffineTransform3D model )
	{
		this.timepoint = timepointIndex;
		this.setup = setupIndex;
		this.model = model;
		this.transformList = new ArrayList<AffineTransform3D>();
		this.transformList.add( model.copy() );
	}

	/**
	 * Creates a new {@link ViewRegistration} object using the identity transformation
	 * 
	 * @param timepointIndex
	 * @param setupIndex
	 */
	public ViewRegistration( final int timepointIndex, final int setupIndex )
	{
		this ( timepointIndex, setupIndex, new AffineTransform3D() );
	}

	/**
	 * Load a ViewSetup from an XML file.
	 *
	 * @param elem
	 *            The "ViewRegistration" DOM element.
	 */
	public ViewRegistration( final Element elem )
	{
		this(
				Integer.parseInt( elem.getElementsByTagName( "timepoint" ).item( 0 ).getTextContent() ),
				Integer.parseInt( elem.getElementsByTagName( "setup" ).item( 0 ).getTextContent() ),
				XmlHelpers.loadAffineTransform3D( ( Element ) elem.getElementsByTagName( "affine" ).item( 0 ) )
		);
	}
	
	/**
	 * Computes the resulting {@link AffineTransform3D} when concatenating all transforms from the list
	 */
	public void concatenateAffineTransforms()
	{
		model.set( transformList.get( 0 ) );
		
		for ( int m = 1; m < transformList.size(); ++m )
			model.concatenate( transformList.get( m ) );
	}
	
	public void concatenateAffineTransform( final AffineTransform3D transform )
	{
		transformList.add( transform );
		model.concatenate( transform );
	}

	public void preconcatenateAffineTransform( final AffineTransform3D transform )
	{
		final ArrayList< AffineTransform3D > tmpList = new ArrayList<AffineTransform3D>();
		
		tmpList.addAll( transformList );
		transformList.clear();
		transformList.add( transform );
		transformList.addAll( tmpList );
		
		model.preConcatenate( transform );
	}

	/**
	 * Get the timepoint index.
	 *
	 * @return timepoint index
	 */
	public int getTimepointIndex()
	{
		return timepoint;
	}

	/**
	 * Get the setup index (within the timepoint).
	 *
	 * @return setup index
	 */
	public int getSetupIndex()
	{
		return setup;
	}

	/**
	 * Get the affine registration model of this view mapping local into world
	 * coordinates.
	 *
	 * @return registration model
	 */
	public AffineTransform3D getModel()
	{
		return model;
	}

	public Element toXml( final Document doc )
	{
		final Element elem = doc.createElement( "ViewRegistration" );

		elem.appendChild( XmlHelpers.intElement( doc, "timepoint", getTimepointIndex() ) );
		elem.appendChild( XmlHelpers.intElement( doc, "setup", getSetupIndex() ) );
		elem.appendChild( XmlHelpers.affineTransform3DElement( doc, "affine", getModel() ) );

		return elem;
	}
}
