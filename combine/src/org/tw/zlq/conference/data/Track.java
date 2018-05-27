package org.tw.zlq.conference.data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.tw.zlq.core.Element;
import org.tw.zlq.core.Solution;
import org.tw.zlq.utils.Utils;

public class Track {
	private List<Session> morningSessions;
	private List<Session> afternoonSessions;
	private int morningSessionTimeSum = 0;
	private int afternoonSessionTimeSum = 0;
	private static final Date morningStartDate = Utils.getDate(Consts.MORNING_START_HOUR);
	private static final Date afternoonStartDate = Utils.getDate(Consts.AFTERNOON_START_HOUR);
	public List<Session> getMorningSessions() {
		return morningSessions;
	}
	/**
	 * 添加一个session到上午议程中，并更新session时间
	 * @param session
	 */
	public void addMorningSession(Session session) {
		Session newSession = session.clone();
		if (morningSessions.isEmpty()) {
			newSession.setTime(Utils.dateFormat(morningStartDate));
		} else {
			String sessionTime = Utils.dateFormat(
					Utils.addMinutes(morningStartDate, morningSessionTimeSum));
			newSession.setTime(sessionTime);
		}
		
		morningSessions.add(newSession);
		morningSessionTimeSum += newSession.getValue();
	}
	public List<Session> getAfternoonSessions() {
		return afternoonSessions;
	}
	/**
	 * 添加一个session到下午议程中，并更新session时间
	 * @param session
	 */
	public void addAfternoonSession(Session session) {
		Session newSession = session.clone();
		if (afternoonSessions.isEmpty()) {
			newSession.setTime(Utils.dateFormat(afternoonStartDate));
		} else {
			String sessionTime = Utils.dateFormat(
					Utils.addMinutes(afternoonStartDate, afternoonSessionTimeSum));
			newSession.setTime(sessionTime);
		}
		afternoonSessions.add(newSession);
		afternoonSessionTimeSum += newSession.getValue();
	}
	public Track() {
		this.morningSessions = new LinkedList<>();
		this.afternoonSessions = new LinkedList<>();
	}
	
	public Track(List<Session> morningSessions, List<Session> afternoonSessions) {
		super();
		this.morningSessions = morningSessions;
		this.afternoonSessions = afternoonSessions;
	}
	
	public String toString() {
		StringBuilder strBuffer = new StringBuilder();
		for (Element element : morningSessions) {
			Session session = (Session) element;
			strBuffer.append(session).append("\n");
		}
		
		for (Element element : afternoonSessions) {
			Session session = (Session) element;
			strBuffer.append(session).append("\n");
		}
		return strBuffer.toString();
	}
	
	public static Track fromSolution(Solution solution) {
		Track track = new Track();
		int timeSum = 0;
		for (Element elem : solution.getElements()) {
			Session session = (Session) elem;
			timeSum += session.getValue();
			if (timeSum <= Consts.MORNING_TIME_LENTH) {
				track.addMorningSession(session);
			} else {
				track.addAfternoonSession(session);
			}
		}
		return track;
	}
}
