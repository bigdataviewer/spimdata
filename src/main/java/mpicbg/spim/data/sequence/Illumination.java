package mpicbg.spim.data.sequence;

/**
 * Defines a illumination direction which is part of the ViewSetup
 * 
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 */
public class Illumination 
{	
	/**
	 * The name of this illumination direction, for example used to replace it in filenames when opening
	 * individual 3d-stacks 
	 * (e.g. SPIM_TL20_Angle45_illum2.tif or SPIM_TL5_left.czi)
	 */
	protected String name;
	
	/**
	 * The unique id of this illumination direction, defines for example the position in a file
	 */
	protected int id;
	
	public Illumination( final int id, final String name )
	{
		this.name = name;
		this.id = id;
	}

	public Illumination( final int id )
	{
		this( id, Integer.toString( id ) );
	}
	
	public void setName( final String name ) { this.name = name; }
	public String getName() { return name; }
	public int getId() { return id; }
}
