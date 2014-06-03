package mpicbg.spim.data.sequence;

import java.util.Map;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.ViewSetupAttributes;
import mpicbg.spim.data.generic.sequence.BasicViewSetup;
import net.imglib2.Dimensions;

/**
 * A collection of parameters describing the setup for a particular stack coming
 * from a SPIM microscope (angle, illumination direction, etc). A
 * {@link ViewSetup} is a {@link BasicViewSetup} that must have a channel,
 * angle, and illumination direction.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class ViewSetup extends BasicViewSetup implements Comparable< ViewSetup >
{
	private Channel channel;

	private Angle angle;

	private Illumination illumination;

	protected static String channelAttributeKey = ViewSetupAttributes.getNameForClass( Channel.class );

	protected static String angleAttributeKey = ViewSetupAttributes.getNameForClass( Angle.class );

	protected static String illuminationAttributeKey = ViewSetupAttributes.getNameForClass( Illumination.class );

	/**
	 * TODO
	 *
	 * @param id
	 * @param name
	 * @param size
	 * @param voxelSize
	 * @param channel
	 * @param angle
	 * @param illumination
	 */
	public ViewSetup(
			final int id,
			final String name,
			final Dimensions size,
			final VoxelDimensions voxelSize,
			final Channel channel,
			final Angle angle,
			final Illumination illumination )
	{
		super( id, name, size, voxelSize );
		setChannel( channel );
		setAngle( angle );
		setIllumination( illumination );
	}

	/**
	 * Get the unique id of this {@link ViewSetup}.
	 *
	 * @return unique id.
	 */
	@Override
	public int getId()
	{
		return super.getId();
	}

	/**
	 * Whether this setup has has a {@link #getName()}.
	 *
	 * @return true, if this setup has a name.
	 */
	@Override
	public boolean hasName()
	{
		return super.hasName();
	}

	/**
	 * Get the name of this setup.
	 *
	 * @return the name of this setup or null if it is not set.
	 */
	@Override
	public String getName()
	{
		return super.getName();
	}

	/**
	 * Whether this setup has has a {@link #getSize()}.
	 *
	 * @return true, if this setup has a size.
	 */
	@Override
	public boolean hasSize()
	{
		return super.hasSize();
	}

	/**
	 * Get the {@link Dimensions} of images from this setup.
	 *
	 * @return the {@link Dimensions} of images from this setup or null if it
	 *         is not set.
	 */
	@Override
	public Dimensions getSize()
	{
		return super.getSize();
	}

	/**
	 * Set the {@link Dimensions} of images from this setup.
	 */
	@Override
	public void setSize( final Dimensions size )
	{
		super.setSize( size );
	}

	/**
	 * Whether this setup has has a {@link #getVoxelSize()}.
	 *
	 * @return true, if this setup has a voxel size.
	 */
	@Override
	public boolean hasVoxelSize()
	{
		return super.hasVoxelSize();
	}

	/**
	 * Get the {@link VoxelDimensions} of images from this setup.
	 *
	 * @return The {@link VoxelDimensions} of images from this setup or null if it is
	 * not set.
	 */
	@Override
	public VoxelDimensions getVoxelSize()
	{
		return super.getVoxelSize();
	}

	/**
	 * Set the {@link VoxelDimensions} of images from this setup.
	 */
	@Override
	public void setVoxelSize( final VoxelDimensions voxelSize )
	{
		super.setVoxelSize( voxelSize );
	}

	/**
	 * Get the {@link Channel}.
	 *
	 * @return the channel
	 */
	public Channel getChannel()
	{
		return channel;
	}

	/**
	 * Set the {@link Channel}.
	 *
	 * @param channel
	 *            the channel
	 */
	protected void setChannel( final Channel channel )
	{
		this.channel = channel;
		getAttributes().put( channelAttributeKey, channel );
	}

	/**
	 * Get stage rotation {@link Angle}.
	 *
	 * @return the angle.
	 */
	public Angle getAngle()
	{
		return angle;
	}

	/**
	 * Set the rotation {@link Angle}
	 *
	 * @param angle
	 *            the angle.
	 */
	protected void setAngle( final Angle angle )
	{
		this.angle = angle;
		getAttributes().put( angleAttributeKey, angle );
	}

	/**
	 * Get the {@link Illumination} direction.
	 *
	 * @return the illumination direction
	 */
	public Illumination getIllumination()
	{
		return illumination;
	}

	/**
	 * Set the {@link Illumination} direction.
	 *
	 * @param illumination the illumination direction
	 */
	protected void setIllumination( final Illumination illumination )
	{
		this.illumination = illumination;
		getAttributes().put( illuminationAttributeKey, illumination );
	}

	/**
	 * Compares the {@link #getId() ids}.
	 */
	@Override
	public int compareTo( final ViewSetup o )
	{
		return getId() - o.getId();
	}

	/**
	 * Set the attributes map. And set the individual angle, channel, and
	 * illumination fields.
	 */
	@Override
	protected void setAttributes( final Map< String, Entity > attributes )
	{
		super.setAttributes( attributes );
		setChannel( ( Channel ) attributes.get( channelAttributeKey ) );
		setAngle( ( Angle ) attributes.get( angleAttributeKey ) );
		setIllumination( ( Illumination ) attributes.get( illuminationAttributeKey ) );
	}

	ViewSetup()
	{}
}
