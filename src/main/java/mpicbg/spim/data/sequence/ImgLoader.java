package mpicbg.spim.data.sequence;

import java.io.File;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.numeric.real.FloatType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ImgLoader
{
	/**
	 * initialize the loader from a &lt;{@value XmlKeys#IMGLOADER_TAG}&gt; DOM element.
	 */
	public void init( final Element elem, final File basePath );

	/**
	 * create a &lt;{@value XmlKeys#IMGLOADER_TAG}&gt; DOM element for this loader.
	 */
	public Element toXml( final Document doc, final File basePath );

	// TODO: TypedImgLoader
	// min max
	// List of ImgLoader

	/**
	 * Get {@link FloatType} image normalized to the range [0,1]. Also updates the metadata of the ViewSetup if
	 * calibration and/or dimensions are set to -1 still. 
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the image.
	 * @param normalize
	 * 			  if the image should be normalized to [0,1] or not
	 * @return {@link FloatType} image
	 */
	public RandomAccessibleInterval< FloatType > getImage( ViewDescription< ?, ? > view, boolean normalize );

	/**
	 * Get {@link UnsignedShortType} un-normalized image. Also updates the metadata of the ViewSetup if
	 * calibration and/or dimensions are set to -1 still. 
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the image.
	 * @return {@link UnsignedShortType} image.
	 */
	public RandomAccessibleInterval< UnsignedShortType > getUnsignedShortImage( ViewDescription< ?, ? > view );
	
	/**
	 * Load only the metadata for this viewdescription and update the viewsetup accordingly. 
	 * This includes the calibration and dimensions.
	 * 
	 * @param view
	 *            timepoint and setup for which to retrieve the image.
	 * @return
	 */
	public boolean loadMetaData( ViewDescription< ?, ? > view );
}
