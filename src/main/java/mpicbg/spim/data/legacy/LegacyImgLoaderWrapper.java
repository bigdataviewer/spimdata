package mpicbg.spim.data.legacy;

import java.util.HashMap;

import mpicbg.spim.data.generic.sequence.ImgLoaderHint;
import mpicbg.spim.data.sequence.ImgLoader;
import mpicbg.spim.data.sequence.SetupImgLoader;
import mpicbg.spim.data.sequence.ViewId;
import mpicbg.spim.data.sequence.VoxelDimensions;
import net.imglib2.Dimensions;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

//@Deprecated
public class LegacyImgLoaderWrapper< T, I extends LegacyImgLoader< T > > implements ImgLoader
{
	protected final I legacyImgLoader;

	private final HashMap< Integer, SetupImgLoaderWrapper > setupImgLoaders;

	public LegacyImgLoaderWrapper( final I legacyImgLoader )
	{
		this.legacyImgLoader = legacyImgLoader;
		setupImgLoaders = new HashMap< Integer, SetupImgLoaderWrapper >();
	}

	@Override
	public synchronized SetupImgLoaderWrapper getSetupImgLoader( final int setupId )
	{
		SetupImgLoaderWrapper sil = setupImgLoaders.get( setupId );
		if ( sil == null )
		{
			sil = new SetupImgLoaderWrapper( setupId );
			setupImgLoaders.put( setupId, sil );
		}
		return sil;
	}

	public class SetupImgLoaderWrapper implements SetupImgLoader< T >
	{
		private final int setupId;

		protected SetupImgLoaderWrapper( final int setupId )
		{
			this.setupId = setupId;
		}

		@Override
		public RandomAccessibleInterval< T > getImage( final int timepointId, final ImgLoaderHint... hints )
		{
			return legacyImgLoader.getImage( new ViewId( timepointId, setupId ) );
		}

		@Override
		public T getImageType()
		{
			return legacyImgLoader.getImageType();
		}

		@Override
		public RandomAccessibleInterval< FloatType > getFloatImage( final int timepointId, final boolean normalize, final ImgLoaderHint... hints )
		{
			return legacyImgLoader.getFloatImage( new ViewId( timepointId, setupId ), normalize );
		}

		@Override
		public Dimensions getImageSize( final int timepointId )
		{
			return legacyImgLoader.getImageSize( new ViewId( timepointId, setupId ) );
		}

		@Override
		public VoxelDimensions getVoxelSize( final int timepointId )
		{
			return legacyImgLoader.getVoxelSize( new ViewId( timepointId, setupId ) );
		}
	}
}
