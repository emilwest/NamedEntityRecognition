package util;

import java.io.OutputStream;
import java.io.PrintStream;

/** Class which extends PrintStream. It's used to ovverride the print method, 
 * so that each character in System.out can be printed out one at a time in 
 * a certain sleep interval. Just for stylistic purposes.
 * @author Emil
 *
 */
public class MyPrintStream extends PrintStream {

	private long sleep;

	/** Constructor
	 * @param out Outputsream
	 * @param status True or false
	 */
	public MyPrintStream(OutputStream out, boolean status) {
		super(out);
	}

	/** Set sleep in milliseconds
	 * @param newSleep long
	 */
	public void setSleep(long newSleep) {
		sleep = newSleep;
	}

	/** Returns sleep in milliseconds
	 * @return sleep
	 */
	public long getSleep() {
		return sleep;
	}

	/* (non-Javadoc)
	 * @see java.io.PrintStream#print(java.lang.String)
	 */
	@Override
	public void print(String s) {
		try {
			for (int i = 0; i < s.length(); i++) {
				super.print(s.charAt(i));
				Thread.sleep(getSleep());
			}
		} catch (Exception e) {
		}
	}

}
