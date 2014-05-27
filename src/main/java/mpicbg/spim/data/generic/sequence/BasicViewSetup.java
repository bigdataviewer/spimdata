package mpicbg.spim.data.generic.sequence;

import java.util.HashMap;
import java.util.Map;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.ViewSetupAttributes;
import mpicbg.spim.data.sequence.VoxelDimensions;
import net.imglib2.Dimensions;

/**
 * An view setup is an {@link Entity} that may have a name, image size, and voxel size.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class BasicViewSetup extends Entity
{
	/**
	 * The name of this setup or null if it is not set.
	 */
	private String name;

	/**
	 * The {@link Dimensions} of images from this setup or null if it is not
	 * set.
	 */
	private Dimensions size;

	/**
	 * The {@link VoxelDimensions} of images from this setup or null if it is
	 * not set.
	 */
	private VoxelDimensions voxelSize;

	/**
	 * Map attribute name to attribute entity.
	 *
	 * TODO: private or protected?
	 * TODO: final?
	 * TODO: create in constructor?
	 * TODO: getAttributes() unmodifiable?
	 */
	private Map< String, Entity > attributes;

	public BasicViewSetup( final int id, final String name, final Dimensions size, final VoxelDimensions voxelSize )
	{
		super( id );
		this.name = name;
		this.size = size;
		this.voxelSize = voxelSize;
		this.attributes = new HashMap< String, Entity >();
	}

	/**
	 * Whether this setup has has a {@link #getName()}.
	 *
	 * @return true, if this setup has a name.
	 */
	public boolean hasName()
	{
		return name != null;
	}

	/**
	 * Get the name of this setup.
	 *
	 * @return the name of this setup or null if it is not set.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Whether this setup has has a {@link #getSize()}.
	 *
	 * @return true, if this setup has a size.
	 */
	public boolean hasSize()
	{
		return size != null;
	}

	/**
	 * Get the {@link Dimensions} of images from this setup.
	 *
	 * @return the {@link Dimensions} of images from this setup or null if it
	 *         is not set.
	 */
	public Dimensions getSize()
	{
		return size;
	}

	/**
	 * Whether this setup has has a {@link #getVoxelSize()}.
	 *
	 * @return true, if this setup has a voxel size.
	 */
	public boolean hasVoxelSize()
	{
		return voxelSize != null;
	}

	/**
	 * Get the {@link VoxelDimensions} of images from this setup.
	 *
	 * @return The {@link VoxelDimensions} of images from this setup or null if it is
	 * not set.
	 */
	public VoxelDimensions getVoxelSize()
	{
		return voxelSize;
	}

	public Map< String, Entity > getAttributes()
	{
		return attributes;
	}

	@SuppressWarnings( "unchecked" )
	public < T extends Entity > T getAttribute( final Class< T > attributeClass )
	{
		return ( T ) attributes.get( ViewSetupAttributes.getNameForClass( attributeClass ) );
	}

	public < T extends Entity > void setAttribute( final T attribute )
	{
		attributes.put( ViewSetupAttributes.getNameForClass( attribute.getClass() ), attribute );
	}

	protected void setName( final String name )
	{
		this.name = name;
	}

	protected void setSize( final Dimensions size )
	{
		assert size.numDimensions() == 3;
		this.size = size;
	}

	protected void setVoxelSize( final VoxelDimensions voxelSize )
	{
		assert voxelSize.numDimensions() == 3;
		this.voxelSize = voxelSize;
	}

	protected void setAttributes( final Map< String, Entity > attributes )
	{
		this.attributes = attributes;
	}

	protected BasicViewSetup()
	{}
}
