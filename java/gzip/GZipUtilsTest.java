/**
 * 2010-4-13
 */
package org.zlex.commons.io;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Test;

/**
 * @author 梁栋
 * @since 1.0
 */
public class GZipUtilsTest {

	private String inputStr = "zlex@zlex.org,snowolf@zlex.org,zlex.snowolf@zlex.org";

	@Test
	public final void testDataCompress() throws Exception {

		System.err.println("原文:\t" + inputStr);
		System.err.println("长度:\t" + inputStr.length());

		byte[] input = inputStr.getBytes();

		byte[] data = GZipUtils.compress(input);
		System.err.println("压缩后:\t");
		System.err.println("长度:\t" + data.length);

		byte[] output = GZipUtils.decompress(data);
		String outputStr = new String(output);
		System.err.println("解压缩后:\t" + outputStr);
		System.err.println("长度:\t" + outputStr.length());

		assertEquals(inputStr, outputStr);

	}

	@Test
	public final void testFileCompress() throws Exception {

		FileOutputStream fos = new FileOutputStream("d:/f.txt");

		fos.write(inputStr.getBytes());
		fos.flush();
		fos.close();

		GZipUtils.compress("d:/f.txt");

		GZipUtils.decompress("d:/f.txt.gz");

		File file = new File("d:/f.txt");

		FileInputStream fis = new FileInputStream(file);

		DataInputStream dis = new DataInputStream(fis);

		byte[] data = new byte[(int) file.length()];
		dis.readFully(data);

		fis.close();

		String outputStr = new String(data);
		assertEquals(inputStr, outputStr);
	}
}
