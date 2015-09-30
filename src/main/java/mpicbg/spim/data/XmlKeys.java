package mpicbg.spim.data;

/**
 * Definition of tag and attribute names.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
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

	public static final String CHANNELS_TAG = "Channels";
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
