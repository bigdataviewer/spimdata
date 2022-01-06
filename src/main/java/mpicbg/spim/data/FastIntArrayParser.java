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

import gnu.trove.list.array.TIntArrayList;

/**
 * Fast parser for a space separated list of integers.
 * <pre>{@code
 * int[] x = FastIntsParser.parseInts(" 26 14    +7 -9 0 42 ");
 *
 * // Will return:
 * // x == new int[] { 26, 14, 7, -9, 0, 42 };
 * }</pre>
 *
 * @author Matthias Arzt
 */
class FastIntArrayParser {

	private static final char EOL = 0;
	private final String text;
	private char symbol;
	private int cursorPosition;
	private boolean success = true;

	private FastIntArrayParser(String text) {
		this.text = text;
		this.cursorPosition = 0;
		this.symbol = text.charAt(cursorPosition);
	}

	/**
	 * Parses the give text as a space separated list of integers.
	 * <pre>{@code
	 * int[] x = FastIntsParser.parseInts(" 26 14    +7 -9 0 42 ");
	 *
	 * // Will return:
	 * // x == new int[] { 26, 14, 7, -9, 0, 42 };
	 * }</pre>
	 * @return The list of integers.
	 * @throws NumberFormatException on syntax error.
	 */
	public static int[] toIntArray(String text) {
		FastIntArrayParser parser = new FastIntArrayParser(text);
		parser.parseSpaces();
		int[] ints = parser.parseInts();
		if (!parser.eol()) throw new NumberFormatException();
		return ints;
	}

	/**
	 * Parses a space separated list of integers.
	 * Consumes as many characters possible, until there is an
	 * unexpected character / syntax error.
	 * @return The list of integers. Returns an empty array if nothing could be parsed.
	 * {@link #success} is always set to true.
	 */
	private int[] parseInts() {
		TIntArrayList values = new TIntArrayList();
		while (true) {
			int val = parseInt();
			if (success) values.add(val);
			else break;
			parseSpaces();
			if (!success) break;
			parseSpaces();
		}
		success = true;
		return values.toArray();
	}

	/**
	 * Consumes as many space characters as possible from the text.
	 * {@link #success} is always set to true.
	 */
	private void parseSpaces() {
		do {
			parseSpace();
		}
		while (success);
		success = true;
	}

	/**
	 * Parses an integer number in decimal format. For example:
	 * "436", "-78", "0", "+56". Success is set to true if successful.
	 */
	private int parseInt() {
		parseCharacter('-');
		if (success) return -parsePositiveInt();
		parseCharacter('+');
		return parsePositiveInt();
	}

	/**
	 * Parses a positive integer number in decimal format.
	 */
	private int parsePositiveInt() {
		int value = parseNumber();
		if (!success) return 0;
		while (true) {
			int numericValue = parseNumber();
			if (!success) break;
			value *= 10;
			value += numericValue;
		}
		success = true;
		return value;
	}

	/**
	 * Parse a single space character. Success is set if
	 * successful.
	 */
	private void parseSpace() {
		success = Character.isSpaceChar(symbol);
		if (success) forwardCursor();
	}

	/**
	 * Parses the given character. If successful, the cursor is forwarded
	 * by one position, and success = true is set.
	 */
	private void parseCharacter(char c) {
		success = symbol == c;
		if (success) forwardCursor();
	}

	/**
	 * Parses a numeric character '0'-'9'. Success is set, and the cursor
	 * is forwarded by one position if the the character at the cursor is
	 * a numeric cursor.
	 */
	private int parseNumber() {
		int value = Character.getNumericValue(symbol);
		success = value >= 0;
		if (success) forwardCursor();
		return value;
	}

	private void forwardCursor() {
		cursorPosition++;
		symbol = cursorPosition < text.length() ? text.charAt(cursorPosition) :
				EOL;
	}

	private boolean eol() {
		return symbol == EOL;
	}
}
