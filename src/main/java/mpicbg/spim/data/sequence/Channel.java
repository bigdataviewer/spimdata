package mpicbg.spim.data.sequence;

/**
 * Defines a channel which is part of the ViewSetup
 * 
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 */
public class Channel implements Comparable< Channel > 
{	
	/**
	 * The name of this channel, for example used to replace it in filenames when opening
	 * individual 3d-stacks 
	 * (e.g. SPIM_TL20_Angle45_Channel5.tif or SPIM_TL5_GFP.czi)
	 */
	protected String name;
	
	/**
	 * The unique id of this channel, defines for example the position in a file
	 */
	protected int id;
	
	public Channel( final int id, final String name )
	{
		this.name = name;
		this.id = id;
	}

	public Channel( final int id )
	{
		this( id, Integer.toString( id ) );
	}
	
	public void setName( final String name ) { this.name = name; }
	public String getName() { return name; }
	public int getId() { return id; }

	@Override
	public int compareTo( final Channel channel ) { return getId() - channel.getId(); }
}
