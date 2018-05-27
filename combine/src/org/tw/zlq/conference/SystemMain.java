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
	 * �ж���ѡ����ʱ���ܺ��Ƿ���ڻ��������ʱ��
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
		// ������track1
		Processor track1Processor = new Processor(initSessions, 
				judgeCondition);
		track1Processor.selectCombine();
		// ��ȷ������track1�Ľ���������ٿ�ʣ��������Ƿ�����track2
		// ���ս�solutionת����ʵ�����ݣ����ں������
		for (Solution solution1 : track1Processor.getSortedSolutions()) {
			Collection<Element> leftSessions = Utils.diff(initSessions, solution1.getElements());
			
			// �׶ζ���Ҫ̰��ƥ��
			Processor track2Processor = new Processor(new HashSet<>(leftSessions), judgeCondition);
			track2Processor.selectCombine();
			for (Solution solution2 : track2Processor.getSortedSolutions()) { 
				// ������ܴ���һ���������Ϊ���������һ��Сʱ�ĵ��Կռ䣬��˿��ܵ��²��ֺ�ѡ����û����ȫ���룬Ҳ�����㷽��
				// ��������Ҫ�ų���������
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
