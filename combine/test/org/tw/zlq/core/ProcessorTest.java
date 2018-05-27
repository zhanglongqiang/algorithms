/**
 * 
 */
package org.tw.zlq.core;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;
import org.tw.zlq.conference.condition.StageSumCondition;
import org.tw.zlq.utils.Utils;



/**
 * @author longqiang
 *
 */
public class ProcessorTest {
	@Test
	public void test1() {
		String sampleStr = "1,2,5,3,2";
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new SumCondition(5));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
	}
	
	/**
	 * 1. 存在满足第一阶段求和，小于第二阶段范围最小值
	 * 预期： 无解决方案
	 */
	@Test
	public void test3() {
		String sampleStr = "30, 30, 45, 45, 30, 60";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		Assert.assertTrue(processor.getSortedSolutions().isEmpty());
	}
	
	/**
	 * 2. 存在满足第一阶段求和，大于第二阶段范围最大值
	 * 预期： 无解决方案
	 * 60,60,60, 241
	 */
	@Test
	public void test4() {
		String sampleStr = "60,60,60, 241";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
		Assert.assertTrue(processor.getSortedSolutions().isEmpty());
	}
	
	/**
	 * 3. 存在满足第一阶段求和，等于第二阶段范围最小值
 	* 预期： 有
 	* 30, 30, 45, 45, 30, 60, 60, 60
	 */
	@Test
	public void test5() {
		String sampleStr = "30, 30, 45, 45, 30, 60, 60, 60";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
		Assert.assertTrue(!processor.getSortedSolutions().isEmpty());
	}
	
	/**
 4. 存在满足第一阶段求和，等于第二阶段范围最大值
  预期： 有
 180, 130, 50, 60
	 */
	@Test
	public void test6() {
		String sampleStr = "180, 130, 50, 60";
		
		Collection<Element> sampleData = Utils.genElements(sampleStr);
		Processor processor = new Processor(new HashSet<>(sampleData), new StageSumCondition(180, 180, 240));
		processor.selectCombine();
		for (Solution s : processor.getSortedSolutions()) {
			System.out.println(s.getElements());
		}
		Assert.assertTrue(!processor.getSortedSolutions().isEmpty());
	}
}
