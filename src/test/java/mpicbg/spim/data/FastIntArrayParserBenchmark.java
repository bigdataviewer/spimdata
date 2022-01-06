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

import net.imglib2.util.StopWatch;

import java.util.Random;
import java.util.StringJoiner;

/**
 * Benchmark that compares the performance of {@link FastIntArrayParser} to
 * the implementation previously used in {@link XmlHelpers}.
 */
public class FastIntArrayParserBenchmark {

	public static void main(String... args) {
		String text = exampleText();
		StopWatch watch;

		watch = StopWatch.createAndStart();
		runSimpleParser(text);
		System.out.println("Simple parser runtime: " + watch);

		watch = StopWatch.createAndStart();
		FastIntArrayParser.toIntArray(text);
		System.out.println("FastIntArrayParser runtime: " + watch);
	}

	private static String exampleText() {
		StringJoiner joiner = new StringJoiner(" ");
		Random random = new Random();
		for (int i = 0; i < 100_000; i++)
			joiner.add(Integer.toString(random.nextInt()));
		return joiner.toString();
	}

	private static int[] runSimpleParser(String text) {
		// Simple but slow way to parse a space separated list of integers.
		String[] entries = text.split("\\s+");
		if (entries.length == 1 && entries[0].isEmpty()) return new int[0];
		final int[] array = new int[entries.length];
		for (int i = 0; i < entries.length; ++i)
			array[i] = Integer.parseInt(entries[i]);
		return array;
	}
}
