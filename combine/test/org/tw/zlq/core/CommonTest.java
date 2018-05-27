package org.tw.zlq.core;

import org.junit.Test;

public class CommonTest {

	@Test
	public void combineSum() {
		System.out.println(A(3,2));
		System.out.println(C(3,2));
		System.out.println(C(19,11));
	}

	public long C(int n, int m) {
		return A(n,m)/ A(m,m);
	}
	
	public long A(int up, int iter) {
		long result = 1;
		for (int i=iter;i>0;i--) {
			result *= up;
			up--;
		}
		return result;
	}
}
