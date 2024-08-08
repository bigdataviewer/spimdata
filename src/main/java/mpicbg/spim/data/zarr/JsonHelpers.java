package mpicbg.spim.data.zarr;

import java.net.URI;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;


public class JsonHelpers
{

	/**
	 * @param basePath if null put the absolute path, otherwise relative to this
	 */
	public static JsonElement pathElementURI( URI path, final URI basePath )
	{
		if ( basePath == null )
		{
			path = path.normalize();
		}
		else
		{
			// Try to build a relative path. If can't, make it absolute.
			path = basePath.relativize( path ).normalize();
			if ( path.toString().isEmpty() )
				path = URI.create( "." );
		}
		return new JsonPrimitive( path.toString() );
	}

}
