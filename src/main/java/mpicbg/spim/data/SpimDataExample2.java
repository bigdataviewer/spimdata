/*
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
package mpicbg.spim.data;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import mpicbg.spim.data.registration.ViewRegistration;
import mpicbg.spim.data.registration.ViewRegistrations;
import mpicbg.spim.data.registration.ViewTransform;
import mpicbg.spim.data.registration.ViewTransformAffine;
import mpicbg.spim.data.registration.XmlIoViewRegistrations;
import mpicbg.spim.data.sequence.Angle;
import mpicbg.spim.data.sequence.Channel;
import mpicbg.spim.data.sequence.FinalVoxelDimensions;
import mpicbg.spim.data.sequence.Illumination;
import mpicbg.spim.data.sequence.MissingViews;
import mpicbg.spim.data.sequence.SequenceDescription;
import mpicbg.spim.data.sequence.Tile;
import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.TimePoints;
import mpicbg.spim.data.sequence.TimePointsPattern;
import mpicbg.spim.data.sequence.ViewId;
import mpicbg.spim.data.sequence.ViewSetup;
import mpicbg.spim.data.sequence.VoxelDimensions;
import mpicbg.spim.data.sequence.XmlIoSequenceDescription;
import mpicbg.spim.data.sequence.XmlIoTimePoints;
import mpicbg.spim.data.sequence.XmlIoViewSetups;
import net.imglib2.Dimensions;
import net.imglib2.FinalDimensions;
import net.imglib2.realtransform.AffineTransform3D;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * The methods {@link #exampleViewSetups()}, {@link #exampleTimePoints()},
 * {@link #exampleViewRegistrations()}, and
 * {@link #exampleSequenceDescription()} construct {@link ViewSetup}s,
 * {@link TimePoints}, {@link ViewRegistrations}, and
 * {@link SequenceDescription} respectively. These are serialized to xml,
 * deserialized, and serialized again. The two serialized XML versions are
 * printed (and should be the same).
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class SpimDataExample2
{
	public static ArrayList< ViewSetup > createSetups()
	{
		final ArrayList< ViewSetup > setups = new ArrayList< ViewSetup >();

		final Channel c0 = new Channel( 0 );

		final Angle a0 = new Angle( 0, "0", 240, new double[] {1, 0, 0} );
		final Angle a1 = new Angle( 1, "120", 120, new double[] {1, 0, 0} );
		final Angle a2 = new Angle( 2, "240" );

		final Illumination i0 = new Illumination( 0 );

		final Tile t0 = new Tile( 0, "Tile0", new double[]{ 0.0, 0.0, 0.0 } );

		final Dimensions d0 = new FinalDimensions( 1000l, 1000l, 300l );
		final VoxelDimensions vd0 = new FinalVoxelDimensions( "px", 1, 1, 1 );
		setups.add( new ViewSetup( 0, "setup 0", d0, vd0, t0, c0, a0, i0 ) );
		setups.add( new ViewSetup( 1, "setup 1", d0, vd0, t0, c0, a1, i0 ) );
		setups.add( new ViewSetup( 2, "setup 2", null, null, t0, c0, a2, i0 ) );

		return setups;
	}

	public static TimePoints createTimePoints() throws ParseException
	{
//		final ArrayList< TimePoint > tps = new ArrayList< TimePoint >();
//		tps.add( new TimePoint( 10 ) );
//		tps.add( new TimePoint( 11 ) );
//		tps.add( new TimePoint( 12 ) );
//		tps.add( new TimePoint( 13 ) );
//		final TimePoints timepoints = new TimePoints( tps );
		final TimePoints timepoints = new TimePointsPattern( "1-100:20" );
		return timepoints;
	}

	public static SequenceDescription createSequenceDescription() throws ParseException
	{
		final TimePoints timepoints = createTimePoints();
		final ArrayList< ViewSetup > setups = createSetups();

		final ArrayList< ViewId > missing = new ArrayList< ViewId >();
		missing.add( new ViewId( 1, 0 ) );
		missing.add( new ViewId( 21, 1 ) );
		missing.add( new ViewId( 41, 3 ) );
		final MissingViews missingViews = new MissingViews( missing );

		final SequenceDescription sequence = new SequenceDescription( timepoints, setups, null, missingViews );
		return sequence;
	}

	public static ViewRegistrations createViewRegistrations()
	{
		final HashMap< ViewId, ViewRegistration > registrations = new HashMap< ViewId, ViewRegistration >();

		registrations.put( new ViewId( 21, 1 ), new ViewRegistration( 21, 1 ) );

		final AffineTransform3D t00 = new AffineTransform3D();
		t00.set( 1, 0, 0, 1.3, 0, 1, 0, 1.3, 0, 0, 1, 2.3 );
		registrations.put( new ViewId( 1, 0 ), new ViewRegistration( 0, 0, t00 ) );

		final ArrayList< ViewTransform > list = new ArrayList< ViewTransform >();
		list.add( new ViewTransformAffine( null, t00 ) );
		list.add( new ViewTransformAffine( "identity", new AffineTransform3D() ) );
		registrations.put( new ViewId( 41, 1 ), new ViewRegistration( 41, 1, list ) );

		final ViewRegistrations viewRegistrations = new ViewRegistrations( registrations );
		return viewRegistrations;
	}

	public static void exampleViewSetups() throws IOException, SpimDataException
	{
		final XmlIoViewSetups io = new XmlIoViewSetups();
		final ArrayList< ViewSetup > setups = createSetups();
		final Document doc = new Document( io.toXml( setups ) );
		new XMLOutputter( Format.getPrettyFormat() ).output( doc, System.out );

		final HashMap< Integer, ViewSetup > fromXml = io.fromXml( doc.getRootElement() );
		new XMLOutputter( Format.getPrettyFormat() ).output( new Document( new XmlIoViewSetups().toXml( fromXml ) ), System.out );
	}

	public static void exampleTimePoints() throws ParseException, IOException, SpimDataException
	{
		final XmlIoTimePoints io = new XmlIoTimePoints();
		final TimePoints timepoints = createTimePoints();
		final Document doc = new Document( io.toXml( timepoints ) );
		new XMLOutputter( Format.getPrettyFormat() ).output( doc, System.out );

		final TimePoints fromXml = io.fromXml( doc.getRootElement() );
		for ( final TimePoint tp : fromXml.getTimePointsOrdered() )
			System.out.println( tp.getName() );
		new XMLOutputter( Format.getPrettyFormat() ).output( new Document( new XmlIoTimePoints().toXml( fromXml ) ), System.out );
	}

	public static void exampleSequenceDescription() throws IOException, ParseException, SpimDataException
	{
		final XmlIoSequenceDescription io = new XmlIoSequenceDescription();
		final SequenceDescription sequence = createSequenceDescription();
		final Document doc = new Document( io.toXml( sequence, ( URI ) null ) );
		new XMLOutputter( Format.getPrettyFormat() ).output( doc, System.out );

		final SequenceDescription fromXml = io.fromXml( doc.getRootElement(), ( URI ) null );
		new XMLOutputter( Format.getPrettyFormat() ).output( new Document( new XmlIoSequenceDescription().toXml( fromXml, ( URI ) null ) ), System.out );
	}

	public static void exampleViewRegistrations() throws SpimDataException, IOException
	{
		final XmlIoViewRegistrations io = new XmlIoViewRegistrations();
		final ViewRegistrations registrations = createViewRegistrations();
		final Document doc = new Document( io.toXml( registrations ) );
		new XMLOutputter( Format.getPrettyFormat() ).output( doc, System.out );

		final ViewRegistrations fromXml = io.fromXml( doc.getRootElement() );
		new XMLOutputter( Format.getPrettyFormat() ).output( new Document( new XmlIoViewRegistrations().toXml( fromXml ) ), System.out );
	}

	public static void main( final String[] args ) throws IOException, SpimDataException, ParseException
	{
		exampleViewSetups();
		exampleTimePoints();
		exampleViewRegistrations();
		exampleSequenceDescription();
	}

}
