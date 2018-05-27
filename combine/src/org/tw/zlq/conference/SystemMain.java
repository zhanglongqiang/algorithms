package org.tw.zlq.conference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tw.zlq.conference.condition.StageSumCondition;
import org.tw.zlq.conference.data.Conference;
import org.tw.zlq.conference.data.Consts;
import org.tw.zlq.conference.data.Session;
import org.tw.zlq.conference.data.Track;
import org.tw.zlq.conference.error.ErrorCode;
import org.tw.zlq.conference.error.TwException;
import org.tw.zlq.core.Condition;
import org.tw.zlq.core.Element;
import org.tw.zlq.core.Processor;
import org.tw.zlq.core.Solution;
import org.tw.zlq.utils.Utils;

public class SystemMain {
	private Set<Element> initSessions;
	private List<Conference> possibleSolutions;
	private File inputData;
	public SystemMain(File input) {
		this.initSessions = new HashSet<>();
		this.possibleSolutions = new ArrayList<>();
		this.inputData = input;
	}
	
	public List<Conference> getPossibleSolutions() {
		return possibleSolutions;
	}

	public Session line2Sessions(String rawLine, int index) {
		if (rawLine == null) {
			throw new TwException(ErrorCode.InputErr, "empty line");
		}
		String line = rawLine.trim();
		String regexStr1 = "^(.*)\\s+(\\d+)min$";
		String regexStr2 = "^(.*)\\s+lightning$";
		Matcher m1 = Pattern.compile(regexStr1).matcher(line);
		Matcher m2 = Pattern.compile(regexStr2).matcher(line);
		int cost = 0;
		String title = null;
		boolean isLightning = false;
		if (m1.matches()) {
			title = m1.group(1);
			cost = Integer.parseInt(m1.group(2));
		} else if (m2.matches()) {
			title = m2.group(1);
			cost = 5;
			isLightning = true;
		} else {
			throw new TwException(ErrorCode.InputErr, "Invalid line:" + rawLine);
		}
		Session session = new Session(index, title, cost);
		session.setLightning(isLightning);
		return session;
	}
	
	public void loadData() {
		int lineCount = 0;
		try (InputStream inStream = new FileInputStream(inputData);
				InputStreamReader input = new InputStreamReader(inStream);
				BufferedReader reader = new BufferedReader(input)){
			String line = reader.readLine();
			while (line != null) {
				initSessions.add(line2Sessions(line, lineCount++));
				line = reader.readLine();
			}
		} catch (IOException e) {
			throw new TwException(ErrorCode.InputErr, "Read input file error.", e);
		}
		return;
	}
	
	/**
	 * 判定候选议题时间总和是否大于会议议程总时长
	 */
	private void preCheck() {
		int timeCostSum = 0;
		for (Element element : initSessions) {
			timeCostSum += element.getValue();
		}
		if (timeCostSum > (Consts.MORNING_TIME_LENTH + Consts.AFTERNOON_TIME_RANGE_LENTH_END) * 2) {
			throw new TwException(ErrorCode.NoSolutionErr, "Total time cost too lang of Candidate sessions.");
		}
	}
	
	public void doAnalyse() {
		preCheck();
		Condition judgeCondition = new StageSumCondition(Consts.MORNING_TIME_LENTH,
				Consts.AFTERNOON_TIME_RANGE_LENTH_START, 
				Consts.AFTERNOON_TIME_RANGE_LENTH_END);
		// 先生成track1
		Processor track1Processor = new Processor(initSessions, 
				judgeCondition);
		track1Processor.selectCombine();
		// 先确定所有track1的解决方案，再看剩余的议题是否满足track2
		// 最终将solution转化成实例数据，便于后续输出
		for (Solution solution1 : track1Processor.getSortedSolutions()) {
			Collection<Element> leftSessions = Utils.diff(initSessions, solution1.getElements());
			
			// 阶段二需要贪婪匹配
			Processor track2Processor = new Processor(new HashSet<>(leftSessions), judgeCondition);
			track2Processor.selectCombine();
			for (Solution solution2 : track2Processor.getSortedSolutions()) { 
				// 这里可能存在一种情况：因为下午会议由一个小时的弹性空间，因此可能导致部分候选议题没有完全排入，也能满足方案
				// 所以这里要排除这种例外
				if (solution1.getElements().size() + solution2.getElements().size() != initSessions.size()) {
					continue;
				}
				Conference conference = new Conference(
						Track.fromSolution(solution1), 
						Track.fromSolution(solution2));
				System.out.println(conference);
				possibleSolutions.add(conference);
			}
		}
	}
	
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			throw new TwException(ErrorCode.InputErr, "Input file path needed.");
		}
		SystemMain conferenceOrginiser = new SystemMain(new File(args[0]));
		conferenceOrginiser.loadData();
		conferenceOrginiser.doAnalyse();
		List<Conference> solutions = conferenceOrginiser.getPossibleSolutions();
		if (solutions.isEmpty()) {
			throw new TwException(ErrorCode.NoSolutionErr, null);
		}
		
		for (int i=0;i<solutions.size();i++) {
			Conference solution = solutions.get(i);
			System.out.println("Solution:" + i);
			System.out.println(solution);
		}
	}
}
