package cz.alisma.alej.prog.image.gif;

import java.util.ArrayList;
import java.util.List;

public class BlockCreator implements ByteTransformer{
	public static List<Integer> finalCode = new ArrayList<>();
	private static List<Integer> semiProduct = new ArrayList<>();

	@Override
	public void add(int code) {
		if(semiProduct.size() == 255) {
			finalCode.add(semiProduct.size());
			for(Integer b : semiProduct) {
				finalCode.add(b);
			}
			semiProduct.clear();
		}
		else {
			semiProduct.add(code);
		}
	}

	public byte[] get() {
		finalCode.add(semiProduct.size());
		for(Integer c : semiProduct) {
			finalCode.add(c);
		}
		finalCode.add(0);
		byte[] result = new byte[finalCode.size()];
		for(int i = 0; i < finalCode.size(); i++) {
			result[i] = (byte)(int)finalCode.get(i);
		}
		return result;
	}

}
