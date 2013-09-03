package mpicbg.spim.data.sequence;

/**
 * A collection of parameters describing the setup for a particular view, e.g.
 * angle, illumination direction, etc.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class ViewSetup implements Comparable< ViewSetup >
{
	/**
	 * This unique id is the index of this {@link ViewSetup} in {@link SequenceDescription#setups}.
	 */
	private final int id;

	/**
	 * Angle in degrees.
	 */
	private int angle;

	/**
	 * Illumination direction index.
	 */
	private int illumination;

	/**
	 * Channel index
	 */
	private int channel;

	/**
	 * width of stack slice in pixels.
	 */
	private int width;

	/**
	 * height of stack slice in pixels.
	 */
	private int height;

	/**
	 * number of slices.
	 */
	private int depth;

	/**
	 * Unit for pixel calibration
	 */
	private String unit;

	/**
	 * width of a pixel in {@link #unit}.
	 */
	private double pixelWidth;

	/**
	 * height of a pixel in {@link #unit}.
	 */
	private double pixelHeight;

	/**
	 * depth of a pixel in {@link #unit}.
	 */
	private double pixelDepth;

	public ViewSetup(
			final int id,
			final int angle,
			final int illumination,
			final int channel,
			final int width,
			final int height,
			final int depth,
			final String unit,
			final double pixelWidth,
			final double pixelHeight,
			final double pixelDepth )
	{
		this.id = id;
		this.angle = angle;
		this.illumination = illumination;
		this.channel = channel;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.unit = unit;
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
		this.pixelDepth = pixelDepth;
	}

	public ViewSetup(
			final int id,
			final int angle,
			final int illumination,
			final int channel )
	{
		this( id, angle, illumination, channel, -1, -1, -1, "", 1, 1, 1 );
	}

	/**
	 * Get ViewSetup index.
	 *
	 * @return index.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Get stage rotation angle in degrees.
	 *
	 * @return angle in degrees
	 */
	public int getAngle()
	{
		return angle;
	}

	/**
	 * Set stage rotation angle in degrees.
	 *
	 * @param angle
	 *            angle in degrees
	 */
	public void setAngle( final int angle )
	{
		this.angle = angle;
	}

	/**
	 * Get index of illumination direction.
	 *
	 * @return illumination direction index
	 */
	public int getIllumination()
	{
		return illumination;
	}

	/**
	 * Set index of illumination direction.
	 *
	 * @param illumination
	 *            illumination direction index
	 */
	public void setIllumination( final int illumination )
	{
		this.illumination = illumination;
	}

	/**
	 * Get channel index.
	 *
	 * @return channel index
	 */
	public int getChannel()
	{
		return channel;
	}

	/**
	 * Set channel index.
	 *
	 * @param channel
	 *            channel index
	 */
	public void setChannel( final int channel )
	{
		this.channel = channel;
	}

	/**
	 * Get width of stack slice in pixels.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @return width in pixels
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Set width of stack slice in pixels.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @param width
	 *            width in pixels
	 */
	public void setWidth( final int width )
	{
		this.width = width;
	}

	/**
	 * Get height of stack slice in pixels.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @return height in pixels
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Set height of stack slice in pixels.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @param height
	 *            height in pixels
	 */
	public void setHeight( final int height )
	{
		this.height = height;
	}

	/**
	 * Get number of slices.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @return number of slices
	 */
	public int getDepth()
	{
		return depth;
	}

	/**
	 * Set number of slices.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @param depth
	 *            number of slices
	 */
	public void setDepth( final int depth )
	{
		this.depth = depth;
	}

	/**
	 * Get unit for pixel calibration.
	 *
	 * @return unit for pixel calibration.
	 */
	public String getPixelSizeUnit()
	{
		return unit;
	}

	/**
	 * Set unit for pixel calibration.
	 *
	 * @param unit
	 *            unit for pixel calibration.
	 */
	public void setPixelSizeUnit( final String unit )
	{
		this.unit = unit;
	}

	/**
	 * Get width of a pixel in unit {@link #getPixelSizeUnit()}.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @return width of a pixel in unit {@link #getPixelSizeUnit()}.
	 */
	public double getPixelWidth()
	{
		return pixelWidth;
	}

	/**
	 * Set width of a pixel in unit {@link #getPixelSizeUnit()}.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @param pixelWidth
	 *            width of a pixel in unit {@link #getPixelSizeUnit()}.
	 */
	public void setPixelWidth( final double pixelWidth )
	{
		this.pixelWidth = pixelWidth;
	}

	/**
	 * Get height of a pixel in unit {@link #getPixelSizeUnit()}.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @return height of a pixel in unit {@link #getPixelSizeUnit()}.
	 */
	public double getPixelHeight()
	{
		return pixelHeight;
	}

	/**
	 * Set height of a pixel in unit {@link #getPixelSizeUnit()}.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @param pixelHeight
	 *            height of a pixel in unit {@link #getPixelSizeUnit()}.
	 */
	public void setPixelHeight( final double pixelHeight )
	{
		this.pixelHeight = pixelHeight;
	}

	/**
	 * Get depth of a pixel in unit {@link #getPixelSizeUnit()}.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @return depth of a pixel in unit {@link #getPixelSizeUnit()}.
	 */
	public double getPixelDepth()
	{
		return pixelDepth;
	}

	/**
	 * Set depth of a pixel in unit {@link #getPixelSizeUnit()}.
	 * A value of <em>-1</em> means that this property is undefined.
	 *
	 * @param pixelDepth
	 *            depth of a pixel in unit {@link #getPixelSizeUnit()}.
	 */
	public void setPixelDepth( final double pixelDepth )
	{
		this.pixelDepth = pixelDepth;
	}

	@Override
	public int compareTo( final ViewSetup o )
	{
		return id - o.id;
	}
}
