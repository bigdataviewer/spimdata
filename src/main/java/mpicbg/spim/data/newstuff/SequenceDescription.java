package mpicbg.spim.data.newstuff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mpicbg.spim.data.ImgLoader;

/**
 * A SPIM sequence consisting of a list of timepoints and a list of view setups.
 * Every (timepoint, setup) pair is a view (i.e., an image stack acquired at that time with that setup).
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class SequenceDescription< T extends TimePoint, V extends ViewSetup >
{
	/**
	 * timepoint id for every timepoint index.
	 */
	final protected ArrayList< T > timepoints;

	/**
	 * angle and illumination setup for every view-setup index.
	 */
	final protected ArrayList< V > setups;

	final protected MissingViews missingViews;

	/**
	 * Relative paths in the XML sequence description are interpreted with respect to this.
	 */
	final protected File basePath;

	/**
	 * load images for every view. might be null.
	 */
	final protected ImgLoader imgLoader;

	final protected ArrayList< ViewDescription< T, V > > viewDescriptions;

	public SequenceDescription( final List< ? extends T > timepoints, final List< ? extends V > setups, final MissingViews missingViews, final File basePath, final ImgLoader imgLoader )
	{
		this.timepoints = new ArrayList< T >( timepoints );
		this.setups = new ArrayList< V >( setups );
		this.missingViews = missingViews;
		this.basePath = basePath;
		this.imgLoader = imgLoader;
		this.viewDescriptions = createViewDescriptions();
	}

	protected ArrayList< ViewDescription< T, V > > createViewDescriptions()
	{
		final ArrayList< ViewDescription< T, V > > viewDescriptions = new ArrayList< ViewDescription< T, V > >();
		// TODO: fill viewDescriptions
		return missingViews.markMissingViews( viewDescriptions );
	}

	/**
	 * Get number of timepoints in this sequence.
	 *
	 * @return number of timepoints
	 */
	final public int numTimePoints()
	{
		return timepoints.size();
	}

	/**
	 * Get number of view setups in this sequence.
	 *
	 * @return number of view setups
	 */
	final public int numViewSetups()
	{
		return setups.size();
	}

	/**
	 * Get the base path of the sequence. Relative paths in the XML sequence
	 * description are interpreted with respect to this.
	 *
	 * @return the base path of the sequence
	 */
	public synchronized File getBasePath()
	{
		return basePath;
	}

	public ViewDescription< T, V > getViewDescription( final int timepoint, final int setup )
	{
		return null;
	}
}
