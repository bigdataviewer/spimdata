/*
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2022 BigDataViewer developers.
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
package mpicbg.spim.data;

/**
 * Definition of tag and attribute names.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 */
public class XmlKeys
{
	/**
	 * SpimData
	 */
	public static final String SPIMDATA_TAG = "SpimData";
	public static final String SPIMDATA_VERSION_ATTRIBUTE_NAME = "version";
	public static final String SPIMDATA_VERSION_ATTRIBUTE_CURRENT = "0.2";
	public static final String BASEPATH_TAG = "BasePath";

	/**
	 * Generic
	 */
	public static final String ID_TAG = "id";
	public static final String NAME_TAG = "name";

	/**
	 * SequenceDescription
	 */
	public static final String SEQUENCEDESCRIPTION_TAG = "SequenceDescription";

	public static final String IMGLOADER_TAG = "ImageLoader";
	public static final String IMGLOADER_FORMAT_ATTRIBUTE_NAME = "format";

	public static final String TILES_TAG = "Tiles";
	public static final String TILE_TAG = "Tile";
	public static final String TILE_LOCATION_TAG = "location";

	public static final String CHANNELS_TAG1 = "Channels";
	public static final String CHANNEL_TAG = "Channel";

	public static final String ILLUMINATIONS_TAG = "Illuminations";
	public static final String ILLUMINATION_TAG = "Illumination";

	public static final String ANGLES_TAG = "Angles";
	public static final String ANGLE_TAG = "Angle";
	public static final String ANGLE_ROTATIONAXIS_TAG = "axis";
	public static final String ANGLE_ROTATIONDEGREES_TAG = "degrees";

	public static final String VIEWSETUPS_TAG = "ViewSetups";
	public static final String VIEWSETUP_TAG = "ViewSetup";
	public static final String VIEWSETUP_ANGLE_ID_TAG = "angle";
	public static final String VIEWSETUP_ILLUMINATION_ID_TAG = "illumination";
	public static final String VIEWSETUP_CHANNEL_ID_TAG = "channel";
	public static final String VIEWSETUP_SIZE_TAG = "size";
	public static final String VIEWSETUP_VOXELSIZE_TAG = "voxelSize";
	public static final String VIEWSETUP_ATTRIBUTES_TAG = "attributes";

	public static final String VOXELDIMENSIONS_UNIT_TAG = "unit";
	public static final String VOXELDIMENSIONS_SIZE_TAG = "size";

	public static final String TIMEPOINTS_TAG = "Timepoints";
	public static final String TIMEPOINTS_TYPE_ATTRIBUTE_NAME = "type";
	public static final String TIMEPOINTS_RANGE_TYPE = "range";
	public static final String TIMEPOINTS_RANGE_FIRST = "first";
	public static final String TIMEPOINTS_RANGE_LAST = "last";
	public static final String TIMEPOINTS_LIST_TYPE = "list";
	public static final String TIMEPOINTS_TIMEPOINT_TAG = "Timepoint";
	public static final String TIMEPOINTS_PATTERN_TYPE = "pattern";
	public static final String TIMEPOINTS_PATTERN_TAG = "integerpattern";

	public static final String MISSINGVIEWS_TAG = "MissingViews";
	public static final String MISSINGVIEW_TAG = "MissingView";
	public static final String MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME = "timepoint";
	public static final String MISSINGVIEW_SETUP_ATTRIBUTE_NAME = "setup";

	public static final String ATTRIBUTES_TAG = "Attributes";
	public static final String ATTRIBUTES_NAME_ATTRIBUTE_NAME = "name";
	public static final String ATTRIBUTES_SPECIALIZED_ATTRIBUTE_NAME = "specialized";

	/**
	 * ViewRegistrations
	 */
	public static final String VIEWREGISTRATIONS_TAG = "ViewRegistrations";

	public static final String VIEWREGISTRATION_TAG = "ViewRegistration";
	public static final String VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME = "timepoint";
	public static final String VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME = "setup";

	public static final String VIEWTRANSFORM_TAG = "ViewTransform";
	public static final String VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME = "type";
	public static final String VIEWTRANSFORM_TYPE_VALUE_AFFINE = "affine";
	public static final String VIEWTRANSFORM_TYPE_VALUE_GENERIC = "generic";
	public static final String VIEWTRANSFORM_CLASS_ATTRIBUTE_NAME = "class";

	public static final String VIEWTRANSFORM_NAME_TAG = "Name";
	public static final String VIEWTRANSFORM_AFFINE_TAG = "affine";
}
