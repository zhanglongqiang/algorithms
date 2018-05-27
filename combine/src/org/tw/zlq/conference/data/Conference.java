package org.tw.zlq.conference.data;

public class Conference {
	private Track track1;
	private Track track2;
	public Conference(Track track1, Track track2) {
		super();
		this.track1 = track1;
		this.track1.addMorningSession(new Session(9999, "Lunch", 60));
		this.track1.addAfternoonSession(new Session(9998, "Networking Event", 0));
		this.track2 = track2;
		this.track2.addMorningSession(new Session(9997, "Lunch", 60));
		this.track2.addAfternoonSession(new Session(9996, "Networking Event", 0));
	}
	public Track getTrack1() {
		return track1;
	}
	public void setTrack1(Track track1) {
		this.track1 = track1;
	}
	public Track getTrack2() {
		return track2;
	}
	public void setTrack2(Track track2) {
		this.track2 = track2;
	}
	
	public String toString() {
		return String.format("Track 1:\n%s\nTrack 2:\n%s", track1.toString(), track2.toString());
	}
}
