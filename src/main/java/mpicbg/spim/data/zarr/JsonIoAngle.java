/*-
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2024 BigDataViewer developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package mpicbg.spim.data.zarr;

import static mpicbg.spim.data.XmlKeys.ANGLE_ROTATIONAXIS_TAG;
import static mpicbg.spim.data.XmlKeys.ANGLE_ROTATIONDEGREES_TAG;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.sequence.Angle;
import mpicbg.spim.data.sequence.Tile;

@JsonViewSetupAttributeIo( name = "angle", type = Angle.class )
public class JsonIoAngle implements JsonIoViewSetupAttribute< Angle >
{
	@Override
	public Angle deserialize( final JsonElement json, final JsonDeserializationContext context ) throws JsonParseException
	{
		final JsonObject jsonObject = json.getAsJsonObject();
		final int id = jsonObject.get( "id" ).getAsInt();
		final String name = jsonObject.get( "name" ).getAsString();
		double rotationAngleDegrees = Double.NaN;
		double[] rotationAxis = null;
		if ( jsonObject.has( "degrees" ) && jsonObject.has( "axis" ) )
		{
			rotationAngleDegrees = jsonObject.get( "degrees" ).getAsDouble();
			rotationAxis = context.deserialize( jsonObject.get( "axis" ), double[].class );
		}
		return new Angle( id, name, rotationAngleDegrees, rotationAxis );
	}

	@Override
	public JsonElement serialize( final Angle angle, final JsonSerializationContext context )
	{
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty( "id", angle.getId() );
		jsonObject.addProperty( "name", angle.getName() );
		if ( angle.hasRotation() )
		{
			jsonObject.add( "axis", context.serialize( angle.getRotationAxis() ) );
			jsonObject.add( "degrees", context.serialize( angle.getRotationAngleDegrees() ) );
		}
		return jsonObject;
	}
}
