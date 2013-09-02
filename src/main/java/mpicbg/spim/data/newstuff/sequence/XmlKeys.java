package mpicbg.spim.data.newstuff.sequence;

/**
 * Definition of tag and attribute names.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class XmlKeys
{
	public static final String SEQUENCEDESCRIPTION_TAG = "SequenceDescription";
	public static final String SEQUENCEDESCRIPTION_VERSION_ATTRIBUTE_NAME = "version";
	public static final String SEQUENCEDESCRIPTION_VERSION_ATTRIBUTE_DEFAULT = "0.1";

	public static final String IMGLOADER_TAG = "ImageLoader";
	public static final String IMGLOADER_CLASS_ATTRIBUTE_NAME = "class";

	public static final String TIMEPOINTS_TAG = "Timepoints";
	public static final String TIMEPOINTS_TYPE_ATTRIBUTE_NAME = "type";
	public static final String TIMEPOINTS_RANGE_TYPE = "range";
	public static final String TIMEPOINTS_RANGE_FIRST = "first";
	public static final String TIMEPOINTS_RANGE_LAST = "last";

	public static final String MISSINGVIEWS_TAG = "MissingViews";
	public static final String MISSINGVIEW_TAG = "MissingView";
	public static final String MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME = "timepoint";
	public static final String MISSINGVIEW_SETUP_ATTRIBUTE_NAME = "setup";

	public static final String VIEWSETUPS_TAG = "ViewSetups";
	public static final String VIEWSETUP_TAG = "ViewSetup";
	public static final String VIEWSETUP_ID_TAG = "id";
	public static final String VIEWSETUP_ANGLE_TAG = "angle";
	public static final String VIEWSETUP_ILLUMINATION_TAG = "illumination";
	public static final String VIEWSETUP_CHANNEL_TAG = "channel";
	public static final String VIEWSETUP_WIDTH_TAG = "width";
	public static final String VIEWSETUP_HEIGHT_TAG = "height";
	public static final String VIEWSETUP_DEPTH_TAG = "depth";
	public static final String VIEWSETUP_UNIT_TAG = "pixelSizeUnit";
	public static final String VIEWSETUP_PIXELWIDTH_TAG = "pixelWidth";
	public static final String VIEWSETUP_PIXELHEIGHT_TAG = "pixelHeight";
	public static final String VIEWSETUP_PIXELDEPTH_TAG = "pixelDepth";
}
