package mpicbg.spim.data.generic.sequence;

import net.imglib2.img.array.ArrayImg;

/**
 * Default set of {@link ImgLoaderHint}.
 *
 * <p>
 * Note that hints may be ignored by {@link BasicImgLoader} implementations.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public enum ImgLoaderHints implements ImgLoaderHint
{
	/**
	 * Indicates that the caller wants to access all pixels of an image.
	 * Therefore, the {@link BasicImgLoader} should decide to load the image
	 * immediately instead of lazily loading and caching. This may result in
	 * faster loading and/or access (if for example the image data fits into an
	 * {@link ArrayImg}). However, it will also require more memory than
	 * cache-backed images.
	 *
	 * <p>
	 * Note that hints may be ignored by {@link BasicImgLoader} implementations.
	 */
	LOAD_COMPLETELY
}
