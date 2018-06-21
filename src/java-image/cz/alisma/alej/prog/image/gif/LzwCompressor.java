package cz.alisma.alej.prog.image.gif;

import java.util.HashMap;
import java.util.Map;

public class LzwCompressor {
	public static Map<String, Integer> codeTableInit(int colorCount) {
		Map<String, Integer> codeTable = new HashMap<String, Integer>();
		for (int i = 0; i < colorCount; i += 1) {
			codeTable.put(Integer.toString(i), i);
		}
		return codeTable;
	}
	
	public static void compress(int[] colorIndexStream, LzwOutput output, int colorCount) {
		int cc = colorCount;
		int eoi = colorCount + 1;
		int nextCode = colorCount + 2;
		String indexBuffer = "";
		String k = "";
		
		Map<String, Integer> codeTable = codeTableInit(colorCount);
		
		output.add(cc, bitSize);
		indexBuffer += Integer.toString(colorIndexStream[0]);
		for (int i = 1; i < colorIndexStream.length; i += 1) {
			k = Integer.toString(colorIndexStream[i]);
			if (codeTable.containsKey(indexBuffer + "," + k)) {
				indexBuffer += ",";
				indexBuffer += k;
			} else {
				codeTable.put(indexBuffer + "," + k, nextCode);
				nextCode += 1;
				output.add(codeTable.get(indexBuffer), bitSize);
				indexBuffer = k;
				k = "";
			}
		}
		output.add(codeTable.get(indexBuffer), bitSize);
		output.add(eoi, bitSize);
	}
}
